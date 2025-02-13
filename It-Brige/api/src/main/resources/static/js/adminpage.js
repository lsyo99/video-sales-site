import { loadCSS } from "../util/loadCSS.js";
import { fetchWithAuth } from "../util/fetchWithAuth.js";

// 관리자 페이지 렌더링
export async function renderAdminPage() {
    const main = document.getElementById("main");
    loadCSS("/css/admin.css"); // 관리자 전용 스타일 로드

    main.innerHTML = `
        <div class="admin-container">
            <div class="sidebar">
                <h2>관리자 페이지</h2>
                <ul class="sidebar-menu">
                    <li><a id="menu-register-course">강의 등록하기</a></li>
                    <li><a id="menu-edit-course">강의 수정하기</a></li>
                    <li><a id="menu-delete-user">회원 삭제하기</a></li>
                </ul>
            </div>
            <div class="main-wrapper">
                <div class="main-content" id="main-content"></div>
            </div>
        </div>
    `;

    document.getElementById("menu-register-course").addEventListener("click", renderCourseUploadPage);
    document.getElementById("menu-edit-course").addEventListener("click", renderCourseEditPage);
    document.getElementById("menu-delete-user").addEventListener("click", renderUserDeletePage);

    renderCourseUploadPage(); // 기본으로 강의 등록 페이지 표시
}

// ✅ 강의 등록 페이지 생성
// ✅ 강의 등록 페이지 생성 (JSON + 파일 업로드)
async function renderCourseUploadPage() {
    const mainContent = document.getElementById("main-content");
    mainContent.innerHTML = `
        <h2>강의 등록하기</h2>
        <form id="course-upload-form" enctype="multipart/form-data">
            <label for="title">강의 제목</label>
            <input type="text" id="title" name="title" required>

            <label for="category">카테고리</label>
            <select id="category" name="category">
                <option value="웹개발">웹개발</option>
                <option value="앱개발">앱개발</option>
                <option value="데이터사이언스">데이터사이언스</option>
                <option value="AI">인공지능</option>
                <option value="기타">기타</option>
            </select>

            <label for="thumbnail">썸네일 이미지</label>
            <input type="file" id="thumbnail" name="thumbnail" accept="image/*" required>

            <label for="lecture_detail_images">강의 상세 이미지 (여러 개 선택 가능)</label>
            <input type="file" id="lecture_detail_images" name="lecture_detail_images" accept="image/*" multiple required>
            <ul id="detail-image-list"></ul>

            <label for="lecture_videos">강의 영상 (여러 개 선택 가능 - MP4)</label>
            <input type="file" id="lecture_videos" name="lecture_videos" accept="video/mp4" multiple required>
            <ul id="video-list"></ul>

            <label for="price">가격 (₩)</label>
            <input type="number" id="price" name="price" min="0" required>

            <label for="sales">판매량</label>
            <input type="number" id="sales" name="sales" min="0" required>

            <label for="tags">태그 (쉼표로 구분)</label>
            <input type="text" id="tags" name="tags" placeholder="예: JavaScript, React, 웹개발">

            <button type="submit">강의 등록</button>
        </form>
    `;

    function updateFileList(inputId, listId) {
        document.getElementById(inputId).addEventListener("change", function () {
            const fileList = document.getElementById(listId);
            fileList.innerHTML = "";
            if (this.files.length > 0) {
                for (let file of this.files) {
                    const listItem = document.createElement("li");
                    listItem.textContent = file.name;
                    fileList.appendChild(listItem);
                }
            } else {
                fileList.innerHTML = "<li>선택된 파일 없음</li>";
            }
        });
    }

    updateFileList("lecture_detail_images", "detail-image-list");
    updateFileList("lecture_videos", "video-list");

document.getElementById("course-upload-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData();

    // JSON 데이터를 FormData에 추가 (Blob 사용)
    const courseData = {
        title: document.getElementById("title").value,
        category: document.getElementById("category").value,
        price: document.getElementById("price").value,
        sales: document.getElementById("sales").value,
        tags: document.getElementById("tags").value.split(",").map(tag => tag.trim())
    };
    formData.append("courseData", new Blob([JSON.stringify(courseData)], { type: "application/json" }));

    // 파일 추가
    formData.append("thumbnail", document.getElementById("thumbnail").files[0]);

    const detailImages = document.getElementById("lecture_detail_images").files;
    for (let i = 0; i < detailImages.length; i++) {
        formData.append("lecture_detail_images", detailImages[i]);
    }

    const lectureVideos = document.getElementById("lecture_videos").files;
    for (let i = 0; i < lectureVideos.length; i++) {
        formData.append("lecture_videos", lectureVideos[i]);
    }

    // ✅ 로컬 스토리지에서 토큰 가져오기 (예제)
    const token = localStorage.getItem("accessToken");
    console.log("토큰",token);
    try {
        const response = await fetch("/api/saveLecture", {
            method: "POST",
            body: formData,  // multipart-form-data로 전송
            headers: {
                "Authorization": `Bearer ${token}`,
                "refresh-token": sessionStorage.getItem("refreshToken")
            }
        });

        if (!response.ok) throw new Error("강의 등록 실패");

        alert("강의가 등록되었습니다.");
    } catch (error) {
        alert("강의 등록 중 오류 발생");
        console.error(error);
    }
});

}

// ✅ 강의 수정 페이지 생성
async function renderCourseEditPage() {
    const mainContent = document.getElementById("main-content");
    mainContent.innerHTML = `
        <h2>강의 수정하기</h2>
        <button id="delete-selected-courses">선택한 강의 삭제</button>
        <div id="course-list">
            <p>강의를 불러오는 중...</p>
        </div>
    `;

    try {
        const response = await fetchWithAuth("/open-api/adminpage/courses");
        if (!response.ok) throw new Error("강의 목록 불러오기 실패");

        const data = await response.json();
        const courses = data.body;
        const courseList = document.getElementById("course-list");
        courseList.innerHTML = "";

        courses.forEach((course) => {
            courseList.innerHTML += `
                <div class="course-item">
                    <input type="checkbox" class="course-checkbox" data-id="${course.lecture_id}">
                    <span>${course.title} - ₩${course.price}</span>
                </div>
            `;
        });

        document.getElementById("delete-selected-courses").addEventListener("click", async () => {
            const selectedCourses = document.querySelectorAll(".course-checkbox:checked");
            if (selectedCourses.length === 0) {
                alert("삭제할 강의를 선택하세요.");
                return;
            }

            const courseIds = Array.from(selectedCourses).map(cb => cb.dataset.id);
            try {
                await fetchWithAuth("/open-api/adminpage/delete-courses", {
                    method: "DELETE",
                    body: JSON.stringify({ course_ids: courseIds }),
                    headers: { "Content-Type": "application/json" },
                });
                alert("선택한 강의가 삭제되었습니다.");
                renderCourseEditPage();
            } catch (error) {
                alert("강의 삭제 실패");
            }
        });
    } catch (error) {
        console.error("강의 수정 페이지 로딩 오류:", error);
    }
}

// ✅ 회원 삭제 페이지 생성
async function renderUserDeletePage() {
    const mainContent = document.getElementById("main-content");
    mainContent.innerHTML = `
        <h2>회원 삭제하기</h2>
        <input type="text" id="search-user" placeholder="회원 ID 또는 이름 검색">
        <div id="user-list">
            <p>회원 정보를 불러오는 중...</p>
        </div>
    `;

    document.getElementById("search-user").addEventListener("input", (e) => {
        const keyword = e.target.value.toLowerCase();
        document.querySelectorAll(".user-item").forEach(item => {
            const text = item.textContent.toLowerCase();
            item.style.display = text.includes(keyword) ? "block" : "none";
        });
    });

    try {
        const response = await fetchWithAuth("/open-api/adminpage/users");
        if (!response.ok) throw new Error("회원 목록 불러오기 실패");

        const data = await response.json();
        const users = data.body;
        const userList = document.getElementById("user-list");
        userList.innerHTML = "";

        users.forEach((user) => {
            userList.innerHTML += `
                <div class="user-item">
                    <span>${user.username} (${user.user_id})</span>
                    <button class="delete-user-btn" data-id="${user.user_id}">삭제</button>
                </div>
            `;
        });

        document.querySelectorAll(".delete-user-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                const userId = e.target.dataset.id;
                try {
                    await fetchWithAuth(`/open-api/admin/delete-user/${userId}`, { method: "DELETE" });
                    alert(`회원 ${userId} 삭제 완료`);
                    renderUserDeletePage();
                } catch (error) {
                    alert("회원 삭제 실패");
                }
            });
        });
    } catch (error) {
        console.error("회원 삭제 페이지 로딩 오류:", error);
    }
}
