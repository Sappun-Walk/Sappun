
var isDuplicatedUsername = true;
var isDuplicatedNickname = true;

function checkUsername() {
    const username = document.getElementById('username').value;

    const usernameRegex = /^[a-zA-Z0-9]*$/;
    const usernameLength = username.length;

    if (usernameLength < 4 || usernameLength > 15 || !usernameRegex.test(username)) {
        alert('유저네임은 최소 4글자, 최대 15글자로 입력하고, 영어 대소문자와 숫자만 입력 가능합니다.');
        return;
    }

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

    const nicknameRegex = /^[가-힣a-zA-Z0-9]*$/;
    const nicknameLength = nickname.length;

    if (nicknameLength < 2 || nicknameLength > 10 || !nicknameRegex.test(nickname)) {
        alert('닉네임은 최소 2글자, 최대 10글자로 입력하고, 한글, 영어, 숫자만 입력 가능합니다.');
        return;
    }


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

function signup() {
    // 사용자 이름 또는 패스워드가 비어있을 경우 오류 처리
    let username = document.getElementById('username').value;
    let nickname = document.getElementById('nickname').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let confirmPassword = document.getElementById('confirmPassword').value;
    let image = document.getElementById('image');

    if (!username) {
        alert('username을 입력하세요.');
        return;
    } else if (!nickname) {
        alert('nickname을 입력하세요.');
        return;
    } else if (!email) {
        alert('email을 입력하세요.');
        return;
    } else if (!password) {
        alert('password을 입력하세요.');
        return;
    } else if (!confirmPassword) {
        alert('confirmPassword을 입력하세요.');
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
    formData.append("email", email);
    formData.append("password", password);
    formData.append("confirmPassword", confirmPassword);

    if (image.files.length > 0) {
        const imageFile = image.files[0];

        // 파일 크기가 20MB를 초과하는지 확인합니다.
        if (imageFile.size > 20 * 1024 * 1024) {
            alert('파일 크기가 20MB 제한을 초과합니다. 더 작은 파일을 선택하세요.');
            imageInput.value = ''; // 입력값 초기화
            return;
        }

        resizeAndSend(imageFile, formData);
    } else {
        // 이미지가 없는 경우 바로 요청 보냄
        const emptyFile = new File([''], 'empty.txt', {type: 'text/plain'});
        formData.append('image', emptyFile);

        sendRequest(formData);
    }

    function resizeAndSend(file, formData) {
        resizeImage(file)
            .then(resizedImage => {
                formData.append('image', resizedImage);
                sendRequest(formData);
            })
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
            img.onerror = () => {
                reject(new Error('이미지를 읽는 중 오류가 발생했습니다.'));
            };
            img.src = URL.createObjectURL(file);
        });
    }


    function sendRequest(formData) {
        // 요청 보내기
        fetch('/api/users/signup', {
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
                    // 서버 응답이 200이면 로그인 성공
                    alert('회원가입이 성공적으로 이루어졌습니다.');
                    window.location.href = '/api/users/login-page';
                } else {
                    // 서버 응답이 200이 아니면 로그인 실패
                    alert('회원가입 수정 실패: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error signup:', error);
                // 오류 발생 시의 로직을 추가할 수 있습니다.
            });
    }
}

document.getElementById('image').addEventListener('change', function (e) {
    var files = e.target.files;
    var numFiles = files.length;

    // 파일 수가 1개를 초과하는지 확인합니다.
    if (numFiles > 1) {
        alert('최대 1개의 파일을 선택하세요.');
        this.value = ''; // 입력값 초기화
        return;
    }

    for (var i = 0; i < numFiles; i++) {
        var file = files[i];

        // 파일 크기가 20MB를 초과하는지 확인합니다.
        if (file.size > 20 * 1024 * 1024) {
            alert('파일 크기가 20MB 제한을 초과합니다. 더 작은 파일을 선택하세요.');
            this.value = ''; // 입력값 초기화
            return;
        }

        // 이미지 미리보기
        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('preview').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});