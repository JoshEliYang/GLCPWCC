/**
 * Created by johnson on 2016/8/15.
 */

/**
 * max number in a pge (used for pagination)
 *
 * @type {number}
 */
var maxPage = 12;

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
        ResourceService.getResource($scope, $http, type, offset, maxPage);
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
        $http({
            method: "GET",
            /*url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,*/
            url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=1',
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
                resourceDat.item[i].date = TimestampToDate(resourceDat.item[i].update_time);
            }
            $scope.resourceDat = resourceDat;
            doPaginating($scope, offset, $scope.resourceDat);
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

    var doPaginating = function ($scope, offset, resourceDat) {
        // var maxPage = 200;
        var pageNow = Math.floor(offset / maxPage);
        pageNow += 1;

        var pageTotal = Math.ceil(resourceDat.total_count / maxPage);
        $scope.pageTotal = pageTotal;
        if (pageTotal == 0) pageTotal = 1;

        var pageList = new Array();
        if (pageTotal <= 10) {
            for (var i = 0; i < pageTotal; i++) {
                var item = {
                    "page": i + 1,
                    "offset": i * maxPage,
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
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPage,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < 10; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else if (pageNow > pageTotal - 5) {  // when this page is in last 5 pages
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPage,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageTotal; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else {    // when this page is in the middle
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPage,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageNow + 5; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPage,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            }
        }
        $scope.pageList = pageList;

    };
});
