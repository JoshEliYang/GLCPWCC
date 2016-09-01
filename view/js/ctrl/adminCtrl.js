/**
 * Created by johnson on 2016/8/3.
 *
 * When I written it, only God and I known what it meant.
 * Now, only God knows!
 */

var pageMax = 10;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, AdminServiceGlobal, PaginationServiceGlobal) {

    var getUserCallback = function (data) {
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
                $scope.adminList = showList;
            });
        else
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, 0, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.adminList = showList;
            });
    };

    AdminServiceGlobal.getAllUser($http, getUserCallback);

    AdminServiceGlobal.getAllUserLevel($http, function callback(data) {
        $scope.UserLevelList = data;
    });

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
            AdminServiceGlobal.doInsert($http, params, function callback() {
                AdminServiceGlobal.getAllUser($http, getUserCallback);
            });
            $('#adminConfigModal').modal('hide');
        } else if (params.title == "修改") {
            delete params.title;
            delete params.passwd;
            delete params.passwdConfirm;
            AdminServiceGlobal.doEdit($http, params, function callback() {
                AdminServiceGlobal.getAllUser($http, getUserCallback);
            });
            $('#adminConfigModal').modal('hide');
        }

        location.href = "#btnPanel";
        location.href = "#head";
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
                        AdminServiceGlobal.doDelete($http, $scope.adminList[i].id, function callback() {
                            AdminServiceGlobal.getAllUser($http, getUserCallback);
                        });
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
                        AdminServiceGlobal.doResetPasswd($http, params, function callback() {
                            AdminServiceGlobal.getAllUser($http, getUserCallback);
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
            "pageGroup": $scope.pageGroup
        }, index, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.adminList = showList;
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
                $scope.adminList = showList;
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
                $scope.adminList = showList;
            });
        }
    };

});
