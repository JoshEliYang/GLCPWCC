/**
 * Created by johnson on 2016/11/5.
 */

var getBasic = function () {
    return JSON.parse(sessionStorage.getItem("basic"));
};

var param = {
    startDate: "2016-11-01",
    endDate: "2016-11-07",
    tagList: [-255, -1, 147],
    dateType: 3
};

$.ajax({
    type: "GET",
    url: getSubscribeInfoByDay + '?token=' + getCookie("token") + '&wechatAccount=' + getBasic().id,
    contentType: "application/json; charset=utf-8",
    // data: JSON.stringify(param),
    dataType: "json",
    success: function (data) {
        if (data.code != 0) {
            showError(data.msg);
            return;
        }

        // alert(JSON.stringify(data.data));
        // alert(JSON.stringify(data.data.result["-255"]));

        var settingList = data.data.settingList;

        var xArray;
        var max = 0;

        for (var itemArray in data.data.result) {
            var datArray = data.data.result[itemArray];

            xArray = new Array();
            for (var item in datArray) {
                xArray.push(datArray[item].date);
                if (datArray[item].subscribe > max) max = datArray[item].subscribe;
                if (datArray[item].unsubscribe > max) max = datArray[item].unsubscribe;
            }
        }

        diagramInit("diagram1", function (canvas) {
            signAsix(canvas, xArray, getMax(max));
            for (var i = 0; i < settingList.length; i++) {
                var datArray = data.data.result[settingList[i].tagId];
                drawData(canvas, datArray, getMax(max), settingList[i].color);
            }
        });
    },
    error: function () {
        showError('请求失败<br>请检查您的网络！');
    }
});

var showError = function (msg) {
    $.alert({
        theme: "material",
        title: "警告",
        content: '<b>' + msg + '</b>',
        confirmButtonClass: 'btn-info',
        autoClose: 'confirm|10000'
    });
};

/**
 * calculate max scale for axis Y
 *
 * @param dat
 * @returns {*}
 */
function getMax(dat) {
    var count = 0;
    var res = dat;
    var resDat;
    while (true) {
        res = Math.floor(res / 10);
        if (res == 0) break;
        resDat = res;
        count++;
    }
    res = resDat + 1;
    for (var i = 0; i < count; i++) {
        res = res * 10;
    }
    return res;
}