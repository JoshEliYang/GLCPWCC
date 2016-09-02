/**
 * Created by L on 2016/9/1.
 */

var pageMax = 10;

var app = angular.module('wechatApp', []);
app.controller('wechatController', function($scope, $http, TagService, PaginationServiceGlobal) {
    $scope.pageNow = 0;

    getAll($scope, $http);

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
            $scope.tagList = showList;
        });
    };

    $scope.openAddDialog = function () {
        $('#tagAddModal').modal('show');
    };

    $scope.openEditDialog = function () {
        var count = 0;
        for (var i = 0; i < $scope.tagList.length; i++) {
            if ($scope.tagList[i].checked) {
                target = i;
                count++;
            }
        }
        if (count == 0) {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请先选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        if (count > 1) {
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请去除多余选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        $scope.editTagName = $scope.tagList[target].name;
        $('#tagEditModal').modal('show');
    };

    $scope.tagNameChange = function () {
        if ($scope.tagName == "") {
            $scope.tagNameEmpty = true;
            return false;
        } else {
            $scope.tagNameEmpty = false;
        }
        return true;
    };

    $scope.doInsert = function () {
        TagService.doInsert($scope, $http);
        $('#tagAddModal').modal('hide');
    };

    $scope.doEdit = function () {
        TagService.doEdit($scope, $http);
        $('#tagEditModal').modal('hide');
    };

    $scope.doCheckAll = function () {
        var flag = true;
        for (var i = 0; i < $scope.tagList.length; i++) {
            if ($scope.tagList[i].checked == false) {
                flag = false;
                break;
            }
        }

        for (var i = 0; i < $scope.tagList.length; i++)
            $scope.tagList[i].checked = !flag;
        $scope.checkAll = !flag;
    };

    $scope.doCheck = function (index) {
        if ($scope.tagList[index].checked)
            $scope.checkAll = !$scope.tagList[index].checked;
        $scope.tagList[index].checked = !$scope.tagList[index].checked;
    };

    $scope.doDelete = function () {
        var count = 0;
        for(var i = 0; i < $scope.tagList.length; i++){
            if($scope.tagList[i].checked){
                count++;
            }
        }
        if(count==0){
            $.alert({
                theme: "material",
                title: "警告",
                content: "<b>请先选择</b>",
                confirmButtonClass: 'btn-info',
                autoClose: 'confirm|10000'
            });
            return;
        }
        $.confirm({
            title: '删除确认',
            content: '选中' + count + '项，确认删除？',
            theme: "material",
            confirmButtonClass: 'btn-info',
            cancelButtonClass: 'btn-danger',
            confirm: function () {
                for (var i = 0; i < $scope.tagList.length; i++) {
                    if ($scope.tagList[i].checked) {
                        TagService.doDelete($scope, $http, $scope.tagList[i].id);
                    }
                }
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
            $scope.tagList = showList;
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
                $scope.tagList = showList;
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
                $scope.tagList = showList;
            });
        }
    };

});
