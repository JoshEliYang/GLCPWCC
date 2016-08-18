/**
 * Created by L on 2016/8/10.
 */

var app = angular.module('wechatApp', []);
app.controller('wechatController', function($scope, $location) {
    // $scope.myUrl = $location.absUrl();
});


//////////////////////////////////////////////// Service //////////////////////////////////////////////////////////
// app.service('TagService', function () {
//     this.getAll = function ($scope, $http) {
//         getAll($scope, $http);
//     };
//     var getAll = function ($scope, $http) {
//         $http({
//             method: "GET",
//             url: tagGetUrl + '?token=' + getCookie("token"),
//             'Content-Type': 'application/json'
//         }).success(function (data) {
//             if (data.code != 0) {
//                 $.alert({
//                     theme: "material",
//                     title: "警告",
//                     content: '<b>' + data.msg + '</b>',
//                     confirmButtonClass: 'btn-info',
//                     autoClose: 'confirm|10000'
//                 });
//                 return;
//                 var tagList = data.data;
//
//         })
//     }
// })