import { loadCSS } from "../util/loadCSS.js";

// 마이페이지 렌더링
export function renderMyPage() {
    const main = document.getElementById('main');
    loadCSS('/css/mypage.css');
    main.innerHTML = `
        <div class="mypage-container">
            <div class="sidebar">
                <h2>안녕하세요</h2>
                <ul class = "sidebar-menu">
                    <li><a id="menu-lectures">내 강의 보기</a></li>
                    <li><a id="menu-edit-profile">회원정보 수정</a></li>
                    <li><a id="menu-transactions">거래 내역</a></li>
                </ul>
            </div>
            <div class="main-wrapper">
                <div class="main-content" id="main-content">
                    <!-- 여기에 동적으로 콘텐츠가 삽입됩니다. -->
                </div>
            </div>
        </div>
    `;

    // 내 강의 목록 페이지 기본 렌더링
    renderLecturePage();

    // 사이드바 클릭 이벤트 등록
    document.getElementById('menu-lectures').addEventListener('click', renderLecturePage);
    document.getElementById('menu-edit-profile').addEventListener('click', renderEditProfileForm);
    document.getElementById('menu-transactions').addEventListener('click', renderTransactionPage);
}

// 내 강의 목록 페이지 생성
async function renderLecturePage() {
    const mainContent = document.getElementById('main-content');
    mainContent.innerHTML = `
        <h2>내 강의 목록</h2>
        <div id="lecture-list" class="lecture-list">
            <p>강의를 불러오는 중...</p>
        </div>
    `;

    try {
        let user_id = Number(sessionStorage.getItem('user_id'));
        if (user_id === 0) {
            user_id = 1;
        }
        console.log('User ID:', user_id);
        const response = await fetch(`/mypage/mypage/${user_id}`); // 가짜 데이터 파일 경로

        if (!response.ok) throw new Error('강의 데이터를 불러오는 데 실패했습니다.');

        const data = await response.json();
        const lectures = data.body; // 응답의 body 배열에 강의 목록이 포함되어 있음
        console.log('Lectures data:', lectures); // 데이터를 확인

        const lectureList = document.getElementById('lecture-list');
        lectureList.innerHTML = ''; // 초기 메시지 제거

        lectures.forEach(lecture => {
            lectureList.innerHTML += `
                <div class="lecture-item">
                    <!-- 강의 제목 및 카테고리 -->
                    <div class="lecture-details">
                        <p class="lecture-title">${lecture.title}</p>
                        <p class="lecture-category">${lecture.category || "카테고리 없음"}</p>
                    </div>

                    <!-- 시청하기 버튼과 썸네일 이미지가 양끝에 배치됨 -->
                    <div class="thumbnail-actionsContainer">
                        <div class="lecture-thumbnail">
                            <img src="${lecture.thumbnail_url}" alt="${lecture.title} 썸네일" class="thumbnail-img">
                        </div>
                        <div class="lecture-actions">
                            <a href="#" class="watch-button" data-lecture-id="${lecture.lecture_id}">
                                <span>시청하기</span>
                            </a>
                        </div>
                    </div>
                </div>
            `;
        });
    } catch (error) {
        console.error("Error loading lectures:", error); // 오류 메시지 출력
    }
}


// 회원정보 수정 페이지 생성
async function renderEditProfileForm() {
    const mainContent = document.getElementById('main-content');

    mainContent.innerHTML = `
        <h2>회원정보 수정</h2>
        <form id="edit-profile-form">
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>

            <label for="email">이메일</label>
            <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>

            <label for="phone">전화번호</label>
            <input type="text" id="phone" name="phone" placeholder="전화번호를 입력하세요">

            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">

            <button type="submit">저장</button>
        </form>
    `;

    const response = await fetch('./json/mock_profile.json'); // 가짜 데이터 파일 경로
    const profile = await response.json();

    document.getElementById('name').value = profile.name;
    document.getElementById('email').value = profile.email;
    document.getElementById('phone').value = profile.phone;

    document.getElementById('edit-profile-form').addEventListener('submit', (e) => {
        e.preventDefault();
        alert('회원정보가 저장되었습니다. (가짜 데이터)');
    });
}

// 거래 내역 페이지 생성
async function renderTransactionPage() {
    const mainContent = document.getElementById('main-content');
    mainContent.innerHTML = `
        <h2 class="transaction-title">거래 내역</h2>

        <div id="transaction-list" class="transaction-list">
            <p>거래 내역을 불러오는 중...</p>
        </div>
    `;

    try {

            let user_id = Number(sessionStorage.getItem('user_id'));
            if (user_id === 0) {
                user_id = 1;
            }
            console.log('User ID:', user_id);
        const response = await fetch(`/mypage/mypage/${user_id}`); // 가짜 데이터 파일 경로
        if (!response.ok) throw new Error('거래 내역 데이터를 불러오는 데 실패했습니다.');

        const dataLayer = await response.json();
        console.log("성공");
        const transactions = dataLayer.body;
        console.log('transactions is complete');

        const transactionList = document.getElementById('transaction-list');
        transactionList.innerHTML = ''; // 초기 메시지 제거

        transactions.forEach(transaction => {
            transactionList.innerHTML += `
                <div class="transaction-item">
                    <div class="payment-header">
                        <p class="status-label">결제 완료</p>
                        <p class="payment-date">${transaction.payed_data}</p>
                    </div>
                    <p class="transaction-title">${transaction.title}</p>
                    <div class="payment-info">
                        <div class="payment-details">
                            <p>가격: <strong>${transaction.first_price}원</strong></p>
                            <p>적용된 할인율: <strong>${transaction.salse || '0'}%</strong></p>
                            <p>결제 수단: <strong>${transaction.pay_method}</strong></p>
                        </div>
                        <div class="total-amount">
                            <p>결제 금액: <strong>${transaction.account}원</strong></p>
                        </div>
                    </div>
                    <div class="action-buttons">
                        <button class="receipt-button">결제 확인서</button>
                        <button class="refund-button">환불 신청</button>
                    </div>
                </div>
            `;
        });
    } catch (error) {
        document.getElementById('transaction-list').innerHTML = `<p>${error.message}</p>`;
    }
}
// 새창여는 함수
function openNewPageForVideo(lectureId){
const PageUrl = `/video.html?lecture_id=$(lectureId)`;
window.open(PageUrl, '_blank','width = 800,height=600');
}
document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', (event) => {
        if (event.target.closest('.watch-button')) {
            event.preventDefault();
            const button = event.target.closest('.watch-button');
            const lectureId = button.getAttribute('data-lecture-id');
            if (lectureId) {
                openNewPageForVideo(lectureId);
            } else {
                console.error('Lecture ID not found!');
            }
        }
    });
});


// 초기화 함수
//document.addEventListener('DOMContentLoaded', renderMyPage);
