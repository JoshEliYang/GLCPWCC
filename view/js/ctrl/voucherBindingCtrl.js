/**
 * Created by Administrator on 2016/12/6.
 */


$("#begin_time").asDatepicker();
$("#end_time").asDatepicker();


angular.module("voucher", ['ui.bootstrap', 'voucherBindingService', 'tm.pagination']).controller('voucherCtrl', function ($scope, voucherBinding) {
    $scope.begin_time = null;
    $scope.end_time = null;
    $scope.wechatAccount = JSON.parse(sessionStorage.getItem('basic')).id;
    $scope.token = JSON.parse(sessionStorage.getItem('token'));
    $scope.orderBy = 'totalAmount';
    $scope.sort = 'desc';
    $scope.offset = 0;
    $scope.count = 100;
    $scope.userNum = 0;
    $scope.voucherNum = 0;
    $scope.vouchers;

    $scope.openFilter = function () {
        $('#filterDialog').modal('show');
        $scope.choseSex = false;
        $scope.choseTel = false;
        $scope.choseId = false;
        $scope.choseAge = false;
        $scope.choseAmount = false;
        $scope.choseTime = false;
    };
    /*
     $scope.display = function(){
     if($scope.choseSex == false){
     $scope.filterSex = null;
     }
     if($scope.choseTel == false){
     $scope.filterTel = null;
     }
     if($scope.choseId == false){
     $scope.filterIdCard = null;
     }
     if($scope.choseAge == false){
     $scope.filterAgeMin = null;
     $scope.filterAgeMan = null;
     }
     if($scope.choseAmount == false){
     $scope.filterCostMin = null;
     $scope.filterCostMax = null;
     }
     if($scope.choseTime == false){
     $scope.begin_time = null;
     $scope.end_time = null;
     }
     };

     $scope.display();

     */
    $scope.choseOrder = function (order) {
        $scope.orderBy = order;
    };

    $scope.choseSort = function (sort) {
        $scope.sort = sort;
    };


    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 100,
        pagesLength: 15,
        perPageOptions: [50, 100, 150, 200],
        onChange: function () {
            $scope.skipTemp = ($scope.paginationConf.currentPage - 1) * 100;
            $scope.filterConfirm2();
        }
    };


    $scope.filterConfirm = function () {
        $("#loadingDialog").modal('show');
        var begin_time = $('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
        var end_time = $('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');

        $scope.param = {};
        $scope.param.filter = {};
        $scope.param.order = {};
        $scope.param.count = $scope.paginationConf.itemsPerPage;
        $scope.param.offset = 0;
        if ($scope.choseSex == true) {
            $scope.param.filter.sex = $scope.filterSex;
        }
        if ($scope.choseTel == true) {
            $scope.param.filter.phone = $scope.filterTel;
        }
        if ($scope.choseId == true) {
            $scope.param.filter.idCard = $scope.filterIdCard;
        }
        if ($scope.choseAge == true) {
            $scope.param.filter.ageST = $scope.filterAgeMin;
            $scope.param.filter.ageED = $scope.filterAgeMax;
        }
        if ($scope.choseAmount == true) {
            $scope.param.filter.amountST = $scope.filterCostMin;
            $scope.param.filter.amountED = $scope.filterCostMax;
        }
        if ($scope.choseTime == true) {
            $scope.param.filter.orderST = begin_time[0];
            $scope.param.filter.orderED = end_time[0];
        }
        $scope.param.order.orderBy = $scope.orderBy;
        $scope.param.order.sort = $scope.sort;
        voucherBinding.getUserFilter($scope.token, $scope.wechatAccount, $scope.param).success(function (data) {
            $scope.items = data.data;
            $scope.paginationConf.totalItems = data.count;
            $scope.allUserNum = data.count;
        }).error(function (data) {
            console.log(data.msg);
        })
    };

    $scope.filterConfirm();

    $scope.filterConfirm2 = function () {
        $("#loadingDialog").modal('show');
        var begin_time = $('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
        var end_time = $('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');

        var param = {};
        param.filter = {};
        param.order = {};
        param.count = $scope.paginationConf.itemsPerPage;
        param.offset = $scope.skipTemp;
        if ($scope.choseSex == true) {
            param.filter.sex = $scope.filterSex;
        }
        if ($scope.choseTel == true) {
            param.filter.phone = $scope.filterTel;
        }
        if ($scope.choseId == true) {
            param.filter.idCard = $scope.filterIdCard;
        }
        if ($scope.choseAge == true) {
            param.filter.ageST = $scope.filterAgeMin;
            param.filter.ageED = $scope.filterAgeMan;
        }
        if ($scope.choseAmount == true) {
            param.filter.amountST = $scope.filterCostMin;
            param.filter.amountED = $scope.filterCostMax;
        }
        if ($scope.choseTime == true) {
            param.filter.orderST = begin_time[0];
            param.filter.orderED = end_time[0];
        }
        param.order.orderBy = $scope.orderBy;
        param.order.sort = $scope.sort;
        voucherBinding.getUserFilter($scope.token, $scope.wechatAccount, param).success(function (data) {
            $scope.items = data.data;
            $("#loadingDialog").modal('hide');
            $('#filterDialog').modal('hide');
        }).error(function (data) {
            console.log(data.msg);
        })
    };

    $scope.checked = function (index) {
        if ($scope.items[index].checked != undefined) {
            $scope.items[index].checked = !$scope.items[index].checked;
        } else {
            $scope.items[index].checked = true;
        }
        var num = selectCheck();
        if (num == $scope.items.length) {
            $scope.checkAllParam = true;
        } else {
            $scope.checkAllParam = false;
        }
    };

    var selectCheck = function () {
        var num = 0;
        for (var i = 0; i < $scope.items.length; i++) {
            if ($scope.items.checked)
                num++;
        }
        return num;
    };


    $scope.checkAllParam = false;
    $scope.doCheckAll = function () {
        $scope.checkAllParam = !$scope.checkAllParam;
        for (var i = 0; i < $scope.items.length; i++) {
            $scope.items[i].checked = $scope.checkAllParam;
        }
    };

    $scope.restVoucher = function () {
        $("#loadingDialog").modal('show');
        voucherBinding.getRestVoucher($scope.token, $scope.wechatAccount).success(function (data) {
            $scope.vouchers = data.data;
            $("#loadingDialog").modal('hide');
        }).error(function (data) {
            console.log(data.msg);
        })
    };
    $scope.openBindChosen = function () {
        $scope.chosenUserNum = 0;
        $scope.chosenVoucherNum = 0;
        for (var i in $scope.items) {
            if ($scope.items[i].checked == true)
                $scope.chosenUserNum++;
        }
        $scope.restVoucher();
        $("#bindChosenDialog").modal('show');
    };
    $scope.voucherChecked = function (index) {
        if ($scope.vouchers[index].checked != undefined) {
            if ($scope.vouchers[index].checked == true) {
                $scope.chosenVoucherNum -= parseInt($scope.vouchers[index].number);
            }
            else {
                $scope.chosenVoucherNum += parseInt($scope.vouchers[index].number);
            }
            $scope.vouchers[index].checked = !$scope.vouchers[index].checked;
        } else {
            $scope.chosenVoucherNum += parseInt($scope.vouchers[index].number);
            $scope.vouchers[index].checked = true;
        }


        $scope.showornoshow = $scope.chosenVoucherNum >= $scope.allUserNum;
        $scope.showornoshow2 = $scope.chosenVoucherNum >= $scope.chosenUserNum;


    };
    $scope.openBindAll = function () {
        $scope.chosenVoucherNum = 0;
        $scope.restVoucher();
        $("#bindAllDialog").modal("show");
    };


    $scope.bindConfirm = function () {
        $("#loadingDialog").modal('show');
        $scope.param.count = 1000000;
        $scope.param.promotionIds = [];
        $scope.param.users = [];
        var promCount = 0;
        for (var i = 0; i < $scope.vouchers.length; i++) {
            if ($scope.vouchers[i].checked == true) {
                $scope.param.promotionIds[promCount] = $scope.vouchers[i].promotionId;
                promCount++;
            }
        }
        var dataItems = $scope.param;
        // debugger;
        voucherBinding.blingVoucher($scope.token, $scope.wechatAccount, dataItems).success(function (data) {
            $("#loadingDialog").modal('hide');
            $("#bindAllDialog").modal("hide");
        }).error(function (data) {
            console.log(data.msg);
        });
        window.parent.job('open');
    };

    $scope.bindselectConfirm = function () {
        $("#loadingDialog").modal('show');
        $scope.param.promotionIds = [];
        $scope.param.users = [];
        $scope.param.count = 1000000;
        $scope.param.customerCount = $scope.chosenUserNum;
        var promCount = 0;
        for (var i = 0; i < $scope.vouchers.length; i++) {
            if ($scope.vouchers[i].checked == true) {
                $scope.param.promotionIds[promCount] = $scope.vouchers[i].promotionId;
                promCount++;
            }
        }

        var userCount = 0;
        for (var i in $scope.items) {
            if ($scope.items[i].checked == true) {
                $scope.param.users[userCount] = $scope.items[i].customerId;
                userCount++;
            }
        }
        var dataItems = $scope.param;
        // debugger;
        voucherBinding.blingVoucher($scope.token, $scope.wechatAccount, dataItems).success(function (data) {
            $("#bindChosenDialog").modal("hide");
            $("#loadingDialog").modal('hide');
        }).error(function (data) {
            console.log(data.msg);
        });
        window.parent.job('open');

    };


    $scope.openConfig = function () {
        $('#configDialog').modal("show");
        voucherBinding.getVoucherConfig($scope.token, $scope.wechatAccount).success(function (data) {
            if (data.code != 0) {
                alert(data.msg);
                return;
            }
            $scope.voucherConfigData = data.data;
            console.log(JSON.stringify(data.data))
        }).error(function () {
            console.log(data.msg);
        });
    };

    $scope.setConfig = function () {
        voucherBinding.setVoucherConfig($scope.token, $scope.wechatAccount, $scope.voucherConfigData).success(function (data) {
            if (data.code != 0) {
                alert(data.msg);
                return;
            }
            $('#configDialog').modal("hide");
        }).error(function () {
            console.log(data.msg);
        });
    };
});

