import { renderHomePage } from './home.js';
import { renderHeader } from './header.js';
import { renderLectureDetailPage } from './lectureDetails.js';
import { renderLoginPage } from './login.js';
import { logout } from '../util/logout.js';
import { renderMyPage } from './mypage.js';
// 라우팅 테이블 설정
const routes = {
    '/': renderHomePage,
    '/lecture/:id': (params) => renderLectureDetailPage(params),
    '/login': renderLoginPage,
    '/logout': () => {
            logout(); // 로그아웃 호출
        },
        '/mypage': renderMyPage,

};

// URL에서 동적 매개변수 추출
function extractParams(route, path) {
    const routeParts = route.split('/');
    const pathParts = path.split('/');
    const params = {};

    routeParts.forEach((part, index) => {
        if (part.startsWith(':')) {
            const paramName = part.slice(1); // ':' 제거
            params[paramName] = pathParts[index];
        }
    });

    return params;
}

// 현재 URL에 맞는 라우트를 찾고 렌더링
function handleRoute() {
    const path = location.pathname;
    if(path.startsWith('/open-api')){
        return;
    }

    // 항상 헤더를 렌더링
    renderHeader();

    // 로그인 페이지에서는 헤더를 숨김
    const header = document.querySelector('header');
    if (path === '/login') {
        if (header) {
            header.style.display = 'none';
        }
        renderLoginPage();
        return;
    }

    // 다른 페이지에서는 헤더를 표시
    if (header) {
        header.style.display = 'block';
    }

    // 라우트 매칭 및 렌더링
    for (const route in routes) {
        const isMatch =
            route.split('/').length === path.split('/').length &&
            route.split('/').every(
                (part, index) =>
                    part.startsWith(':') || part === path.split('/')[index]
            );
            console.log('route')

        if (isMatch) {
             const params = extractParams(route, path);
                        const renderFunction = routes[route];

                        // 디버깅용 로그
                        console.log(`Matched route: ${route}`, { params, renderFunction });

                        if (typeof renderFunction === 'function') {
                            renderFunction(params); // 매개변수 전달
                            console.log('Params passed to render function:', params);
                            return;
                        } else {
                            console.error(`Route ${route} is not a function. Check the routes object.`);
                        }
        }
    }

    // 404 처리 (라우트가 없는 경우)
    const main = document.getElementById('main');
    if (main) {
        main.innerHTML = '<h2>404: Page Not Found</h2>';
    }
}

// 브라우저 히스토리 API 사용 -> 앞/뒤로 가기 이벤트 처리
window.addEventListener('popstate', handleRoute);

// URL 변경 시 라우터 작동 함수
export function navigateTo(url) {
    console.log(`Navigating to: ${url}`); // 디버깅 로그 추가
    if (url === '/logout') {
        logout(); // 로그아웃 라우트 처리
    } else {
        history.pushState(null, '', url);
        handleRoute();
    }
}

// 전역 객체에 navigateTo 등록
window.navigateTo = navigateTo;

// 초기화 함수: 앱 시작 시 실행
function init() {
    renderHeader(); // 초기 헤더 렌더링
    handleRoute(); // 현재 경로에 맞는 콘텐츠 렌더링
}

// DOMContentLoaded 이벤트로 초기화 함수 호출
document.addEventListener('DOMContentLoaded', init);
