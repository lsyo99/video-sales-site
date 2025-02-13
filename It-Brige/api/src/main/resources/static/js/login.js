import { loadCSS } from "../util/loadCSS.js";
import { login } from "../util/loginfunc.js"; // API 호출 함수 임포트

export function renderLoginPage() {
    loadCSS('/css/login.css');

    const header = document.querySelector('header');
    if (header) {
        header.style.display = 'none';
    }

    const main = document.getElementById('main');
    if (!main) {
        console.error("Main element with id 'main' not found.");
        return;
    }

    main.innerHTML = `
        <section class="login-page">
            <div class="login-container">
                <img src="./logo.png" alt="ITBRIDGE Logo" class="login-logo">
                <h1>인생을 바꾸는 교육, ItBridge.</h1>
                <button class="kakao-login-btn">카카오로 1초 만에 시작하기</button>
                <div class="divider">
                    <span>또는 이메일로 로그인</span>
                </div>
                <form id="loginForm" class="login-form">
                    <div class="form-group">
                        <label for="email">이메일</label>
                        <input type="email" id="email" placeholder="이메일을 입력하세요" required>
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호</label>
                        <input type="password" id="password" placeholder="비밀번호를 입력하세요" required>
                        <button type="button" class="show-password">👁️</button>
                    </div>
                    <button type="submit" class="login-btn">로그인</button>
                    <button type="button" class="signup-btn">이메일로 회원가입</button>
                </form>
                <div class="help-links">
                    <a href="javascript:void(0);">비밀번호 찾기</a> |
                    <a href="javascript:void(0);">자주 묻는 질문 바로가기</a>
                </div>
            </div>
        </section>
    `;

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const userData = await login(email, password); // login 함수 호출
        if (!userData || !userData.user_id) {
            throw new Error('로그인 데이터가 유효하지 않습니다.');
        }

        sessionStorage.setItem('user_id', userData.user_id);
        console.log('로그인 성공, user_id:', userData.user_id);
        sessionStorage.setItem('refreshToken', userData.refresh_token);
        console.log("로그인 성공, 리프레시 토큰은", userData.refresh_token);
        sessionStorage.setItem('accessToken', userData.access_token);
                console.log("로그인 성공, 엑세스 토큰은", userData.access_token);
        const checkaccesstoken = sessionStorage.getItem('accessToken');
//        if(!checkaccesstoken){
//         throw new Error('로그인 응답 데이터가 올바르지 않습니다.');
//        }

        sessionStorage.setItem('user_role',userData.user_role);
        console.log("유저권한", userData.user_role);
        // 로그인 성공 후 메인 페이지로 이동
        // 로그인 성공 후 메인 페이지로 이동

        window.location.href = '/';
    } catch (error) {
        console.error('Error during login:', error);
        alert(error.message || '로그인에 실패했습니다. 다시 시도하세요.');
    }
});

}
