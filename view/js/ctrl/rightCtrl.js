/**
 * Created by johnson on 2016/9/1.
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, AdminServiceGlobal, ButtonServiceGlobal) {

    /**
     * init and draw table on the page
     */
    var init = function () {
        // get all modules
        ButtonServiceGlobal.getButtonGroupOnly($http, function (data) {
            $scope.ButtonGroups = data;
            // get all user levels
            AdminServiceGlobal.getAllUserLevel($http, function callback(data) {
                $scope.UserLevelList = data;
                // get level-buttonGroup mapping
                AdminServiceGlobal.getAllLevelRights($http, function (data) {
                    $scope.rightLevelList = data;

                    /**
                     * levelButtonMapping[level_index][button_index] = true/false
                     * @type {Array}
                     */
                    $scope.levelButtonMapping = new Array();
                    for (var i = 0; i < $scope.UserLevelList.length; i++) {
                        var buttonRow = new Array();
                        for (var j = 0; j < $scope.ButtonGroups.length; j++) {
                            if (getRight($scope.UserLevelList[i].id, $scope.ButtonGroups[j].id, data)) {
                                buttonRow.push({
                                    "column": j,
                                    "value": true
                                });
                            } else {
                                buttonRow.push({
                                    "column": j,
                                    "value": false
                                });
                            }
                        }
                        var rowDat = {
                            "row": i,
                            "levelName": $scope.UserLevelList[i].levelName,
                            "levelRight": buttonRow
                        };
                        $scope.levelButtonMapping.push(rowDat);
                    }
                });
            });
        });
    };
    init();

    /**
     * get enable/unable by levelId and groupId
     *
     * @param levelId
     * @param groupId
     * @param data
     * @returns {boolean}
     */
    var getRight = function (levelId, groupId, data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].levelId == levelId && data[i].groupId == groupId) {
                return true;
            }
        }
        return false;
    };

    /**
     * enable/unable level's right
     *
     * @param row
     * @param column
     */
    $scope.setRight = function (row, column) {
        var getDat = function (data) {
            return {
                "levelId": $scope.UserLevelList[data.row].id,
                "groupId": $scope.ButtonGroups[data.column].id,
                "enable": !getRight($scope.UserLevelList[data.row].id, $scope.ButtonGroups[data.column].id, $scope.rightLevelList)
            };
        };
        AdminServiceGlobal.setLevelRight($http, getDat({
            "row": row,
            "column": column
        }), function () {
            init();
        });
    };

    /**
     * do add/edit/delete
     *
     * @param action
     */
    $scope.openNewLevel = function (action) {
        var getChecked = function () {
            var list = new Array();
            var result = {
                "list": list,
                "count": 0
            };
            for (var i = 0; i < $scope.levelButtonMapping.length; i++) {
                if ($scope.levelButtonMapping[i].checked) {
                    result.count++;
                    var dat = {
                        "id": $scope.UserLevelList[i].id,
                        "levelName": $scope.levelButtonMapping[i].levelName
                    };
                    result.list.push(dat);
                }
            }
            return result;
        };

        if (action == 'add') {
            $scope.modalDat = {
                "levelName": "",
                "title": "添加"
            };
            $('#LevelModel').modal('show');
        } else if (action == 'edit') {
            var result = getChecked();
            if (result.count != 1) {
                showWarning('请选着1个对象，本操作不能多选');
                return;
            }
            $scope.modalDat = {
                "levelName": result.list[0].levelName,
                "id": result.list[0].id,
                "title": "修改"
            };
            $('#LevelModel').modal('show');
        } else if (action == 'delete') {
            var resultx = getChecked();
            $.confirm({
                title: '警告',
                content: '本次删除会删除相关用户，且不可恢复，确认删除？',
                theme: "material",
                confirmButtonClass: 'btn-info',
                cancelButtonClass: 'btn-danger',
                confirm: function () {
                    for (var i = 0; i < resultx.list.length; i++) {
                        AdminServiceGlobal.removeUserLevel($http, resultx.list[0].id, function () {
                            init();
                        });
                    }
                },
                cancel: function () {
                }
            });
        }
    };

    /**
     * add new level
     */
    $scope.addNewLevel = function () {
        if ($scope.modalDat.title == '添加') {
            AdminServiceGlobal.addAdminLevel($http, {
                "levelName": $scope.modalDat.levelName
            }, function callback() {
                $('#LevelModel').modal('hide');
                init();
            });
        } else if ($scope.modalDat.title == '修改') {
            AdminServiceGlobal.editUserLevel($http, {
                "id": $scope.modalDat.id,
                "levelName": $scope.modalDat.levelName
            }, function () {
                $('#LevelModel').modal('hide');
                init();
            });
        }
    };

    $scope.doCheck = function (index) {
        $scope.levelButtonMapping[index].checked = !$scope.levelButtonMapping[index].checked;
    };

    var showWarning = function (msg) {
        $.alert({
            theme: "material",
            title: "警告",
            content: '<b>' + msg + '</b>',
            confirmButtonClass: 'btn-info',
            autoClose: 'confirm|10000'
        });
    };
});
