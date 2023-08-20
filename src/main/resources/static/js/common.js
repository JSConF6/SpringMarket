$(function() {

    // 모달 닫기 버튼 클릭 시 해당 모달안에 있는 모든 input 초기화
    $(".modal-close").on("click", function() {
        const modalId = $(this).data("modal-id");
        $("#" + modalId).find("input").val("");
    });

    // 비밀번호 보기 클릭 시
    $(".pw-check").on("click", function() {
       const type = $(this).siblings("input").attr("type");

       if (type === "password") {
           $(this).siblings("input").attr("type", "text");
       } else {
           $(this).siblings("input").attr("type", "password");
       }
    });
});