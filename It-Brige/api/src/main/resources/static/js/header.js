export async function renderHeader() {

    // 기존 헤더 제거 후 새로 렌더링
    const existingHeader = document.querySelector('header');
    if (existingHeader) existingHeader.remove();

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

    const userRole = localStorage.getItem('user_role');
    console.log("유저 권한16",userRole);
    // 로그인 상태 확인
    const username = sessionStorage.getItem('username');
      if (username) {
//          userRole = getUserRole(); // 사용자 역할 가져오기
//          console.log("홈 권한 :",userRole);
          firstRow.innerHTML = `
              <button class="category-btn">☰ 카테고리</button>
              <input type="text" class="search-input" placeholder="내게 필요한 강의를 찾아보세요!" id="searchInput">
              <div class="account-links">
                  <span class="username">${username}님</span>
                  <a href="javascript:void(0);" id="logoutLink">로그아웃</a>
                  <div class="dropdown-menu">
                      <a href="javascript:void(0);" onclick="navigateTo('/mypage')">마이페이지</a>
                      <a href="javascript:void(0);" onclick="navigateTo('/mycourses')">내 강의 이동</a>
                     ${userRole === "ADMIN" ? `<a href="javascript:void(0);" onclick="navigateTo('/adminpage')">강의 등록</a>` : ""}
                  </div>
              </div>
          `;


        setTimeout(() => {
            const logoutLink = document.getElementById('logoutLink');
            if (logoutLink) {
                logoutLink.addEventListener('click', () => logout());
            }
        });
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
    //검색기능
    setTimeout(() => {
        const searchInput = document.getElementById('searchInput');


        if (searchInput) {
            // 엔터 키 이벤트 리스너
            searchInput.addEventListener('keydown', (event) => {
                if (event.key === 'Enter') {
                    const keyword = searchInput.value.trim();
                    if (keyword) {
                        navigateTo(`/search?keyword=${encodeURIComponent(keyword)}`);
                    } else {
                        alert('검색어를 입력해주세요.');
                    }
                }
            });



        } else {
            console.error("⚠️ 검색 입력란 (#searchInput)을 찾을 수 없습니다.");
        }
    });


    // 두 번째 줄: 네비게이션 링크들
    const secondRow = document.createElement('div');
    secondRow.classList.add('second-row');
    secondRow.innerHTML = `
        <nav class="nav-links">
            <a href="javascript:void(0);" onclick="navigateTo('/notice')">[공지]</a>
            <a href="javascript:void(0);" onclick="navigateTo('/discount')">할인 강의</a>
            <a href="javascript:void(0);" onclick="navigateTo('/new')">신규 강의</a>
//            <a href="javascript:void(0);" onclick="navigateTo('curriculum')">커리큘럼</a>
//            <a href="javascript:void(0);" onclick="navigateTo('offline')">오프라인</a>
//            <a href="javascript:void(0);" onclick="navigateTo('certification')">인증과정</a>
//            <a href="javascript:void(0);" onclick="navigateTo('seminar')">세미나</a>
        </nav>
    `;
    navbar.appendChild(secondRow);

    // 세 번째 줄: 아이콘 링크들
//    const thirdRow = document.createElement('div');
//    thirdRow.classList.add('third-row');
//    thirdRow.innerHTML = `
//        <nav class="icon-links">
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/ai')" title="인공지능">
//                    <img src="image/icons/ai.png" alt="AI Icon" class="nav-icon"> 인공지능
//                </a>
//
//            </div>
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/web-dev')" title="웹개발">
//                    <img src="image/icons/웹개발.png" alt="Web Dev Icon" class="nav-icon"> 웹개발
//                </a>
//
//            </div>
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/app-dev')" title="앱개발">
//                    <img src="image/icons/앱개발.png" alt="App Dev Icon" class="nav-icon"> 앱개발
//                </a>
//
//            </div>
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/certification')" title="자격증">
//                    <img src="image/icons/자격증.png" alt="Certification Icon" class="nav-icon"> 자격증
//                </a>
//
//            </div>
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/design')" title="디자인">
//                    <img src="image/icons/디자인.png" alt="Design Icon" class="nav-icon"> 디자인
//                </a>
//
//            </div>
//            <div class="dropdown-container">
//                <a href="javascript:void(0);" onclick="navigateTo('/data-analysis')" title="데이터 분석">
//                    <img src="image/icons/데이터분석.png" alt="Data Analysis Icon" class="nav-icon"> 데이터 분석
//                </a>
//
//            </div>
//        </nav>
//    `;
//    navbar.appendChild(thirdRow);

    // 네비게이션 바를 container에 추가
    container.appendChild(navbar);
    header.appendChild(container);

    // 문서에 공지 배너와 헤더 추가
    document.body.insertBefore(announcement, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild.nextSibling);
    }



export function logout() {
    console.log('Logging out...');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    sessionStorage.clear();

    // 헤더 다시 렌더링
    renderHeader();

    // 로그인 페이지로 이동
    navigateTo('/');
}


