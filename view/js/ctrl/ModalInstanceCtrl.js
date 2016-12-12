/**
 * Created by Administrator on 2016/12/6.
 */

var app = angular.module("voucher",[]);
app.controller('ModalInstanceCtrl', function ($scope,$modalInstance,items) {

/*angular.module('voucher').controller('ModalInstanceCtrl',function($scope,$modalInstance,items){ //依赖于modalInstance*/
    $scope.items = items;
    $scope.selected = {
        item : $scope.items[0]
    };
    $scope.ok = function(){
        $modalInstance.close($scope.selected.item); //关闭并返回当前选项
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出

    }
}
);
