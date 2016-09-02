/**
 * Created by L on 2016/9/1.
 */

var pageMax = 5;

var app = angular.module('wechatApp', []);

app.controller('wechatController', function ($scope, $http, QrcodeService, PaginationServiceGlobal) {
    $scope.pageNow = 0;

    QrcodeService.getAll($scope, $http);

    $scope.pagination = function (data) {
        PaginationServiceGlobal.doPagination(data, pageMax, function (pageGroup, totalCount) {
            $scope.pageGroup = pageGroup;
            $scope.totalCount = totalCount;
        });
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.pageGroup
        }, $scope.pageNow, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.qrcodeList = showList;
        });
    };

    $scope.showQrcode = function (index) {
        $scope.qrcodeData = {
            "title":$scope.qrcodeList[index].name,
            "qrcode" :"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + $scope.qrcodeList[index].ticket,
            "url" : $scope.qrcodeList[index].url
        };
        $('#qrcodeModal').modal('show');
    };

    $scope.doInsert = function () {
        QrcodeService.createQrcode($scope, $http, $scope.addQrcodeName, $scope.addQrcodeId);
        $('#qrcodeAddModal').modal('hide');
    };

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
    };

    $scope.openPage = function (index) {
        PaginationServiceGlobal.getPage({
            "pageNow": $scope.pageNow,
            "pageGroup": $scope.pageGroup
        }, index, function (pageNow, pageGroup, showList) {
            $scope.pageNow = pageNow;
            $scope.pageGroup = pageGroup;
            $scope.qrcodeList = showList;
        });
    };

    $scope.getPrevious = function () {
        if ($scope.pageNow != 0) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow - 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.qrcodeList = showList;
            });
        }
    };

    $scope.getNext = function () {
        if ($scope.pageNow < $scope.pageGroup.length - 1) {
            PaginationServiceGlobal.getPage({
                "pageNow": $scope.pageNow,
                "pageGroup": $scope.pageGroup
            }, $scope.pageNow + 1, function (pageNow, pageGroup, showList) {
                $scope.pageNow = pageNow;
                $scope.pageGroup = pageGroup;
                $scope.qrcodeList = showList;
            });
        }
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