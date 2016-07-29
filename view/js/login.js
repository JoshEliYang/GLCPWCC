/**
 * Created by johsnon on 2016/7/27.
 */

var username = getCookie("username");
var token = getCookie("token");

if (username != null && username != "") {
    document.getElementById("username").value = username;
    if (token != null && token != "") {
        location.href = "index.html";
    }
}

function usernameOnEnter() {
    if (event.keyCode == 13) {
        var passwdInput = document.getElementById("passwd");
        passwdInput.focus();
    }
}

function passwdOnEnter() {
    if (event.keyCode == 13) {
        var verifyInput = document.getElementById("verifyCode");
        verifyInput.focus();
    }
}

function verifyOnEnter() {
    if (event.keyCode == 13) {
        doLogin();
    }
}

function doLogin() {
    var param = {
        "username": document.getElementById("username").value,
        "passwd": md5(document.getElementById("passwd").value),
        "verification": document.getElementById("verifyCode").value,
        "longTermFlag": document.getElementById("keepLogin").checked
    };
    $.ajax({
        type: "POST",
        url: loginUrl,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(param),
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                $.alert('<br>' + data.msg + '</br>');
                document.getElementById("resetBtn").click();
                return;
            }

            if (document.getElementById("keepLogin").checked)
                setCookie("token", data.data.token, 30);
            else
                setCookie("token", data.data.token, 1);
            setCookie("username", document.getElementById("username").value, 30);
            location.href = "index.html";
        },
        error: function () {
            $.alert("<b>请求失败<br>请检查您的网络！</br>");
        }
    });
}

