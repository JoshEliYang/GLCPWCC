/**
 * Created by johnson on 2016/8/3.
 *
 * When I written it, only God and I known what it meant.
 * Now, only God knows!
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, UserService) {
    UserService.getAllUser($scope, $http);
    UserService.getAllUserLevel($scope, $http);

    $scope.adminCheckAll = function () {
        var flag = true;
        for (var i = 0; i < $scope.adminList.length; i++) {
            if ($scope.adminList[i].checked == false) {
                flag = false;
                break;
            }
        }

        for (var j = 0; j < $scope.adminList.length; j++)
            $scope.adminList[j].checked = !flag;
        $scope.checkAll = !flag;
    };

    $scope.adminCheck = function (index) {
        if ($scope.adminList[index].checked)
            $scope.checkAll = !$scope.adminList[index].checked;
        $scope.adminList[index].checked = !$scope.adminList[index].checked;
    };

    $scope.openAddDialog = function () {
        $scope.modelDat = {
            "title": "添加",
            "username": "",
            "realname": "",
            "email": "",
            "passwd": "",
            "passwdConfirm": "",
            "userLevel": 1
        };
        $scope.modelLevelSelect(0);
        $('#adminConfigModal').modal('show');
    };

    $scope.openEditDialog = function () {
        var count = 0, target;
        for (var i = 0; i < $scope.adminList.length; i++) {
            if ($scope.adminList[i].checked) {
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

        $scope.modelDat = {
            "title": "修改",
            "id": $scope.adminList[target].id,
            "username": $scope.adminList[target].username,
            "realname": $scope.adminList[target].realname,
            "email": $scope.adminList[target].email,
            "userLevel": $scope.adminList[target].userLevel
        };
        for (var j = 0; j < $scope.UserLevelList.length; j++) {
            if ($scope.adminList[target].userLevel == $scope.UserLevelList[j].id) {
                $scope.modelLevelSelect(j);
                break;
            }
        }

        $('#adminConfigModal').modal('show');
    };

    $scope.modelLevelSelect = function (index) {
        $scope.modelLevelDat = $scope.UserLevelList[index].levelName;
        $scope.modelDat.userLevel = $scope.UserLevelList[index].id;
    };

    $scope.modelConfim = function (title) {
        var params = $scope.modelDat;
        if (!$scope.usernameChange()) return;
        if (!$scope.realnameChange()) return;
        if (!$scope.mailChange()) return;

        if (params.title == "添加") {
            if (!$scope.passwdChange()) return;
            delete params.title;
            delete params.passwdConfirm;
            UserService.doInsert($scope, $http, params);
            $('#adminConfigModal').modal('hide');
        } else if (params.title == "修改") {
            delete params.title;
            delete params.passwd;
            delete params.passwdConfirm;
            UserService.doEdit($scope, $http, params);
            $('#adminConfigModal').modal('hide');
        }
    };

    $scope.usernameChange = function () {
        if ($scope.modelDat.username == "") {
            $scope.usernameEmpty = true;
            return false;
        } else {
            $scope.usernameEmpty = false;
        }
        return true;
    };

    $scope.realnameChange = function () {
        if ($scope.modelDat.realname == "") {
            $scope.realnameEmpty = true;
            return false;
        } else {
            $scope.realnameEmpty = false;
        }
        return true;
    };

    $scope.mailChange = function () {
        var partten = new RegExp('.+@.+\..+');
        if (partten.test($scope.modelDat.email)) {
            $scope.mailError = false;
        } else {
            $scope.mailError = true;
            return false;
        }
        return true;
    };

    $scope.passwdChange = function () {
        if ($scope.modelDat.passwd == "") {
            $scope.passwdEmpty = true;
            return false;
        } else {
            $scope.passwdEmpty = false;
        }
        if ($scope.modelDat.passwd == $scope.modelDat.passwdConfirm) {
            $scope.passwdError = false;
        } else {
            $scope.passwdError = true;
            return false;
        }
        return true;
    };

    $scope.doDelete = function () {
        var count = 0;
        for (var i = 0; i < $scope.adminList.length; i++) {
            if ($scope.adminList[i].checked)
                count++;
        }
        $.confirm({
            title: '删除确认',
            content: '选中' + count + '项，确认删除？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                for (var i = 0; i < $scope.adminList.length; i++) {
                    if ($scope.adminList[i].checked)
                        UserService.doDelete($scope, $http, $scope.adminList[i].id);
                }
            },
            cancel: function () {
            }
        });
    };

    $scope.resetPasswd = function (errorFlag) {
        var count = 0;
        for (var i = 0; i < $scope.adminList.length; i++) {
            if ($scope.adminList[i].checked)
                count++;
        }
        if (count < 1) {
            $.alert({
                theme: "material",
                title: "警告",
                content: "请先选择",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        var confirmContent = '<div class="form-group"><input type="password" class="form-control" placeholder="密码" id="newPasswd"></div>' +
            '<div class="form-group"><input type="password" class="form-control" placeholder="确认密码" id="newPasswdConfirm"></div>';
        if (errorFlag) {
            confirmContent = '<p class="errorBlock">密码不一致</p>' + confirmContent;
        }
        $.confirm({
            title: '请输入新密码',
            content: confirmContent,
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                var newPasswd = $('#newPasswd').val();
                var newPasswdConfirm = $('#newPasswdConfirm').val();
                if (newPasswd != newPasswdConfirm) {
                    $scope.resetPasswd(true);
                    return;
                }

                for (var i = 0; i < $scope.adminList.length; i++) {
                    if ($scope.adminList[i].checked) {
                        var params = {
                            "id": $scope.adminList[i].id,
                            "passwd": newPasswd
                        };
                        UserService.doResetPasswd($scope, $http, params);
                    }
                }
            },
            cancel: function () {
            }
        });

    };

});

//////////////////////////////////////// service ////////////////////////////////////////////////////
app.service('UserService', function () {
    this.getAllUser = function ($scope, $http) {
        getAllUser($scope, $http);
    };
    var getAllUser = function ($scope, $http) {
        $http({
            method: "GET",
            url: userUrlAll + '?token=' + getCookie("token"),
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

            var adminList = data.data;
            for (var i = 0; i < adminList.length; i++) {
                adminList[i].checked = false;
            }
            $scope.adminList = adminList;
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

    this.getAllUserLevel = function ($scope, $http) {
        $http({
            method: "GET",
            url: userLevelsUrlAll + '?token=' + getCookie("token"),
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

            $scope.UserLevelList = data.data;
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

    this.doInsert = function ($scope, $http, params) {
        $http({
            method: "POST",
            url: userInsertUrl + '?token=' + getCookie("token"),
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

            getAllUser($scope, $http);
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

    this.doEdit = function ($scope, $http, params) {
        $http({
            method: "PUT",
            url: userEditUrl + '?token=' + getCookie("token"),
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

            getAllUser($scope, $http);
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

    this.doDelete = function ($scope, $http, id) {
        $http({
            method: "DELETE",
            url: userDeleteUrl + id + '?token=' + getCookie("token"),
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

            getAllUser($scope, $http);
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

    this.doResetPasswd = function ($scope, $http, params) {
        $http({
            method: "PATCH",
            url: resetPasswdUrl + '?token=' + getCookie("token"),
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

            getAllUser($scope, $http);
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