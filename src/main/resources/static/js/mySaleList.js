$(document).ready(function () {
    const id = $("#id").val();
    console.log("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    console.log(id);

    $.ajax({
        url : "/product/getProductList/" + id,
        type : "GET",
        dataType: 'json', // 받을 데이터 타입
        data : {id : id}
    }).done((res) => {
        if (res.code === 1) {
            alert("리스트 호출 완료");

            console.log(res.data);
        }
    }).fail((err) => {
        const errRes = err.responseJSON;
        alert(errRes.msg);
    });
});