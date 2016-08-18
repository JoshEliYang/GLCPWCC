/**
 * Created by L on 2016/8/16.
 */

var app = angular.module('wechatApp', []);

app.controller('wechatController', function ($scope, $http, QrcodeService) {
    QrcodeService.getAll($scope, $http);

    $scope.testconsole = function () {
        alert("success!");
    }

    $scope.opeAddDialog = function () {
        alert("success!");
    }
    
    $scope.doCheckAll = function () {
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
        if (param.count == $scope.qrcodeList.length) {
            $scope.checkAll = false;
            doChecked(false);
        } else {
            $scope.checkAll = true;
            doChecked(true);
        }
    };
    
    $scope.doSelect = function (index) {
        $scope.qrcodeList[index].checked = !$scope.qrcodeList[index].checked;
        var param = {
            "count": 0,
            "target": 0
        };
        getSelect(param);
            if (param.count == $scope.qrcodeList.length) {
            $scope.checkAll = true;
        } else {
            $scope.checkAll = false;
        }
    };

    var getSelect = function (param) {
        param.count = 0;
        param.target = 0;
        for (var i = 0; i < $scope.qrcodeList.length; i++) {
            if ($scope.qrcodeList[i].checked) {
                param.count++;
                param.target = i;
            }
        }
    };

    var doChecked = function (flag) {
        for (var i = 0; i < $scope.qrcodeList.length; i++) {
            $scope.qrcodeList[i].checked = flag;
        }
    };

    
})



//////////////////////////////////  Service  /////////////////////////////////
app.service('QrcodeService', function () {
    this.getAll = function ($scope, $http) {
      getAll($scope, $http);
    };

    var getAll = function ($scope, $http) {
        $http({
            method: 'GET',
            url: qrcodeGetUrl + '?token=' + getCookie("token"),
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
            var qrcodeList = data.data;
            for (var i = 0; i < qrcodeList.length; i++) {
                qrcodeList[i].checked = false;
            }
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
    };


})