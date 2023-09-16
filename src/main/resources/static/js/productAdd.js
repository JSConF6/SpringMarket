$(document).ready(function () {
    const uploadedImages = [];

    $.ajax({
        url: "/product/getCategory",
        type: "GET",
        dataType: 'json', // 받을 데이터 타입

    }).done((res) => {
        if (res.code === 1) {
            console.log(res.data);
            const selectElement = document.getElementById("category");

            selectElement.innerHTML = "";

            res.data.forEach((optionText) => {
                const option = document.createElement("option");
                option.value = optionText.id; // 옵션의 값을 설정합니다.
                option.text = optionText.name;  // 옵션의 텍스트를 설정합니다.
                selectElement.appendChild(option);
            });
        } else {
            console.log(res);
        }
    }).fail((err) => {
        const errRes = err.responseJSON;
        alert(errRes.msg);
    });

    $("#productAdd").on("click", function () {
        const id = $("#id").val();
        const category = $("#category").val();
        const name = $("#name").val();
        const price = $("#price").val();
        const content = $("#floatingContent").val();
        const images = $(".images");
        const imageFiles = new Array();

        console.log(id);
        console.log(name);
        console.log(price);
        console.log(content);
        console.log(category);
        console.log(imageFiles);

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



        const data = new FormData();
        data.append("userId",id);
        data.append("categoryId",category);
        data.append("name",name);
        data.append("price",price);
        data.append("content",content);
        for(let i = 0; i < images.length; i++){
            data.append("imageFiles",images[i].files[0]);
        }




        $.ajax({
            url : "/product/productAdd",
            type : "POST",
            dataType: 'json', // 받을 데이터 타입
            data : data,
            contentType: false,
            processData: false,
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

    let imageCounter = 0; // 이미지 업로더의 개수를 세기 위한 변수
    let imageLength = 0;

    $("#addImage").on("click", function () {
        if (imageLength < 5) { // 이미지 업로더의 최대 개수를 설정
            const container = document.createElement('div');
            container.classList.add('me-4', 'product-img');

            container.innerHTML = `
            <label for="imageUpload${imageCounter}" class="border rounded d-flex flex-column align-items-center justify-content-center link-dark text-decoration-none me-3">
                <img src="/img/개선.PNG" width="80px" height="80px" id="previewImage${imageCounter}" />
                <input type="file" class="images" id="imageUpload${imageCounter}" style="display: none;" accept="image/*" th:onchange="previewFile(${imageCounter})" />
            </label>
            <i class="position-relative fa-solid fa-xmark cursor-pointer product-del-btn" th:onclick="removeImageUploader(this.parentNode)"></i>
        `;

            document.getElementById('imageList').appendChild(container);
            imageCounter++;
            imageLength++;
        } else {
            alert('이미지 업로더는 최대 5개까지 가능합니다.');
        }
    });

    $('#imageList').on('click', '.product-del-btn', function () {
        $(this).parent().remove();
        imageLength--;
    });

    // 파일 선택 시 이미지 미리보기를 업데이트
    $('#imageList').on('change', 'input[type="file"]', function () {
        const imageIndex = $(this).attr('id').replace('imageUpload', '');
        const preview = $(`#previewImage${imageIndex}`)[0];
        const file = this.files[0];


        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
                preview.src = e.target.result;
            };

            reader.readAsDataURL(file);
        } else {
            preview.src = "/img/개선.PNG"; // 기본 이미지를 보여줄 수 있도록 설정
        }
    });


})