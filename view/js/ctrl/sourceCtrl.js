/**
 * Created by johnson on 2016/8/15.
 *
 * Trust me, try to understand this script is wasting your time.
 * If you are doing that, forget it and go get some drink.
 *
 * If you still want to fix some things, here are some tips for you.
 * Tips: ...\(-_-)/
 * I hope you won't make a mess of it, good luck for you!
 */

/**
 * max number in a page (used for pagination)
 *
 * @type {number}
 */
var maxPage = 12;
var maxPageNews = 3;

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, ResourceServiceGlobal) {

    $.getJSON('js/service/sourceType.json', function (data) {
        $scope.sourceTypeDat = data;
        getDefault();
    });

    var getDefault = function () {
        for (var i = 0; i < $scope.sourceTypeDat.sourceType.length; i++) {
            if ($scope.sourceTypeDat.sourceType[i].default) {
                $scope.sourceTypeDat.default = $scope.sourceTypeDat.sourceType[i];
                // $scope.$apply();
                $scope.getResource($scope.sourceTypeDat.sourceType[i].type, 0);
                break;
            }
        }
    };

    $scope.sourceTypeChange = function (index) {
        for (var i = 0; i < $scope.sourceTypeDat.sourceType.length; i++)
            $scope.sourceTypeDat.sourceType[i].default = false;
        $scope.sourceTypeDat.sourceType[index].default = true;
        $scope.sourceTypeDat.default = $scope.sourceTypeDat.sourceType[index];
        $scope.getResource($scope.sourceTypeDat.sourceType[index].type, 0);
    };

    $scope.getResource = function (type, offset) {
        if (type == 'news') {
            ResourceServiceGlobal.getResource($http, {
                "type": type,
                "offset": offset,
                "count": maxPageNews
            }, function (pageTotal, pageList) {
                $scope.pageTotal = pageTotal;
                $scope.pageList = pageList;
            }, function callback(resourceDat) {
                $scope.resourceDat = resourceDat;
            });
        } else {
            ResourceServiceGlobal.getResource($http, {
                "type": type,
                "offset": offset,
                "count": maxPage
            }, function (pageTotal, pageList) {
                $scope.pageTotal = pageTotal;
                $scope.pageList = pageList;
            }, function callback(resourceDat) {
                $scope.resourceDat = resourceDat;
            });
        }
    };


    $scope.goPrevious = function (type) {
        var index = 0;
        for (var i = 0; i < $scope.pageList.length; i++) {
            if ($scope.pageList[i].css == 'active') {
                index = i;
                break;
            }
        }
        if (i != 0) {
            $scope.getResource(type, $scope.pageList[i - 1].offset);
        }
    };

    $scope.goNext = function (type) {
        var index = 0;
        for (var i = 0; i < $scope.pageList.length; i++) {
            if ($scope.pageList[i].css == 'active') {
                index = i;
                break;
            }
        }
        if (i != $scope.pageList.length - 1) {
            $scope.getResource(type, $scope.pageList[i + 1].offset);
        }
    };

    $scope.openPicDialog = function (index) {
        $scope.picDialogDat = $scope.resourceDat.item[index];
        $('#picDetailModal').modal('show');
        location.href = "#head";
        location.href = "#picDetailModa";
    };

    $scope.openNewsDialog = function (index) {
        $scope.newsModelDat = $scope.resourceDat.item[index];
        $('#newsDetailModal').modal('show');
        location.href = "#head";
        location.href = "#picDetailModa";
    };
});


var clip = new ZeroClipboard(document.getElementById("picCopyBtn"), {
    moviePath: "lib/ZeroClipboard/ZeroClipboard.swf"
});

clip.on('complete', function (client, args) {
    $.alert({
        theme: "material",
        title: "复制成功",
        content: '<b>复制内容：' + args.text + '</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
});

var newsClip = new ZeroClipboard(document.getElementById("newsCopyBtn"), {
    moviePath: "lib/ZeroClipboard/ZeroClipboard.swf"
});

newsClip.on('complete', function (client, args) {
    $.alert({
        theme: "material",
        title: "复制成功",
        content: '<b>复制内容：' + args.text + '</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
});