/**
 * Created by johnson on 2016/8/23.
 */


$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, TemplateMessageServiceGlobal) {
    TemplateMessageServiceGlobal.getButton($http, function callback(data) {
        for (var i = 0; i < data.length; i++) {
            data[i].css = "";
        }
        data[0].css = "active";
        $scope.buttonsList = data;
        $scope.buttonSelected = data[0];
        sessionStorage.setItem("messagePusherDat", JSON.stringify($scope.buttonSelected));
    });

    $scope.changeBtn = function (id) {
        for (var i = 0; i < $scope.buttonsList.length; i++) {
            $scope.buttonsList[i].css = "";
        }
        $scope.buttonsList[id].css = "active";
        $scope.buttonSelected = $scope.buttonsList[id];
        sessionStorage.setItem("messagePusherDat", JSON.stringify($scope.buttonSelected));
    };
});


/**
 * pass message to parent window
 *
 * @param option
 * @param param
 */
function job(option, param) {
    window.parent.job(option, param);
}
