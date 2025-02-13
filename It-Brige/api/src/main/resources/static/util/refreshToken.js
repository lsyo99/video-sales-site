// util/refreshToken.js

export async function refreshAccessToken() {
    const refreshToken = localStorage.getItem("refreshToken");
    if (!refreshToken) {
        console.error("Refresh Token이 없습니다.");
        return false;
    }

    try {
        const response = await fetch("/open-api/refresh", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ refreshToken }),
        });

        if (!response.ok) {
            throw new Error("토큰 갱신 실패");
        }

        const data = await response.json();
        const newAccessToken = data.body.accessToken;
        const newRefreshToken = data.body.refreshToken;

        localStorage.setItem("accessToken", newAccessToken);
        if (newRefreshToken) {
            localStorage.setItem("refreshToken", newRefreshToken);
        }

        console.log("토큰 갱신 성공");
        return true;
    } catch (error) {
        console.error("토큰 갱신 실패:", error);
        return false;
    }
}
