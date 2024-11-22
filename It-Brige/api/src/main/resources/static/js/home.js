import { loadCSS } from "../util/loadCSS.js";

// JSON 데이터 로드 함수
export async function fetchCourses() {
    const response = await fetch('/json/courses.json');  //TODO 나중에 경로만 API로 변경
    if (!response.ok) {
        throw new Error('Failed to fetch courses');
    }
    const data = await response.json();
    return data;
}

// 이미지 데이터를 가져오는 함수
async function fetchImages() {
    try {
        const response = await fetch('/json/imagehome.json'); // TODO API에서 이미지 데이터 가져오기
        if (!response.ok) {
            throw new Error('Failed to fetch images');
        }
        const images = await response.json();

        // img-List 컨테이너 가져오기
        const imgList = document.getElementById('img-List');
        if (!imgList) {
            console.error("Element with ID 'img-List' not found.");
            return;
        }

        imgList.innerHTML = ''; // 기존 콘텐츠 제거

        // 이미지 데이터 렌더링
        images.forEach(({ id, url }) => {
            const imgItem = document.createElement('div');
            imgItem.classList.add('promo-item'); // 클래스는 동일하게 사용
            imgItem.style.backgroundImage = `url('${url}')`;

            // 이미지에 클릭 이벤트 추가
            const link = document.createElement('a');
            link.href = `/image/${id}`; // 클릭 시 이동할 링크
            link.target = '_blank'; // 새 탭에서 열기
            link.textContent = ''; // 텍스트 비우기 (링크만 적용)

            imgItem.appendChild(link);
            imgList.appendChild(imgItem);
        });
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
            <h2>실시간 BEST 인기 강의</h2>
            <p>가장 많은 수강생이 주목하는 TOP 5 강의를 만나보세요.</p>
            <div class="category-buttons">
                <button class="category-btn" data-category="전체">전체</button>
                <button class="category-btn" data-category="딜러닝">딥러닝</button>
                <button class="category-btn" data-category="재무/회계/세무">재무/회계/세무</button>
            </div>
            <div class = "course-list-container">
            <div id="course-list" class="course-list"></div></div>
            <div class = "img-list-container">
            <div id="img-List" class="img-List"></div></div> <!-- img-List로 통합 -->
        </section>
    `;

    // 강의 데이터를 로드 및 렌더링
    fetchCourses().then(courses => {
        renderCourses(courses);

        // 카테고리 필터 버튼 이벤트 추가
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

    // 이미지 데이터를 로드 및 렌더링
    fetchImages();
}

// 강의 카드를 렌더링하는 함수
function renderCourses(courses) {
    const courseList = document.getElementById('course-list');
    if (!courseList) {
        console.error("Element with id 'course-list' not found.");
        return;
    }

    courseList.innerHTML = ''; // 기존 강의 목록 초기화

    courses.forEach(course => {
        const courseCard = document.createElement('div');
        courseCard.classList.add('course-card');
        courseCard.innerHTML = `
            <div class="course-rank">${course.rank}위</div>
            <img src="${course.image}" alt="${course.title}" class="course-image">
            <div class="course-info">
                <h3 class="course-title">${course.title}</h3>
                <p class="course-category">${course.category}</p>
                <p class="course-price">최저가 ${course.price.toLocaleString()}원~</p>
                <p class="course-likes">${course.likes} ♥</p>
                <div class="course-tags">${course.tags.map(tag => `<span class="tag">${tag}</span>`).join('')}</div>
            </div>
        `;
        courseList.appendChild(courseCard);
    });
}
