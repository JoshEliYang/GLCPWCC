/**
 * Created by L on 2016/8/16.
 */

var app = angular.module('wechatApp', []);

app.controller('wechatController', function ($scope, $http, QrcodeService) {
    QrcodeService.getAll($scope, $http);

    $scope.showQrcode = function (index) {
        $scope.qrcodeData = {
            "title":$scope.qrcodeList[index].name,
            "url" :"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + $scope.qrcodeList[index].ticket
        };
        $('#qrcodeModal').modal('show');
    }
    
    $scope.doInsert = function () {
        QrcodeService.createQrcode($scope, $http, $scope.addQrcodeName, $scope.addQrcodeId);
        $('#qrcodeAddModal').modal('hide');
    }

    $scope.openAddDialog = function () {
        $.confirm({
            title: '确认',
            content:'推荐在标签页面里生成二维码，继续吗？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                $('#qrcodeAddModal').modal('show');
            },
            cancel: function () {
            }
        })
    }
})

//////////////////////////////////  Service  /////////////////////////////////
app.service('QrcodeService', function () {
    this.getAll = function ($scope, $http) {
        getAll($scope, $http);
    };

    var getAll = function ($scope, $http) {
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