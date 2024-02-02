
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

    var isDuplicatedUsername = true;
    var isDuplicatedNickname = true;

    function checkUsername() {
    const username = document.getElementById('username').value;

    // Request DTO 구성
    const usernameVerifyReq = {
    username: username,
};

    fetch('/api/users/username', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(usernameVerifyReq),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    if (data.data.isDuplicated) {
    // 중복된 경우 메시지 표시
    alert('사용 불가능한 아이디입니다.');
    isDuplicatedUsername = true;
} else {
    // 중복되지 않은 경우 메시지 초기화
    alert('사용 가능한 아이디입니다.');
    isDuplicatedUsername = false;
}
} else {
    alert(data.message);
}
})
    .catch(error => {
    console.error('Error checking username:', error);
});
}

    function checkNickname() {
    const nickname = document.getElementById('nickname').value;

    // Request DTO 구성
    const nicknameVerifyReq = {
    nickname: nickname,
};

    fetch('/api/users/nickname', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(nicknameVerifyReq),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    if (data.code === 200) {
    if (data.data.isDuplicated) {
    // 중복된 경우 메시지 표시
    alert('사용 불가능한 닉네임입니다.');
    isDuplicatedNickname = true;
} else {
    // 중복되지 않은 경우 메시지 초기화
    alert('사용 가능한 닉네임입니다.');
    isDuplicatedNickname = false;
}
} else {
    alert(data.message);
}
})
    .catch(error => {
    console.error('Error checking nickname:', error);
});
}

    function updateProfile() {
    var image = document.getElementById('image');
    var username = document.getElementById('username').value;
    var nickname = document.getElementById('nickname').value;

    if (image.files.length === 0 && !username && !nickname) {
    alert('입력값이 없습니다.');
    return;
}

    if (document.getElementById('username').value.trim() !== '' && isDuplicatedUsername) {
    // 입력값이 있고, 중복된 경우
    alert("아이디 중복 확인을 해주세요.");
    return;
} else if (document.getElementById('nickname').value.trim() !== '' && isDuplicatedNickname) {
    // 입력값이 있고, 중복된 경우
    alert("닉네임 중복 확인을 해주세요.");
    return;
}

    let formData = new FormData();
    formData.append("username", username);
    formData.append("nickname", nickname);

    // 파일이 선택되지 않은 경우 빈 파일 전송
    if (image.files.length > 0) {
    formData.append('image', image.files[0]);
} else {
    // 빈 파일 생성 및 전송
    const emptyFile = new File([''], 'empty.txt', {type: 'text/plain'});
    formData.append('image', emptyFile);
}

    // 요청 보내기
    fetch('/api/users/profile', {
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
    alert('프로필 수정이 성공적으로 이루어졌습니다.');
    window.location.href = '/api/users';
} else {
    alert('프로필 수정 실패: ' + data.message);
}
})
    .catch(error => {
    console.error('Error updating profile:', error);
});
}
