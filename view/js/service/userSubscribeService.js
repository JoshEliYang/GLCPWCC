/**
 * Created by johnson on 2016/11/5.
 * modified by xiaozhu on 2016/11/28.
 */

var getBasic = function () {
    return JSON.parse(sessionStorage.getItem("basic"));
};



var settingList;

var totilItens;

$.ajax({
    type: "GET",
    url: tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (data) {

        totilItens = data.data.tags;
    }
})


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

        settingList = data.data.settingList;

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
                drawMutipleLines(canvas, datArray, settingList[i].color, settingList[i].uncolor, settingList[i].tagId, settingList[i].tagName, xArray.length, getMax(max));
            }
        });

        setDiagram(settingList, "diagram1Setting");
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

$("#show").click(function(){
    var tds = "";
    for(var i in totilItens){
        totilItens[i].checked  = false;
    }

    for(var i = 0;i<settingList.length;i++){
        for(var j = 0 ;j<totilItens.length;j++){
            if(settingList[i].tagId == totilItens[j].id){
                totilItens[j].checked = true;
            }
        }
    }

    //totilItens = data.data.tags;
    for(var i in totilItens){
        if(totilItens[i].checked == true){
            tds += '<tr >'+'<td >'+
                    /*'<input type="checkbox" totilItens[i].checked >' +'</td>'+'<td>'+*/
                '<input type="checkbox" checked="'+totilItens[i].checked+'" onchange="detail('+totilItens[i].id+')">' +'</td>'+'<td>'+
                totilItens[i].id +'</td>'+'<td>'+
                totilItens[i].name +'</td>'+ '</tr>';
        }else{
            tds += '<tr onclick="detail('+totilItens[i].id+')">'+'<td>'+
                    /*'<input type="checkbox" totilItens[i].checked >' +'</td>'+'<td>'+*/
                '<input type="checkbox" >' +'</td>'+'<td>'+
                totilItens[i].id +'</td>'+'<td>'+
                totilItens[i].name +'</td>'+ '</tr>';
        }
        //totilItens[i].checked = "checked";

    }

    $('#tbody').html(tds);
    $('#ConfigModal').modal('show')
})


var detail = function(id){

    for(var i in totilItens){
        if(totilItens[i].id ==id){
            if(totilItens[i].checked ==true){
                totilItens[i].checked = false;

                deleteItems(totilItens[i].id)
            }else if(totilItens[i].checked ==false){
                totilItens[i].checked ==true
                addItems(totilItens[i].id,totilItens[i].name)
            }
        }
    }
}

/**
 *
 * calculate max scale for axis Y
 *
 * @param dat
 * @returns {*}
 */

function getRGB(){
    var rand = Math.floor(Math.random( ) * 0xFFFFFF).toString(16);
    return "#"+rand;
}



function addItems(tagId,name){
var temp ={
    "tagId":tagId,
    "tagname":name,
    "color":getRGB(),
    "uncolor":getRGB(),
    "basicId":JSON.parse(sessionStorage.getItem('basic')).id
};
    //$('#loadingDialog').modal('show');
    $.ajax({
        type: "POST",
        url: addItem + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data:JSON.stringify(temp),
        success: function (data) {
        }
    })

}

function deleteItems(tagId){
    var temp ={
        "tagId":tagId,
    };
    //$('#loadingDialog').modal('show');
    $.ajax({
        type: "POST",
        url: deleteItem + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data:JSON.stringify(temp),
        success: function (data) {
        }
    })

}



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


function setDiagram(settingList, elementId) {
    var tBody = document.getElementById(elementId);
    tBody.innerHTML = "";
    for (var i = 0; i < settingList.length; i++) {
        tBody.innerHTML += "<tr onmousemove='drawBoldByTagId(" + settingList[i].tagId + ")' onmouseleave='resetDiagram()'>"
            + "<td>" + settingList[i].tagName + "</td>"
            + "<td width='50' align='center'><div class='boll' style='background: " + settingList[i].color + "'></div></td>"
            /*+ "<td width='50' align='center'><div class='boll' style='background: " + settingList[i].uncolor + "'></div></td>"*/
            + "</tr>";
    }



}

$("#closeModal").click(function(){
    $('#ConfigModal').modal('hide');


    $.ajax({
        type: "GET",
        url: tagGetUrl + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            totilItens = data.data.tags;
        }
    })


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
            settingList = data.data.settingList;

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
                    drawMutipleLines(canvas, datArray, settingList[i].color, settingList[i].uncolor, settingList[i].tagId, settingList[i].tagName, xArray.length, getMax(max));
                }
            });

            setDiagram(settingList, "diagram1Setting");
        },
        error: function () {
            showError('请求失败<br>请检查您的网络！');
        }
    });








})