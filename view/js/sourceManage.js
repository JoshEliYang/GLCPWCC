/**
 * Created by johnson on 2016/8/15.
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

app.controller('wechatCtrl', function ($scope, $http, BasicService, ResourceService) {
    BasicService.getSourceType($scope, $http, ResourceService);
    // BasicService.getDefault($scope, $http, ResourceService);

    $scope.sourceTypeChange = function (index) {
        for (var i = 0; i < $scope.sourceTypeDat.sourceType.length; i++)
            $scope.sourceTypeDat.sourceType[i].default = false;
        $scope.sourceTypeDat.sourceType[index].default = true;
        $scope.sourceTypeDat.default = $scope.sourceTypeDat.sourceType[index];
        $scope.getResource($scope.sourceTypeDat.sourceType[index].type, 0);
    };

    $scope.getResource = function (type, offset) {
        if (type == 'news') {
            ResourceService.getResource($scope, $http, type, offset, maxPageNews);
        } else {
            ResourceService.getResource($scope, $http, type, offset, maxPage);
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

app.service('BasicService', function () {
    this.getSourceType = function ($scope, $http, ResourceService) {
        $.getJSON('js/sourceType.json', function (data) {
            $scope.sourceTypeDat = data;
            getDefault($scope, $http, ResourceService);
        });
    };

    this.getDefault = function ($scope, $http, ResourceService) {
        getDefault($scope, $http, ResourceService);
    };
    var getDefault = function ($scope, $http, ResourceService) {
        for (var i = 0; i < $scope.sourceTypeDat.sourceType.length; i++) {
            if ($scope.sourceTypeDat.sourceType[i].default) {
                $scope.sourceTypeDat.default = $scope.sourceTypeDat.sourceType[i];
                // $scope.$apply();
                ResourceService.getResource($scope, $http, 'image', 0, maxPage);
                break;
            }
        }
    };
});

app.service('ResourceService', function () {
    this.getResource = function ($scope, $http, type, offset, count) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            /*url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=1',*/
            'Content-Type': 'application/json'
            //data: params
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

            var resourceDat = data.data;
            for (var i = 0; i < resourceDat.item.length; i++) {
                if (type == 'image') {
                    var str = resourceDat.item[i].url;
                    str = str.replace('http://mmbiz.qpic.cn/mmbiz', 'https://mmbiz.qlogo.cn/mmbiz');
                    resourceDat.item[i].url = getImageUrl + '?wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id + '&url=' + encodeURI(str);
                }
                if (type == 'news') {
                    for (var j = 0; j < resourceDat.item[i].content.news_item.length; j++) {
                        var strx = resourceDat.item[i].content.news_item[j].thumb_url;
                        strx = strx.replace('http://mmbiz.qpic.cn/mmbiz', 'https://mmbiz.qlogo.cn/mmbiz');
                        resourceDat.item[i].content.news_item[j].thumb_url = getImageUrl + '?wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id + '&url=' + encodeURI(strx);
                    }
                }
                resourceDat.item[i].date = TimestampToDate(resourceDat.item[i].update_time);
            }
            $scope.resourceDat = resourceDat;
            if (type == 'news') {
                doPaginating($scope, offset, maxPageNews, $scope.resourceDat);
            } else {
                doPaginating($scope, offset, maxPage, $scope.resourceDat);
            }

            $('#loadingDialog').modal('hide');
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

    var doPaginating = function ($scope, offset, maxPageDat, resourceDat) {
        // var maxPageDat = 200;
        var pageNow = Math.floor(offset / maxPageDat);
        pageNow += 1;

        var pageTotal = Math.ceil(resourceDat.total_count / maxPageDat);
        $scope.pageTotal = pageTotal;
        if (pageTotal == 0) pageTotal = 1;

        var pageList = new Array();
        if (pageTotal <= 10) {
            for (var i = 0; i < pageTotal; i++) {
                var item = {
                    "page": i + 1,
                    "offset": i * maxPageDat,
                    "css": ""
                };
                if (pageNow == (i + 1)) item.css = 'active';
                pageList.push(item);
            }
        } else {
            var itemDat;
            var j = 0;
            if (pageNow < 5) {  // when this page number is below 5
                for (j = 0; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < 10; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else if (pageNow > pageTotal - 5) {  // when this page is in last 5 pages
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageTotal; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else {    // when this page is in the middle
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageNow + 5; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            }
        }
        $scope.pageList = pageList;

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