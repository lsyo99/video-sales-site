import { loadCSS } from "../util/loadCSS.js";

export async function renderLectureDetailPage(params) {
    const lectureId = params.id; // 동적 매개변수 추출
    console.log(`Rendering details for lecture ID: ${lectureId}`); // 디버깅 로그
    loadCSS("/css/lectureDetails.css");

    try {
        const response = await fetch(`/open-api/lecture/${lectureId}/images`);
        if (!response.ok) {
            throw new Error(`Failed to fetch images for lecture ID: ${lectureId}`);
        }

        const images = await response.json();
        const sortedImages = images.body.sort((a, b) => a.sortImg - b.sortImg);

        // 상세 이미지 컨테이너 생성
        const imageContainer = document.createElement('div');
        imageContainer.classList.add('image-detail-container');

        // 각 이미지를 생성
        sortedImages.forEach((image) => {
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
    } catch (error) {
        console.error('Error fetching lecture images:', error);
    }
}