/**
 * Created by johnson on 16-7-20.
 */


$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, AdminServiceGlobal, ButtonServiceGlobal, BasicServiceGlobal) {
    var token = getParameter('ticket');
    setCookie('token', token);
    sessionStorage.setItem('token', token);

    // AdminServiceGlobal.getAdmin($http, function callback(data) {
    //     $scope.realname = data.realname;
    // });

    SSO.verify(function (userData) {
        $scope.realname = userData.realName;
        var admin = {
            "realname": userData.realName,
            "username": userData.name
        };
        sessionStorage.setItem("admin", JSON.stringify(admin));
        $scope.$apply();
    });

    ButtonServiceGlobal.getButtonGroup($http, ButtonServiceGlobal.getButtons, function (buttonsList, selectId) {
        $scope.buttonsList = buttonsList;
        ButtonServiceGlobal.changeButton($scope, selectId);
    }, function (buttonGroupList) {
        $scope.buttonGroupList = buttonGroupList;
    });

    BasicServiceGlobal.getBasic($http, function callback(basicList) {
        var i = 0;
        for (; i < basicList.length; i++) {
            if (basicList[i].default) {
                $scope.basicTitle = basicList[i].remark;
                sessionStorage.setItem("basic", JSON.stringify(basicList[i]));
                break;
            }
        }
        if (i == basicList.length) {
            $scope.basicTitle = "未选择";
        }
        $scope.basicList = basicList;
    });

    $scope.changeTag = function (id) {
        for (var i = 0; i < $scope.buttonGroupList.length; i++) {
            if (id == $scope.buttonGroupList[i].id) {
                $scope.buttonGroupList[i].default = true;
                $scope.buttonGroupList[i].class = "active";
            } else {
                $scope.buttonGroupList[i].default = false;
                $scope.buttonGroupList[i].class = "";
            }
        }
        ButtonServiceGlobal.getButtons($http, id, function (buttonsList, selectId) {
            $scope.buttonsList = buttonsList;
            ButtonServiceGlobal.changeButton($scope, selectId);
        });
    };

    $scope.changeBtn = function (id) {
        for (var i = 0; i < $scope.buttonsList.length; i++) {
            if (id == $scope.buttonsList[i].id) {
                $scope.buttonsList[i].default = true;
                $scope.buttonsList[i].class = "active";
            } else {
                $scope.buttonsList[i].default = false;
                $scope.buttonsList[i].class = "";
            }
        }
        ButtonServiceGlobal.changeButton($scope, id);
    };

    $scope.basicSelect = function (id) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (id == $scope.basicList[i].id) {
                $scope.basicTitle = $scope.basicList[i].remark;
                $scope.basicList[i].default = true;
                sessionStorage.setItem("basic", JSON.stringify($scope.basicList[i]));
                window.parent.frames["mainFrame"].location.reload();
            } else {
                $scope.basicList[i].default = false;
            }
        }
    };

    $scope.logout = function () {
        AdminServiceGlobal.logout();
    };
});


var jobList = new Array();

/**
 * job dialog
 *
 * @param option (open, close, set, add)
 * @param param (taskTimestamp,task, message, running, progress, max)
 */
function job(option, param) {
    var drawDialog = function () {
        var jobGUI = "";
        for (var i = jobList.length - 1; i >= 0; i--) {
            var valueNow = Math.round(jobList[i].progress / jobList[i].max * 100);

            if (jobList[i].running) {
                jobGUI += '<div>';
            } else {
                jobGUI += '<div style="opacity: 0.7">';
            }
            jobGUI += '<label>任务' + jobList[i].taskTimestamp + ': ' + jobList[i].task + '</label>';
            jobGUI += '<div class="progress" style="margin-bottom: 0"> ' +
                '<div class="progress-bar" role="progressbar" aria-valuenow="' + jobList[i].progress + '" aria-valuemin="0" ' +
                ' aria-valuemax="' + jobList[i].max + '" style="min-width: 2em;width: ' + valueNow + '%;">' +
                valueNow + '% ' +
                '</div></div>';
            jobGUI += '<p>' + jobList[i].message + '</p></div>'

        }
        $('#jobContent').html(jobGUI);
    };

    var setJobInfo = function () {
        $('#taskNum').html(jobList.length);

        var infoList = '';
        for (var i = 0; i < jobList.length; i++) {
            infoList += '<li role="presentation"><a onclick="$(\'#jobDialog\').modal(\'show\');">任务' + jobList[i].taskTimestamp + ':' + jobList[i].task;
            if (jobList[i].running) {
                infoList += ' -正在运行</a></li>';
            } else {
                infoList += ' -已结束</a></li>';
            }
        }
        $('#infoList').html(infoList);
    };

    if (option == "open") {
        $('#jobDialog').modal('show');
    } else if (option == "close") {
        $('#jobDialog').modal('hide');
    } else if (option == "set") {
        var index = param.index;
        delete param.index;
        jobList[index] = param;
        drawDialog();
        setJobInfo();
    } else if (option == "add") {
        jobList.push(param);
        drawDialog();
        setJobInfo();
    }
}


if (!window.WebSocket) {
    $.alert({
        theme: "material",
        title: "错误",
        content: '<b>您的浏览器不支持WebSocket技术！请跟换浏览器</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
}

var socket = new WebSocket(socketUrl);
socket.onopen = function () {
    console.log("socket opened");
};

socket.onmessage = function (msg) {
    console.log("socket receive message: " + msg);
    var indata = JSON.parse(msg.data);
    var i = 0;
    for (; i < jobList.length; i++) {
        if (jobList[i].taskTimestamp == indata.taskTimestamp) {
            indata.index = i;
            job("set", indata);
            break;
        }
    }
    if (i >= jobList.length) {
        job("add", indata);
    }
};
socket.onerror = function (err) {
    console.log("socket error: " + err);
    $.alert({
        theme: "material",
        title: "错误",
        content: '<b>WebSocket发生错误，请刷新重试！' + err.toString() + '</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
};

socket.onclose = function () {
    console.log("socket closed");
    $.alert({
        theme: "material",
        title: "错误",
        content: '<b>WebSocket连接断开，请刷新重试！</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
};
