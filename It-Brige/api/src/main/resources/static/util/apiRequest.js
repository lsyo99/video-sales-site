// util/apiRequest.js

import { refreshAccessToken } from "./refreshToken.js";

export async function apiRequest(url, options = {}) {
    const accessToken = localStorage.getItem("accessToken");
    if (!accessToken) {
        alert("로그인이 필요합니다.");
        navigateTo("/login");
        return null;
    }

    // Authorization 헤더 추가
    options.headers = {
        ...options.headers,
        "Authorization-Token": accessToken,
        "Content-Type": "application/json",
    };

    const response = await fetch(url, options);

    if (response.status === 401) {
        console.warn("Access Token 만료, Refresh Token으로 갱신 시도...");
        const isRefreshed = await refreshAccessToken();
        if (isRefreshed) {
            options.headers["Authorization-Token"] = localStorage.getItem("accessToken");
            return await fetch(url, options);
        } else {
            alert("다시 로그인해 주세요.");
            navigateTo("/login");
        }
    }
    return response;
}
