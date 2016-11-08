angular.module('operation', ['ui.bootstrap','operationLogService','tm.pagination']).controller('operationCtrl', function ($scope,operationLog) {


    $scope.begin_time =  new Date(new Date()-5*24*60*60*1000);

    $scope.end_time = new Date();
    $scope.param = {};
    $scope.skipTemp = 0;
    $scope.items_per_page = 50;
    $scope.showFlage = 0;
    $scope.tableFlag= 0;

    $scope.basicId = "1";
    //$scope.adminId = JSON.parse(sessionStorage.getItem('admin'));
    $scope.basicAll = JSON.parse(sessionStorage.getItem('basic'));
    $scope.token = JSON.parse(sessionStorage.getItem('token'));
    $scope.url = exloreLog + '?token=' + $scope.token;

    operationLog.getDebug($scope.token).success(function(date){
        $scope.ifDebug = date.data;
    }).error(function(){

    });


    $scope.searchLog = function(){
        $scope.param = {};
        $scope.param.startTime = $scope.begin_time;
        $scope.param.endTime = $scope.end_time;
        $scope.param.skip = $scope.skipTemp;
        $scope.param.basicId = 1;
        if($scope.adminId == undefined || $scope.adminId == null){}else{
            $scope.param.adminId = $scope.adminId.id;
        }
        if($scope.action == undefined || $scope.action == null || $scope.action == ""){}else{
            $scope.param.action = $scope.action;
        }
        if($scope.status == undefined || $scope.status == null || $scope.status == ""){}else{
            $scope.param.status = $scope.status;
        }

        operationLog.queryOperationlog($scope.token, $scope.param).success(function (data) {
            $scope.items = data.data.logs;
            $scope.totalCount = data.data.total;
            $scope.paginationConf.totalItems = data.data.total;
            $scope.lenth = Math.ceil($scope.totalCount/50);
            $scope.pageLength($scope.lenth);

            console.log(data);
        }).error(function (data) {
            console.log(data);
        });
    }
    $scope.searchLog();


    $scope.maxDate = new Date();
    operationLog.getUserAll($scope.token).success(function (data) {
        $scope.adminAll = data.data;
        console.log(data);
    }).error(function (data) {
        console.log(data);
    });

    $scope.pageLength = function(lenth) {
        $scope.page = {};
        for (var i = 1; i <= lenth; i++) {
            $scope.page[i] = i;
        }
        console.log($scope.page);
        return $scope.page;
    }

    $scope.dateactivitydisplay = function () {
        $scope.today = function () { //创建一个方法
            $scope.bt = new Date(); //定义一个属性来接收当天日期
        };
        $scope.today(); // 定义一个属性来接收当天日期
        $scope.clear = function () {  //当运行clear的时候将dt置为空
            $scope.bt = null;
        }
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
        }
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
        }
        //该属性放在页面中，控制时间
        //date-disabled = "disabled(date,mode)"
        $scope.disabled = function (date, mode) {
            if (date.getDay() === 1)
                return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6))
        }
        $scope.toggleMin = function () {
            $scope.minDate = $scope.minDate ? null : new Date(); //3元表达式，没啥好说的
        }
        $scope.toggleMin();
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        }
        $scope.formats = ['yyyy-MM-dd hh:mm:ss', 'yyy/MM/dd', 'yyyy.MM.dd', 'shortDate']; ///日期显示格式
        $scope.format = $scope.formats[0];  // 将formats的第0项设为默认显示格式
    }
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
    }

    $scope.chose = function(){
        if($scope.formData.chickenEgg == 'standard' ){
            $scope.tableFlag= 0;
        }else{
            $scope.tableFlag= 1;
        }

    }

    $scope.choseadminId = function(){
        $scope.adminid = $scope.adminId.id;
    }

    $scope.choseadbasicId = function(){
        $scope.adminid = $scope.adminId.id;
    };

    $scope.clearLog = function(){
        operationLog.clearLog($scope.token).success(function (data) {
            $scope.searchLog();
        }).error(function (data) {
            console.log(data);
        });
    }

    $scope.aa = function(a){
        alert(a)
    }

    $scope.ifdebug = function(status){
        if(status == true){
            operationLog.trueDebug($scope.token).success(function(data){
                console.log(data);
            }).error(function(){
                console.log(data);
            })
        }else{
            operationLog.falseDebug($scope.token).success(function(data){
                console.log(data);
            }).error(function(data){

            })
        }
    }


   /* $scope.showq = function () {
        debugger;
        $scope.modelDat = {
            "title": "添加",
            "msgTypeName": "未选择",
            "value": "",
            "msgType": -1
        };
        $scope.showFlage = 1 ;
    };*/

    //$scope.pageCl($scope.totalCount);

    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 50,
        pagesLength: 15,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.skipTemp = ($scope.paginationConf.currentPage -1)*50;
            $scope.searchLog();
        }
    }


    $scope.openAddDialog = function (item) {
        $scope.deatildata = item;
        $('#tagAddModal').modal('show');
    };

});