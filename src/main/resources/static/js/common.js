let alarmSocket = null;

$(function() {
    const isLogin = $("#isLogin").val();

    // 로그인 시 알림 웹소켓을 연결한다.
//    if (isLogin === 'true') {
//        alarmConnectWS();
//        getAlarmCount();
//    }

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

    $(".toast-body").on("click", "#alarmMessage",  function() {
        const link = $(this).data("href");
        const alarmId = $(this).data("id");

        console.log(link)

        $.ajax({
           url : `/alarm/${alarmId}/update`,
           type : "GET"
        }).done((res) => {
            getAlarmCount();
        }).fail((err) => {
            console.log(err);
        });

        location.href = link;
    });
});

function alarmConnectWS() {
    alarmSocket = new WebSocket("ws://localhost:8080/ws/alarm");

    alarmSocket.onopen = function (e) {}

    alarmSocket.onclose = function (e) {}

    alarmSocket.onmessage = async function (message) {
        const msg = message.data;
        const msgSplit = msg.split(",");

        const msgData = `<a class="link-dark text-decoration-none cursor-pointer" id="alarmMessage" data-id="${msgSplit[0]}" data-href="${msgSplit[1]}">${msgSplit[2]}</a>`;

        $(".alarm-toast .toast-body").html(msgData);
        $(".alarm-toast").addClass("show");

        getAlarmCount();

        setTimeout(() => {
            $(".alarm-toast").removeClass("show");
        },5000);
    }
}

function getAlarmCount() {
    $.ajax({
        url : "/alarm/count",
        type : "GET",
    }).done((res) => {
        $("#alarmCount").text(res);
    }).fail((err) => {
        console.log(err);
    });
}