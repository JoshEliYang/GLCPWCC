/**
 * Created by Administrator on 2017/1/4.
 */


$("#begin_time").asDatepicker();
$("#end_time").asDatepicker();
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate() //day
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
        function (a) {
            return parseInt(a, 10) - 1;
        }).match(/\d+/g) + ')');
    return date;
}

angular.module("subscribe", ['ui.bootstrap', 'userSubscribeTableService', 'tm.pagination']).controller('subscribeCtrl', function ($scope, subscribeTable) {


    $scope.wechatAccount = "1";


    $scope.unit = 24 * 60 * 60 * 1000;
    $scope.today = new Date();
    $scope.endDate = $scope.today.format("yyyy-MM-dd");
    $scope.startDate = new Date($scope.today - $scope.unit * 6).format("yyyy-MM-dd");

    $scope.choseWeek = true;
    $scope.choseMonth = false;
    $scope.choseCustom = false;


    $scope.week = function () {
        $scope.choseWeek = true;
        $scope.choseMonth = false;
        $scope.choseCustom = false;
    };
    $scope.month = function () {
        $scope.choseWeek = false;
        $scope.choseMonth = true;
        $scope.choseCustom = false;
    };
    $scope.custom = function () {
        $scope.choseWeek = false;
        $scope.choseMonth = false;
        $scope.choseCustom = true;
    };

    $scope.getDates = function () {
        if ($scope.choseWeek == true) {
            $scope.endDate = $scope.today.format("yyyy-MM-dd");
            $scope.startDate = new Date($scope.today - $scope.unit * 6).format("yyyy-MM-dd");
        } else if ($scope.choseMonth == true) {
            $scope.endDate = $scope.today.format("yyyy-MM-dd");
            $scope.startDate = new Date($scope.today - $scope.unit * 30).format("yyyy-MM-dd");
        } else if ($scope.choseCustom == true) {
            var begin_time = $('#begin_time').asDatepicker('getDate', 'yyyy-mm-dd');
            var end_time = $('#end_time').asDatepicker('getDate', 'yyyy-mm-dd');
            $scope.startDate = begin_time[0];
            $scope.endDate = end_time[0];
        }

        $scope.dates = [];
        $scope.end_date = new Date($scope.endDate);
        $scope.start_date = new Date($scope.startDate);
        $scope.diff = ($scope.end_date - $scope.start_date) / $scope.unit + 1;

        for (var i = 0; i < $scope.diff; i++) {
            var date = new Date($scope.end_date - $scope.unit * i).format("yyyy-MM-dd");
            $scope.dates.unshift(date);
        }
    };
    $scope.getDates();


    $scope.search = function () {
        $scope.getDates();
        var dateArea = {};
        dateArea.startDate = $scope.startDate;
        dateArea.endDate = $scope.endDate;
        subscribeTable.getSubscribeCount(dateArea, $scope.wechatAccount).success(function (data) {
            $scope.tagInfo = data.data;
        })
    };

    $scope.export = function () {
        $scope.getDates();
        var url = "http://home.g-super.net:8080/GLCPWCC/subscribers/" + $scope.startDate + "/" + $scope.endDate + "?wechatAccount=1&timestamp=" + new Date().getTime();
        window.location.href = url;
    };

    $scope.search();
});
