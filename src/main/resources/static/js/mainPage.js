

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
                window.location.href = mainPage;
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

    //마우스 커서에 따라 애니메이션 됨
    document.addEventListener("DOMContentLoaded", function () {
    var items = document.querySelectorAll('.gallery-item');

    items.forEach(function (item) {
    item.addEventListener('mousemove', function (e) {
    var x = e.offsetX;
    var y = e.offsetY;
    var rotateY = -1 / 5 * x + 20
    var rotateX = 4 / 30 * y - 20
    item.style = `transform : scale(1.2) perspective(500px) rotateX(${rotateX}deg) rotateY(${rotateY}deg); z-index : 2;`;
});
    item.addEventListener('mouseout', function () {
    item.style = 'transform : scale(1) perspective(500px) rotateY(0deg) rotateX(0deg); z-index : 1;'
})
});
});

    //사진 순위에 따라 색상이 변함
    document.querySelectorAll('.gallery-item').forEach(function (item, index) {
    var overlay = item.querySelector('.img-section');

    overlay.addEventListener('mousemove', function (e) {
    var x = e.offsetX;
    var y = e.offsetY;
    var rotateY = -1 / 5 * x + 20;
    var rotateX = 4 / 30 * y - 20;
    var opacity = x / 50; // 빛나는 정도를 조절하기 위한 opacity 값 변경

    switch (index) {
    case 0:
    overlay.style = `background-position: ${x / 5 + y / 5}%; filter: opacity(${opacity}); brightness(1.1); box-shadow: 0 0 20px gold; background-color: gold;`;
    break;
    case 1:
    overlay.style = `background-position: ${x / 5 + y / 5}%; filter: opacity(${opacity}); brightness(1.1); box-shadow: 0 0 20px silver; background-color: silver;`;
    break;
    case 2:
    overlay.style = `background-position: ${x / 5 + y / 5}%; filter: opacity(${opacity}); brightness(1.1); box-shadow: 0 0 20px #cd7f32; background-color: #cd7f32;`;
    break;
    default:
    break;
}
});

    overlay.addEventListener('mouseout', function () {
    overlay.style = 'filter: opacity(1); box-shadow: none; background-color: initial;';
});
});
