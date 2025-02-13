import { renderHomePage } from './home.js';
import { renderHeader } from './header.js';
import { renderLectureDetailPage } from './lectureDetails.js';
import { renderLoginPage } from './login.js';
import { logout } from '../util/logout.js';
import { renderMyPage } from './mypage.js';
import { renderNoticePage } from './notice.js';
import { renderNoticeDetailPage } from './notice.js';
import { renderNoticeWritePage } from './notice.js';
import { performSearch } from '../util/search.js';
import { renderDiscountCourses, renderNewCourses } from "./headerToCourse.js";
import {renderPaymentPage,renderPaymentFailPage,renderPaymentSuccessPage } from "./payment.js";
import { renderAdminPage } from "./adminpage.js";  // ✅ 수정된 코드

// 라우팅 테이블 설정
const routes = {
    '/': renderHomePage,
    '/lecture/:id': (params) => renderLectureDetailPage(params),
    '/login': renderLoginPage,
    '/logout': () => {
        logout(); // 로그아웃 호출
    },
    '/mypage': renderMyPage,
   '/notice/:page': renderNoticePage,
   '/notice/detail/:id': renderNoticeDetailPage,
   '/board/notice/write' : renderNoticeWritePage,
   '/search': () => {
           const params = new URLSearchParams(location.search);
           const keyword = params.get('keyword');
           if (keyword) {
               performSearch(keyword);
           } else {
               document.getElementById('main').innerHTML = '<p>검색어를 입력하세요.</p>';
           }
       },
       "/discount": renderDiscountCourses,
       "/new": renderNewCourses,
       '/payment/:id': renderPaymentPage,
       '/payment/success': renderPaymentSuccessPage,
        "/payment/fail/:id": (params) => renderPaymentFailPage(params),
        "/adminpage" : renderAdminPage,

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
    let path = location.pathname;

    if (path.startsWith('/open-api')) {
        return; // API 요청은 무시
    }

    if (path === '/notice' || path === '/notice/') {
        console.log("📌 `/notice` 요청을 `/notice/1`로 변경");
        history.replaceState(null, '', '/notice/1');
        path = '/notice/1';
    }

    // 항상 헤더를 렌더링
    renderHeader();

    let main = document.getElementById('main');
    if (!main) {
        console.warn("⚠️ main 요소가 없음. 자동 생성합니다.");
        main = document.createElement('main');
        main.id = 'main';
        document.body.appendChild(main);
    }
    main.innerHTML = ''; // 기존 내용 초기화

    // 로그인 페이지에서는 헤더 숨기기
    if (path === '/login') {
        document.querySelector('header').style.display = 'none';
        renderLoginPage();
        return;
    } else {
        document.querySelector('header').style.display = 'block';
    }

    // 라우트 매칭 및 렌더링
    for (const route in routes) {
        const isMatch = route.split('/').length === path.split('/').length &&
            route.split('/').every(
                (part, index) => part.startsWith(':') || part === path.split('/')[index]
            );

        if (isMatch) {
            const params = extractParams(route, path);
            routes[route](params); // 해당 경로의 렌더링 함수 실행
            return;
        }
    }

    // 404 페이지 처리
    main.innerHTML = '<h2>404: 페이지를 찾을 수 없습니다.</h2>';
}

// URL 변경 시 동작
export function navigateTo(url) {
    console.log(`Navigating to: ${url}`); // 디버깅 로그 추가
    if (url === '/logout') {
        logout(); // 로그아웃 라우트 처리
    } else if (url !== location.pathname) {
        history.pushState(null, '', url);
        handleRoute();
    }
}
// 페이지가 처음 로드될 때 `handleRoute()` 실행
document.addEventListener('DOMContentLoaded', handleRoute);

// 브라우저 히스토리 API 사용 -> 앞/뒤로 가기 이벤트 처리
window.addEventListener('popstate', handleRoute);

// 전역 객체에 navigateTo 등록
window.navigateTo = navigateTo;

// 초기화 함수: 앱 시작 시 실행
function init() {
    renderHeader(); // 초기 헤더 렌더링
    handleRoute(); // 현재 경로에 맞는 콘텐츠 렌더링
}

// DOMContentLoaded 이벤트로 초기화 함수 호출
document.addEventListener('DOMContentLoaded', init);
