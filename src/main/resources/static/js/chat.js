$(function() {
    const nickname = $("#nickname").val();
    const roomId = $("#roomId").val();

    const chatSocket = new WebSocket(`ws://localhost:8080/ws/chat/${roomId}`);

    chatSocket.onopen = function() {}

    chatSocket.onclose = function (e) {}

    chatSocket.onmessage = async function (message) {
        const msg = message.data;

        const lastMessageDateContainer = getLastMessageDateContainer();
        const lastMessageDate = $(lastMessageDateContainer).text();

        if (lastMessageDate === getCurrentDate()) {
            $(lastMessageDateContainer).parent().append(otherMessageItem(msg ,getCurrentTime()));
        } else {
            $(".chat-message-container").append(OtherMessageDateItem(msg, getCurrentDate(), getCurrentTime()));
        }

        moveScroll()
    }

    // 채팅 버튼 클릭 시
    $("#chatForm").on("submit", function(e) {
        e.preventDefault();

        const sellerId = $("#sellerId").val();

        const message = $(".chat-send-input").val();

        const lastMessageDateContainer = getLastMessageDateContainer();
        const lastMessageDate = $(lastMessageDateContainer).text();

        if (message === "") {
            return;
        }

        const data = {
            roomId : roomId,
            message : message,
        };

        chatSocket.send(JSON.stringify(data));

        // message container 안에 메시지 추가
        if (lastMessageDate === getCurrentDate()) {
            $(lastMessageDateContainer).parent().append(messageItem(message, getCurrentTime()));
        } else {
            $(".chat-message-container").append(messageDateItem(message, getCurrentDate(), getCurrentTime()));
        }

        const msgDateItemLength = $(".chat-message-container .chat-message").length;

        if (msgDateItemLength === 1) {
            const alarmData = {
                roomId : roomId,
                sellerId : sellerId,
                message : `${nickname}님이 채팅을 보냈습니다.`
            };

            alarmSocket.send(JSON.stringify(alarmData));
        }

        // 메시지 입력하면 스크롤 맨아래로 옮기기
        moveScroll()

        // 메시지 input 비우기
        $(".chat-send-input").val("");
    });

    moveScroll();
});

function messageItem(message, time) {
    const item =
        `<div class="ps-3 d-flex justify-content-end me-3 align-content-center mt-2 chat-message-item">
            <span class="text-muted my-chat-date">${time}</span>
            <div class="rounded border ms-2 p-2 h-100 text-end chat-message">
                <span>${message}</span>
            </div>
        </div>`;
    return item;
}

function otherMessageItem(message, time) {
    const item =
            `<div class="ps-2 d-flex align-items-center mt-2 mb-1 chat-message-item">
                <img src="/img/개선.PNG" width="50px" height="50px" class="rounded-circle border border-dark"/>
                <div class="rounded border ms-2 me-2 p-2 h-100 chat-message">
                    <span>${message}</span>
                </div>
                <span class="text-muted me-2 mb-3 chat-date">${time}</span>
            </div>`;
    return item;
}

function messageDateItem(message, date, time) {
    const item =
                `<div class="message-date-container">
                    <span class="d-flex justify-content-center mt-3 mb-3 text-muted message-date">${date}</span>
                    <div class="ps-3 d-flex justify-content-end me-3 align-content-center mt-2 chat-message-item">
                        <span class="text-muted my-chat-date">${time}</span>
                        <div class="rounded border ms-2 p-2 h-100 text-end chat-message">
                            <span>${message}</span>
                        </div>
                    </div>
                </div>`;
    return item;
}

function OtherMessageDateItem(message, date, time) {
    const item =
                `<div class="message-date-container">
                    <span class="d-flex justify-content-center mt-3 mb-3 text-muted message-date">${date}</span>
                    <div class="ps-2 d-flex align-items-center mt-2 mb-1 chat-message-item">
                        <img src="/img/개선.PNG" width="50px" height="50px" class="rounded-circle border border-dark"/>
                        <div class="rounded border ms-2 me-2 p-2 h-100 chat-message">
                            <span>${message}</span>
                        </div>
                        <span class="text-muted me-2 mb-3 chat-date">${time}</span>
                    </div>
                </div>`;
    return item;
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
    $(".chat-message-container").scrollTop($(".chat-message-container")[0].scrollHeight);
}

function getLastMessageDateContainer() {
    const messageDateList = $(".message-date-container .message-date");
    const messageDateLength = $(".message-date-container .message-date").length;
    const lastMessageDateContainer = $(messageDateList[messageDateLength - 1]);
    return lastMessageDateContainer;
}