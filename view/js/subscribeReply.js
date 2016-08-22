/**
 * Created by johnson on 2016/8/21.
 */

var pageMax = 10;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, SubscribeService, MsgTypeService) {
    $scope.pageNow = 0;

    SubscribeService.getSubscribeReply($scope, $http);
    MsgTypeService.getall($scope, $http);

    $scope.doCheckAll = function () {
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count == $scope.subscribeList.length) {
            $scope.checkAll = false;
            doChecked(false);
        } else {
            $scope.checkAll = true;
            doChecked(true);
        }
    };

    var doChecked = function (flag) {
        for (var i = 0; i < $scope.subscribeList.length; i++) {
            $scope.subscribeList[i].checked = flag;
        }
    };

    $scope.doSelect = function (index) {
        $scope.subscribeList[index].checked = !$scope.subscribeList[index].checked;
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count == $scope.subscribeList.length) {
            $scope.checkAll = true;
        } else {
            $scope.checkAll = false;
        }
    };


    $scope.openAddDialog = function () {
        $scope.modelDat = {
            "title": "添加",
            "msgTypeName": "未选择",
            "msgType": -1
        };
        $('#ConfigModal').modal('show');
    };

    $scope.msgTypeSelect = function (index) {
        $scope.modelDat.msgTypeName = $scope.msgTypeList[index].remark;
        $scope.modelDat.msgType = $scope.msgTypeList[index].id;
        modalSelectCheck();
    };

    var modalSelectCheck = function () {
        if ($scope.modelDat.msgType == -1) {
            $scope.selectEmpty = true;
        } else {
            $scope.selectEmpty = false;
        }
        return $scope.selectEmpty;
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

    $scope.modelConfim = function (title) {
        if (title == '添加') {
            var params = $scope.modelDat;
            delete params.title;
            delete params.msgTypeName;

            if ($scope.modelDat.msgType == 1) {
                var replyDat = document.getElementById('repleyContent').value;
                params.reply = replyDat;
            }
            SubscribeService.insert($scope, $http, params);
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
            params.id = $scope.subscribeList[param.target].id;
            params.inUsing = $scope.subscribeList[param.target].inUsing;
            params.subscribe = true;

            SubscribeService.edit($scope, $http, params);
            $('#ConfigModal').modal('hide');
        }
    };

    var getSelect = function (param) {
        param.count = 0;
        param.target = 0;
        for (var i = 0; i < $scope.subscribeList.length; i++) {
            if ($scope.subscribeList[i].checked) {
                param.count++;
                param.target = i;
            }
        }
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
            "msgTypeName": $scope.subscribeList[param.target].msgTypeName,
            "msgType": $scope.subscribeList[param.target].msgType,
            "reply": $scope.subscribeList[param.target].reply
        };
        $('#ConfigModal').modal('show');
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
                for (var i = 0; i < $scope.subscribeList.length; i++) {
                    if ($scope.subscribeList[i].checked) {
                        SubscribeService.delete($scope, $http, $scope.subscribeList[i].id);
                    }
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.setInUsing = function (index) {
        var params = {
            "id": $scope.subscribeList[index].id
        };
        SubscribeService.setInUsing($scope, $http, params)
    };

    $scope.openPage = function (index) {
        SubscribeService.getPage($scope, index);
    };

    $scope.getPrevious = function () {
        if ($scope.pageNow != 0) {
            SubscribeService.getPage($scope, $scope.pageNow - 1);
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.pageGroup.length - 1) {
            SubscribeService.getPage($scope, $scope.pageNow + 1);
        }
    };
});


////////////////////////////////////////////////  service /////////////////////////////////////////
app.service('SubscribeService', function () {
    var getBasic = function () {
        return JSON.parse(sessionStorage.getItem("basic"));
    };

    this.getSubscribeReply = function ($scope, $http) {
        getSubscribeReply($scope, $http);
    };
    var getSubscribeReply = function ($scope, $http) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: subscribeReplyUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
            $scope.pageGroup = doPagination(data.data, pageMax);
            if ($scope.pageNow < $scope.pageGroup.length)
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
        for (var i = 0; i < $scope.pageGroup.length; i++) {
            for (var j = 0; j < $scope.pageGroup[i].data.length; j++) {
                $scope.pageGroup[i].data[j].checked = false;
            }
            $scope.pageGroup[i].css = "";
        }
        $scope.pageGroup[page].css = "active";
        $scope.subscribeList = $scope.pageGroup[page].data;
    };

    this.getPage = function ($scope, page) {
        getPage($scope, page);
    };

    this.insert = function ($scope, $http, params) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: insertSubscribeUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
            getSubscribeReply($scope, $http);
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
            getSubscribeReply($scope, $http);
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
            getSubscribeReply($scope, $http);
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
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: setSubscribeInUsingUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
            getSubscribeReply($scope, $http);
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