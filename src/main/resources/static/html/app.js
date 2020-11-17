var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    //连接ws
    var socket = new SockJS('http://localhost:8080/websocket');
    stompClient = Stomp.over(socket);
    //初始化连接
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //订阅topic 每个大屏可以订阅不同的topic 前缀为topic  后缀为大屏名称 可以为screen1 例如 /topic/screen1
        stompClient.subscribe('/topic/hello', function (greeting) {
            showGreeting(greeting);
        });
    });
}
//关闭链接
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
//有的接口可以通过这个发送消息 比如需要的接口名 但一定要加前缀 /app  方法名写在后面 如下 /app/hello
//具体可以查看stomp如何使用
function sendName() {
    stompClient.send("/app/hello",{},{});
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
