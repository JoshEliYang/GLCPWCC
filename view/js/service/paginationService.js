/**
 * Created by johnson on 2016/8/28.
 */


app.service('PaginationServiceGlobal', function () {

    /**
     * do pagination just in the front-end level
     *
     * @param allDat
     * @param maxItem
     * @param callback
     */
    this.doPagination = function (allDat, maxItem, callback) {
        var pageGroup = new Array();
        var pages = new Array();
        var count = 0;
        for (var i = 0; i < allDat.length; i++) {
            if (count < maxItem) {
                pages.push(allDat[i]);
                count++;
            }
            if (count >= maxItem) {
                var item = {
                    "css": "",
                    "data": pages
                };
                count = 0;
                pageGroup.push(item);
                pages = new Array();
            }
        }
        if (count != 0) {
            var item = {
                "css": "",
                "data": pages
            };
            pageGroup.push(item);
        }
        callback(pageGroup, allDat.length);
    };

    /**
     * get page
     *
     * @param pageDat
     * @param page
     * @param callback
     */
    this.getPage = function (pageDat, page, callback) {
        if (page >= pageDat.pageGroup.length) {
            page = 0;
        }
        pageDat.pageNow = page;
        for (var i = 0; i < pageDat.pageGroup.length; i++) {
            for (var j = 0; j < pageDat.pageGroup[i].data.length; j++) {
                pageDat.pageGroup[i].data[j].checked = false;
            }
            pageDat.pageGroup[i].css = "";
        }
        pageDat.pageGroup[page].css = "active";
        pageDat.showList = pageDat.pageGroup[page].data;
        callback(pageDat.pageNow, pageDat.pageGroup, pageDat.showList);
    };
});