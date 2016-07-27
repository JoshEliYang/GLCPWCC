/**
 * Created by johnson on 16-7-20.
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, InitService) {
    InitService.getUser($scope);
});

app.service('InitService', function () {
    this.getUser = function ($scope) {
        var token = getCookie("token");
        var username = getCookie("username");
        if (token == null || username == null) {
            location.href = "login.html";
            return;
        }
        $scope.username = username;
    }
});
