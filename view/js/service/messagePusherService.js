/**
 * Created by johnson on 2016/8/30.
 */

app.service('TemplateMessageServiceGlobal', function () {

    var getBasic = function () {
        return JSON.parse(sessionStorage.getItem("basic"));
    };

    /**
     * get button of different message templates
     *
     * @param $http
     * @param callback
     */
    this.getButton = function ($http, callback) {
        $http({
            method: "GET",
            url: TemplateMessageButtonsUrl + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
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
