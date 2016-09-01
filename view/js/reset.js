/**
 * Created by Administrator on 2016/9/1.
 */
function show(r){
    if(r.value=='gr'){
        document.getElementById('gr').style.display = "";
        document.getElementById('qy').style.display = "none";
    }else{
        document.getElementById('gr').style.display = "none";
        document.getElementById('qy').style.display = "";
    }
}
var regName=/^[_\w]{6,64}$/;
var regPass=/^[a-z,A-Z,0-9,_]{6,12}$/;
var regEmail=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
var regRealname=/^[\u4E00-\u9FA5]{2,10}$/;

function checkName(){
    var name=document.getElementById("username");
    var error=document.getElementById("perfect_psnl");
    error.innerHTML="";

    if(name.value==""){
        error.innerHTML="请填写登录名！";
        return;
    }
    if(!regName.test(name.value)){
        error.innerHTML="登录名不合要求,必须是6-64位英文数字或_的组合!";
    }
}

function checkPass(){
    var pwd1=document.getElementById("pwd1");
    var error=document.getElementById("perfect_pwd1");
    error.innerHTML="";
    if(pwd1.value==""){
        error.innerHTML="请填写登录密码！";
        return;
    }

    if(!regPass.test(pwd1.value)){
        error.innerHTML="密码长度在6~12位！";
    }
}

function checkRepass(){
    var pwd1=document.getElementById("pwd1");
    var pwd2=document.getElementById("pwd2");
    var error=document.getElementById("perfect_pwd2");
    error.innerHTML="";
    if(pwd2.value==""){
        error.innerHTML="确认密码不能为空！";
    }

    if(pwd1.value!=pwd2.value){
        error.innerHTML="两次输入的密码不相符!";
    }
}

function checkEmail(){
    var email=document.getElementById("email");
    var error=document.getElementById("perfect_email");
    error.innerHTML="";
    if(email.value==""){
        error.innerHTML="请填写您的邮箱地址！";
        return;
    }

    if(!regEmail.test(email.value)){
        error.innerHTML="电子邮箱格式填写有误！";
    }
}

function checkRealname(){
    var realname=document.getElementById("realname");
    var error=document.getElementById("perfect_realname");
    error.innerHTML="";
    if(realname.value==""){
        error.innerHTML="请填写您的真实姓名！";
        return;
    }

    if(!regRealname.test(realname.value)){
        error.innerHTML="真实姓名填写有误！";
    }
}

