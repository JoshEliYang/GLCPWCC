/**
 * Created by Administrator on 2016/12/6.
 */


    $("#begin_time").asDatepicker();
    $("#end_time").asDatepicker();


angular.module("voucher", ['ui.bootstrap']).controller('voucherCtrl', function ($scope, $modal, $log) {

    $scope.begin_time = null;
    $scope.end_time = null;

    $scope.dateactivitydisplay = function () {
        $scope.today = function () { //创建一个方法
            $scope.bt = new Date(); //定义一个属性来接收当天日期
        };
        $scope.today(); // 定义一个属性来接收当天日期
        $scope.clear = function () {  //当运行clear的时候将dt置为空
            $scope.bt = null;
        };
        $scope.activity_begin = function ($event) { //创建open方法 。 下面默认行为并将opened 设为true
            $scope.end_time = null;
            $event.preventDefault();
            //$event.stopPropagation();
            $scope.activibegin_open = true;
            $scope.maxDate = new Date();
            //var timestamp = Date.parse($scope.begin_time);
            //timestamp = timestamp / 1000;
            //var aa = new Date(new Date().toLocaleDateString()).getTime()
            //aa = aa/1000 +  60 * 60 * 24;
            //if( aa < timestamp){
            //    $scope.begin_time =  new Date(new Date()-5*24*60*60*1000);;
            //}
        };
        $scope.activity_end = function ($event) {
            if ($scope.begin_time != null && $scope.begin_time != undefined && $scope.begin_time != "") {
                $scope.minDate = new Date(($scope.begin_time / 1000 + 86400 * 30) * 1000);
                if ($scope.minDate >= new Date()) {
                    $scope.minDate = new Date();
                }
                $event.preventDefault();
                //$event.stopPropagation();
                $scope.activiend_open = true;
            }
            else {
                alert("请填写开始时间！");
            }
        };
        //该属性放在页面中，控制时间
        //date-disabled = "disabled(date,mode)"
        $scope.disabled = function (date, mode) {
            if (date.getDay() === 1)
                return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6))
        };
        $scope.toggleMin = function () {
            $scope.minDate = $scope.minDate ? null : new Date(); //3元表达式，没啥好说的
        };
        $scope.toggleMin();
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };
        $scope.formats = ['yyyy-MM-dd', 'yyy/MM/dd', 'yyyy.MM.dd', 'shortDate']; ///日期显示格式
        $scope.format = $scope.formats[0];  // 将formats的第0项设为默认显示格式
    };
    $scope.dateactivitydisplay();

    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };


    $scope.openFilter = function () {
        $('#filterDialog').modal('show');
//        $scope.items = ['html5', 'jq', 'FE-演示平台'];
//        var modalInstance = $modal.open({
//            templateUrl: 'usersFilter.html',
//            controller: 'usersFilterCtrl',
//            resolve: {
//                items: function () {
//                    return $scope.items;
//                }
//            }
//        });
//        modalInstance.opened.then(function () {
//            console.log('modal is opened');
//        });
//        modalInstance.result.then(function (result) {
//            console.log(result);
//        }, function (reason) {
//            console.log(reason);
//            $log.info('Modal dismissed at: ' + new Date());
//        });

    };


    $scope.filterConfirm=function(){
        var begin_time=$('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
        var end_time=$('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');
        alert(begin_time+"-"+end_time);
    };

    $scope.openVoucher = function () {
        $scope.items = ['html5', 'jq', 'FE-演示平台'];
        var modalInstance = $modal.open({
            templateUrl: 'createVoucher.html',
            controller: 'createVoucherCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });
        modalInstance.opened.then(function () {
            console.log('modal is opened');
        });
        modalInstance.result.then(function (result) {
            console.log(result);
        }, function (reason) {
            console.log(reason);
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    $scope.blindAll = function () {
        $scope.items = ['html5', 'jq', 'FE-演示平台'];
        var modalInstance = $modal.open({
            templateUrl: 'blindAll.html',
            controller: 'blindAllCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });
        modalInstance.opened.then(function () {
            console.log('modal is opened');
        });
        modalInstance.result.then(function (result) {
            console.log(result);
        }, function (reason) {
            console.log(reason);
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    $scope.blindChosen = function () {
        $scope.items = ['html5', 'jq', 'FE-演示平台'];
        var modalInstance = $modal.open({
            templateUrl: 'blindChosen.html',
            controller: 'blindChosenCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });
        modalInstance.opened.then(function () {
            console.log('modal is opened');
        });
        modalInstance.result.then(function (result) {
            console.log(result);
        }, function (reason) {
            console.log(reason);
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


});

angular.module('voucher').controller('usersFilterCtrl', function ($scope, $modalInstance, items) { //依赖于modalInstance
    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    }
});

angular.module('voucher').controller('createVoucherCtrl', function ($scope, $modalInstance, items) { //依赖于modalInstance
    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    }
});


angular.module('voucher').controller('blindAllCtrl', function ($scope, $modalInstance, items) { //依赖于modalInstance
    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    }
});


angular.module('voucher').controller('blindChosenCtrl', function ($scope, $modalInstance, items) { //依赖于modalInstance
    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    }
});


/*
 app.controller("filterCon",function($scope){*/
