var emailC = false;
var passwordC = false;

$(document).ready(function () {


    $("#login_id").on("keyup",function () {
        var email = $(this);
        console.log(email.val())

        const regExp =/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;


        if(email.val().match(regExp)){
            email.addClass("is-valid");
            email.removeClass("is-invalid");
            emailC= true;
        }else{
            email.addClass("is-invalid");
            email.removeClass("is-valid");
            emailC = false;
        }
    })
    $("#member_password").on("keyup",function () {
        var password = $(this);

        if(password.val() !== null && password.val() !== ""){
            password.addClass("is-valid");
            password.removeClass("is-invalid");
            passwordC = true;
        }else{
            password.addClass("is-invalid");
            password.removeClass("is-valid");
            passwordC = false;
        }
    })

    $("#signin").on("submit", function (e) {
       if (!(emailC === true && passwordC ===true )) {
           e.preventDefault();
           alert("필수 입력사항을 모두 입력해주세요");
       }

    });
})



