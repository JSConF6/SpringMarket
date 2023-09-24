var stompClient = null;

$(function() {
    const nickname = $("#nickname").val();
    const roomId = $("#roomId").val();
    const currentUserId = $("#currentUserId").val();

    const sock = new SockJS("http://localhost:8080/ws/chat");
    stompClient = Stomp.over(sock);
    stompClient.connect({}, function(frame) {
        console.log("StompClient Connected : ", frame);
        stompClient.subscribe(`/topic/chat/room/${roomId}`, function(message) {
            const msgSplit = message.body.split("/");
            const senderId = msgSplit[0];
            const msg = msgSplit[1];
            const otherImageFileName = msgSplit[2] != null ? "/upload/" + msgSplit[2] : "";

            if (senderId !== currentUserId) {
                const lastMessageDateContainer = getLastMessageDateContainer();
                const lastMessageDate = $(lastMessageDateContainer).text();

                if (lastMessageDate === getCurrentDate()) {
                    $(lastMessageDateContainer).parent().append(otherMessageItem(otherImageFileName, msg ,getCurrentTime()));
                } else {
                    $(".chat-message-container").append(OtherMessageDateItem(otherImageFileName, msg, getCurrentDate(), getCurrentTime()));
                }

                moveScroll();
            }
        });
    });

    // 채팅 버튼 클릭 시
    $("#chatForm").on("submit", function(e) {
        e.preventDefault();

        const sellerId = $("#sellerId").val();

        const chatSendInput = $(".chat-send-input");

        const message = $(chatSendInput).val();

        const lastMessageDateContainer = getLastMessageDateContainer();
        const lastMessageDate = $(lastMessageDateContainer).text();

        if (message === "") {
            return;
        }

        const data = {
            roomId : roomId,
            senderId : currentUserId,
            message : message,
        };

        stompClient.send("/app/chat/message", {}, JSON.stringify(data));

        // message container 안에 메시지 추가
        if (lastMessageDate === getCurrentDate()) {
            $(lastMessageDateContainer).parent().append(messageItem(message, getCurrentTime()));
        } else {
            $(".chat-message-container").append(messageDateItem(message, getCurrentDate(), getCurrentTime()));
        }

        // 메시지 입력하면 스크롤 맨아래로 옮기기
        moveScroll()

        // 메시지 input 비우기
        $(chatSendInput).val("");
    });

    moveScroll();
});

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("StompClient Disconnected");
}

function messageItem(message, time) {
    return `<div class="ps-3 d-flex justify-content-end me-3 align-content-center mt-2 chat-message-item">
                <span class="text-muted my-chat-date">${time}</span>
                <div class="rounded border ms-2 p-2 h-100 text-end chat-message">
                    <span>${message}</span>
                </div>
            </div>`;
}

function messageDateItem(message, date, time) {
    return `<div class="message-date-container">
                <span class="d-flex justify-content-center mt-3 mb-3 text-muted message-date">${date}</span>
                <div class="ps-3 d-flex justify-content-end me-3 align-content-center mt-2 chat-message-item">
                    <span class="text-muted my-chat-date">${time}</span>
                    <div class="rounded border ms-2 p-2 h-100 text-end chat-message">
                        <span>${message}</span>
                    </div>
                </div>
            </div>`;
}

function otherMessageItem(otherImageFileName, message, time) {
    console.log(otherImageFileName)
    return `<div class="ps-2 d-flex align-items-center mt-2 mb-1 chat-message-item">
                <img src=${otherImageFileName !== "" ? otherImageFileName : "/img/default.PNG"} width="50px" height="50px" class="rounded-circle border border-dark"/>
                <div class="rounded border ms-2 me-2 p-2 h-100 chat-message">
                    <span>${message}</span>
                </div>
                <span class="text-muted me-2 mb-3 chat-date">${time}</span>
            </div>`;
}

function OtherMessageDateItem(otherImageFileName, message, date, time) {
    console.log(otherImageFileName)
    return `<div class="message-date-container">
                <span class="d-flex justify-content-center mt-3 mb-3 text-muted message-date">${date}</span>
                <div class="ps-2 d-flex align-items-center mt-2 mb-1 chat-message-item">
                    <img src=${otherImageFileName !== "" ? otherImageFileName : "/img/default.PNG"} width="50px" height="50px" class="rounded-circle border border-dark"/>
                    <div class="rounded border ms-2 me-2 p-2 h-100 chat-message">
                        <span>${message}</span>
                    </div>
                    <span class="text-muted me-2 mb-3 chat-date">${time}</span>
                </div>
            </div>`;
}

function getCurrentTime() {
    const today = new Date();

    const hours = ('0' + today.getHours()).slice(-2);
    const minutes = ('0' + today.getMinutes()).slice(-2);

    return hours + ":" + minutes;
}

function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

function moveScroll() {
    const chatMessageContainer = $(".chat-message-container");
    $(chatMessageContainer).scrollTop($(chatMessageContainer)[0].scrollHeight);
}

function getLastMessageDateContainer() {
    const messageDateList = $(".message-date-container .message-date");
    const messageDateLength = messageDateList.length;
    return $(messageDateList[messageDateLength - 1]);
}