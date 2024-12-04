export async function login(username, password) {
    const response = await fetch('/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
        const data = await response.json();

        // JWT 저장
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);

        // 상태 업데이트
        sessionStorage.setItem('username', data.username);

        // 페이지 이동
        window.location.href = '/';
    } else {
        console.error('Login failed');
    }
}