
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
    var mapImage = '';

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

    // 게시글 작성 페이지 경유지 추가
    function addStopover() {
    // 현재 경유지 입력란 개수 확인
    var stopoverContainer = document.getElementById('stopovers-container');
    var stopoverSections = stopoverContainer.getElementsByClassName('stopover-section');

    if (stopoverSections.length < 5) {
    // 5개 미만일 때만 새로운 경유지 입력란 생성
    var newStopoverSection = document.createElement('div');
    newStopoverSection.classList.add('stopover-section');


    var input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', 'stopover');

    // 새로운 경유지 입력란에 삭제 버튼 추가
    var removeStopoverButton = document.createElement('button');
    removeStopoverButton.setAttribute('type', 'button');
    removeStopoverButton.textContent = '➖';
    removeStopoverButton.classList.add('btn-stopover'); // Adding the class
    removeStopoverButton.addEventListener('click', function () {
    addRemoveStopover(newStopoverSection);
});

    newStopoverSection.appendChild(input);
    newStopoverSection.appendChild(removeStopoverButton);

    // 생성된 입력란을 컨테이너에 추가
    stopoverContainer.appendChild(newStopoverSection);
} else {
    alert('경유지는 최대 5개까지만 입력 가능합니다.');
}
}

    // 공통 함수: 삭제 버튼이 속한 div 요소를 찾아서 삭제
    function removeFromContainer(element) {
    var container = document.getElementById('stopovers-container');
    container.removeChild(element);
}

    // 이전 입력된 경유지 입력란 삭제 함수
    function removeStopover(button) {
    var stopoverSection = button.parentNode;
    removeFromContainer(stopoverSection);
}

    // 추가 입력할 경유지 입력란 삭제 함수
    function addRemoveStopover(stopoverSection) {
    removeFromContainer(stopoverSection);
}

    //게시글 작성
    function saveBoard() {
    var stopoverContainer = document.getElementById('stopovers-container');
    var stopoverInputs = stopoverContainer.querySelectorAll('.stopover-section input[name="stopover"]');
    var region = document.getElementById('region').value;
    var title = document.getElementById('title').value;
    var departure = document.getElementById('departure').value;
    var destination = document.getElementById('destination').value;
    var content = document.getElementById('content').value;
    var imageInputs = document.getElementById('image-multiple');

    if (!title) {
    alert("제목을 입력해주세요.");
    return;
} else if(title.length > 20) {
    alert("제목은 최대 20자까지만 입력 가능합니다.");
    return;
} else if(!departure) {
    alert("출발지를 입력해주세요.");
    return;
} else if (!destination) {
    alert("목적지를 입력해주세요.");
    return;
} else if (!content) {
    alert("내용을 입력해주세요.");
    return;
} else if (content.length > 255) {
    alert('내용은 최대 255자까지만 입력 가능합니다.');
    return;
}

    if (mapImage === '') {
    alert("지도를 캡쳐해주세요!");
    return
}

    function resizeImage(file) {
    return new Promise((resolve, reject) => {
    const img = new Image();
    img.onload = () => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    const MAX_WIDTH = 800;
    const MAX_HEIGHT = 600;
    let width = img.width;
    let height = img.height;

    if (width > height) {
    if (width > MAX_WIDTH) {
    height *= MAX_WIDTH / width;
    width = MAX_WIDTH;
}
} else {
    if (height > MAX_HEIGHT) {
    width *= MAX_HEIGHT / height;
    height = MAX_HEIGHT;
}
}

    canvas.width = width;
    canvas.height = height;
    ctx.drawImage(img, 0, 0, width, height);
    canvas.toBlob(blob => {
    blob.name = file.name;
    resolve(blob);
}, file.type);
};
    img.onerror = reject;
    img.src = URL.createObjectURL(file);
});
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
    formData.append('image', mapImage);

    const resizePromises = [];
    for (let i = 0; i < imageInputs.files.length; i++) {
    resizePromises.push(
    resizeImage(imageInputs.files[i]).then(resizedImage => {
    formData.append('photoImages', resizedImage);
})
    );
}

    Promise.all(resizePromises).then(() => {
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
    console.error('Error:', error);
});
});
}

    function resizeImage(file) {
    return new Promise((resolve, reject) => {
    const img = new Image();
    img.onload = () => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    const MAX_WIDTH = 800;  // 리사이징할 최대 가로 크기
    const MAX_HEIGHT = 600; // 리사이징할 최대 세로 크기
    let width = img.width;
    let height = img.height;

    // 이미지 비율 유지하면서 사이즈 조정
    if (width > height) {
    if (width > MAX_WIDTH) {
    height *= MAX_WIDTH / width;
    width = MAX_WIDTH;
}
} else {
    if (height > MAX_HEIGHT) {
    width *= MAX_HEIGHT / height;
    height = MAX_HEIGHT;
}
}

    canvas.width = width;
    canvas.height = height;
    ctx.drawImage(img, 0, 0, width, height);
    canvas.toBlob(resolve, file.type);
};
    img.onerror = reject;
    img.src = URL.createObjectURL(file);
});
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

    //지도 관련 Script
    let map, drawingManager, polyline;

    function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 37.5665, lng: 126.9780},
        zoom: 12,
    });

    // Drawing Manager 초기화
    drawingManager = new google.maps.drawing.DrawingManager({
    drawingControl: false,
    drawingMode: google.maps.drawing.OverlayType.POLYLINE,
});
    drawingManager.setMap(map);

    // 선이 그려질 때 이벤트 리스너 등록
    google.maps.event.addListener(drawingManager, 'overlaycomplete', function (event) {
    if (event.type === google.maps.drawing.OverlayType.POLYLINE) {
    // 새로운 선이 그려질 때마다 기존의 선을 모두 제거
    clearPolylines();
    polyline = event.overlay;
}
});

    // 선 그리기 버튼 클릭 시 이벤트 핸들러
    document.getElementById('drawLine').addEventListener('click', function () {
    drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYLINE);
});

    // 이미지 캡처 버튼 클릭 시 이벤트 핸들러
    document.getElementById('captureImage').addEventListener('click', function () {
    captureMapImage();
});

    // 현재 위치 찾기 버튼 클릭 시 이벤트 핸들러
    document.getElementById('locateCurrentPosition').addEventListener('click', function () {
    locateCurrentPosition();
});
}

    function clearPolylines() {
    if (polyline) {
    polyline.setMap(null);
    polyline = null;
}
}

    function captureMapImage() {

    // 만약 캡처를 다시 하는 경우 이전 데이터를 삭제함
    if(mapImage !== '') {
    // 서버로 API 요청 보내기
    fetch(`/api/boards/map?mapImage=${encodeURIComponent(mapImage)}`, {
    method: 'DELETE',
})
    .then(response => {
    if (!response.ok) {
    throw new Error(`HTTP error! Status: ${response.status}`);
}
    // API 응답을 문자열로 변환
    return response.text();
})
    .catch(error => {
    console.error('Error saving map image:', error);
});
}

    if (!polyline) {
    alert('산책로를 먼저 그려주세요.');
    return;
}

    const staticMapOptions = {
    center: polyline.getPath().getArray()[0].toJSON(),
    zoom: map.getZoom(),
    size: '500x500',
    maptype: 'roadmap',
};

    // 현재까지 그려진 선을 추가
    const path = google.maps.geometry.encoding.encodePath(polyline.getPath());
    staticMapOptions.path = `color:0x0000ff|weight:5|enc:${path}`;

    const imageUrl = `https://maps.googleapis.com/maps/api/staticmap?${serializeObject(staticMapOptions)}&key=AIzaSyCFn4liuOJQ1QaeiKk0gdA5G3tA6Sa9vwg`;

    fetch(imageUrl)
    .then(response => response.blob())
    .then(blob => {
    // Blob을 FormData에 추가
    const formData = new FormData();
    formData.append('mapImage', blob);

    // 서버로 API 요청 보내기
    fetch('/api/boards/map', {
    method: 'POST',
    body: formData,
})
    .then(response => {
    if (!response.ok) {
    throw new Error(`HTTP error! Status: ${response.status}`);
}
    // API 응답을 문자열로 변환
    return response.text();
})
    .then(data => {
    mapImage = data;
})
    .catch(error => {
    console.error('Error saving map image:', error);
});
});

    // 캡처 결과를 화면에 표시
    const capturedImageDiv = document.getElementById('capturedImage');
    capturedImageDiv.innerHTML = `<img src="${imageUrl}" style="border-radius: 15px;" alt="Captured Map">`;
}

    function locateCurrentPosition() {
    if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
    const currentPos = {
    lat: position.coords.latitude,
    lng: position.coords.longitude
};

    map.setCenter(currentPos);
}, function () {
    alert('현재 위치를 가져올 수 없습니다.');
});
} else {
    alert('브라우저가 Geolocation API를 지원하지 않습니다.');
}
}

    // 객체를 쿼리 문자열로 직렬화하는 함수
    function serializeObject(obj) {
    return Object.keys(obj)
    .map(key => `${key}=${encodeURIComponent(obj[key])}`)
    .join('&');
}

    function handleRegionChange() {
    // 선택된 값 가져오기
    var selectedValue = document.getElementById("region").value;
    var newLatLng = {lat: 37.5665, lng: 126.9780}; // 서울특별시 위경도
    var newZoomLevel = 12;

    if(selectedValue === "REGION1"){
    newLatLng = {lat: 37.5665, lng: 126.9780}; // 서울특별시 위경도
}
    else if(selectedValue === "REGION2"){
    newLatLng = {lat: 35.1379, lng: 129.0556}; // 부산특별시 위경도
    newZoomLevel = 11;
}
    else if(selectedValue === "REGION3"){
    newLatLng = {lat: 35.8704, lng: 128.5910}; // 대구광역시 위경도
}
    else if(selectedValue === "REGION4"){
    newLatLng = {lat: 37.4489, lng: 126.7031}; // 인천광역시 위경도
}
    else if(selectedValue === "REGION5"){
    newLatLng = {lat: 35.1595 , lng: 126.8526}; // 광주광역시 위경도
}
    else if(selectedValue === "REGION6"){
    newLatLng = {lat: 35.5383, lng: 129.3113}; // 울산광역시 위경도
}
    else if(selectedValue === "REGION7"){
    newLatLng = {lat: 36.5040, lng: 127.2494}; // 세종특별자치시 위경도
}
    else if(selectedValue === "REGION8"){
    newLatLng = {lat: 37.2636, lng: 127.0286}; // 경기도 수원시 위경도
    newZoomLevel = 10;
}
    else if(selectedValue === "REGION9"){
    newLatLng = {lat: 37.8222, lng: 128.1550}; // 강원특별자치도 춘천시 위경도
    newZoomLevel = 9;
}
    else if(selectedValue === "REGION10"){
    newLatLng = {lat: 36.8001, lng: 127.7270}; // 충청북도 위경도
    newZoomLevel = 10;
}
    else if(selectedValue === "REGION11"){
    newLatLng = {lat: 36.5184, lng: 126.8000}; // 충청남도 위경도
    newZoomLevel = 9;
}
    else if(selectedValue === "REGION12"){
    newLatLng = {lat: 35.7175, lng: 127.1530}; // 전북특별자치도 위경도
    newZoomLevel = 9;
}
    else if(selectedValue === "REGION13"){
    newLatLng = {lat: 34.8169, lng: 126.9794}; // 전라남도 위경도
    newZoomLevel = 9;
}
    else if(selectedValue === "REGION14"){
    newLatLng = {lat: 36.5759, lng: 128.5059}; // 경상북도 위경도
    newZoomLevel = 9;
}
    else if(selectedValue === "REGION15"){
    newLatLng = {lat: 35.2279, lng: 128.6811}; // 경상남도 위경도
    newZoomLevel = 10;
}
    else if(selectedValue === "REGION16"){
    newLatLng = {lat: 33.4312, lng: 126.5437}; // 제주특별자치도 위경도
    newZoomLevel = 10;
}
    moveMapToLocation(newLatLng, newZoomLevel)
}
    function moveMapToLocation(newLatLng, newZoomLevel) {
    if (map) {
    // Map 객체가 존재하면 중심과 줌 레벨을 업데이트하여 지도 이동
    map.setCenter(newLatLng);
    map.setZoom(newZoomLevel);
} else {
    console.error("지도가 활성화 되어 있지 않습니다!");
}
}

    document.getElementById('image-multiple').addEventListener('change', function(e) {
    var files = e.target.files;
    var numFiles = files.length;
    var previewContainer = document.getElementById('previewContainer');

    // 파일 수가 4개를 초과하는지 확인합니다.
    if (numFiles > 4) {
    alert('최대 4개의 파일을 선택하세요.');
    this.value = ''; // 입력값 초기화
    return;
}

    // 이전 미리보기를 지웁니다.
    previewContainer.innerHTML = '';

    for (var i = 0; i < numFiles; i++) {
    var file = files[i];

    // 파일 크기가 10MB를 초과하는지 확인합니다.
    if (file.size > 10 * 1024 * 1024) {
    alert('파일 크기가 10MB 제한을 초과합니다. 더 작은 파일을 선택하세요.');
    this.value = ''; // 입력값 초기화
    return;
}

    var reader = new FileReader();
    reader.onload = (function(file) {
    return function(e) {
    var img = new Image();
    img.src = e.target.result;
    img.onload = function() {
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');

    // 이미지 너비가 800px를 초과하는지 확인하여 리사이징합니다.
    if (img.width > 800) {
    var scaleFactor = 800 / img.width;
    canvas.width = 800;
    canvas.height = img.height * scaleFactor;
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
} else {
    canvas.width = img.width;
    canvas.height = img.height;
    ctx.drawImage(img, 0, 0);
}

    // 캔버스를 PNG 형식으로 변환합니다.
    var resizedImageData = canvas.toDataURL('image/png');

    // 리사이징된 이미지를 미리보기로 표시합니다.
    var previewImage = document.createElement('img');
    previewImage.src = resizedImageData;
    previewImage.classList.add('preview-image');
    previewContainer.appendChild(previewImage);

    // 모달을 열기 위한 클릭 이벤트를 추가합니다.
    previewImage.addEventListener('click', function() {
    document.getElementById('modalImg').src = this.src;
    document.getElementById('modal').style.display = 'block';
});

    // 리사이징된 이미지를 image-multiple에 추가합니다.
    var formData = new FormData();
    formData.append('resizedImages', file);
}
};
})(file);

    reader.readAsDataURL(file);
}
});

    // 이미지 외부를 클릭할 때 모달을 닫습니다.
    window.addEventListener('click', function(e) {
    var modal = document.getElementById('modal');
    if (e.target == modal) {
    modal.style.display = 'none';
}
});

    // 이미지 데이터를 Base64 형식으로 가져오는 함수
    function getBase64Image(img) {
    var canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;

    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);

    var dataURL = canvas.toDataURL('image/png');
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, '');
}
