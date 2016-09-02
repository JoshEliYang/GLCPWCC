/**
 * Created by johsnon on 2016/7/29.
 */

var pageMax = 2;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, BasicServiceGlobal, PaginationServiceGlobal) {
    $scope.pageNow = 0;
    // do paginate
    var getDataCallback = function (data) {
        PaginationServiceGlobal.doPagination(data, pageMax, function (pageGroup, totalCount) {
            $scope.pageGroup = pageGroup;
            $scope.totalCount = totalCount;
        });
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.pageGroup
        }, $scope.pageNow, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.basicList = showList;
        });
    };

    // get all basic data and do paginate
    BasicServiceGlobal.getAll($http, getDataCallback);

    $scope.usingChecked = function (id, isUsing) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (id == $scope.basicList[i].id) {
                var param = $scope.basicList[i];
                param.using = isUsing;
                delete param.checked;
                BasicServiceGlobal.setUsing($http, param, function callback() {
                    BasicServiceGlobal.getAll($http, getDataCallback);
                });
                break;
            }
        }
    };

    $scope.defaultChecked = function (id) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (id == $scope.basicList[i].id) {
                var param = $scope.basicList[i];
                delete param.checked;
                BasicServiceGlobal.setDefault($http, param, function callback() {
                    BasicServiceGlobal.getAll($http, getDataCallback);
                });
                break;
            }
        }
    };

    $scope.doSelect = function (index) {
        $scope.basicList[index].checked = !$scope.basicList[index].checked;
    };

    $scope.openAddDialog = function () {
        $scope.basicModelData = {
            "title": "添加",
            "remark": "",
            "wechatAccount": "",
            "appId": "",
            "appSecret": "",
            "token": "",
            "url": "",
            "using": false,
            "default": false,
            "tokenServer": "",
            "usingTokenServer": false
        };
        $('#basicConfigModal').modal('show');
        var modelRemark = document.getElementById('modelRemark');
        modelRemark.focus();
    };

    $scope.basicModelConfim = function (flag) {
        if (flag == "添加") {
            var param = $scope.basicModelData;
            delete param.title;
            BasicServiceGlobal.insert($http, param, function callback() {
                BasicServiceGlobal.getAll($http, getDataCallback);
            });
        } else if (flag == "修改") {
            var paramEdit = $scope.basicModelData;
            delete paramEdit.title;
            delete paramEdit.checked;
            BasicServiceGlobal.edit($http, paramEdit, function callback() {
                BasicServiceGlobal.getAll($http, getDataCallback);
            });
        }
        $('#basicConfigModal').modal('hide');
        location.href = "#btnPanel";
        location.href = "#head";
    };

    $scope.basicModelUsing = function () {
        $scope.basicModelData.using = !$scope.basicModelData.using;
    };

    $scope.basicModelDefault = function () {
        $scope.basicModelData.default = !$scope.basicModelData.default
    };

    $scope.openEditDialog = function () {
        var target, count = 0;
        for (var i = 0; i < $scope.basicList.length; i++) {
            if ($scope.basicList[i].checked == true) {
                target = i;
                count++;
            }
        }
        if (count == 0) {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请先选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        if (count > 1) {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请去除多余选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        var param = $scope.basicList[target];
        param.title = "修改";
        $scope.basicModelData = param;
        $('#basicConfigModal').modal('show');
        var modelRemark = document.getElementById('modelRemark');
        modelRemark.focus();
    };

    $scope.doDelete = function () {
        var count = 0;
        for (var i = 0; i < $scope.basicList.length; i++) {
            if ($scope.basicList[i].checked == true) {
                count++;
            }
        }
        $.confirm({
            title: '删除确认',
            content: '选中' + count + '项，确认删除？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                for (var i = 0; i < $scope.basicList.length; i++) {
                    if ($scope.basicList[i].checked == true) {
                        BasicServiceGlobal.delete($http, $scope.basicList[i].id, function callback() {
                            BasicServiceGlobal.getAll($http, getDataCallback);
                        });
                    }
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.doModalChecked = function () {
        $scope.basicModelData.usingTokenServer = !$scope.basicModelData.usingTokenServer;
    };

    $scope.selectTokenServer = function (index) {
        BasicServiceGlobal.selectTokenServer($http, {
            "id": $scope.basicList[index].id,
            "usingTokenServer": !$scope.basicList[index].usingTokenServer
        }, function callback() {
            BasicServiceGlobal.getAll($http, getDataCallback);
        });
    };

    $scope.openPage = function (index) {
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.pageGroup
        }, index, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.basicList = showList;
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
                $scope.basicList = showList;
            });
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.pageGroup.length - 1) {
            ;PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow + 1,function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.basicList = showList;
            });
        }
    };
});

