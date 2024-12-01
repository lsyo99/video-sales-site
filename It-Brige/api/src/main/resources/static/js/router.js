import { renderHomePage } from './home.js';
import { renderHeader } from './header.js';
import { renderImageDetailPage } from './imageDetails.js';


// 라우팅 테이블 설정
const routes = {
    '/': renderHomePage,
    '/image/:id': renderImageDetailPage,
};

// URL에서 동적 매개변수 추출
// routeRarts와 pathParts 배열을 비교해 : 로 시작하는 부분 키로 추출 -> id값 받기 위해
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
// 경로 일치 확인 후 랜더링
function handleRoute() {
    // 항상 헤더를 먼저 렌더링
    renderHeader();

    const path = location.pathname;
    //
    for (const route in routes) {
        const isMatch = route.split('/').length === path.split('/').length &&
                        route.split('/').every((part, index) =>
                            part.startsWith(':') || part === path.split('/')[index]);

        if (isMatch) {
            const params = extractParams(route, path);
            routes[route](params); // 해당 라우트의 렌더링 함수 호출
            return;
        }
    }

    // 404 처리 (라우트가 없는 경우)
    document.getElementById('main').innerHTML = '<h2>404: Page Not Found</h2>';
}

// 브라우저 히스토리 API 사용 -> 앞 뒤로가기
window.addEventListener('popstate', handleRoute);

// URL 변경 시 라우터 작동 함수
//-> pushStatus사용해url이동
export function navigateTo(url) {
    history.pushState(null, '', url);
    handleRoute();
}

// 초기화 함수: 앱 시작 시 실행
function init() {
    renderHeader(); // 초기 헤더 렌더링
    handleRoute(); // 현재 경로에 맞는 콘텐츠 렌더링
}

// DOMContentLoaded 이벤트로 초기화 함수 호출
document.addEventListener('DOMContentLoaded', init);
