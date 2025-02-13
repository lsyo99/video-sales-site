import { loadCSS } from '../util/loadCSS.js';

// 공지사항 페이지 렌더링 함수
export async function renderNoticePage(params) {
    loadCSS('/css/notice.css'); // CSS 로드

//    const isAdmin = true; // ✅ 현재 로그인된 사용자가 admin인지 확인 (임시값, 실제 로직 필요)

    let main = document.getElementById('main');
    if (!main) {
        console.warn("⚠️ main 요소가 없음. 자동 생성합니다.");
        main = document.createElement('main');
        main.id = 'main';
        document.body.appendChild(main);
    }
    const userRole = await getUserRole();
    main.innerHTML = `
     <div class="notice-container">
         <h2>공지사항</h2>



         <!-- 게시글 목록 헤더 -->
         <div class="notice-header">
             <span class="notice-header-left">번호</span>
             <span class="notice-header-left">제목</span>
             <span class="notice-header-right">작성자</span>
             <span class="notice-header-right">작성일</span>
         </div>

         <!-- 게시글 목록 -->
         <ul class="notice-list"></ul>

         <!-- 검색 바 -->
                  <div class="search-container">
                      <input id="search-input" type="text" placeholder="검색어를 입력하세요" />
                      <button id="search-button">검색</button>
                  </div>

         <!-- 페이지네이션 -->
         <div class="pagination"></div>

         <!-- "새 글 쓰기" 버튼 -->
         ${userRole === 'ADMIN' ? '<button class="new-post-btn" onclick="navigateTo(\'/board/notice/write\')">새 글 쓰기</button>' : ''}
     </div>
    `;



    const page = params?.page ? parseInt(params.page) : 1; // 기본값 1

    try {
        const response = await fetch(`/open-api/posts/board/1?page=${page - 1}&size=10&sort=createdAt,DESC`);
        const data = await response.json();

        console.log("📌 API 응답 데이터:", data);

        if (!data || !data.body || !data.body.content || data.body.content.length === 0) {
            document.querySelector('.notice-list').innerHTML = '<p>등록된 공지사항이 없습니다.</p>';
            return;
        }

        const noticeList = document.querySelector('.notice-list');

        // 게시글 렌더링 함수
        const renderPosts = (posts) => {
            noticeList.innerHTML = ''; // 초기화
            posts.forEach((post, index) => {
                let createdAt;
                if (Array.isArray(post.created_at)) {
                    createdAt = new Date(
                        post.created_at[0],
                        post.created_at[1] - 1,
                        post.created_at[2],
                        post.created_at[3],
                        post.created_at[4],


                        post.created_at[5]
                    ).toLocaleDateString();
                } else {
                    createdAt = post.created_at ? new Date(post.created_at).toLocaleDateString() : "날짜 없음";
                }

                const listItem = document.createElement('li');
                listItem.classList.add('notice-item');
                listItem.innerHTML = `
                    <span class="notice-item-left">${index + 1 + (page - 1) * 10}</span>
                    <span class="notice-item-left">
                        <a href="javascript:void(0);" onclick="navigateTo('/notice/detail/${post.id}')">
                            ${post.title}
                        </a>
                    </span>
                    <span class="notice-item-right">${post.user_name || "알 수 없음"}</span>
                    <span class="notice-item-right">${createdAt}</span>
                `;
                noticeList.appendChild(listItem);
            });
        };

        renderPosts(data.body.content);

        // 검색 이벤트 처리
        document.getElementById('search-button').addEventListener('click', () => {
            const query = document.getElementById('search-input').value.trim().toLowerCase();

            if (!query) {
                renderPosts(data.body.content); // 검색어가 없으면 전체 게시글 표시
                return;
            }

            const filteredPosts = data.body.content.filter((post) =>
                post.title.toLowerCase().includes(query) || (post.user_name || "").toLowerCase().includes(query)
            );
            renderPosts(filteredPosts);
        });

        // 페이지네이션
        const pagination = document.querySelector('.pagination');
        pagination.innerHTML = `
            <button ${page > 1 ? '' : 'disabled'} onclick="navigateTo('/notice/${page - 1}')">이전</button>
            <span>페이지 ${page} / ${data.body.total_pages}</span>
            <button ${page < data.body.total_pages ? '' : 'disabled'} onclick="navigateTo('/notice/${page + 1}')">다음</button>
        `;
    } catch (error) {
        console.error('❌ 공지사항 로드 오류:', error);
        main.innerHTML += '<p>공지사항을 불러오는 중 오류가 발생했습니다.</p>';
    }
}

// 공지사항 상세 페이지 렌더링
export async function renderNoticeDetailPage(params) {
    loadCSS('/css/notice.css'); // CSS 파일 로드

    const postId = params.id;

    let main = document.getElementById('main');
    if (!main) {
        main = document.createElement('main');
        main.id = 'main';
        document.body.appendChild(main);
    }
    main.innerHTML = `
    <div class="detailNotice-container">
        <!-- 제목 컨테이너 -->
        <div class="title-container">
            <h3 id="notice-title"></h3>
        </div>

        <!-- 공지사항 본문 컨텐츠 -->
        <div class="notice-content">
            <p id="notice-body"></p>
            <p id="notice-author"></p>
            <p id="notice-date"></p>

        </div>

        <!-- 댓글 목록 -->
        <div class="comments-container">
            <h4>댓글</h4>
            <ul id="comments-list"></ul>
        </div>

        <!-- 댓글 작성 -->
        <div class="comment-form">
            <textarea id="comment-input" placeholder="댓글을 입력하세요"></textarea>
            <button id="comment-submit">댓글 작성</button>
        </div>

        <!-- 목록으로 버튼 -->
        <div class="button-container">
            <button class="back-btn" onclick="navigateTo('/notice/1')">목록으로</button>
        </div>
    </div>`;

    try {
        const response = await fetch(`/open-api/posts/${postId}`);
        const data = await response.json();

        console.log("📌 API 응답 데이터 (상세):", data);

        if (!data || !data.body) {
            main.innerHTML += '<p>존재하지 않는 게시글입니다.</p>';
            return;
        }

        const post = data.body;
        document.getElementById("notice-title").textContent = post.title;
        document.getElementById("notice-body").textContent = post.body;
        document.getElementById("notice-author").textContent = `작성자 :${post.user_name}`;
        let createdAt;
        if (Array.isArray(post.created_at)) {
            createdAt = new Date(
                post.created_at[0],
                post.created_at[1] - 1,
                post.created_at[2],
                post.created_at[3],
                post.created_at[4],
                post.created_at[5]
            ).toLocaleDateString();
        } else {
            createdAt = post.created_at ? new Date(post.created_at).toLocaleDateString() : "날짜 없음";
        }
        document.getElementById("notice-date").textContent = `작성일: ${createdAt}`;



        // 댓글 렌더링
// 댓글 렌더링
const commentsList = document.getElementById('comments-list');
commentsList.innerHTML = ''; // 기존 댓글 초기화

post.comments.forEach(comment => {
    const rawDate = comment.created_at; // 예: [2025, 1, 30, 22, 5, 10]
    let formattedDate;

    if (Array.isArray(rawDate)) {
        // 배열 형태의 날짜를 포맷팅
        formattedDate = `${rawDate[0]}.${rawDate[1]}.${rawDate[2]} ${String(rawDate[3]).padStart(2, '0')}:${String(rawDate[4]).padStart(2, '0')}:${String(rawDate[5]).padStart(2, '0')}`;
    } else {
        formattedDate = "날짜 없음"; // 날짜 정보가 없을 경우 기본값
    }

    // 댓글 추가
    const commentItem = document.createElement('li');
    commentItem.innerHTML = `
        <div>
            <strong>${comment.user_name || '알 수 없음'}</strong>: ${comment.body}
        </div>
        <div style="font-size: 12px; color: gray;">작성일: ${formattedDate}</div>
    `;
    commentsList.appendChild(commentItem);
});

        // 댓글 작성 이벤트
        document.getElementById('comment-submit').addEventListener('click', async () => {
            const commentInput = document.getElementById('comment-input');
            const newComment = commentInput.value.trim();

            if (!newComment) {
                alert("댓글을 입력하세요.");
                return;
            }
let user_id = Number(sessionStorage.getItem('user_id'));
            if (user_id === 0) {
                user_id = 1;
                console("유저 아이디가 1로 되었습니다.",user_id);
            }
            try {
                const commentResponse = await fetch(`/open-api/comments/create/comment`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        post_id: postId,
                        comment: newComment,
                        user_id: user_id, //
                    }),
                });

                if (!commentResponse.ok) throw new Error("댓글 작성 실패");

                // 댓글 추가 후 재렌더링
                renderNoticeDetailPage(params);
            } catch (error) {
                console.error("❌ 댓글 작성 오류:", error);
                alert("댓글 작성 중 오류가 발생했습니다.");
            }
        });
    } catch (error) {
        console.error('❌ 공지사항 상세 오류:', error);
        main.innerHTML += '<p>게시글을 불러오는 중 오류가 발생했습니다.</p>';
    }
}

// 새 글 작성 페이지 렌더링
// 새 글 작성 페이지 렌더링
export function renderNoticeWritePage() {
    loadCSS('/css/notice.css'); // CSS 파일 로드

    let main = document.getElementById('main');
    if (!main) {
        main = document.createElement('main');
        main.id = 'main';
        document.body.appendChild(main);
    }

    // 새 글 작성 폼 HTML 구성
    main.innerHTML = `
    <div class="newNotice-container">
        <h2>공지사항 작성</h2>
        <form id="postForm">
            <table class="form-table">
                <tr>
                    <th><label for="title">제목</label></th>
                    <td><input type="text" id="title" placeholder="제목을 입력하세요" required /></td>
                </tr>
                <tr>
                    <th><label for="author">작성자</label></th>
                    <td><input type="text" id="author" placeholder="작성자를 입력하세요" value="관리자" readonly /></td>
                </tr>
                <tr>
                    <th><label for="body">내용</label></th>
                    <td>
                        <textarea id="body" placeholder="내용을 입력하세요 (표, 파일 첨부 가능)" required></textarea>
                        <button type="button" id="add-image-btn" class="add-image-btn">이미지 추가</button>
                    </td>
                </tr>
                <tr>
                    <th><label for="file-upload">첨부파일</label></th>
                    <td>
                        <input type="file" id="file-upload" accept="image/*,.pdf,.doc,.docx" multiple />
                        <div id="file-list-container">
                            <ul id="file-list"></ul>
                        </div>
                        <button type="button" id="delete-files-btn" class="delete-btn">선택 파일 삭제</button>
                    </td>
                </tr>
            </table>
            <div class="form-actions">
                <button type="submit" class="submit-btn">저장</button>
            </div>
        </form>
    </div>`;

    // 이미지 추가 버튼 이벤트 리스너
    document.getElementById('add-image-btn').addEventListener('click', () => {
        const imageURL = prompt('추가할 이미지 URL을 입력하세요:');
        if (imageURL) {
            const bodyField = document.getElementById('body');
            bodyField.value += `\n<img src="${imageURL}" alt="이미지" style="max-width:100%;">\n`;
        }
    });

    // 파일 업로드 이벤트 리스너
    document.getElementById('file-upload').addEventListener('change', function () {
        const fileList = document.getElementById('file-list');
        fileList.innerHTML = ''; // 기존 파일 리스트 초기화

        Array.from(this.files).forEach((file, index) => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <input type="checkbox" class="file-checkbox" data-index="${index}">
                <span>${file.name}</span>
            `;
            fileList.appendChild(listItem);
        });
    });

    // 파일 삭제 버튼 이벤트 리스너
    document.getElementById('delete-files-btn').addEventListener('click', () => {
        const fileList = document.getElementById('file-list');
        const checkboxes = fileList.querySelectorAll('.file-checkbox:checked');
        checkboxes.forEach((checkbox) => {
            const parent = checkbox.parentElement;
            parent.remove(); // 체크된 파일 삭제
        });
    });

    // 폼 제출 이벤트 리스너 추가
    document.getElementById('postForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const title = document.getElementById('title').value;
        const body = document.getElementById('body').value;
        const files = document.getElementById('file-upload').files;

let user_id = Number(sessionStorage.getItem('user_id')); // user_id를 정확히 읽음
if (!user_id || isNaN(user_id)) { // user_id가 유효하지 않으면 기본값 설정
    user_id = 1;
    console.log('user_id가 유효하지 않아 기본값 1로 설정되었습니다.', user_id);
} else {
    console.log('세션에서 가져온 user_id:', user_id);
}

        const postCreateRequest = {
            board_id: 1, // 공지사항 게시판 ID
            title: title,
            body: body,
            user_id: user_id,
        };

        try {
            // 첫 번째 요청: createPost
            const createPostResponse = await fetch('/open-api/posts/create/body', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postCreateRequest),
            });

            if (!createPostResponse.body) throw new Error('게시글 등록 실패');
            const createPostData = await createPostResponse.json();
            const postId = createPostData.body.id; // createPost의 응답에서 postId 가져오기

            console.log("📌 게시글 생성 성공, Post ID:", postId);

            // 두 번째 요청: uploadFiles
            if (files.length > 0) {
                const formData = new FormData();
                for (const file of files) {
                    formData.append('files', file);
                }

                const uploadFilesResponse = await fetch(`/open-api/posts/create/${postId}/files`, {
                    method: 'POST',
                    body: formData,
                });

                if (!uploadFilesResponse.body) throw new Error('파일 업로드 실패');
                console.log("📌 파일 업로드 성공");
            }

            alert('게시글이 성공적으로 등록되었습니다.');
            navigateTo('/notice/1'); // 등록 후 공지사항 목록으로 이동
        } catch (error) {
            console.error('❌ 게시글 등록 오류:', error);
            alert('게시글을 등록하는 중 오류가 발생했습니다.');
        }
    });
}


async function getUserRole() {
    console.log("스토리지에서 토큰 발급");
    console.log(sessionStorage.getItem('accessToken'));

    try {
        const response = await fetch('/open-api/user/role', {
            headers: { 'authorization-token': sessionStorage.getItem('authorizationToken') }
        });

        if (!response.ok) throw new Error('Failed to fetch user role');

        const data = await response.json();
        console.log("📌 API 응답 데이터:", data);

        // API 응답 구조 확인 후 올바른 접근 방식 적용
        const role = data?.body?.role || null;
        console.log("📌 user role:", role);

        return role;
    } catch (error) {
        console.error('Error fetching user role:', error);
        return null;
    }
}


