const params = new URLSearchParams(window.location.search);
const lectureId = params.get('lecture_id');

if (lectureId) {
    console.log(`Lecture ID: ${lectureId}`);
    // 이곳에서 lectureId로 서버에 요청을 보내 비디오 데이터를 가져오세요.
} else {
    console.error('Lecture ID not found in URL');
}