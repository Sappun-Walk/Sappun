
    function onPasswordUpdate() {
    let prePassword = $('#prePassword').val();
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    // 패스워드 입력란이 비어있을 경우 오류 처리
    if (!prePassword || !newPassword || !confirmPassword) {
    alert('공란을 빠짐없이 입력해주세요.');
    return;
}
    // Request DTO 구성
    const pwUpdateReq = {
    prePassword: prePassword,
    newPassword: newPassword,
    confirmPassword: confirmPassword
};
    fetch('/api/users/profile/password', {
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
    // 서버 응답이 200이면 로그인 성공
    alert('비밀번호 변경이 이루어졌습니다.');
    window.location.href = '/api/users';
} else {
    // 서버 응답이 200이 아니면 로그인 실패
    alert('비밀번호 변경이 실패했습니다: ' + data.code);
}
})
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