export async function fetchWithAuth(url, options = {}) {
    const accessToken = localStorage.getItem('accessToken');
    console.log("리엑세스 토큰토큰",accessToken);
    const refreshToken = sessionStorage.getItem('refreshToken');
    console.log("리프레시토큰",refreshToken);
    if (!accessToken) {
        console.warn("🔑 토큰이 없습니다. 로그인이 필요합니다.");
        alert("로그인이 필요합니다.");
        window.location.href = "/login";
        return;
    }

    options.headers = {
        ...options.headers,
        "authorization-token": accessToken,
        "refresh-token": refreshToken,
        "Content-Type": "application/json"
    };

    try {
        const response = await fetch(url, options);
        const data = await response.json(); // 응답을 JSON으로 변환

        if (data.result.result_code !== 200) { // API 응답에서 result_code 체크
            throw new Error(`API 오류: ${data.result.result_code} - ${data.result.result_mesage}`);
        }

        return data; // `body`가 아니라 전체 데이터를 반환
    } catch (error) {
        console.error("❌ fetchWithAuth 오류:", error);
        throw error;
    }
}
