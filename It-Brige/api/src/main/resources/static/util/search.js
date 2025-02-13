import { loadCSS } from "../util/loadCSS.js";

// 검색 수행 함수
export async function performSearch(keyword) {
    try {
        const response = await fetch(`/open-api/lecture/search?search=${encodeURIComponent(keyword)}`);
        if (!response.ok) {
            throw new Error('Failed to fetch search results');
        }

        const data = await response.json();
        console.log('Search Results:', data);
        renderSearchResults(data.body || [], keyword); // keyword 전달
    } catch (error) {
        console.error('Error performing search:', error);
        alert('검색 중 오류가 발생했습니다.');
    }
}

// 검색 결과 렌더링 함수
export function renderSearchResults(lectures, keyword) { // keyword 추가
    const main = document.getElementById('main');
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="resultsearch-container">
            <h2>"${keyword}"</h2> <!-- keyword 표시 -->
            <div class="course-list-container">
                <div id="course-list" class="course-list"></div>
            </div>
        </section>
    `;

    renderCourses(lectures);
}

// 검색결과 강의 카드를 렌더링하는 함수
function renderCourses(courses) {
    loadCSS("/css/search.css");
    const courseList = document.getElementById("course-list");
    if (!courseList) {
        console.error("Element with id 'course-list' not found.");
        return;
    }

    if (!Array.isArray(courses)) {
        console.error("Courses is not an array:", courses);
        courseList.innerHTML = `<p>검색 결과가 없습니다.</p>`;
        return;
    }

    const baseURL = "http://localhost:8080";

    courseList.innerHTML = courses.map((course, index) => `
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
