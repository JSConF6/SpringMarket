$(document).ready(function () {
    const id = $("#id").val();

    $.ajax({
        url : "/product/getProductList/" + id,
        type : "GET",
        dataType: 'json', // 받을 데이터 타입
        data : {id : id}
    }).done((res) => {
        if (res.code === 1) {

            console.log(res.data);
        }
    }).fail((err) => {
        const errRes = err.responseJSON;
        alert(errRes.msg);
    });
});