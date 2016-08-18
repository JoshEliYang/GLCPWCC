/**
 * Created by johsnon on 2016/7/28.
 */


$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, KeyWordService, MsgTypeService) {
    KeyWordService.getKeyWord($scope, $http);
    MsgTypeService.getall($scope, $http);

    $scope.openAddDialog = function () {
        $scope.modelDat = {
            "title": "添加",
            "msgTypeName": "未选择",
            "value": "",
            "msgType": -1
        };
        $('#ConfigModal').modal('show');
    };

    $scope.msgTypeSelect = function (index) {
        $scope.modelDat.msgTypeName = $scope.msgTypeList[index].remark;
        $scope.modelDat.msgType = $scope.msgTypeList[index].id;
        modalSelectCheck();
    };

    $scope.modelConfim = function (title) {
        if ($scope.keywordsChange()) return;
        if (modalSelectCheck()) return;
        if ($scope.replyChange()) return;

        if (title == "添加") {
            var params = $scope.modelDat;
            delete params.title;
            delete params.msgTypeName;

            var replyDat = document.getElementById('repleyContent').value;
            params.reply = replyDat;
            KeyWordService.insert($scope, $http, params);
            $('#ConfigModal').modal('hide');
        }
    };

    $scope.doCheckAll = function () {
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count == $scope.keyWordsList.length) {
            $scope.checkAll = false;
            doChecked(false);
        } else {
            $scope.checkAll = true;
            doChecked(true);
        }
    };

    var doChecked = function (flag) {
        for (var i = 0; i < $scope.keyWordsList.length; i++) {
            $scope.keyWordsList[i].checked = flag;
        }
    };

    $scope.doSelect = function (index) {
        $scope.keyWordsList[index].checked = !$scope.keyWordsList[index].checked;
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count == $scope.keyWordsList.length) {
            $scope.checkAll = true;
        } else {
            $scope.checkAll = false;
        }
    };

    var getSelect = function (param) {
        param.count = 0;
        param.target = 0;
        for (var i = 0; i < $scope.keyWordsList.length; i++) {
            if ($scope.keyWordsList[i].checked) {
                param.count++;
                param.target = i;
            }
        }
    };

    $scope.keywordsChange = function () {
        if ($scope.modelDat.value == "") {
            $scope.keywordsEmpty = true;
        } else {
            $scope.keywordsEmpty = false;
        }
        return $scope.keywordsEmpty;
    };

    var modalSelectCheck = function () {
        if ($scope.modelDat.msgType == -1) {
            $scope.selectEmpty = true;
        } else {
            $scope.selectEmpty = false;
        }
        return $scope.selectEmpty;
    };

    $scope.replyChange = function () {
        if ($scope.modelDat.reply == "") {
            $scope.replyTextEmpty = true;
        } else {
            $scope.replyTextEmpty = false;
        }
        return $scope.replyTextEmpty;
    };

    $scope.openEditDialog = function () {
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count > 1) {
            $.alert({
                theme: "material",
                title: "警告",
                content: '<b>请取消多余选</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        if (param.count < 1) {
            $.alert({
                theme: "material",
                title: "警告",
                content: '<b>请先选择</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        $scope.modelDat = {
            "title": "修改",
            "msgTypeName": $scope.keyWordsList[param.target].msgTypeName,
            "value": $scope.keyWordsList[param.target].value,
            "msgType": $scope.keyWordsList[param.target].msgType,
            "reply": $scope.keyWordsList[param.target].reply
        };
        $('#ConfigModal').modal('show');
    };

    $scope.addHyperlink = function () {
        $scope.addHyperlinkFlag = true;
    };

    $scope.doAddHyperlink = function () {
        var hyperlinkText = document.getElementById('hyperlinkText').value;
        var hyperlinkUrl = document.getElementById('hyperlinkUrl').value;
        var hyperDat = '<a href="' + hyperlinkUrl + '">' + hyperlinkText + '</a>';
        insertText(document.getElementById('repleyContent'), hyperDat);
        $scope.addHyperlinkFlag = false;
    }
});

//////////////////////////////////////// service ////////////////////////////////////////////////////
app.service('KeyWordService', function () {
    var getBasic = function () {
        return JSON.parse(sessionStorage.getItem("basic"));
    };

    this.getKeyWord = function ($scope, $http) {
        getKeyWords($scope, $http);
    };
    var getKeyWords = function ($scope, $http) {
        $http({
            method: "GET",
            url: keyWordsUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert({
                    theme: "material",
                    title: "警告",
                    content: '<b>' + data.msg + '</b>',
                    confirmButtonClass: 'btn-info',
                    autoClose: 'confirm|10000'
                });
                return;
            }

            var keyWordsList = data.data;
            for (var i = 0; i < keyWordsList.length; i++) {
                keyWordsList[i].checked = false;
            }
            $scope.keyWordsList = keyWordsList;
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: '<b>请求失败<br>请检查您的网络！</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };

    this.insert = function ($scope, $http, params) {
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: keyWordsInsertUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
            'Content-Type': 'application/json',
            data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert({
                    theme: "material",
                    title: "警告",
                    content: '<b>' + data.msg + '</b>',
                    confirmButtonClass: 'btn-info',
                    autoClose: 'confirm|10000'
                });
                return;
            }

            getKeyWords($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: '<b>请求失败<br>请检查您的网络！</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };
});

app.service('MsgTypeService', function () {
    this.getall = function ($scope, $http) {
        $http({
            method: "GET",
            url: msgTypeUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert({
                    theme: "material",
                    title: "警告",
                    content: '<b>' + data.msg + '</b>',
                    confirmButtonClass: 'btn-info',
                    autoClose: 'confirm|10000'
                });
                return;
            }

            $scope.msgTypeList = data.data;
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: '<b>请求失败<br>请检查您的网络！</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };
});