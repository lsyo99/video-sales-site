let accessToken = localStorage.getItem('accessToken'); // 저장된 토큰 가져오기

export async function fetchWithRetry(url, options = {}) {
    const headers = options.headers || {};
    if (accessToken) {
        headers['Authorization-Token'] = accessToken;
    }

    const response = await fetch(url, { ...options, headers });
    if (response.status === 401 && !options._retry) {
        // 리프레시 토큰을 사용해 액세스 토큰 재발급 시도
        options._retry = true;
        const storedRefreshToken = localStorage.getItem('refreshToken');
        const refreshResponse = await fetch('/open-api/refresh-token', {
            method: 'POST',
            headers: { 'refresh-token': storedRefreshToken },
            credentials: 'include',
        });

        if (refreshResponse.ok) {
            const { accessToken: newAccessToken, refreshToken: newRefreshToken } = await refreshResponse.json();

            // 새로운 토큰 저장
            accessToken = newAccessToken;
            localStorage.setItem('accessToken', newAccessToken);
            if (newRefreshToken) {
                localStorage.setItem('refreshToken', newRefreshToken);
            }

            headers['Authorization-Token'] = newAccessToken;
            return await fetch(url, { ...options, headers });
        } else {
            console.warn('Token refresh failed. Logging out...');
            logout(); // 로그아웃 호출
        }
    }
    return response;
}

export async function logout() {
    try {
        console.log('Logging out...');
        const accessToken = localStorage.getItem('accessToken'); // 저장된 액세스 토큰 가져오기
        if (!accessToken) {
            console.error('AccessToken is missing. Redirecting to login...');
            navigateTo('/login');
            return;
        }

        const response = await fetch('/open-api/logout', {
            method: 'POST',
            headers: {
                'Authorization-Token': accessToken, // Authorization-Token 헤더 추가
            },
            credentials: 'include',
        });

        if (!response.ok) {
            console.error('Logout API failed:', await response.text());
            throw new Error(`Logout API failed with status ${response.status}`);
        }
    } catch (err) {
        console.error('Logout API failed:', err);
    } finally {
        console.log('Clearing tokens and redirecting to home page...');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        sessionStorage.clear();

        renderHeader(); // 헤더 다시 렌더링
        navigateTo('/'); // 홈으로 이동
    }
}
