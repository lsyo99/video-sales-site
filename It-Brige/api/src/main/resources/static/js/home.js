import { loadCSS } from "../util/loadCSS.js";
import { navigateTo } from "./router.js";

// JSON 데이터 로드 함수
export async function fetchCourses(category = "all", type = "best") {
    try {
        const url = `/open-api/lecture/${type}?category=${encodeURIComponent(category)}`;
        console.log(`Fetching courses from: ${url}`);
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Failed to fetch ${type} courses`);
        }
        const data = await response.json();
        console.log(`Fetched ${type} courses:`, data);
        return data.body || [];
    } catch (error) {
        console.error(`Error fetching ${type} courses:`, error);
        return [];
    }
}

// 이미지 데이터를 가져오는 함수
async function fetchImages() {
    try {
        const response = await fetch("./json/imagehome.json");
        if (!response.ok) {
            throw new Error("Failed to fetch images");
        }
        const images = await response.json();

        const imgList = document.getElementById("img-List");
        if (!imgList) {
            console.error("Element with ID 'img-List' not found.");
            return;
        }

        imgList.innerHTML = images.map(({ id, url }) => `
            <div class="promo-item" style="background-image: url('${url}');" onclick="navigateTo('/image/${id}')">
            </div>
        `).join("");
    } catch (error) {
        console.error("Error fetching images:", error);
    }
}

export function renderHomePage() {
    loadCSS("/css/home.css");

    const main = document.getElementById("main");
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="home container">
            <nav class="icon-links">
                <div class="borad-container">
                    <div class="third-row">
                        <!-- Dropdown 메뉴 -->
                        ${renderCategoryIcons()}
                    </div>
                </div>
            </nav>
            <!-- BEST 강의 섹션 -->
            <div class="best-course-container">
                <h5>BEST</h5>
                <h2>실시간 BEST 인기 강의</h2>
                <p>가장 많은 수강생이 주목하는 TOP 5 강의를 만나보세요.</p>
                <div class="category-buttons" id="best-category-buttons">
                    ${renderCategoryButtons()}
                </div>
                <div class="course-list-container">
                    <div id="course-list" class="course-list"></div>
                </div>
            </div>
            <!-- 신규 강의 섹션 -->
            <div class="new-course-container">
                <h5>NEW</h5>
                <h2>새로운 강의</h2>
                <p>트렌디한 주제의 새로운 인기 강의를 살펴보세요.</p>
                <div class="category-buttons" id="new-category-buttons">
                    ${renderCategoryButtons()}
                </div>
                <div class="new-course-list-container">
                    <div id="new-course-list" class="new-course-list"></div>
                </div>
            </div>
            <!-- 이미지 섹션 -->
            <div class="img-list-container">
                <div id="img-List" class="img-List"></div>
            </div>
        </section>
    `;

    // BEST 강의 데이터 로드
    fetchCourses("all", "best").then(courses => {
        renderCourses(courses, "course-list");
    });

    // 신규 강의 데이터 로드
    fetchCourses("all", "new").then(courses => {
        renderCourses(courses, "new-course-list");
    });

    // 이미지 로드
    fetchImages();

    // 카테고리 버튼 이벤트 설정
    setupCategoryButtons("best-category-buttons", "best", "course-list");
    setupCategoryButtons("new-category-buttons", "new", "new-course-list");
}

function renderCategoryIcons() {
    const categories = [
        { title: "인공지능", icon: "ai.png", link: "/ai" },
        { title: "웹개발", icon: "웹개발.png", link: "/web-dev" },
        { title: "앱개발", icon: "앱개발.png", link: "/app-dev" },
        { title: "자격증", icon: "자격증.png", link: "/certification" },
        { title: "디자인", icon: "디자인.png", link: "/design" },
        { title: "데이터 분석", icon: "데이터분석.png", link: "/data-analysis" },
    ];

    return categories.map(category => `
        <div class="dropdown-container">
            <a href="javascript:void(0);" onclick="navigateTo('${category.link}')" title="${category.title}">
                <img src="image/icons/${category.icon}" alt="${category.title} Icon" class="nav-icon">
                ${category.title}
            </a>
        </div>
    `).join("");
}

function renderCategoryButtons() {
    const categories = [
        "전체", "딥러닝", "머신러닝", "앱개발",
        "프론트엔드", "백엔드", "자격증", "데이터분석", "빅데이터분석"
    ];

    return categories.map(category => `
        <button class="category-btn" data-category="${category}">${category}</button>
    `).join("");
}

function setupCategoryButtons(buttonsContainerId, type, listId) {
    const buttonsContainer = document.getElementById(buttonsContainerId);
    if (!buttonsContainer) return;

    buttonsContainer.querySelectorAll(".category-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const category = button.getAttribute("data-category") || "all";
            const courses = await fetchCourses(category, type);
            renderCourses(courses, listId);
        });
    });
}

function renderCourses(courses, containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        console.error(`Element with id '${containerId}' not found.`);
        return;
    }

    if (!Array.isArray(courses)) {
        console.error("Courses is not an array:", courses);
        container.innerHTML = `<p>No courses available</p>`;
        return;
    }

    const baseURL = "http://localhost:8080";
    container.innerHTML = courses.map((course, index) => `
        <div class="course-card" onclick="navigateTo('/lecture/${course.id}')">
            <div class="course-rank">${index + 1}위</div>
            <img src="${baseURL}${course.thumbnail_url}" alt="${course.title}" class="course-image">
            <div class="course-info">
                <h3 class="course-title">${course.title}</h3>
                <p class="course-category">${course.category || "카테고리 없음"}</p>
                <div class="price-container">
                    <p class="course-sale">${course.sales || "할인 없음"}</p>
                    <p class="course-price">최저가 ${course.price ? course.price.toLocaleString() + "원~" : "정보 없음"}</p>
                </div>
                <p class="course-likes">${course.likes || 0} ♥</p>
                <div class="course-tags">
                    ${
                        Array.isArray(course.tags)
                            ? course.tags.map(tag => `<span class="tag">${tag}</span>`).join("")
                            : "태그 없음"
                    }
                </div>
            </div>
        </div>
    `).join("");
}
