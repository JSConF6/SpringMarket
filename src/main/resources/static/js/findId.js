var phone_number = false;

$(document).ready(function () {


    $("#floatingInputPhone").on("focusout",function () {
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


    $("#findid").on("submit", function (e) {
       if (!(phone_number === true )) {
           e.preventDefault();
           alert("전화번호를 입력해주세요");
       }else{
           const formData = new FormData(event.target);
               const userPhone = formData.get('phone_number');
               console.log(userPhone)
               // API 호출
               fetch(`/auth/findUserEmail?phone_number=${userPhone}`)
                   .then(response => response.json())
                   .then(data => {
                       // API 응답 데이터 처리
                       if (data.code === "200") {
                           alert(`사용자 이메일: ${data.username}`);
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



