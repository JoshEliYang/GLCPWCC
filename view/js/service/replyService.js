/**
 * Created by johnson on 2016/8/29.
 */

app.service('ReplyServiceGlobal', function () {

    var getBasic = function () {
        return JSON.parse(sessionStorage.getItem("basic"));
    };

    /**
     * get all keyword reply data
     *
     * @param $http
     * @param callback
     */
    this.getKeyWords = function ($http, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: keyWordsUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * insert auto reply message
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.insert = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: keyWordsInsertUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * set inUsing flag
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.setInUsing = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "POST",
            url: keyWordsSetInUsing + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * edit auto-reply message
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.edit = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "PUT",
            url: keyWordsEdit + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * delete
     *
     * @param $http
     * @param id
     * @param callback
     */
    this.delete = function ($http, id, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "DELETE",
            url: deleteKeyWordsUrl + id + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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

    /**
     * get all subscribe reply message
     *
     * @param $http
     * @param callback
     */
    this.getSubscribeReply = function ($http, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: subscribeReplyUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * insert subscribe reply message
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.insertSubscribe = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: insertSubscribeUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * edit subscribe reply message
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.editSubscribe = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "PUT",
            url: keyWordsEdit + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
     * delete subscribe reply message
     *
     * @param $http
     * @param id
     * @param callback
     */
    this.deleteSubscribe = function ($http, id, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "DELETE",
            url: deleteKeyWordsUrl + id + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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

    /**
     * set inUsing flag in subscribe reply messages
     *
     * @param $http
     * @param params
     * @param callback
     */
    this.setInUsingSubscribe = function ($http, params, callback) {
        $('#loadingDialog').modal('show');
        params.basicId = getBasic().id;
        $http({
            method: "POST",
            url: setSubscribeInUsingUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
