var phone_number = false;
var username = false;

$(document).ready(function () {

    $("#floatingInputId").on("keyup",function () {
        var email = $(this);

        if(email === ""){
            username = false;
            email.addClass("is-invalid");
            email.removeClass("is-valid");
        }else{
            username = true;
            email.addClass("is-valid");
            email.removeClass("is-invalid");
        }
    })

    $("#floatingInputPhone").on("keyup",function () {
        var phone = $(this);

        if(phone === ""){
            phone_number = false;
            phone.addClass("is-invalid");
            phone.removeClass("is-valid");
        }else{
            phone_number = true;
            phone.addClass("is-valid");
            phone.removeClass("is-invalid");
        }
    })


    $("#findpw").on("submit", function (e) {
       if (!(phone_number === true && username === true)) {
           e.preventDefault();
           alert("필수사항을 입력해주세요");
       }else{
           const formData = new FormData(event.target);
               const userPhone = formData.get('phone_number');
               const userName = formData.get('username');
               console.log(userPhone)
               // API 호출
               fetch(`/auth/findUserPw?phone_number=${userPhone}&username=${userName}`)
                   .then(response => response.json())
                   .then(data => {
                       // API 응답 데이터 처리
                       if (data.code === "200") {
                           if(confirm("비밀번호를 변경 하시겠습니까?")){
                               location.href=`/auth/changePw?username=${userName}`
                           }
                       } else {
                           alert('사용자를 찾을 수 없습니다.');
                       }
                   })
                   .catch(error => {
                       console.error('API 호출 오류:', error);
                       alert('API 호출 중 오류가 발생했습니다.');
                   });
       }

    });
})



