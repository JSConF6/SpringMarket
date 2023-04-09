var emailC = false;
var passwordC = false;
var passwordConfirmC = false;
var nicknameC = false;
var phoneC = false;

$(document).ready(function () {


    $("#email").on("focusout",function () {
        var email = $(this);
        console.log(email.val())

        const regExp =/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;


        if(email.val().match(regExp)){

            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/auth/findEmail',
                dataType: 'json', // 받을 데이터 타입
                data : {email : email.val()},
                success: function(response) {
                    // 요청이 성공했을 때 실행할 코드 작성
                    if(response.code === "000"){
                        email.addClass("is-valid");
                        email.removeClass("is-invalid");
                        emailC = true;
                    }else {
                        email.addClass("is-invalid");
                        email.removeClass("is-valid");
                        emailC = false;
                    }
                },
                error: function(error) {
                    // 요청이 실패했을 때 실행할 코드 작성
                    console.log(error);
                }
            });



        }else{
            email.addClass("is-invalid");
            email.removeClass("is-valid");
            emailC = false;
        }
    })
    $("#password").on("focusout",function () {
        var password = $(this);

        const regExp =  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()_+|{}<>?])[A-Za-z\d~!@#$%^&*()_+|{}<>?]{8,16}$/;
        console.log(password)

        if(password.val().match(regExp)){
            password.addClass("is-valid");
            password.removeClass("is-invalid");
            passwordC = true;
        }else{
            password.addClass("is-invalid");
            password.removeClass("is-valid");
            passwordC = false;
        }
    })
    $("#passwordConfirm").on("focusout",function () {
        var passwordConfirm = $(this);
        console.log(passwordConfirm)
        if(passwordConfirm.val() === $("#password").val()){
            passwordConfirm.addClass("is-valid");
            passwordConfirm.removeClass("is-invalid");
            passwordConfirmC = true;
        }else{
            passwordConfirm.addClass("is-invalid");
            passwordConfirm.removeClass("is-valid");
            passwordConfirmC = false;
        }
    })
    $("#nickname").on("focusout",function () {
        var nickname = $(this);
        console.log(nickname)
        const regExp = /^[가-힣a-zA-Z]{2,}$/;
        if(nickname.val().match(regExp)){
            nickname.addClass("is-valid");
            nickname.removeClass("is-invalid");
            nicknameC = true;
        }else{
            nickname.addClass("is-invalid");
            nickname.removeClass("is-valid");
            nicknameC = false;
        }
    })
    $("#phone").on("focusout",function () {
        var phone = $(this);
        console.log(phone)
        const regExp = /^01([0|1|6|7|8|9])(\d{3}|\d{4})\d{4}$/;
        if(phone.val().match(regExp)){
            phone.addClass("is-valid");
            phone.removeClass("is-invalid");
            phoneC = true;
        }else{
            phone.addClass("is-invalid");
            phone.removeClass("is-valid");
            phoneC = false;
        }
    })

    $("#signin").on("submit", function (e) {
       if (!(emailC === true && passwordC ===true && passwordConfirmC ===true && nicknameC ===true && phoneC ===true)) {
           e.preventDefault();
           alert("필수 입력사항을 모두 입력해주세요");
       }


    });


})



