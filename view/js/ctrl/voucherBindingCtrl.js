/**
 * Created by Administrator on 2016/12/6.
 */


    $("#begin_time").asDatepicker();
    $("#end_time").asDatepicker();


angular.module("voucher", ['ui.bootstrap','voucherBindingService','tm.pagination']).controller('voucherCtrl', function ($scope,voucherBinding) {
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

    $scope.openFilter = function () {
        $('#filterDialog').modal('show');
        $scope.choseSex = false;
        $scope.choseTel = false;
        $scope.choseId = false;
        $scope.choseAge = false;
        $scope.choseAmount = false;
        $scope.choseTime = false;

    };

    $scope.openVoucher = function(){
        $("#voucherDialog").modal('show');
    };
    $scope.openBind = function(str){
        $("#bindDialog").modal('show');
        if(str){
            $scope.choseArea = str;
        }
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
    $scope.choseOrder = function(order){
        $scope.orderBy = order;
    };

    $scope.choseSort = function(sort){
        $scope.sort = sort;
    };



    $scope.paginationConf = {
        currentPage: 1,
        totalItems:0,
        itemsPerPage: 100,
        pagesLength: 15,
        perPageOptions: [ 50, 100, 150,200],
        onChange: function(){
            $scope.skipTemp = ($scope.paginationConf.currentPage -1)*100;
            $scope.filterConfirm2();
        }
    };


    $scope.filterConfirm=function(){
        $("#loadingDialog").modal('show');
        var begin_time = $('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
        var end_time = $('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');

        var param = {};
        param.filter = {};
        param.order = {};
        param.count = $scope.paginationConf.itemsPerPage;
        param.offset = 0;
        if($scope.choseSex == true){
            param.filter.sex = $scope.filterSex;
        }
        if($scope.choseTel == true){
            param.filter.phone = $scope.filterTel;
        }
        if($scope.choseId == true){
            param.filter.idCard = $scope.filterIdCard;
        }
        if($scope.choseAge == true){
            param.filter.ageST = $scope.filterAgeMin;
            param.filter.ageED = $scope.filterAgeMan;
        }
        if($scope.choseAmount == true){
            param.filter.amountST = $scope.filterCostMin;
            param.filter.amountED = $scope.filterCostMax;
        }
        if($scope.choseTime == true){
            param.filter.orderST = begin_time[0];
            param.filter.orderED = end_time[0];
        }
        param.order.orderBy = $scope.orderBy;
        param.order.sort = $scope.sort;
        voucherBinding.getUserFilter($scope.token,$scope.wechatAccount,param).success(function(data){
            $scope.items = data.data;
            $scope.paginationConf.totalItems = data.count;
            $("#loadingDialog").modal('hide');
            $('#filterDialog').modal('hide');
        }).error(function(data){
            console.log(data.msg);
        })
    };

    $scope.filterConfirm();

    $scope.filterConfirm2=function(){
        $("#loadingDialog").modal('show');
        var begin_time = $('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
        var end_time = $('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');

        var param = {};
        param.filter = {};
        param.order = {};
        param.count = $scope.paginationConf.itemsPerPage;
        param.offset = $scope.skipTemp;
        if($scope.choseSex == true){
            param.filter.sex = $scope.filterSex;
        }
        if($scope.choseTel == true){
            param.filter.phone = $scope.filterTel;
        }
        if($scope.choseId == true){
            param.filter.idCard = $scope.filterIdCard;
        }
        if($scope.choseAge == true){
            param.filter.ageST = $scope.filterAgeMin;
            param.filter.ageED = $scope.filterAgeMan;
        }
        if($scope.choseAmount == true){
            param.filter.amountST = $scope.filterCostMin;
            param.filter.amountED = $scope.filterCostMax;
        }
        if($scope.choseTime == true){
            param.filter.orderST = begin_time[0];
            param.filter.orderED = end_time[0];
        }
        param.order.orderBy = $scope.orderBy;
        param.order.sort = $scope.sort;
        voucherBinding.getUserFilter($scope.token,$scope.wechatAccount,param).success(function(data){
            $scope.items = data.data;
            $("#loadingDialog").modal('hide');
            $('#filterDialog').modal('hide');
        }).error(function(data){
            console.log(data.msg);
        })
    };

});

