import { loadCSS } from "../util/loadCSS.js";
import { navigateTo } from "./router.js";

// JSON 데이터 로드 함수
async function fetchCourses(category = "all") {
    try {
        const url = `/open-api/lecture/best?category=${encodeURIComponent(category)}`;
        console.log(`Fetching courses from: ${url}`);
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Failed to fetch courses");
        }
        const data = await response.json();
        console.log("Fetched courses:", data);
        return data.body || [];
    } catch (error) {
        console.error("Error fetching courses:", error);
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

// 홈 페이지 렌더링 함수
export function renderHomePage() {
    loadCSS("/css/home.css");

    const main = document.getElementById("main");
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="home container">
            <div class="borad-container">
                <h5>BEST</h5>
                <h2>실시간 BEST 인기 강의</h2>
                <p>가장 많은 수강생이 주목하는 TOP 5 강의를 만나보세요.</p>
                <div class="category-buttons">
                    <button class="category-btn" data-category="all">전체</button>
                    <button class="category-btn" data-category="딥러닝">딥러닝</button>
                    <button class="category-btn" data-category="머신러닝">머신러닝</button>
                    <button class="category-btn" data-category="앱개발">앱개발</button>
                    <button class="category-btn" data-category="프론트엔드">프론트엔드</button>
                    <button class="category-btn" data-category="백엔드">백엔드</button>
                    <button class="category-btn" data-category="자격증">자격증</button>
                    <button class="category-btn" data-category="데이터분석">데이터분석</button>
                    <button class="category-btn" data-category="빅데이터분석">빅데이터분석</button>
                </div>
                <div class="course-list-container">
                    <div id="course-list" class="course-list"></div>
                </div>
                <div class="img-list-container">
                    <div id="img-List" class="img-List"></div>
                </div>
            </div>
        </section>
    `;

    // 기본 카테고리 '전체' 데이터 로드
    fetchCourses().then(courses => {
        renderCourses(courses);
    }).catch(error => {
        console.error("Error fetching courses:", error);
    });

    // 카테고리 버튼 이벤트 설정
    document.querySelectorAll(".category-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const category = button.getAttribute("data-category") || "all";
            const filteredCourses = await fetchCourses(category);
            renderCourses(filteredCourses);
        });
    });

    // 이미지 로드
    fetchImages();
}

// 강의 카드를 렌더링하는 함수
function renderCourses(courses) {
    const courseList = document.getElementById("course-list");
    if (!courseList) {
        console.error("Element with id 'course-list' not found.");
        return;
    }

    // 데이터가 배열인지 확인
    if (!Array.isArray(courses)) {
        console.error("Courses is not an array:", courses);
        courseList.innerHTML = `<p>No courses available</p>`;
        return;
    }

    // 강의 카드 렌더링
    courseList.innerHTML = courses.map((course, index) => `
        <div class="course-card" onclick="navigateTo('/lecture/${course.id}')">
            <div class="course-rank">${index + 1}위</div>
            <img src="${course.image}" alt="${course.title}" class="course-image">
            <div class="course-info">
                <h3 class="course-title">${course.title}</h3>
                <p class="course-category">${course.category || "카테고리 없음"}</p>
                <div class="price-container">
                    <p class="course-sale">${course.sale || "할인 없음"}</p>
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
