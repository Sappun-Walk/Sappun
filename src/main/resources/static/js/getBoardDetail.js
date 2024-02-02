
    const host = 'http://' + window.location.host;
    const loginPage = '/api/users/login';
    const logoutUrl = '/api/users/logout';
    const mainPage = '/api/boards/best';
    const createPage = '/api/boards/create-page';
    const updatePage = '/api/boards/update/';
    const regionPage = '/api/boards/region'
    const userUrl = '/api/users'
    const boardUrl = '/api/boards';
    const commentUrl = '/api/comments'
    const report = '/report'
    const like = '/like'
    const updatePassword = '/api/users/profile/password';

    // 댓글 목록을 업데이트하는 함수
    function updateComments(boardId) {
    // AJAX를 사용하여 서버에서 새로운 댓글 목록을 가져옴
    fetch(`/api/boards/${boardId}/comments`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-cache',
    })
        .then(response => response.json())
        .then(data => {
            // 서버에서 반환한 데이터를 토대로 댓글 목록을 업데이트
            var commentIdContainer = document.getElementById('comment-id');
            commentIdContainer.innerHTML = data; // data에는 서버에서 전달한 댓글 목록 HTML이 포함되어야 함
        })
        .catch(error => {
            console.error('Error updating comments:', error);
        });
}

    //댓글 정렬
    function updateSortOrder() {
    // 정렬 방식 및 게시글 ID 가져오기
    var sortOrder = document.getElementById('sortOrder').value;
    // 현재 URL 가져오기
    var currentUrl = window.location.origin + window.location.pathname;
    // 정렬 기준이 이미 존재하는 경우
    if (window.location.search.includes('?')) {
    // 이미 쿼리 매개변수가 있는 경우
    updatedUrl = `${currentUrl}?sortOrder=${sortOrder}`;
} else {
    // 쿼리 매개변수가 없는 경우
    updatedUrl = `${currentUrl}?sortOrder=${sortOrder}`;
}
    // AJAX를 사용하여 서버에 정렬 방식을 전달
    fetch(updatedUrl, {
    method: 'GET',
    headers: {
    'Content-Type': 'application/json'
},
    cache: 'no-cache', // 캐시 방지 옵션 추가
})
    .then(response => response.json())
    .then(data => {
    // 서버에서 반환한 데이터를 처리
    console.log(data);
    // 여기에서 서버 응답을 토대로 화면을 업데이트하거나 추가 로직 수행 가능
    // 예: $('#product-container').empty();, pagination 업데이트 등
})
    .catch(error => {
    console.error('Error updating sort order:', error);
});
    window.location.href = updatedUrl;
}

    // 로그인
    function onLogin() {
    let username = $('#username').val();
    let password = $('#password').val();

    // 사용자 이름 또는 패스워드가 비어있을 경우 오류 처리
    if (!username || !password) {
    alert('사용자 이름과 패스워드를 입력하세요.');
    return;
}

    // Request DTO 구성
    const UserLoginReq = {
    username: username,
    password: password
};

    fetch(loginPage, {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(UserLoginReq),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    // 서버 응답이 200이면 로그인 성공
    alert('로그인이 성공적으로 이루어졌습니다.');
    window.location.href = mainPage;
} else {
    // 서버 응답이 200이 아니면 로그인 실패
    alert('로그인 실패: ' + data.message);
}
})
}

    // 로그인 요청
    function needToLogin() {
    alert("로그인한 사용자만 가능합니다.");
}

    // 로그아웃
    function logout() {
    var confirmation = confirm('로그아웃 하시겠습니까?');

    if (confirmation) {
    fetch(logoutUrl, {method: 'POST'})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('로그아웃이 성공적으로 이루어졌습니다.');
    deleteCookie();
    window.location.href = mainPage;
} else {
    alert('로그아웃 실패: ' + data.message);
}
})
}
}

    // 쿠키 삭제
    function deleteCookie() {
    document.cookie = "AccessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    document.cookie = "RefreshToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

    // 메인 페이지로 이동
    function getMainPage() {
    window.location.href = mainPage;
}

    // 상세 페이지로 이동
    function getBoardDetails(boardId) {
    window.location.href = boardUrl + '/' + boardId;
}

    // 게시글 작성 페이지로 이동
    function redirectToCreatePage() {
    window.location.href = createPage;
}

    // 게시글 수정 페이지 이동
    function getUpdateBoard(boardId) {
    window.location.href = updatePage + boardId;
}

    // 지역 페이지
    function submitForm() {
    document.getElementById('regionForm').submit();
}

    function changeRegionAndPage(element) {
    var region = element.getAttribute('data-region');
    var page = element.getAttribute('data-page');
    window.location.href = regionPage + '?region=' + region + '&page=' + page;
}

    function changeBoardPage(element) {
    var page = element.getAttribute('data-page');
    window.location.href = boardUrl + '/reports' + '?page=' + page;
}

    function changeCommentPage(element) {
    var page = element.getAttribute('data-page');
    window.location.href = commentUrl + '/reports' + '?page=' + page;
}

    // 게시글 삭제
    function deleteBoard(boardId) {
    fetch(boardUrl + '/' + boardId, {method: 'DELETE'})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.code === 200) {
                alert('게시글 삭제가 성공적으로 이루어졌습니다.');
                window.location.href = mainPage;
            } else {
                alert('게시글 삭제 실패: ' + data.message);
            }
        });
}

    // 게시글 신고 사유 입력
    function showInputForm() {
    var inputForm = document.getElementById('inputForm');

    // 현재 상태 확인
    var isHidden = inputForm.style.display === 'none' || inputForm.style.display === '';

    // 상태에 따라 토글
    inputForm.style.display = isHidden ? 'block' : 'none';
}

    // 게시글 신고
    function reportBoard(boardId) {
    const reason = document.getElementById('reason').value;

    // Request DTO 구성
    const request = {
    reason: reason,
};

    fetch(boardUrl + '/' + boardId + report, {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(request),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('게시글 신고가 성공적으로 이루어졌습니다.');
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('게시글 신고 실패: ' + data.message);
}
})

    var inputForm = document.getElementById('inputForm');
    inputForm.style.display = 'none';
}

    // 게시글 신고 취소
    function cancelReportBoard(boardId) {
    // 사용자에게 재확인 알림창 표시
    var confirmation = confirm('신고 취소 하시겠습니까?');

    // 사용자가 확인을 선택한 경우에만 DELETE 요청 보내기
    if (confirmation) {
    fetch(boardUrl + '/' + boardId + report, {method: 'DELETE'})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('신고 취소가 성공적으로 이루어졌습니다.');
    window.location.href = boardUrl + '/report';
} else {
    alert('신고 취소 실패: ' + data.message);
}
})
}
}

    // 게시글 좋아요
    function likeBoard(boardId) {
    fetch(boardUrl + '/' + boardId + like, {
        method: 'POST',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.code === 200) {
                if (data.data.isLiked === true) {
                    alert("좋아요를 눌렀습니다.")
                } else {
                    alert("좋아요를 취소했습니다.")
                }
                window.location.href = boardUrl + '/' + boardId;
            } else {
                alert('게시글 좋아요 실패: ' + data.message);
            }
        })
}

    // 게시글 좋아요에따라 버튼색 변경
    // function likeBoard(boardId) {
    //     fetch(boardUrl + '/' + boardId + '/like', {
    //         method: 'POST',
    //     })
    //         .then(response => {
    //             if (!response.ok) {
    //                 throw new Error('Network response was not ok');
    //             }
    //             return response.json();
    //         })
    //         .then(data => {
    //             if (data.code === 200) {
    //                 // 좋아요 수 갱신
    //                 const likeCountElement = document.querySelector('.board-up p:nth-child(2)');
    //                 likeCountElement.innerText = '좋아요 수: ' + data.likeCount;
    //
    //                 // 버튼 색상 변경
    //                 const likeButton = document.querySelector('.like-btn');
    //                 likeButton.style.backgroundColor = data.isLiked ? '#dc3545' : '#28a745';
    //
    //                 // 페이지 리로드
    //                 window.location.href = boardUrl + '/' + boardId;
    //             } else {
    //                 alert('게시글 좋아요 실패: ' + data.message);
    //             }
    //         })
    // }

    // 댓글 작성
    function saveComment(boardId) {
        const content = document.getElementById('commentContent').value;
        const image = document.getElementById('commentImageInput');

        if (!content) {
            alert('내용을 입력해주세요.');
            return;
        } else if (content.length > 255) {
            alert('내용은 최대 255자까지만 입력 가능합니다.');
            return;
        }

        let formData = new FormData();
        formData.append("boardId", boardId);
        formData.append("content", content);

        // 파일이 선택되지 않은 경우 빈 파일 전송
        if (image.files.length > 0) {
            formData.append('image', image.files[0]);
        } else {
            // 빈 파일 생성 및 전송
            const emptyFile = new File([''], 'empty.txt', {type: 'text/plain'});
            formData.append('image', emptyFile);
        }

        fetch(commentUrl, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (data.code === 200) {
                    alert('댓글 작성이 성공적으로 이루어졌습니다.');
                    window.location.href = boardUrl + '/' + boardId;
                } else {
                    alert('댓글 작성 실패: ' + data.message);
                }
            })
    }

    // 댓글 수정 입력창
    function showCommentUpdateForm(commentId) {
    var formId = 'commentUpdateForm-' + commentId;
    var form = document.getElementById(formId);
    if (form.style.display === 'none') {
    form.style.display = 'block';
} else {
    form.style.display = 'none';
}
}

    // 댓글 수정
    function updateComment(boardId, commentId) {
    const commentContent = document.getElementById('inputCommentContent-' + commentId).value;
    const commentImage = document.getElementById('inputCommentImage-' + commentId);

    if (!commentContent) {
    alert('내용을 입력해주세요.');
    return;
}

    let formData = new FormData();
    formData.append("content", commentContent);

    // 파일이 선택되지 않은 경우 빈 파일 전송
    if (commentImage.files.length > 0) {
    formData.append('image', commentImage.files[0]);
} else {
    // 빈 파일 생성 및 전송
    const emptyFile = new File([''], 'empty.txt', {type: 'text/plain'});
    formData.append('image', emptyFile);
}

    fetch(commentUrl + '/' + commentId, {
    method: 'PATCH',
    body: formData
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('댓글이 수정되었습니다.');
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('댓글 수정 실패: ' + data.message);
}
})

    // 입력창을 숨기기
    var inputForm = document.getElementById('commentUpdateForm');
    inputForm.style.display = 'none';
}

    // 댓글 삭제
    function deleteComment(boardId, commentId) {
    fetch(commentUrl + '/' + commentId, {method: 'DELETE'})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('댓글이 삭제되었습니다.');
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('댓글 삭제 실패: ' + data.message);
}
});
}

    // 댓글 신고 사유 입력
    function showCommentInputForm(commentId) {
    var formId = 'myCommentForm-' + commentId;
    var form = document.getElementById(formId);
    if (form.style.display === 'none') {
    form.style.display = 'block';
} else {
    form.style.display = 'none';
}
}

    // 댓글 신고
    function reportComment(boardId, commentId) {
    const reason = document.getElementById('commentReason-' + commentId).value;

    const request = {
    reason: reason,
};

    fetch(commentUrl + '/' + commentId + report, {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(request),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert("댓글 신고되었습니다.")
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('댓글 신고 실패: ' + data.message);
}
})

    var inputForm = document.getElementById('commentInputForm');
    inputForm.style.display = 'none';
}

    // 댓글 신고 취소
    function cancelReportComment(commentId) {
    // 사용자에게 재확인 알림창 표시
    var confirmation = confirm('신고 취소 하시겠습니까?');

    // 사용자가 확인을 선택한 경우에만 DELETE 요청 보내기
    if (confirmation) {
    fetch(commentUrl + '/' + commentId + report, {method: 'DELETE'})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('신고 취소가 성공적으로 이루어졌습니다.');
    window.location.href = commentUrl + '/report';
} else {
    alert('신고 취소 실패: ' + data.message);
}
})
}
}

    // 댓글 좋아요
    function likeComment(boardId, commentId) {
    fetch(commentUrl + '/' + commentId + like, {
    method: 'POST',
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    if (data.data.isLiked === true) {
    alert("좋아요를 눌렀습니다.")
} else {
    alert("좋아요를 취소했습니다.")
}
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('댓글 좋아요 실패: ' + data.message);
}
})
}

    // 이미지 미리보기
    function readURL(input) {
    if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
    document.getElementById('preview').src = e.target.result;
};
    reader.readAsDataURL(input.files[0]);
} else {
    document.getElementById('preview').src = "";
}
}

    // 사이드 메뉴바
    function openNav() {
    document.getElementById("sidebar").style.width = "250px";
    document.getElementById("main").style.mginLeft = "250px";
    document.getElementById("openBtn").classList.add("hide");
    document.addEventListener("click", closeNavOutside);
}

    function closeNav() {
    document.getElementById("sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
    document.getElementById("openBtn").classList.remove("hide");
    document.removeEventListener("click", closeNavOutside);
}

    function closeNavOutside(event) {
    var sidebar = document.getElementById("sidebar");
    var openBtn = document.getElementById("openBtn");
    if (event.target !== sidebar && event.target !== openBtn && !sidebar.contains(event.target)) {
    closeNav();
}
}

    function navigateTo(url) {
    closeNav(); // 사이드바를 닫습니다.
    window.location.href = url; // 지정된 URL로 이동합니다.
}

    // 프로필을 보여주는 함수
    function fetchUserProfile(userId) {
    return fetch('/api/users/profile/' + userId, {
    method: 'GET',
}).then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    return {
    nickname: data.data.nickname,
    email: data.data.email,
    profileUrl: data.data.profileUrl,
    score: data.data.score
};
} else {
    throw new Error('프로필 조회 실패: ' + data.message);
}
})
}

    async function showProfile(userId) {
    try {
    const profileInfo = await fetchUserProfile(userId);

    // 모달 컨테이너를 표시하고 프로필 정보를 채움
    const modalContainer = document.getElementById('modal-container');
    const closeBtn = document.getElementById('close-btn');
    const profileImage = document.getElementById('profile-image');
    const score = document.getElementById('score');

    document.getElementById('nickname').textContent = profileInfo.nickname;
    document.getElementById('email').textContent = profileInfo.email;
    profileImage.src = profileInfo.profileUrl;
    if (profileInfo.score < 0) {
    score.src = "https://sappun-walk.s3.ap-northeast-2.amazonaws.com/image/Warned.png"
} else if (profileInfo.score <= 300) {
    score.src = "https://sappun-walk.s3.ap-northeast-2.amazonaws.com/image/Bronze.png"
} else if (profileInfo.score <= 700) {
    score.src = "https://sappun-walk.s3.ap-northeast-2.amazonaws.com/image/Silver.png"
} else if (profileInfo.score <= 1200) {
    score.src = "https://sappun-walk.s3.ap-northeast-2.amazonaws.com/image/Gold.png"
} else {
    score.src = "https://sappun-walk.s3.ap-northeast-2.amazonaws.com/image/Platinum.png"
}

    // 닫기 버튼을 클릭하면 모달을 숨김
    closeBtn.onclick = function () {
    modalContainer.style.display = 'none';
};
    // 모달을 표시
    modalContainer.style.display = 'flex';

} catch (error) {
    // 프로필 조회에 실패한 경우 처리
    console.error('프로필 조회 중 오류 발생:', error);
    alert('프로필 조회에 실패했습니다.');
}
}

    // 모달 열기
    function openModal(imgElement) {
    var modal = document.getElementById("myModal");
    var modalImg = document.getElementById("modalImg");
    modal.style.display = "block";
    modalImg.src = imgElement.src;
}

    // 모달 닫기
    function closeModal(event) {
    var modal = document.getElementById("myModal");
    if (event.target === modal) {
    modal.style.display = "none";
}
}