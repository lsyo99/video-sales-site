import { loadCSS } from "../util/loadCSS.js";

export async function renderLectureDetailPage(params) {
    const lectureId = params.id; // 동적 매개변수 추출
    console.log(`Rendering details for lecture ID: ${lectureId}`); // 디버깅 로그
    loadCSS("/css/lectureDetails.css"); // CSS 경로 수정

    try {
        const response = await fetch(`/open-api/lecture/${lectureId}/images`);
        if (!response.ok) {
            throw new Error(`Failed to fetch images for lecture ID: ${lectureId}`);
        }

        const images = await response.json();

        // 확장자 필터링 (PNG, JPG, JPEG만)
        const validImages = images.body
            .filter(img => img.url.match(/\.(png|jpe?g)$/i))
            .sort((a, b) => a.sortImg - b.sortImg); // 원래 정렬 방식 유지

        // 기존 버튼 제거 (강의 상세 페이지를 다시 들어올 때 중복 방지)
        const existingButton = document.querySelector('.enroll-button-container');
        if (existingButton) {
            existingButton.remove();
        }

        // 상세 이미지 컨테이너 생성
        const imageContainer = document.createElement('div');
        imageContainer.classList.add('image-detail-container');

        // 정렬된 이미지들을 한 번에 추가
        validImages.forEach((image) => {
            const imgElement = document.createElement('img');
            imgElement.src = image.url;
            imgElement.alt = `Image ${image.sortImg}`;
            imgElement.classList.add('image-detail');

            imageContainer.appendChild(imgElement);
        });

        const mainContent = document.getElementById('main');
        if (mainContent) {
            mainContent.innerHTML = ''; // 기존 콘텐츠 초기화
            mainContent.appendChild(imageContainer);
        }

        // 수강 신청 버튼 생성
        const enrollButton = document.createElement('div');
        enrollButton.classList.add('enroll-button-container');
        enrollButton.innerHTML = `
            <button class="enroll-button">
                수강 신청하기
            </button>
        `;
        document.body.appendChild(enrollButton); // body에 버튼 추가

        // 버튼 클릭 시 navigateTo 실행 + 버튼 제거
        const enrollBtn = enrollButton.querySelector('.enroll-button');
        enrollBtn.addEventListener('click', () => {
            navigateTo(`/payment/${lectureId}`);
            enrollButton.remove(); // 버튼 제거
        });

    } catch (error) {
        console.error('Error fetching lecture images:', error);
    }
}

// 네비게이션 변경 시 수강 신청 버튼 제거
window.addEventListener('popstate', () => {
    const enrollButton = document.querySelector('.enroll-button-container');
    if (enrollButton) {
        enrollButton.remove();
    }
});
