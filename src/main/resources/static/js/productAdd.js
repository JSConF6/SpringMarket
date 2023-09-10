
$(document).ready(function () {

    $("#productAdd").on("click", function() {
        const id = $("#id").val();
        const category = $("#category").val();
        const name = $("#name").val();
        const price = $("#price").val();
        const content = $("#floatingContent").val();

        console.log(id);
        console.log(name);
        console.log(price);
        console.log(content);
        console.log(category);

        if (name === "") {
            alert("제목을 입력해주세요");
            $("#name").focus();
            return;
        }
        if (category === "") {
            alert("카테고리를 선택해주세요");
            $("#category").focus();
            return;
        }
        if (price === "") {
            alert("가격을 입력해주세요");
            $("#price").focus();
            return;
        }
        if (content === "") {
            alert("내용을 입력해주세요");
            $("#floatingContent").focus();
            return;
        }

        const data = {
            userId: id,
            categoryId: category,
            name: name,
            price: price,
            content: content
        }

        $.ajax({
            url : "/product/productAdd",
            type : "POST",
            dataType: 'json', // 받을 데이터 타입
            data : data
        }).done((res) => {
            if (res.code === 1) {
                alert("상품이 등록되었습니다.");
                location.href = "/mypage/mySaleList"
            } else {
                console.log(res);
            }
        }).fail((err) => {
            const errRes = err.responseJSON;
            alert(errRes.msg);
        });

    });

})