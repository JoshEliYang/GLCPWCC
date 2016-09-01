/**
 * Created by johnson on 2016/8/30.
 */

app.service('WechatMenuServiceGlobal', function () {

    var getBasic = function () {
        return JSON.parse(sessionStorage.getItem("basic"));
    };

    /**
     * get WeChat menu
     *
     * @param $http
     * @param callback
     */
    this.getMenu = function ($http, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: getMenuUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
            // url: getMenuUrl + '?token=' + getCookie("token") + '&wechatAccount=1',
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            callback(JSON.parse(data.data));
            $('#loadingDialog').modal('hide');
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * set WeChat menu
     *
     * @param $http
     * @param menu
     * @param callback
     */
    this.setMenu = function ($http, menu, callback) {
        $('#loadingDialog').modal('show');
        $http({
            method: "POST",
            url: getMenuUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
            'Content-Type': 'application/json',
            data: menu
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            $('#loadingDialog').modal('hide');
            callback(JSON.parse(data.data));
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
