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

            // 응답 데이터 구조 확인 및 필드 추출
            const { access_token, refresh_token, username,user_id } = data.body;

            // 필드 유효성 검사
            if (!access_token || !refresh_token || !username) {
                console.error('Login response missing critical fields:', data);
                alert('로그인 응답 데이터가 올바르지 않습니다.');
                return;
            }

            // JWT 및 사용자 정보 저장
            localStorage.setItem('accessToken', access_token);
            localStorage.setItem('refreshToken', refresh_token);
            sessionStorage.setItem('username', username);
            sessionStorage.setItem('user_id,',user_id);
            console.log('username:', username);

            // 페이지 이동
            window.location.href = '/';
        } else {
            // 서버 응답이 실패한 경우
            const errorData = await response.json();
            console.error('Login failed:', errorData);
            alert(errorData.message || '로그인에 실패했습니다.');
        }
    } catch (error) {
        // 네트워크 또는 기타 오류 처리
        console.error('Error during login:', error);
        alert('로그인 중 오류가 발생했습니다.');
    }
}
