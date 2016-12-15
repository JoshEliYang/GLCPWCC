angular.module('voucherBindingService', [])
    .factory('voucherBinding', function ($http) {
        return{

            getUserFilter: function (token,wechatAccount,data) {
                return $http({
                    method: 'POST',
                    url: usersFilter + '?token=' + token + '&wechatAccount=' +wechatAccount,
                    data:data
                });
            },

            getRestVoucher:function(token,wechatAccount){
                return $http({
                    method:'GET',
                    url:restVoucher + '?token=' + token + '&wechatAccount=' +wechatAccount
                })
            },

            blingVoucher:function(token,wechatAccount,data) {
                return $http({
                    method:'POST',
                    url:bindVoucher + '?token=' + token + '&wechatAccount=' +wechatAccount,
                    data:data
                })
            },

            bindChosenVoucher:function(token,wechatAccount,data){
                return $http({
                    method:'POST',
                    url:bindChosen + '?token=' + token + '&wechatAccount=' +wechatAccount,
                    data:data
                })
            }

        }
    });/**
 * Created by Administrator on 2016/12/6.
 */
