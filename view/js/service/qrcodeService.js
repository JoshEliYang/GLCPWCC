/**
 * Created by L on 2016/9/1.
 */
app.service('QrcodeService', function () {
    this.getAll = function ($scope, $http) {
        getAll($scope, $http);
    };

    var getAll = function ($scope, $http) {
        $('#loadingDialog').modal('show');
        $http({
            method: 'GET',
            url: qrcodeGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
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
            qrcodeList = data.data;
            $scope.qrcodeList = qrcodeList;
            $scope.pagination(qrcodeList);
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
    }

    this.createQrcode = function ($scope, $http, name, id) {
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
    }


})