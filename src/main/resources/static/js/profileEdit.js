$(function() {
    // 프로필 수정 버튼 클릭
    $("#profileEditBtn").on("click", function(e) {
        e.preventDefault();

        const formData = new FormData();
        formData.append("username", $("input[name='username']").val());
        formData.append("nickname", $("input[name='nickname']").val());
        formData.append("path", $("#imgPath").val());
        formData.append("file", $("#imgFile")[0].files[0]);

        // ajax 처리
        $.ajax({
            url : "/mypage/profile/edit",
            type : "POST",
            encType : 'multipart/form-data',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
        }).done((res) => {
            if (res === 1) {
                alert("프로필 수정이 완료되었습니다.");
                location.reload();
            } else {
                alert("프로필 수정에 실패했습니다.");
                location.reload();
            }
        }).fail((err) => {
            alert("프로필 수정에 실패했습니다.");
            location.reload();
        })
    });

    // 카메라 버튼 클릭
    $("#profileImgBtn").on("click", function() {
        $("#imgFile").click();
    });

    // 이미지 변경
    $("#imgFile").on("change", function(e) {
        const imgFile = e.target.files[0];
        const imgPath = `/img/${imgFile.name}`;
        const previewImg = URL.createObjectURL(imgFile);

        $("#profileImg").attr("src", previewImg);
        $("#imgPath").val(imgPath);
    });
});