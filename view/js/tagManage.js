/**
 * Created by L on 2016/8/10.
 */

var app = angular.module('wechatApp', []);
app.controller('wechatController', function($scope, $http, TagService) {
    getAll($scope, $http);
    
    $scope.openAddDialog = function () {
        $('#tagAddModal').modal('show');
    }

    $scope.openEditDialog = function () {
        var count = 0;
        for (var i = 0; i < $scope.tagList.length; i++) {
            if ($scope.tagList[i].checked) {
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
        $scope.editTagName = $scope.tagList[target].name;
        $('#tagEditModal').modal('show');
    }

    $scope.tagNameChange = function () {
        if ($scope.tagName == "") {
            $scope.tagNameEmpty = true;
            return false;
        } else {
            $scope.tagNameEmpty = false;
        }
        return true;
    };
    
    $scope.doInsert = function () {
        TagService.doInsert($scope, $http);
        $('#tagAddModal').modal('hide');
    }

    $scope.doEdit = function () {
        TagService.doEdit($scope, $http);
        $('#tagEditModal').modal('hide');
    }

    $scope.doCheckAll = function () {
        var flag = true;
        for (var i = 0; i < $scope.tagList.length; i++) {
            if ($scope.tagList[i].checked == false) {
                flag = false;
                break;
            }
        }

        for (var i = 0; i < $scope.tagList.length; i++)
            $scope.tagList[i].checked = !flag;
        $scope.checkAll = !flag;
    }
    
    $scope.doCheck = function (index) {
        if ($scope.tagList[index].checked)
            $scope.checkAll = !$scope.tagList[index].checked;
        $scope.tagList[index].checked = !$scope.tagList[index].checked;
    }
    
    $scope.doDelete = function () {
        var count = 0;
        for(var i = 0; i < $scope.tagList.length; i++){
            if($scope.tagList[i].checked){
                count++;
            }
        }
        if(count==0){
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请先选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        $.confirm({
            title: '删除确认',
            content: '选中' + count + '项，确认删除？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                for (var i = 0; i < $scope.tagList.length; i++) {
                    if ($scope.tagList[i].checked) {
                        TagService.doDelete($scope, $http, $scope.tagList[i].id);
                    }
                }
            },
            cancel: function () {
            }
        })
    }

});

//////////////////////////////////////////////// Service //////////////////////////////////////////////////////////
app.service('TagService', function () {
    //console.log(tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id);
    getAll = function ($scope, $http) {
        $http({
            method: "GET",
            url: tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            'Content-Type': 'application/json'
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
            var tagList = data.data.tags;
            for(var i = 0; i < tagList.length; i++){
                tagList[i].checked = false;
            }
            $scope.tagList = tagList;
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

    this.doInsert = function ($scope, $http) {
        var tagParam = {
            "tag" : {
                "name" : $scope.addTagName//标签名
            }
        };
        console.log(tagParam);
        $http({
            method: "POST",
            url: tagCreateUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            'Content-Type': 'application/json',
            data: tagParam
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
            if($scope.addQrcodeCheck == true){
                createQrcode($scope, $http, data.data.tag.id, $scope.addTagName);
            }else {
                getAll($scope, $http);
            }
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

    createQrcode = function ($scope, $http, id, name) {
        var qrParam = {
            "id": id,
            "name": name
        };
        $http({
            method: "POST",
            url: qrcodeCreateUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            'Content-Type': 'application/json',
            data: qrParam
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
        })
    };

    this.doEdit = function ($scope, $http) {
        var param = {
            "tag" : {
                "id" : $scope.tagList[target].id,
                "name" : $scope.editTagName
            }
        };
        $http({
            method: "POST",
            url: tagUpdateUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
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
                content: '<b>请求失败<br>请检查您的网络！</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    }
    
    this.doDelete = function ($scope, $http, id) {
        var param = {
            "tag":{
                "id" : id
            }
        }
        $http({
            method: "POST",
            url: tagDeleteUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
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
                content: '<b>请求失败<br>请检查您的网络！</b>',
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
        });
    }

})



