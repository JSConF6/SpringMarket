$(function() {

    // 카테고리 클릭 시
    $(".category-item").on("click", function() {
       $(".category-item").removeClass("category-active");
       $(this).addClass("category-active");

       // 상품 리스트 가져와서 뿌리기
        $.ajax({
            url : "",
            type: "GET",
        }).done((res) => {
            console.log(res);
        }).fail((err) => {
            console.log(err);
        });
    });

    // 검색 조건 선택 시
    $("#product-select").on("change", function() {
        const val = $(this).val();
        if (val === "title") {
            $(".search-input").attr("placeholder", "제목을 입력해주세요");
        } else {
            $(".search-input").attr("placeholder", "내용을 입력해주세요");
        }
    });
});