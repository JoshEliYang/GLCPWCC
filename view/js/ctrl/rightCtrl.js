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

});
