$(function() {
    // 패스워드 변경 버튼 클릭 시
    $("#pwChangeBtn").on("click", function() {
        const currentPw = $("#currentPassword").val();
        const changePw = $("#changePassword").val();
        const changePwConfirm = $("#changePasswordConfirm").val();

        if (currentPw === "") {
            alert("현재 비밀번호를 입력해주세요");
            $("#currentPassword").focus();
            return;
        }

        if (changePw === "") {
            alert("변경 비밀번호를 입력해주세요");
            $("#changePassword").focus();
            return;
        }

        if (changePwConfirm === "" || changePw !== changePwConfirm) {
            alert("변경 비밀번호를 확인해주세요");
            $("#changePasswordConfirm").focus();
            return;
        }

        const data = {
            currentPassword : currentPw,
            changePassword : changePw
        };

        $.ajax({
            url : "/mypage/changePassword",
            type : "POST",
            contentType : "application/json;charset=UTF-8",
            data : JSON.stringify(data),
        }).done((res) => {
            if (res.code === 1) {
                alert("비밀번호가 변경 되었습니다. 다시 로그인 해주세요.");
                location.replace("/mypage/passwordChangeSuccess");
            } else {
                console.log(res);
            }
        }).fail((err) => {
            const errRes = err.responseJSON;
            alert(errRes.msg);
        });
    });

    // 탈퇴 버튼 클릭 시
    $("#withdrawBtn").on("click", function() {
       const currentPw = $("#withCurrentPassword").val();

       if (currentPw === "") {
           alert("현재 비밀번호를 입력해주세요");
           $("#withCurrentPassword").focus();
           return;
       }

       if (confirm("정말 탈퇴하시겠습니까?")) {
           console.log("탈퇴 시작");
           $.ajax({
               url : "/mypage/withdraw",
               type : "POST",
               data : {
                   currentPassword : currentPw,
               },
           }).done((res) => {
                if (res.code === 1) {
                   alert("그동안 이용해주셔서 감사합니다.");
                   location.replace("/mypage/withdrawSuccess");
                }
           }).fail((err) => {
               const errRes = err.responseJSON;
               alert(errRes.msg);
           })
       }
    });
});