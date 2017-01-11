/**
 * Created by Administrator on 2017/1/4.
 */
angular.module('userSubscribeTableService',[])
    .factory('subscribeTable',function($http){
    return {
        getSubscribeCount : function(data,wechatAccount){
            return $http({
                method:'POST',
                url:getSubscribeCount+"?wechatAccount=" + wechatAccount,
                data:data
            })
        }
    }
});