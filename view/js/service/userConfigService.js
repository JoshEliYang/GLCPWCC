/**
 * Created by Administrator on 2016/12/26.
 */

app.service('UserServiceGlobal',function(){

    /**
     * get admin list
     *
     * @param $http
     * @param callback
     */
    this.getAllUser = function ($http, callback,token, wechatAccount) {
        //$('#loadingDialog').modal('show');
        $http({
            method: "GET",
            url: getUserUrl + '?token=' + token + "&wechatAccount=" + wechatAccount ,
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

});