export function openVideoPage(lectureId) {
    const newWindow = window.open('', '_blank', 'width=1200,height=800');

    if (!newWindow) {
        alert("팝업이 차단되었습니다. 팝업 차단을 해제해주세요.");
        return;
    }

    fetch(`/api/lectures/videos/${lectureId}`)
        .then(async (response) => {
            // 📌 HTTP 상태 코드 확인
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`❌ HTTP 오류: ${response.status} - ${errorText}`);
            }

            // 📌 JSON 응답인지 확인
            const contentType = response.headers.get("content-type");
            if (!contentType || !contentType.includes("application/json")) {
                throw new Error("❌ 서버 응답이 JSON 형식이 아닙니다.");
            }

            return response.json();
        })
        .then(data => {
            // 📌 API 응답 구조 체크
            if (!data || !data.result || data.result.result_code !== 200) {
                throw new Error(`❌ API 오류: ${data.result.result_code} - ${data.result.result_mesage}`);
            }

            if (!data.body || data.body.length === 0) {
                throw new Error("❌ 해당 강좌의 영상이 없습니다.");
            }

            // 📌 비디오 목록 가져오기
            const videos = data.body;
            const firstVideo = videos[0];

            // 📌 동영상 URL 생성 (로컬서버 경로 포함)
            function getVideoUrl(video) {
                return video.url.startsWith("/")
                    ? `http://localhost:8080${video.url}`  // 절대경로 처리
                    : video.url;
            }

            const htmlContent = `
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>강의 시청</title>
                <link rel="stylesheet" type="text/css" href="/css/video.css">
            </head>
            <body>
                <div class="video-container">
                    <div class="video-header">
                        <h2 id="video-title">${firstVideo.title}</h2>
                    </div>
                    <div class="video-content">
                        <div class="video-player">
                            <video id="main-video" controls>
                                <source id="video-source" src="${getVideoUrl(firstVideo)}" type="video/mp4">
                                브라우저가 video 태그를 지원하지 않습니다.
                            </video>
                        </div>
                        <div class="video-list">
                            ${videos.map(video => `
                                <div class="video-item" onclick="changeVideo('${getVideoUrl(video)}', '${video.title}')">
                                    <p>${video.title} (${video.duration})</p>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                </div>
                <script>
                    function changeVideo(url, title) {
                        document.getElementById("main-video").src = url;
                        document.getElementById("video-title").innerText = title;
                    }
                </script>
            </body>
            </html>
            `;

            newWindow.document.write(htmlContent);
            newWindow.document.close();
        })
        .catch(error => {
            newWindow.document.write(`<p>❌ 영상을 불러오는 중 오류가 발생했습니다: ${error.message}</p>`);
            newWindow.document.close();
        });
}
