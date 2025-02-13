import { loadCSS } from "../util/loadCSS.js";

// 공통 데이터 가져오기 함수
async function fetchCoursesByType(type) {
    try {
        const url = `/open-api/lecture/headerToCourse/${type}`;
        console.log(`Fetching ${type} courses from: ${url}`);
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

// 공통 렌더링 함수
function renderCoursePage(title, courses) {
    loadCSS("/css/search.css");

    const main = document.getElementById("main");
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="resultsearch-container">
            <h2>${title}</h2>
            <div class="course-list-container">
                <div id="course-list" class="course-list"></div>
            </div>
        </section>
    `;

    renderCourses(courses);
}

// 강의 리스트 렌더링
function renderCourses(courses) {
    const courseList = document.getElementById("course-list");
    if (!courseList) {
        console.error("Element with id 'course-list' not found.");
        return;
    }

    if (!Array.isArray(courses) || courses.length === 0) {
        courseList.innerHTML = `<p>강의가 없습니다.</p>`;
        return;
    }

    const baseURL = "http://localhost:8080";

    courseList.innerHTML = courses
        .map(
            (course) => `
            <div class="course-card" onclick="navigateTo('/lecture/${course.id}')">
                <img src="${baseURL}${course.thumbnail_url}" alt="${course.title}" class="course-image">
                <div class="course-info">
                    <h3 class="course-title">${course.title}</h3>
                    <p class="course-category">${course.category || "카테고리 없음"}</p>
                    <div class="price-container">
                        <p class="course-sale">${course.sales || "할인 없음"}</p>
                        <p class="course-price">최저가 ${
                            course.price ? course.price.toLocaleString() + "원~" : "정보 없음"
                        }</p>
                    </div>
                    <p class="course-likes">${course.likes || 0} ♥</p>
                </div>
            </div>
        `
        )
        .join("");
}

// 할인 강의 렌더링 함수
export async function renderDiscountCourses() {
    const courses = await fetchCoursesByType("discount");
    renderCoursePage("할인 강의", courses);
}

// 신규 강의 렌더링 함수
export async function renderNewCourses() {
    const courses = await fetchCoursesByType("new");
    renderCoursePage("신규 강의", courses);
}
