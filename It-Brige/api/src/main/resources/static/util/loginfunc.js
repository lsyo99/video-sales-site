export async function login(email, password) {
    try {
        const response = await fetch('/open-api/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                result: null,
                body: { email, password },
            }),
        });

        if (response.ok) {
            const data = await response.json();

            const { access_token, refresh_token, username, user_id,user_role } = data.body;

            if (!access_token || !refresh_token || !username || !user_id|| !user_role) {
                throw new Error('로그인 응답 데이터가 올바르지 않습니다.');
            }

            // JWT 및 사용자 정보 저장
            localStorage.setItem('accessToken', access_token);
            localStorage.setItem('refreshToken', refresh_token);
            sessionStorage.setItem("accessToken",access_token);
            console.log("로그인1 엑세스 토큰",access_token);
            console.log("로그인 리프레시 토큰",refresh_token);
            sessionStorage.setItem('username', username);
            sessionStorage.setItem('user_id', user_id.toString());
            sessionStorage.setItem('user_role',user_role);
            localStorage.setItem('user_role',user_role);
            sessionStorage.setItem('accessToken', access_token);

                    console.log("유저권한", user_role);


            console.log('로그인 성공, username:', username);
            return { user_id, username,refresh_token }; // 반환 값을 보장
        } else {
            const errorData = await response.json();
            throw new Error(errorData.message || '로그인 실패');
        }
    } catch (error) {
        console.error('로그인 중 예외 발생:', error);
        throw error; // 예외를 다시 던져서 호출한 곳에서 처리
    }
}
