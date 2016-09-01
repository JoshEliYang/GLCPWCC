/**
 * Created by johnson on 2016/8/30.
 */

app.service('ResourceServiceGlobal', function () {

    /**
     * get resource
     *
     * @param $http
     * @param param
     * @param paginateCallback
     * @param callback
     */
    this.getResource = function ($http, param, paginateCallback, callback) {
        var type = param.type;
        var offset = param.offset;
        var count = param.count;
        $('#loadingDialog').modal('show');

        $http({
            method: "GET",
            url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id,
            // url: getResourceListUrl + type + "/" + offset + "/" + count + '?token=' + getCookie("token") + '&wechatAccount=1',
            'Content-Type': 'application/json'
            //data: params
        }).success(function (data) {
            if (data.code != 0) {
                showError(data.msg);
                return;
            }

            var resourceDat = data.data;
            for (var i = 0; i < resourceDat.item.length; i++) {
                if (type == 'image') {
                    var str = resourceDat.item[i].url;
                    str = str.replace('http://mmbiz.qpic.cn/mmbiz', 'https://mmbiz.qlogo.cn/mmbiz');
                    resourceDat.item[i].url = getImageUrl + '?wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id + '&url=' + encodeURI(str);
                }
                if (type == 'news') {
                    for (var j = 0; j < resourceDat.item[i].content.news_item.length; j++) {
                        var strx = resourceDat.item[i].content.news_item[j].thumb_url;
                        strx = strx.replace('http://mmbiz.qpic.cn/mmbiz', 'https://mmbiz.qlogo.cn/mmbiz');
                        resourceDat.item[i].content.news_item[j].thumb_url = getImageUrl + '?wechatAccount=' + JSON.parse(sessionStorage.getItem('basic')).id + '&url=' + encodeURI(strx);
                    }
                }
                resourceDat.item[i].date = TimestampToDate(resourceDat.item[i].update_time);
            }
            if (type == 'news') {
                doPaginating(offset, maxPageNews, resourceDat, paginateCallback);
            } else {
                doPaginating(offset, maxPage, resourceDat, paginateCallback);
            }

            callback(resourceDat);
            $('#loadingDialog').modal('hide');
        }).error(function () {
            showError('请求失败<br>请检查您的网络！');
        });
    };

    /**
     * do paginate
     *
     * @param offset
     * @param maxPageDat
     * @param resourceDat
     * @param callback
     */
    var doPaginating = function (offset, maxPageDat, resourceDat, callback) {
        // var maxPageDat = 200;
        var pageNow = Math.floor(offset / maxPageDat);
        pageNow += 1;

        var pageTotal = Math.ceil(resourceDat.total_count / maxPageDat);
        if (pageTotal == 0) pageTotal = 1;

        var pageList = new Array();
        if (pageTotal <= 10) {
            for (var i = 0; i < pageTotal; i++) {
                var item = {
                    "page": i + 1,
                    "offset": i * maxPageDat,
                    "css": ""
                };
                if (pageNow == (i + 1)) item.css = 'active';
                pageList.push(item);
            }
        } else {
            var itemDat;
            var j = 0;
            if (pageNow < 5) {  // when this page number is below 5
                for (j = 0; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < 10; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else if (pageNow > pageTotal - 5) {  // when this page is in last 5 pages
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageTotal; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            } else {    // when this page is in the middle
                for (j = pageNow - 5; j < pageNow - 1; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
                itemDat = {
                    "page": pageNow,
                    "offset": (pageNow - 1) * maxPageDat,
                    "css": "active"
                };
                pageList.push(itemDat);
                for (j = pageNow; j < pageNow + 5; j++) {
                    itemDat = {
                        "page": j + 1,
                        "offset": j * maxPageDat,
                        "css": ""
                    };
                    pageList.push(itemDat);
                }
            }
        }

        callback(pageTotal, pageList);
    };

    var showError = function (msg) {
        $.alert({
            theme: "material",
            title: "警告",
            content: '<b>' + msg + '</b>',
            confirmButtonClass: 'btn-info',
            autoClose: 'confirm|10000'
        });
    };

});
