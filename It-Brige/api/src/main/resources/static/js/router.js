import { renderHeader } from './header.js';
import { renderHomePage } from './home.js';

// 페이지 렌더링 함수 목록
const pages = {
    home: renderHomePage,

};

// 초기화 함수: 헤더를 렌더링하고, 초기 페이지를 설정
function init() {
    renderHeader(); // 헤더를 동적으로 추가

    // 해시 변경 이벤트 설정
    window.addEventListener('hashchange', () => {
        const page = location.hash.replace('#', '') || 'home';
        loadPage(page);
    });

    // 초기 로드 시 현재 경로에 맞는 페이지 렌더링
    const initialPage = location.hash.replace('#', '') || 'home';
    loadPage(initialPage);
}

// 페이지를 동적으로 로드하는 함수
function loadPage(page) {
    const main = document.getElementById('main');
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = ''; // 기존 콘텐츠 제거

    if (pages[page]) {
        pages[page](); // 해당 페이지 렌더링
    } else {
        main.innerHTML = '<h2>Page Not Found</h2>';
    }
}

// 초기화 함수 호출
document.addEventListener('DOMContentLoaded', init);
