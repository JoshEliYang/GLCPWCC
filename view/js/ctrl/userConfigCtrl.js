/**
 * Created by Administrator on 2016/12/26.
 */

var pageMax = 10;

var app=angular.module('userConfig',[]);
app.controller('userConfigCtrl',function($scope,$http,UserServiceGlobal,PaginationServiceGlobal){

    $scope.wechatAccount = 1;
    $scope.token =1482732703364;


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
                $scope.userList = showList;
            });
        else
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, 0, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.userList = showList;
            });
    };

    UserServiceGlobal.getAllUser($http, getUserCallback,$scope.token,$scope.wechatAccount);


});