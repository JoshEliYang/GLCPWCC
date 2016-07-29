/**
 * Created by johsnon on 2016/7/29.
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, BasicService) {
    BasicService.getAll($scope, $http);

});

app.service('BasicService', function () {
    this.getAll = function ($scope, $http) {
        $http({
            method: "GET",
            url: basicUrlAll + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                $.alert('<b>' + data.msg + '</b>');
                return;
            }

            var basicList = data.data;
            for (var i = 0; i < basicList.length; i++) {
                basicList[i].checked = false;
            }
            $scope.basicList = basicList;
        }).error(function () {
            $.alert('<b>请求失败<br>请检查您的网络！</br>');
        });
    }
});
