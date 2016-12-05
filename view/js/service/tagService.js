/**
 * Created by L on 2016/9/1.
 */
app.service('TagService', function () {
    //console.log(tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id);
    getAll = function ($scope, $http) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            'Content-Type': 'application/json'
        }).success(function (data) {
            // debugger;
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
            for (var i = 0; i < tagList.length; i++) {
                tagList[i].checked = false;
            }
            $scope.tagList = tagList;
            $scope.pagination(tagList);
            $('#loadingDialog').modal('hide');
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

    getDeatil = function ($scope, $http, detail) {
        $http({
            method: "GET",
            url: tagGetUrl + "/" + detail + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
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
            var tagList = JSON.parse(data.data).tags;
            for (var i = 0; i < tagList.length; i++) {
                tagList[i].checked = false;
            }
            $scope.tagList = tagList;
            $scope.pagination(tagList);
            $('#loadingDialog').modal('hide');
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
            "tag": {
                "name": $scope.addTagName//标签名
            }
        };
        $('#loadingDialog').modal('show');
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
            if ($scope.addQrcodeCheck == true) {
                createQrcode($scope, $http, data.data.tag.id, $scope.addTagName);
            } else {
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
        $('#loadingDialog').modal('show');
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
            "tag": {
                "id": $scope.tagList[target].id,
                "name": $scope.editTagName
            }
        };
        $('#loadingDialog').modal('show');
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
            "tag": {
                "id": id
            }
        }
        $('#loadingDialog').modal('show');
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