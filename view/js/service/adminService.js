/**
 * Created by johnson on 2016/8/28.
 *
 */

app.service('AdminServiceGlobal', function () {

    this.logout = function () {
        setCookie("token", "", -1);
        location.href = "login.html";
    };

    /**
     * get admin info and storage it into session storage
     * if user haven't login or token has expired, let user login again.
     *
     * please use callback function to get result data
     *
     * @param $http
     * @param callback
     */
    this.getAdmin = function ($http, callback) {
        var token = getCookie("token");
        var username = getCookie("username");
        if (token == null || token == "" || username == null || username == "") {
            location.href = "login.html";
            return;
        }

        $http({
            method: "GET",
            url: userInfoUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code == 101) {
                setCookie("token", "", -1);
                location.href = "login.html";
            }
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            sessionStorage.setItem("admin", JSON.stringify(data.data));
            callback(data.data);
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * get admin list
     *
     * @param $http
     * @param callback
     */
    this.getAllUser = function ($http, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: userUrlAll + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            callback(data.data);
            $('#loadingDialog').modal('hide');
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * get admin levels
     *
     * @param $http
     * @param callback
     */
    this.getAllUserLevel = function ($http, callback) {
        $http({
            method: "GET",
            url: userLevelsUrlAll + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            callback(data.data);
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };


    /**
     * insert admin into db
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.doInsert = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "POST",
            url: userInsertUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            $('#loadingDialog').modal('hide');
            callback();
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    this.doEdit = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PUT",
            url: userEditUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            $('#loadingDialog').modal('hide');
            callback();
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * delete admin by id
     *
     * @param $http
     * @param id
     * @param callback
     */
    this.doDelete = function ($http, id, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "DELETE",
            url: userDeleteUrl + id + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            // data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            $('#loadingDialog').modal('hide');
            callback();
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    this.doResetPasswd = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PATCH",
            url: resetPasswdUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            $('#loadingDialog').modal('hide');
            callback();
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
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