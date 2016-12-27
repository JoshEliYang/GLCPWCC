/**
 * Created by Administrator on 2016/12/26.
 */

app.service('UserServiceGlobal',function(){

    /**
     * get user list
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
            }
            callback(data.data);
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    this.changeLevel = function($http,data,token,wechatAccount,callback){
        $http({
            method:"POST",
            url:changeLevelUrl + '?token=' + token + "&wechatAccount=" + wechatAccount,
            'Content-Type':'application/json',
            data:data
        }).success(function(){
            callback();
        }).error(function(){
            showError('请求失败<br>请检查您的网络！');
        })
    }

});