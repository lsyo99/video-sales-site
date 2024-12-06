let accessToken = null;

async function fetchWithRetry(url, options = {}) {
    const headers = options.headers || {};
    if (accessToken) {
        headers['Authorization-Token'] = accessToken;
    }

    const response = await fetch(url, { ...options, headers });
    if (response.status === 401 && !options._retry) {
        options._retry = true;
        const refreshResponse = await fetch('/refresh-token', { method: 'POST', credentials: 'include' });
        if (refreshResponse.ok) {
            const { accessToken: newAccessToken } = await refreshResponse.json();
            accessToken = newAccessToken;
            headers['Authorization-Token'] = newAccessToken;
            return await fetch(url, { ...options, headers });
        } else {
            logout();
        }
    }
    return response;
}

export async function logout() {
    try {
        await fetch('/logout', { method: 'POST', credentials: 'include' });
    } catch (err) {
        console.error('Logout API failed:', err);
    } finally {
        accessToken = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        sessionStorage.clear();
        navigateTo('/'); // 로그인 페이지로 이동
    }
}
