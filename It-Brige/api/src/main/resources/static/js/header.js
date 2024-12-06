export function renderHeader() {
    // 중복 렌더링 방지: 이미 헤더가 존재하면 렌더링하지 않음
    if (document.querySelector('header')) {
        return;
    }

    // 공지 배너 생성
    const announcement = document.createElement('div');
    announcement.classList.add('announcement');
    announcement.innerHTML = `
        <span>⚠️ 공지 오픈 기념!</span>
        <button class="view-details-btn">인상 강의 확인하기 ➔</button>
    `;

    // 헤더 생성
    const header = document.createElement('header');
    header.classList.add('header');

    // 네비게이션 바를 감싸는 컨테이너
    const container = document.createElement('div');
    container.classList.add('container');

    // 네비게이션 바 생성
    const navbar = document.createElement('div');
    navbar.classList.add('navbar');

    // 로고 생성
    const logo = document.createElement('img');
    logo.src = '/ItBridge-logo.png'; // 로고 경로 수정
    logo.alt = 'ItBridge Logo';
    logo.classList.add('logo');
    logo.onclick = () => navigateTo('/'); // 홈으로 이동
    navbar.appendChild(logo);

    // 첫 번째 줄: 검색 및 계정 링크
    const firstRow = document.createElement('div');
    firstRow.classList.add('first-row');

    // 로그인 상태 확인
    const username = sessionStorage.getItem('username');
    if (username) {
        firstRow.innerHTML = `
            <button class="category-btn">☰ 카테고리</button>
            <input type="text" class="search-input" placeholder="내게 필요한 강의를 찾아보세요!" id="searchInput">
            <div class="account-links">
                <span>${username}님</span>
                <a href="javascript:void(0);" onclick="navigatetTo('/logout)">로그아웃</a>
            </div>
        `;
    } else {
        firstRow.innerHTML = `
            <button class="category-btn">☰ 카테고리</button>
            <input type="text" class="search-input" placeholder="내게 필요한 강의를 찾아보세요!" id="searchInput">
            <div class="account-links">
                <a href="javascript:void(0);" onclick="navigateTo('/login')">로그인</a>
                <a href="javascript:void(0);" onclick="navigateTo('/support')">고객센터</a>
            </div>
        `;
    }
    navbar.appendChild(firstRow);

    // 두 번째 줄: 네비게이션 링크들
    const secondRow = document.createElement('div');
    secondRow.classList.add('second-row');
    secondRow.innerHTML = `
        <nav class="nav-links">
            <a href="javascript:void(0);" onclick="navigateTo('notice')">[공지]</a>
            <a href="javascript:void(0);" onclick="navigateTo('discount')">할인 강의</a>
            <a href="javascript:void(0);" onclick="navigateTo('new')">신규 강의</a>
            <a href="javascript:void(0);" onclick="navigateTo('curriculum')">커리큘럼</a>
            <a href="javascript:void(0);" onclick="navigateTo('offline')">오프라인</a>
            <a href="javascript:void(0);" onclick="navigateTo('certification')">인증과정</a>
            <a href="javascript:void(0);" onclick="navigateTo('seminar')">세미나</a>
        </nav>
    `;
    navbar.appendChild(secondRow);

    // 네비게이션 바를 container에 추가
    container.appendChild(navbar);
    header.appendChild(container);

    // 문서에 공지 배너와 헤더 추가
    document.body.insertBefore(announcement, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild.nextSibling);
}

// 로그아웃 함수
export function logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    sessionStorage.clear();
    navigateTo('/login');
}
