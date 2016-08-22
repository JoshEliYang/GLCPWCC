/**
 * Created by johsnon on 2016/7/28.
 */

/**
 * max number in a page
 *
 * @type {number}
 */
var pageMax = 10;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, KeyWordService, MsgTypeService) {
    $scope.pageNow = 0;

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

            if ($scope.modelDat.msgType == 1) {
                var replyDat = document.getElementById('repleyContent').value;
                params.reply = replyDat;
            }
            KeyWordService.insert($scope, $http, params);
            $('#ConfigModal').modal('hide');
        } else if (title == "修改") {
            var param = {
                "count": 0,
                "target": 0
            };
            getSelect(param);

            var params = $scope.modelDat;
            delete params.title;
            delete params.msgTypeName;

            if ($scope.modelDat.msgType == 1) {
                var replyDat = document.getElementById('repleyContent').value;
                params.reply = replyDat;
            }
            params.id = $scope.keyWordsList[param.target].id;
            params.inUsing = $scope.keyWordsList[param.target].inUsing;

            KeyWordService.edit($scope, $http, params);
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
    };

    $scope.setInUsing = function (index) {
        var params = {
            "id": $scope.keyWordsList[index].id,
            "inUsing": !$scope.keyWordsList[index].inUsing
        };
        KeyWordService.setInUsing($scope, $http, params)
    };

    $scope.doDelete = function () {
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
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
        $.confirm({
            title: '删除确认',
            content: '选中' + param.count + '项，确认删除？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                for (var i = 0; i < $scope.keyWordsList.length; i++) {
                    if ($scope.keyWordsList[i].checked) {
                        KeyWordService.delete($scope, $http, $scope.keyWordsList[i].id);
                    }
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.openPage = function (index) {
        KeyWordService.getPage($scope, index);
    };

    $scope.getPrevious = function () {
        if ($scope.pageNow != 0) {
            KeyWordService.getPage($scope, $scope.pageNow - 1);
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.keyWordsGroup.length - 1) {
            KeyWordService.getPage($scope, $scope.pageNow + 1);
        }
    };

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
        $('#loadingDialog').modal('show');
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

            $scope.totalCount = data.data.length;
            $scope.keyWordsGroup = doPagination(data.data, pageMax);
            if ($scope.pageNow < $scope.keyWordsGroup.length)
                getPage($scope, $scope.pageNow);
            else
                getPage($scope, 0);

            $('#loadingDialog').modal('hide');
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

    var doPagination = function (allDat, maxItem) {
        var pageGroup = new Array();
        var pages = new Array();
        var count = 0;
        for (var i = 0; i < allDat.length; i++) {
            if (count < maxItem) {
                pages.push(allDat[i]);
                count++;
            }
            if (count >= maxItem) {
                var item = {
                    "css": "",
                    "data": pages
                };
                count = 0;
                pageGroup.push(item);
                pages = new Array();
            }
        }
        if (count != 0) {
            var item = {
                "css": "",
                "data": pages
            };
            pageGroup.push(item);
        }
        // console.log(pageGroup);
        return pageGroup;
    };

    var getPage = function ($scope, page) {
        $scope.pageNow = page;
        for (var i = 0; i < $scope.keyWordsGroup.length; i++) {
            for (var j = 0; j < $scope.keyWordsGroup[i].data.length; j++) {
                $scope.keyWordsGroup[i].data[j].checked = false;
            }
            $scope.keyWordsGroup[i].css = "";
        }
        $scope.keyWordsGroup[page].css = "active";
        $scope.keyWordsList = $scope.keyWordsGroup[page].data;
    };

    this.getPage = function ($scope, page) {
        getPage($scope, page);
    };

    this.insert = function ($scope, $http, params) {
        $('#loadingDialog').modal('show');
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

            $('#loadingDialog').modal('hide');
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

    this.setInUsing = function ($scope, $http, params) {
        $('#loadingDialog').modal('show');
        $http({
            method: "POST",
            url: keyWordsSetInUsing + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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

            $('#loadingDialog').modal('hide');
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

    this.edit = function ($scope, $http, params) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "PUT",
            url: keyWordsEdit + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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

            $('#loadingDialog').modal('hide');
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

    this.delete = function ($scope, $http, id) {
        $('#loadingDialog').modal('show');
        $http({
            method: "DELETE",
            url: deleteKeyWordsUrl + id + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
            'Content-Type': 'application/json'
            // data: params
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

            $('#loadingDialog').modal('hide');
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