/**
 * Created by johnson on 2016/8/30.
 */

app.service('ButtonServiceGlobal', function () {

    /**
     * get buttons group
     *
     * @param $http
     * @param getButtons (get buttons function)
     * @param buttonsCallback
     * @param callback
     */
    this.getButtonGroup = function ($http, getButtons, buttonsCallback, callback) {
        $http({
            method: "GET",
            url: buttonGroupUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            var buttonGroupList = data.data;
            for (var i = 0; i < buttonGroupList.length; i++) {
                if (buttonGroupList[i].default == true) {
                    buttonGroupList[i].class = "active";
                    getButtons($http, buttonGroupList[i].id, buttonsCallback);
                } else {
                    buttonGroupList[i].class = "";
                }
            }
            callback(buttonGroupList);
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * get buttons
     *
     * @param $http
     * @param groupId
     * @param callback
     */
    this.getButtons = function ($http, groupId, callback) {
        $http({
            method: "GET",
            url: buttonsUrl + groupId + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            var buttonsList = data.data;
            var selectId;
            for (var i = 0; i < buttonsList.length; i++) {
                if (buttonsList[i].default == true) {
                    buttonsList[i].class = "active";
                    selectId = buttonsList[i].id;
                } else {
                    buttonsList[i].class = "";
                }
            }
            callback(buttonsList, selectId);
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     *
     * @param $scope
     * @param buttonId
     */
    this.changeButton = function ($scope, buttonId) {
        for (var i = 0; i < $scope.buttonsList.length; i++) {
            if (buttonId == $scope.buttonsList[i].id) {
                $scope.mainFramePage = $scope.buttonsList[i].uri;
                $scope.mainFrameHeight = $scope.buttonsList[i].frameHeight;
                break;
            }
        }
    };

    var showError = function (msg) {
        $.alert({
            theme: "material",
            title: "警告",
            content: '<b>' + msg + '</b>',
            confirmButtonClass: 'btn-info',
            autoClose: 'confirm|10000'
        });
    };
});
