export async function login(email, password) {
    const response = await fetch('/open-api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            result: null,
            body: { email, password }
        }),
    });

    if (response.ok) {
        const data = await response.json();

        // JWT 저장
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);

        // 사용자 상태 업데이트
        sessionStorage.setItem('username', data.username);

        // 페이지 이동
        window.location.href = '/';
    } else {
        console.error('Login failed');
        const errorData = await response.json();
        alert(errorData.message || '로그인에 실패했습니다.');
    }
}
