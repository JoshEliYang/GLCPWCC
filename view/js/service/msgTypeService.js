/**
 * Created by johnson on 2016/8/29.
 */

app.service('MsgTypeServiceGlobal', function () {

    /**
     * get all msg type
     *
     * @param $http
     * @param callback
     */
    this.getAll = function ($http, callback) {
        $http({
            method: "GET",
            url: msgTypeUrl + '?token=' + getCookie("token"),
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
