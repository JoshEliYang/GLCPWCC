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

app.controller('wechatCtrl', function ($scope, $http, ReplyServiceGlobal, PaginationServiceGlobal, MsgTypeServiceGlobal) {
    $scope.pageNow = 0;

    // do paginate
    var getDataCallback = function (data) {
        PaginationServiceGlobal.doPagination(data, pageMax, function (pageGroup, totalCount) {
            $scope.keyWordsGroup = pageGroup;
            $scope.totalCount = totalCount;
        });
        if ($scope.pageNow < $scope.keyWordsGroup.length)
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.keyWordsGroup
            }, $scope.pageNow, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.keyWordsGroup = pageGroup;
                $scope.keyWordsList = showList;
            });
        else
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.keyWordsGroup
            }, 0, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.keyWordsGroup = pageGroup;
                $scope.keyWordsList = showList;
            });
    };

    ReplyServiceGlobal.getKeyWords($http, getDataCallback);
    MsgTypeServiceGlobal.getAll($http, function callback(data) {
        $scope.msgTypeList = data;
    });

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
            ReplyServiceGlobal.insert($http, params, function callback() {
                ReplyServiceGlobal.getKeyWords($http, getDataCallback);
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
            params.id = $scope.keyWordsList[param.target].id;
            params.inUsing = $scope.keyWordsList[param.target].inUsing;

            ReplyServiceGlobal.edit($http, params, function callback() {
                ReplyServiceGlobal.getKeyWords($http, getDataCallback);
            });
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
        ReplyServiceGlobal.setInUsing($http, {
            "id": $scope.keyWordsList[index].id,
            "inUsing": !$scope.keyWordsList[index].inUsing
        }, function callback() {
            ReplyServiceGlobal.getKeyWords($http, getDataCallback);
        });
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
                        ReplyServiceGlobal.delete($http, $scope.keyWordsList[i].id, function callback() {
                            ReplyServiceGlobal.getKeyWords($http, getDataCallback);
                        });
                    }
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.openPage = function (index) {
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.keyWordsGroup
        }, index, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.keyWordsGroup = pageGroup;
            $scope.keyWordsList = showList;
        });
    };

    $scope.getPrevious = function () {
        if ($scope.pageNow != 0) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.keyWordsGroup
            }, $scope.pageNow - 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.keyWordsGroup = pageGroup;
                $scope.keyWordsList = showList;
            });
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.keyWordsGroup.length - 1) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.keyWordsGroup
            }, $scope.pageNow + 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.keyWordsGroup = pageGroup;
                $scope.keyWordsList = showList;
            });
        }
    };

});