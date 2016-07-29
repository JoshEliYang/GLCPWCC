/**
 * Created by johnson on 16-7-20.
 */


$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, InitService, AdminService, ButtonService, ViewService) {
    InitService.getUser($scope, $http);
    ButtonService.getButtonGroup($scope, $http);
    InitService.getBasic($scope, $http);

    $scope.changeTag = function (id) {
        ViewService.changeTag($scope, id);
        ButtonService.getButtons($scope, $http, id);
    };

    $scope.changeBtn = function (id) {
        ViewService.changeButton($scope, id);
        ButtonService.changeButton($scope, id);
    };

    $scope.basicSelect = function (id) {
        ViewService.basicSelect($scope, id);
    };

    $scope.logout = function () {
        AdminService.logout();
    };
});

////////////////////////////////////////////// service ///////////////////////////////////////////////////////////////
app.service('InitService', function () {
    this.getUser = function ($scope, $http) {
        var token = getCookie("token");
        var username = getCookie("username");
        if (token == null || token == "" || username == null || username == "") {
            location.href = "login.html";
            return;
        }

        $http({
            method: "GET",
            url: userInfoUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert('<b>' + data.msg + '</b>');
                return;
            }

            sessionStorage.setItem("admin", JSON.stringify(data.data));
            $scope.realname = data.data.realname;
        }).error(function () {
            $.alert('<b>请求失败<br>请检查您的网络！</br>');
        });
    };

    this.getBasic = function ($scope, $http) {
        $http({
            method: "GET",
            url: basicUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert('<b>' + data.msg + '</b>');
                return;
            }

            var basicList = data.data;
            var i = 0;
            for (i = 0; i < basicList.length; i++) {
                if (basicList[i].default) {
                    $scope.basicTitle = basicList[i].remark;
                    break;
                }
            }
            if (i == basicList.length) {
                $scope.basicTitle = "未选择";
            }
            sessionStorage.setItem("basic", JSON.stringify(basicList));
            $scope.basicList = basicList;
        }).error(function () {
            $.alert('<b>请求失败<br>请检查您的网络！</br>');
        });
    }
});

app.service('ViewService', function () {
    this.changeTag = function ($scope, groupId) {
        for (var i = 0; i < $scope.buttonGroupList.length; i++) {
            if (groupId == $scope.buttonGroupList[i].id) {
                $scope.buttonGroupList[i].default = true;
                $scope.buttonGroupList[i].class = "active";
            } else {
                $scope.buttonGroupList[i].default = false;
                $scope.buttonGroupList[i].class = "";
            }
        }
    };

    this.changeButton = function ($scope, buttonId) {
        for (var i = 0; i < $scope.buttonsList.length; i++) {
            if (buttonId == $scope.buttonsList[i].id) {
                $scope.buttonsList[i].default = true;
                $scope.buttonsList[i].class = "active";
            } else {
                $scope.buttonsList[i].default = false;
                $scope.buttonsList[i].class = "";
            }
        }
    };

    this.basicSelect = function ($scope, basicId) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (basicId == $scope.basicList[i].id) {
                $scope.basicTitle = $scope.basicList[i].remark;
                $scope.basicList[i].default = true;
            } else {
                $scope.basicList[i].default = false;
            }
        }
    }
});

app.service('ButtonService', function () {
    this.getButtonGroup = function ($scope, $http) {
        $http({
            method: "GET",
            url: buttonGroupUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert('<b>' + data.msg + '</b>');
                return;
            }

            var buttonGroupList = data.data;
            for (var i = 0; i < buttonGroupList.length; i++) {
                if (buttonGroupList[i].default == true) {
                    buttonGroupList[i].class = "active";
                    getButtons($scope, $http, buttonGroupList[i].id);
                } else {
                    buttonGroupList[i].class = "";
                }
            }
            $scope.buttonGroupList = buttonGroupList;
        }).error(function () {
            $.alert('<b>请求失败<br>请检查您的网络！</br>');
        });
    };

    this.getButtons = function ($scope, $http, groupId) {
        getButtons($scope, $http, groupId);
    };

    var getButtons = function ($scope, $http, groupId) {
        $http({
            method: "GET",
            url: buttonsUrl + groupId + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert('<b>' + data.msg + '</b>');
                return;
            }

            var buttonsList = data.data;
            var selectId;
            for (var i = 0; i < buttonsList.length; i++) {
                if (buttonsList[i].default == true) {
                    buttonsList[i].class = "active";
                    selectId = buttonsList[i].id;
                } else {
                    buttonsList[i].class = "";
                }
            }
            $scope.buttonsList = buttonsList;
            changeButton($scope, selectId);
        }).error(function () {
            $.alert('<b>请求失败<br>请检查您的网络！</br>');
        });
    };

    this.changeButton = function ($scope, buttonId) {
        changeButton($scope, buttonId);
    };

    var changeButton = function ($scope, buttonId) {
        for (var i = 0; i < $scope.buttonsList.length; i++) {
            if (buttonId == $scope.buttonsList[i].id) {
                $scope.mainFramePage = $scope.buttonsList[i].uri;
                $scope.mainFrameHeight = $scope.buttonsList[i].frameHeight;
                break;
            }
        }
    };
});

app.service('AdminService', function () {
    this.logout = function () {
        setCookie("token", "", -1);
        location.href = "login.html";
    }
});

