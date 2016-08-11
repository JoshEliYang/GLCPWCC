/**
 * Created by johsnon on 2016/7/29.
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, BasicService) {
    BasicService.getAll($scope, $http);

    $scope.usingChecked = function (id, isUsing) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (id == $scope.basicList[i].id) {
                var param = $scope.basicList[i];
                param.using = isUsing;
                delete param.checked;
                BasicService.setUsing($scope, $http, param);
                break;
            }
        }
    };

    $scope.defaultChecked = function (id) {
        for (var i = 0; i < $scope.basicList.length; i++) {
            if (id == $scope.basicList[i].id) {
                var param = $scope.basicList[i];
                delete param.checked;
                BasicService.setDefault($scope, $http, param);
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
            BasicService.insert($scope, $http, param);
        } else if (flag == "修改") {
            var paramEdit = $scope.basicModelData;
            delete paramEdit.title;
            delete paramEdit.checked;
            BasicService.edit($scope, $http, paramEdit)
        }
        $('#basicConfigModal').modal('hide');
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
                        BasicService.delete($scope, $http, $scope.basicList[i].id);
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
});


////////////////////////////////////////////////// Service //////////////////////////////////////////////////////
app.service('BasicService', function () {
    this.getAll = function ($scope, $http) {
        getAll($scope, $http);
    };
    var getAll = function ($scope, $http) {
        $http({
            method: "GET",
            url: basicUrlAll + '?token=' + getCookie("token"),
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

            var basicList = data.data;
            for (var i = 0; i < basicList.length; i++) {
                basicList[i].checked = false;
            }
            $scope.basicList = basicList;
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };

    this.setUsing = function ($scope, $http, param) {
        $http({
            method: "PUT",
            url: basicSetUsingUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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

            getAll($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };

    this.setDefault = function ($scope, $http, param) {
        setDefault($scope, $http, param);
    };
    var setDefault = function ($scope, $http, param) {
        $http({
            method: "PUT",
            url: basicSetDefaultUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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

            getAll($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };


    this.insert = function ($scope, $http, param) {
        $http({
            method: "POST",
            url: basicInsertUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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

            getAll($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };

    this.edit = function ($scope, $http, param) {
        $http({
            method: "PUT",
            url: basicEditUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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

            getAll($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    };

    this.delete = function ($scope, $http, id) {
        $http({
            method: "DELETE",
            url: basicDeleteUrl + id + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            // data: param
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

            getAll($scope, $http);
        }).error(function () {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请求失败<br>请检查您的网络！</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    }

});
