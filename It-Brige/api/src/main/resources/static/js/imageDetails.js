import { loadCSS } from "../util/loadCSS.js";
import { navigateTo } from "./router.js"; // 라우터를 가져옴

// 모든 이미지 데이터를 가져오는 함수
async function fetchAllImages() {
    try {
        const response = await fetch('/json/imageDetail.json'); // JSON 데이터 경로
        if (!response.ok) {
            throw new Error('Failed to fetch image details');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching image details:', error);
        return [];
    }
}

// 특정 `pageId`에 해당하는 이미지 필터링
async function fetchImagesByPage(pageId) {
    const allImages = await fetchAllImages();
    return allImages.filter(image => image.pageId === pageId); // pageId에 맞는 이미지 반환
}

// 이미지 상세 페이지 렌더링 함수
export async function renderImageDetailPage(pageId) {
    loadCSS('css/imageDetails.css');

    const main = document.getElementById('main');
    if (!main) {
        console.error("Element with ID 'main' not found.");
        return;
    }

    // 페이지 ID에 해당하는 이미지 데이터를 가져옴
    const imageDetails = await fetchImagesByPage(pageId);

    if (!imageDetails || imageDetails.length === 0) {
        main.innerHTML = '<h2>No images found for this page</h2>';
        return;
    }

    // 이미지 데이터를 동적으로 렌더링
    main.innerHTML = `
        <section class="image-detail-page">
            <h2>Page ${pageId} Images</h2>
            <div class="image-list-container">
                ${imageDetails.map(({ id, url, title, description }) => `
                    <div class="image-card">
                        <div class="image" style="background-image: url('${url}');"></div>
                        <div class="image-info">
                            <h3>${title}</h3>
                            <p>${description}</p>
                        </div>
                    </div>
                `).join('')}
            </div>
        </section>
    `;
}
