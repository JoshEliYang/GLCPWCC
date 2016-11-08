/**
 * Created by johnson on 2016/8/24.
 */

var getBasic = function () {
    return JSON.parse(sessionStorage.getItem("basic"));
};

function do_upload() {
    var data = new FormData($('#uploadForm')[0]);
    //data.append("templateId", "1sa5ZLIto73LIpAaT7kmuHpmvseGQwsdnXM_eEkfOUs");
    data.append("templateId", (JSON.parse(sessionStorage.getItem("messagePusherDat")).templateId));
    data.append("timestamp", new Date().getTime());
    $.ajax({
        url: MessageExcelUrl + "?wechatAccount=" + getBasic().id + "&token=" + getCookie("token"),
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            // alert(JSON.stringify(data));
        }
    });

    window.parent.job('open');
}

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http) {
    $scope.messagePusherDat = JSON.parse(sessionStorage.getItem('messagePusherDat'));
});

var clip = new ZeroClipboard(document.getElementById("templatesCopyBtn"), {
    moviePath: "lib/ZeroClipboard/ZeroClipboard.swf"
});

clip.on('complete', function (client, args) {
    console.log("copy ok");
    $.alert({
        theme: "material",
        title: "复制成功",
        content: '<b>复制内容：' + args.text + '</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
});
