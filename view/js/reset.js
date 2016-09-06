/**
 * Created by Administrator on 2016/9/1.
 */


var adminGlobal = JSON.parse(sessionStorage.getItem("admin"));
document.getElementById("username").value = adminGlobal.username;
document.getElementById("realname").value = adminGlobal.realname;
document.getElementById("email").value = adminGlobal.email;

function ResetInfo() {
    var params = {
        "id": JSON.parse(sessionStorage.getItem("admin")).id,
        "username":document.getElementById("username").value,
        "realname":document.getElementById("realname").value,
        "email": document.getElementById("email").value,
        "userLevel":JSON.parse(sessionStorage.getItem("admin")).userLevel
    };
    $.ajax({
        type: "PUT",
        url: userEditUrl + '?token=' + getCookie("token"),
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(params),
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                $.alert('<br>' + data.msg + '</br>');
                document.getElementById("resetBtn").click();
                return;
            }
            setCookie("token", "", -1);
            location.href = "login.html";
        },
        error: function () {
            $.alert("<b>请求失败<br>请检查您的网络！</br>");
        }
    });
}

function show(r) {
    if (r.value == 'gr') {
        document.getElementById('gr').style.display = "";
        document.getElementById('qy').style.display = "none";
    } else {
        document.getElementById('gr').style.display = "none";
        document.getElementById('qy').style.display = "";
    }
}
var regName = /^[_\w]{0,64}$/;
var regPass = /^[a-z,A-Z,0-9,_]{0,12}$/;
var regEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
var regRealname = /^[\u4E00-\u9FA5]{2,10}$/;

function checkName() {
    var name = document.getElementById("username");
    var error = document.getElementById("perfect_psnl");
    error.innerHTML = "";

    if (name.value == "") {
        error.innerHTML = "请填写登录名！";
        return;
    }
    if (!regName.test(name.value)) {
        error.innerHTML = "登录名不为空";
    }
}

function checkPass() {
    var pwd = document.getElementById("pwd");
    var error = document.getElementById("perfect_pwd");
    error.innerHTML = "";
    if (pwd.value == "") {
        error.innerHTML = "请填写登录密码！";
        return;
    }

    if (!regPass.test(pwd.value)) {
        error.innerHTML = "密码不能为空！";
    }
}
function checkPass() {
    var pwd1 = document.getElementById("pwd1");
    var error = document.getElementById("perfect_pwd1");
    error.innerHTML = "";
    if (pwd1.value == "") {
        error.innerHTML = "请填写登录密码！";
        return;
    }

    if (!regPass.test(pwd1.value)) {
        error.innerHTML = "密码不能为空！";
    }
}

function checkRepass() {
    var pwd1 = document.getElementById("pwd1");
    var pwd2 = document.getElementById("pwd2");
    var error = document.getElementById("perfect_pwd2");
    error.innerHTML = "";
    if (pwd2.value == "") {
        error.innerHTML = "确认密码不能为空！";
    }

    if (pwd1.value != pwd2.value) {
        error.innerHTML = "两次输入的密码不相符!";
    }
}

function checkEmail() {
    var email = document.getElementById("email");
    var error = document.getElementById("perfect_email");
    error.innerHTML = "";
    if (email.value == "") {
        error.innerHTML = "请填写您的邮箱地址！";
        return;
    }

    if (!regEmail.test(email.value)) {
        error.innerHTML = "电子邮箱格式填写有误！";
    }
}

function checkRealname() {
    var realname = document.getElementById("realname");
    var error = document.getElementById("perfect_realname");
    error.innerHTML = "";
    if (realname.value == "") {
        error.innerHTML = "真实姓名不能为空！";
        return;
    }

    if (!regRealname.test(realname.value)) {
        error.innerHTML = "真实姓名填写有误！";
    }
}

function resetPasswd() {
    var pwd = document.getElementById("pwd").value;
    var admin = sessionStorage.getItem("admin");
    var adminObj = JSON.parse(admin);
    var oldPwd = adminObj.passwd;
    pwd = md5(pwd);
    if (pwd != oldPwd) {
        alert("你的旧密码不对，请重新输入!");
        return;
    }
    var pwd1 = document.getElementById("pwd1");
    var params = {
        "id": JSON.parse(sessionStorage.getItem("admin")).id,
        "passwd": document.getElementById("pwd1").value
    };
    $.ajax({
        type: "PATCH",
        url: resetPasswdUrl + '?token=' + getCookie("token"),
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(params),
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                $.alert('<br>' + data.msg + '</br>');
                document.getElementById("resetBtn").click();
                return;
            }
            setCookie("token", "", -1);
            location.href = "login.html";
        },
        error: function () {
            $.alert("<b>请求失败<br>请检查您的网络！</br>");
        }
    });
}

function backhome() {
    location.href = "index.html";
}

