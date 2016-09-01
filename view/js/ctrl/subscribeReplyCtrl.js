/**
 * Created by johnson on 2016/8/21.
 */

var pageMax = 10;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, ReplyServiceGlobal, PaginationServiceGlobal, MsgTypeServiceGlobal) {
    $scope.pageNow = 0;

    // do paginate
    var getDataCallback = function (data) {
        PaginationServiceGlobal.doPagination(data, pageMax, function (pageGroup, totalCount) {
            $scope.pageGroup = pageGroup;
            $scope.totalCount = totalCount;
        });
        if ($scope.pageNow < $scope.pageGroup.length)
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.subscribeList = showList;
            });
        else
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, 0, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.subscribeList = showList;
            });
    };
    ReplyServiceGlobal.getSubscribeReply($http, getDataCallback);

    MsgTypeServiceGlobal.getAll($http, function callback(data) {
        $scope.msgTypeList = data;
    });

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
            ReplyServiceGlobal.insertSubscribe($http, params, function callback() {
                ReplyServiceGlobal.getSubscribeReply($http, getDataCallback);
            });
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

            ReplyServiceGlobal.editSubscribe($http, params, function callback() {
                ReplyServiceGlobal.getSubscribeReply($http, getDataCallback);
            });
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
                        ReplyServiceGlobal.deleteSubscribe($http, $scope.subscribeList[i].id, function callback() {
                            ReplyServiceGlobal.getSubscribeReply($http, getDataCallback);
                        });
                    }
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.setInUsing = function (index) {
        ReplyServiceGlobal.setInUsingSubscribe($http, {
            "id": $scope.subscribeList[index].id
        }, function callback() {
            ReplyServiceGlobal.getSubscribeReply($http, getDataCallback);
        });
    };

    $scope.openPage = function (index) {
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.pageGroup
        }, index, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.subscribeList = showList;
        });
    };

    $scope.getPrevious = function () {
        if ($scope.pageNow != 0) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow - 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.subscribeList = showList;
            });
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.pageGroup.length - 1) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow + 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.subscribeList = showList;
            });
        }
    };
});

