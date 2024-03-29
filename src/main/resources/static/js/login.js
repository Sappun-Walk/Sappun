
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


    //좋아요, 최신순 정렬기능
    function changeSortAndPage(sortBy) {
    var currentUrl = window.location.href;
    var updatedUrl;

    // 정렬 기준이 이미 존재하는 경우
    if (currentUrl.includes('&sortBy=')) {
    updatedUrl = currentUrl.replace(/&sortBy=[^&]*/, '&sortBy=' + sortBy);
} else {
    // 정렬 기준이 존재하지 않는 경우
    if (currentUrl.includes('?')) {
    updatedUrl = currentUrl + '&sortBy=' + sortBy;
} else {
    updatedUrl = currentUrl + '?sortBy=' + sortBy;
}
}

    // size 파라미터가 이미 존재하는 경우
    if (currentUrl.includes('&size=')) {
    updatedUrl = updatedUrl.replace(/&size=[^&]*/, '&size=8'); // 1 대신 적절한 값을 사용
} else {
    // size 파라미터가 존재하지 않는 경우
    if (currentUrl.includes('?')) {
    updatedUrl = updatedUrl + '&size=8'; // 1 대신 적절한 값을 사용
} else {
    updatedUrl = updatedUrl + '?size=8'; // 1 대신 적절한 값을 사용
}
}

    // 새로운 URL로 페이지 이동
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

    // 비밀번호 수정
    function onPasswordUpdate() {
    let prePassword = $('#prePassword').val();
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();

    if (!prePassword || !newPassword || !confirmPassword) {
    alert('공란을 빠짐없이 입력해주세요.');
    return;
}

    const pwUpdateReq = {
    prePassword: prePassword,
    newPassword: newPassword,
    confirmPassword: confirmPassword
};

    fetch(updatePassword, {
    method: 'PATCH',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(pwUpdateReq),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('비밀번호 변경이 이루어졌습니다.');
    window.location.href = userUrl;
} else {
    alert('비밀번호 변경 실패: ' + data.message);
}
})
}

    // 회원 탈퇴
    function deleteUser() {
    var confirmation = confirm('정말로 회원 탈퇴하시겠습니까?');

    // 사용자가 확인을 선택한 경우에만 DELETE 요청 보내기
    if (confirmation) {
    fetch(userUrl, {method: 'DELETE'})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    alert('회원 탈퇴가 성공적으로 이루어졌습니다.');
    window.location.href = mainPage;
} else {
    alert('회원 탈퇴 실패: ' + data.message);
}
})
}
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

    // 게시글 작성 페이지 경유지 추가
    function addStopover() {
    // 현재 경유지 입력란 개수 확인
    var stopoverContainer = document.getElementById('stopovers-container');
    var stopoverSections = stopoverContainer.getElementsByClassName('stopover-section');

    if (stopoverSections.length < 5) {
    // 5개 미만일 때만 새로운 경유지 입력란 생성
    var newStopoverSection = document.createElement('div');
    newStopoverSection.classList.add('stopover-section');

    var label = document.createElement('label');
    label.setAttribute('for', 'stopover');
    label.textContent = '경유지:';

    var input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', 'stopover');

    newStopoverSection.appendChild(label);
    newStopoverSection.appendChild(input);

    // 생성된 입력란을 컨테이너에 추가
    stopoverContainer.appendChild(newStopoverSection);
} else {
    alert('경유지는 최대 5개까지만 입력 가능합니다.');
}
}

    //게시글 작성
    function saveBoard() {
    // 경유지 입력란의 값을 가져오는 함수
    var stopoverContainer = document.getElementById('stopovers-container');
    var stopoverInputs = stopoverContainer.querySelectorAll('.stopover-section input[name="stopover"]');

    var region = document.getElementById('region').value;
    var title = document.getElementById('title').value;
    var departure = document.getElementById('departure').value;
    var destination = document.getElementById('destination').value;
    var content = document.getElementById('content').value;
    var image = document.getElementById('image');

    if (!title && !departure && !destination && !content) {
    alert('입력을 다시 확인해주세요.');
    return;
}

    let formData = new FormData();
    formData.append("region", region);
    formData.append("title", title);
    formData.append("departure", departure);
    stopoverInputs.forEach(function (input) {
    formData.append("stopover", input.value);
});
    formData.append("destination", destination);
    formData.append("content", content);

    // 파일이 선택되지 않은 경우 알림창이 뜨도록 함
    if (image.files.length > 0) {
    formData.append('image', image.files[0]);
} else {
    alert("파일을 첨부해주세요!");
}

    // 요청 보내기
    fetch(boardUrl, {
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
    alert('게시글 작성이 성공적으로 이루어졌습니다.');
    window.location.href = boardUrl + '/region?region=' + region;
} else {
    alert('게시글 작성 실패: ' + data.message);
}
})
    .catch(error => {
    console.error('Error updating profile:', error);
});
}

    // 게시글 수정
    function updateBoard(boardId) {
    // 경유지 입력란의 값을 가져오는 함수
    var stopoverContainer = document.getElementById('stopovers-container');
    var stopoverInputs = stopoverContainer.querySelectorAll('.stopover-section input[name="stopover"]');

    var region = document.getElementById('region').value;
    var title = document.getElementById('title').value;
    var departure = document.getElementById('departure').value;
    var destination = document.getElementById('destination').value;
    var content = document.getElementById('content').value;
    var image = document.getElementById('image');

    if (!title && !departure && !destination && !content) {
    alert('입력을 다시 확인해주세요.');
    return;
}

    let formData = new FormData();
    formData.append("region", region);
    formData.append("title", title);
    formData.append("departure", departure);
    stopoverInputs.forEach(function(input) {
    formData.append("stopover", input.value);
});
    formData.append("destination", destination);
    formData.append("content", content);

    // 파일이 선택되지 않은 경우 빈 파일 전송
    if (image.files.length > 0) {
    formData.append('image', image.files[0]);
} else {
    // 빈 파일 생성 및 전송
    const emptyFile = new File([''], 'empty.txt', {type: 'text/plain'});
    formData.append('image', emptyFile);
}

    // 요청 보내기
    fetch(boardUrl + '/' + boardId, {
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
    alert('게시글 수정이 성공적으로 이루어졌습니다.');
    window.location.href = boardUrl + '/' + boardId;
} else {
    alert('게시글 수정 실패: ' + data.message);
}
})
    .catch(error => {
    console.error('Error updating profile:', error);
});
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
    inputForm.style.display = 'block';
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
                window.location.href = boardUrl + '/' + boardId;
            } else {
                alert('게시글 좋아요 실패: ' + data.message);
            }
        })
}

    // 댓글 작성
    function saveComment(boardId) {
    const content = document.getElementById('commentContent').value;
    const image = document.getElementById('commentImageInput');

    if (!content) {
    alert('내용을 입력해주세요.');
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
    function showCommentUpdateForm() {
    var inputForm = document.getElementById('commentUpdateForm');
    inputForm.style.display = 'block';
}

    // 댓글 수정
    function updateComment(boardId, commentId) {
    const commentContent = document.getElementById('inputCommentContent').value;
    const commentImage = document.getElementById('inputCommentImage');

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
    alert('댓글 수정이 성공적으로 이루어졌습니다.');
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
                alert('댓글 삭제가 성공적으로 이루어졌습니다.');
                window.location.href = boardUrl + '/' + boardId;
            } else {
                alert('댓글 삭제 실패: ' + data.message);
            }
        });
}

    // 댓글 신고 사유 입력
    function showCommentInputForm() {
    var inputForm = document.getElementById('commentInputForm');
    inputForm.style.display = 'block';
}

    // 댓글 신고
    function reportComment(boardId, commentId) {
    const reason = document.getElementById('commentReason').value;

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


