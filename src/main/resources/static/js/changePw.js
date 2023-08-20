var password = false;
var passwordC = false;

$(document).ready(function () {

    $("#floatingInputPw").on("keyup", function () {
        var pw = $(this);

        const regExp =  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()_+|{}<>?])[A-Za-z\d~!@#$%^&*()_+|{}<>?]{8,16}$/;

        if (pw.val().match(regExp)) {
            password = true;
            pw.addClass("is-valid");
            pw.removeClass("is-invalid");
        } else {
            password = false;
            pw.addClass("is-invalid");
            pw.removeClass("is-valid");

        }
    })

    $("#floatingInputPw2").on("keyup", function () {
        var pw2 = $(this);


        if (pw2.val() === $("#floatingInputPw").val()) {
            passwordC = true;
            pw2.addClass("is-valid");
            pw2.removeClass("is-invalid");
        } else {
            passwordC = false;
            pw2.addClass("is-invalid");
            pw2.removeClass("is-valid");
        }
    })


    $("#changepw").on("submit", function (e) {
        if (!(password === true && passwordC === true)) {
            e.preventDefault();
            alert("필수사항을 입력해주세요");
        } else {
            e.preventDefault()
            // 현재 페이지 URL 가져오기
            var currentUrl = window.location.href;

// URL에서 쿼리 문자열 추출
            var queryString = currentUrl.split('?')[1];

// 쿼리 문자열을 매개변수로 분리
            var params = {};
            if (queryString) {
                var paramPairs = queryString.split('&');
                for (var i = 0; i < paramPairs.length; i++) {
                    var pair = paramPairs[i].split('=');
                    var key = decodeURIComponent(pair[0]);
                    var value = decodeURIComponent(pair[1]);
                    params[key] = value;
                }
            }

// username 매개변수 값 추출
            const username = params.username;
            const formData = new FormData(event.target);
            const pw = formData.get('password');
            console.log(pw);
            console.log(username);
            // API 호출
            /*fetch(`/auth/changePw?username=${username}&password=${pw}`)
                .then(response => response.json())
                .then(data => {
                   alert("비밀번호 변경이 완료되었습니다.")
                })
                .catch(error => {
                    console.error('API 호출 오류:', error);
                    alert('API 호출 중 오류가 발생했습니다.');
                });*/

            $.ajax({
                url: '/auth/changePw',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    username: username,
                    password: pw,
                }),
                success: function(data) {
                    debugger;
                    alert("비밀번호 변경이 완료되었습니다.");
                    location.href = "/auth/signin"
                },
                error: function(error) {
                    console.log('API 호출 오류:', error);
                    alert('API 호출 중 오류가 발생했습니다.');
                }
            });
        }

    });
})



