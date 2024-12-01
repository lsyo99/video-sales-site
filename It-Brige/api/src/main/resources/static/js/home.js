import { loadCSS } from "../util/loadCSS.js";
import { navigateTo } from "./router.js"; // 라우터를 가져옴

// JSON 데이터 로드 함수
export async function fetchCourses() {
    const response = await fetch('/json/courses.json');
    if (!response.ok) {
        throw new Error('Failed to fetch courses');
    }
    return await response.json();
}

// 이미지 데이터를 가져오는 함수
async function fetchImages() {
    try {
        const response = await fetch('/json/imagehome.json');  //5초
        if (!response.ok) {
            throw new Error('Failed to fetch images');
        }
        const images = await response.json();

        const imgList = document.getElementById('img-List');
        if (!imgList) {
            console.error("Element with ID 'img-List' not found.");
            return;
        }

        imgList.innerHTML = images.map(({ id, url }) => `
            <div class="promo-item" style="background-image: url('${url}');" onclick="navigateTo('/image/${id}')">
            </div>
        `).join('');
    } catch (error) {
        console.error('Error fetching images:', error);
    }
}

// 홈 페이지 렌더링 함수
export function renderHomePage() {
    loadCSS('css/home.css');

    const main = document.getElementById('main');
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="home container">
            <div class="borad-container">
                <h5>best</h5>
                <h2>실시간 BEST 인기 강의</h2>
                <p>가장 많은 수강생이 주목하는 TOP 5 강의를 만나보세요.</p>
                <div class="category-buttons">
                    <button class="category-btn" data-category="전체">전체</button>
                    <button class="category-btn" data-category="딥러닝">딥러닝</button>
                    <button class="category-btn" data-category="재무/회계/세무">재무/회계/세무</button>
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

    fetchCourses().then(courses => {
        renderCourses(courses);

        document.querySelectorAll('.category-btn').forEach(button => {
            button.addEventListener('click', () => {
                const category = button.getAttribute('data-category');
                const filteredCourses = category === '전체'
                    ? courses
                    : courses.filter(course => course.category === category);
                renderCourses(filteredCourses);
            });
        });
    }).catch(error => {
        console.error('Error fetching courses:', error);
    });

    fetchImages();
}

// 강의 카드를 렌더링하는 함수
function renderCourses(courses) {
    const courseList = document.getElementById('course-list');
    if (!courseList) {
        console.error("Element with id 'course-list' not found.");
        return;
    }

    courseList.innerHTML = courses.map(course => `
        <div class="course-card" onclick="navigateTo('/course/${course.id}')">
            <div class="course-rank">${course.rank}위</div>
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
                    ${course.tags ? course.tags.map(tag => `<span class="tag">${tag}</span>`).join('') : "태그 없음"}
                </div>
            </div>
        </div>
    `).join('');
}
