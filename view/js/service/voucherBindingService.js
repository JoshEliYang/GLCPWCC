angular.module('voucherBindingService', [])
    .factory('voucherBinding', function ($http) {
        return{

            getUserFilter: function (token,wechatAccount,data) {
                return $http({
                    method: 'POST',
                    url: usersFilter + '?token=' + token + '&wechatAccount=' +wechatAccount,
                    data:data
                });
            }

        }
    });/**
 * Created by Administrator on 2016/12/6.
 */
