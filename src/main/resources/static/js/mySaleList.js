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
            const productList = res.data;

            const container = $(".container.mypage-list");

            productList.forEach(product => {
                const item = $("<div></div>")
                    .addClass("d-flex align-items-center border-bottom mypage-list-item")
                    .html(`
                <img src="${product.filePath}" width="150px" height="150px" class="border border-dark me-3"/>
                <div class="flex-column">
                    <div class="d-flex justify-content-between align-items-baseline mb-4">
                        <h2>${product.name}</h2>
                        <div class="dropdown-center">
                            <a class="dropdown-toggle link-dark cursor-pointer" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fa-solid fa-ellipsis-vertical fa-2xl"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item cursor-pointer done" data-productId="${product.id}">거래완료</a></li>
                                <li><a class="dropdown-item cursor-pointer modify" data-productId="${product.id}">게시물 수정</a></li>
                                <li><a class="dropdown-item cursor-pointer delete" data-productId="${product.id}">삭제</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between align-items-baseline text-center">
                        <div>
                            <span class="badge text-bg-dark fs-6 me-3">${product.orderStatus}</span>
                            <span class="fs-4">${product.price}원</span>
                        </div>
                        <div class="d-flex">
                            <span class="d-flex align-items-center me-2">
                                <i class="fa-sharp fa-regular fa-comment me-1"></i>${product.comments}
                            </span>
                            <span class="d-flex align-items-center">
                                <i class="fa-regular fa-heart me-1"></i>${product.likes}
                            </span>
                        </div>
                    </div>
                </div>
            `);

                // 생성한 요소를 컨테이너에 추가합니다.
                container.append(item);
            });

        }
    }).fail((err) => {
        const errRes = err.responseJSON;
        alert(errRes.msg);
    });

    $(document).on("click", ".mypage-list-item .delete", function (){
        const productId = $(this).data("productid")

        if(confirm("상품을 삭제하시겠습니까?")){
            $.ajax({
                url : "/product/productDelete",
                type : "DELETE",
                data : {id : productId}
            }).done((res) => {
                alert("상품이 삭제되었습니다.")
                location.reload();
            });
        }


    })

    $(document).on("click", ".mypage-list-item .modify", function (){
        const productId = $(this).data("productid")

        console.log(productId);
    })

    $(document).on("click", ".mypage-list-item .done", function (){
        const productId = $(this).data("productid")

        console.log(productId);
    })

});