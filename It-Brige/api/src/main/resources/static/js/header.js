export function renderHeader() {
    // 공지 배너 생성
    const announcement = document.createElement('div');
    announcement.classList.add('announcement');
    announcement.innerHTML = `
        <span>⚠️ 공지</span> 오픈 기념!
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
    logo.src = 'ItBridge-logo.png';
    logo.alt = 'ItBridge Logo';
    logo.classList.add('logo');
    logo.onclick = () => navigateTo('home');
    navbar.appendChild(logo);

    // 첫 번째 줄: 검색 및 계정 링크
    const firstRow = document.createElement('div');
    firstRow.classList.add('first-row');
    firstRow.innerHTML = `
        <button class="category-btn">☰ 카테고리</button>
        <input type="text" class="search-input" placeholder="내게 필요한 강의를 찾아보세요!" id="searchInput">
        <div class="account-links">
            <a href="javascript:void(0);" onclick="navigateTo('login')">로그인</a>
            <a href="javascript:void(0);" onclick="navigateTo('support')">고객센터</a>
        </div>
    `;
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

    // 세 번째 줄: 드롭다운 메뉴
    const thirdRow = document.createElement('div');
    thirdRow.classList.add('third-row');
    const mainMenu = document.createElement('ul');
    mainMenu.classList.add('main-menu');
    thirdRow.appendChild(mainMenu);
    navbar.appendChild(thirdRow);

    // 네비게이션 바를 container에 추가
    container.appendChild(navbar);
    header.appendChild(container);

    // 문서에 공지 배너와 헤더 추가
    document.body.insertBefore(announcement, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild.nextSibling);

    // 동적으로 메뉴 항목 추가
    addMenuItems(mainMenu);
}
// addMenuItems 함수 (header.js 내에 위치)
function addMenuItems(mainMenu) {
    // 메뉴 항목과 하위 메뉴 추가 로직
    const menuItems = [
        { 
            name: '모바일', 
            link: 'mobile', 
            subcategories: ['모바일 기초', '모바일 고급'] 
        },
        { 
            name: '웹', 
            link: 'web', 
            subcategories: ['웹 프론트엔드', '웹 백엔드'] 
        },
        { 
            name: '인공지능', 
            link: 'ai', 
            subcategories: ['머신러닝', '딥러닝'] 
        },
        { 
            name: '데이터사이언스', 
            link: 'datascience', 
            subcategories: ['데이터 분석', '통계'] 
        },
        { 
            name: '자격증', 
            link: 'certification', 
            subcategories: ['정보처리기사', '정보보호기사'] 
        },
        { 
            name: '정보보안', 
            link: 'security', 
            subcategories: ['정보보안'] 
        }
    ];

    menuItems.forEach(item => {
        const menuItem = document.createElement('li');
        menuItem.classList.add('item');

        // 상위 메뉴 생성
        const menuLink = document.createElement('a');
        menuLink.href = "javascript:void(0);";
        menuLink.textContent = item.name;
        menuLink.classList.add('menu-link');


        menuItem.appendChild(menuLink);

        // 드롭다운 메뉴 생성
        const dropdown = document.createElement('ul');
        dropdown.classList.add('dropdown');

        item.subcategories.forEach(sub => {
            const subItem = document.createElement('li');
            const subLink = document.createElement('a');
            subLink.href = `#${item.link}/${sub}`; // 링크 형식 설정
            subLink.textContent = sub;
            subItem.appendChild(subLink);
            dropdown.appendChild(subItem);
        });

        menuItem.appendChild(dropdown);
        mainMenu.appendChild(menuItem);

    });
}

