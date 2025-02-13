import { loadCSS } from "../util/loadCSS.js";

export async function renderPaymentPage(params) {
    const lectureId = params.id; // 강의 ID 추출
    loadCSS("/css/payment.css");

    const main = document.getElementById('main');
    main.innerHTML = `
        <div class="payment-container">
            <h2>결제하기</h2>
            <div class="course-info">
                <img id="course-thumbnail" alt="강의 썸네일" style="width:100%; max-height:200px; object-fit:cover; margin-bottom:10px;" />
                <p><strong>강의명:</strong> <span id="course-title">로딩 중...</span></p>
                <p><strong>가격:</strong> <span id="course-price">로딩 중...</span></p>
            </div>
            <div class="payment-form">
                <button id="card-pay-button">카드 결제</button>
            </div>
        </div>
    `;

    // 강의 정보 가져오기
    try {
        const response = await fetch(`/open-api/lecture/forpay/${lectureId}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch lecture details for ID: ${lectureId}`);
        }

        const { body: lecture } = await response.json(); // JSON의 body 부분만 추출

        // 강의 정보 출력
        document.getElementById('course-title').textContent = lecture.title || "제목 없음";
        document.getElementById('course-price').textContent = lecture.price ? `${lecture.price.toLocaleString()}원` : "가격 없음";
        document.getElementById('course-thumbnail').src = lecture.thumbnail_url || "/path/to/default-thumbnail.jpg";

        // 결제 버튼 이벤트 핸들링
        document.getElementById('card-pay-button').addEventListener('click', () => initiatePayment('html5_inicis', lecture));
    } catch (error) {
        console.error('Error fetching lecture details:', error);
        document.getElementById('course-title').textContent = "정보를 가져올 수 없습니다.";
        document.getElementById('course-price').textContent = "정보를 가져올 수 없습니다.";
    }
}

async function initiatePayment(pg, lecture) {
    const merchantUid = "O" + new Date().getTime(); // 고유한 주문번호

    // sessionStorage에서 user_id 가져오기
    const username = sessionStorage.getItem('username');
    if (!username) {
        alert("로그인이 필요합니다.");
        navigateTo('/login'); // 로그인 페이지로 이동
        return;
    }
    const user_id = sessionStorage.getItem('user_id');
    if (!user_id) {
        alert("로그인이 필요합니다.");
        navigateTo('/login'); // 로그인 페이지로 이동
        return;
    }
const token = sessionStorage.getItem('accessToken'); // Access Token
const refreshToken = sessionStorage.getItem('refreshToken'); // Refresh Token
console.log("리프레시토큰",refreshToken)
console.log
    const IMP = window.IMP;
    IMP.init('imp42124572'); // 포트원 가맹점 식별코드

    IMP.request_pay({
        pg, // PG사 (html5_inicis 또는 kakao)
        pay_method: 'card', // 결제 방식
        merchant_uid: merchantUid, // 주문번호
        name: lecture.title, // 상품명
        amount: lecture.price, // 결제 금액
        buyer_name: username, // 구매자 ID (sessionStorage에서 가져옴)
        buyer_email: "test@example.com", // 예제 이메일 (필요시 수정)
    }, async function (rsp) {
        if (rsp.success) {
        console.log("impUid는 ", rsp.imp_uid);
            // 결제 성공 -> 백엔드에 검증 요청
            const validateResponse = await fetch('/api/v1/payments/validate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' ,
                                                                  'Authorization-Token': token,
                                                                  'refresh-token': refreshToken},
                body: JSON.stringify({
                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid,
                    user_id: user_id, // 추가 정보 전달
                    buyer_name: username,
                    pay_method: pg,
                    name: lecture.title,
                    amount: lecture.price,
                    lecture_id: lecture.id,
                }),
            });

            const result = await validateResponse.json();
            if (esult.result.result_code === 200) {
                alert("결제가 완료되었습니다!");
                navigateTo('/payment/success');
            } else {
                alert("결제 금액 검증 실패");
            }
        } else {
            alert("결제가 실패하였습니다.");
              navigateTo(`/payment/fail/${lecture.id}`);
        }
    });
}

export async function renderPaymentSuccessPage() {
    const main = document.getElementById('main');
    main.innerHTML = `<h2>결제가 성공적으로 완료되었습니다! 마이페이지로 이동합니다...</h2>`;

    // 일정 시간(예: 2초) 후 마이페이지로 이동
    setTimeout(() => {
        navigateTo('/mypage');
    }, 2000); // 2초 후 이동
}

export function renderPaymentFailPage(params) {
    const lectureId = params.id; // 강의 ID 추출
    const main = document.getElementById("main");
    main.innerHTML = `<h2>결제가 실패하였습니다. 다시 시도해주세요.</h2>`;
    setTimeout(() => {
        navigateTo(`/payment/${lectureId}`);
    }, 2000); // 2초 후 이동
}