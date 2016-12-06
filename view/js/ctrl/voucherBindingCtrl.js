/**
 * Created by Administrator on 2016/12/6.
 */

/*var app = angular.module("voucher",['ui.bootstrap']);
app.controller('voucherCtrl', function ($scope,$modal,$log) {*/

angular.module("voucher",['ui.bootstrap']).controller('voucherCtrl', function ($scope,$modal,$log) {

    $scope.openFilter = function(){
        $scope.items = ['html5','jq','FE-演示平台'];
        var modalInstance = $modal.open({
            templateUrl : 'myModelContent.html',
            controller : 'ModalInstanceCtrl',
            resolve : {
                items : function() {
                    return $scope.items;
                }
            }
        });
        modalInstance.opened.then(function() {
            console.log('modal is opened');
        });
        modalInstance.result.then(function(result) {
            console.log(result);
        }, function(reason) {
            console.log(reason);
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

angular.module('voucher').controller('ModalInstanceCtrl',function($scope,$modalInstance,items){ //依赖于modalInstance
    $scope.items = items;
    $scope.selected = {
        item : $scope.items[0]
    };
    $scope.ok = function(){
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel');
    }
});




/*
app.controller("filterCon",function($scope){*/
