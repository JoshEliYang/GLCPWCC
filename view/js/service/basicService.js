/**
 * Created by johnson on 2016/8/29.
 *
 * need angularJs, JQuery, JQuery-Confirm
 */


app.service('BasicServiceGlobal', function () {

    /**
     * get all basic model
     *
     * use callback function to get data and do paginate
     *
     * @param $http
     * @param callback
     */
    this.getAll = function ($http, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: basicUrlAll + '?token=' + getCookie("token"),
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
     * get all basic models except unUsing ones
     *
     * @param $http
     * @param callback
     */
    this.getBasic = function ( $http,callback) {
        $http({
            method: "GET",
            url: basicUrl + '?token=' + getCookie("token"),
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
     * set using
     *
     * just send request with no response
     *
     * @param $http
     * @param param
     * @param callback
     */
    this.setUsing = function ($http, param, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PUT",
            url: basicSetUsingUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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
     * set default
     *
     * @param $http
     * @param param
     * @param callback
     */
    this.setDefault = function ($http, param, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PUT",
            url: basicSetDefaultUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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
     * do insert
     *
     * @param $http
     * @param param
     * @param callback
     */
    this.insert = function ($http, param, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "POST",
            url: basicInsertUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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
     * edit basic model
     *
     * @param $http
     * @param param
     * @param callback
     */
    this.edit = function ($http, param, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PUT",
            url: basicEditUrl + '?token=' + getCookie("token"),
            'Content-Type': 'application/json',
            data: param
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
     * do delete
     *
     * @param $http
     * @param id
     * @param callback
     */
    this.delete = function ($http, id, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "DELETE",
            url: basicDeleteUrl + id + '?token=' + getCookie("token"),
            'Content-Type': 'application/json'
            // data: param
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
     * set using third part token server or not
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.selectTokenServer = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "PUT",
            url: selectTokenServerUrl + '?token=' + getCookie("token"),
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