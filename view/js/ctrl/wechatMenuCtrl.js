/**
 * Created by johnson on 2016/8/30.
 */

$(function () {
    $('[data-toggle="popover"]').popover()
});

var app = angular.module('wechatApp', ['ngSanitize']);

app.controller('wechatCtrl', function ($scope, $http, WechatMenuServiceGlobal) {

    $scope.parentButtonChecked = false;

    $scope.checkParentButton = function () {
        $scope.parentButtonChecked = !$scope.parentButtonChecked;
    };

    $.getJSON('js/service/menuSourceType.json', function (data) {
        $scope.menuSourceTypeList = data.menuSourceType;
        $scope.inputTypeDat = $scope.menuSourceTypeList[0];
    });

    $scope.inputTypeSelect = function (index) {
        $scope.inputTypeDat = $scope.menuSourceTypeList[index];
        $scope.inputValue = '';
    };

    $scope.setInputType = function (type) {
        for (var i = 0; i < $scope.menuSourceTypeList.length; i++) {
            if ($scope.menuSourceTypeList[i].type == type) {
                $scope.inputTypeDat = $scope.menuSourceTypeList[i];
                return $scope.menuSourceTypeList[i].inputType;
            }
        }
    };

    /**
     * draw menu on the page
     * considered 3 different situations
     *
     * 1. Have no menu: Just put a 'add new' button on the page
     * 2. All of 3 parent buttons have sub buttons: Add 'add new' buttons at the end of each sub buttons' groups
     * 3. Some of 3 parent buttons have sub buttons and some haven't: Draw 'add new' buttons as sub buttons for those parent buttons which have no sub buttons. And add 'add new' buttons at the end of each sub buttons' groups
     *
     * @param data
     */
    var drawMenuFnc = function (data) {
        if (!data.hasOwnProperty('selfmenu_info')) {
            $scope.originButton = true;
            return;
        }
        $scope.originButton = false;

        for (var i = 0; i < data.selfmenu_info.button.length; i++) {
            data.selfmenu_info.button[i].style = "btn-success";
            data.selfmenu_info.button[i].id = i;

            if (data.selfmenu_info.button[i].hasOwnProperty("sub_button")) {
                for (var j = 0; j < data.selfmenu_info.button[i].sub_button.list.length; j++) {
                    data.selfmenu_info.button[i].sub_button.list[j].style = "btn-info";
                }
                if (data.selfmenu_info.button[i].sub_button.list.length < 15) {
                    data.selfmenu_info.button[i].sub_button.list.push({
                        "type": "newItem",
                        "name": "+ 新按钮",
                        "style": "btn-default"
                    });
                }
            } else {
                data.selfmenu_info.button[i].newButton = true;
            }
        }
        if (data.selfmenu_info.button.length < 3) {
            data.selfmenu_info.button.push({
                "type": "new",
                "name": "+ 新按钮",
                "style": "btn-default",
                "id": data.selfmenu_info.button.length
            });
        }

        $scope.menuDat = data;
        var buttonsStr = JSON.stringify(data.selfmenu_info);
        $scope.codeDat = doJsonFormat(JSON.stringify(getPureDat(JSON.parse(buttonsStr))));
    };

    /**
     * get send-able data from given data.
     *
     *  (Don't ask me why this json contained '$$hashKey'. I have no idea about it, and just do delete.
     *  If you know why, please write after this comment.)
     *
     * @param data
     * @returns {*}
     */
    var getPureDat = function (data) {
        for (var i = data.button.length - 1; i >= 0; i--) {
            if (data.button[i].type == 'new') {
                data.button.splice(i, 1);
                continue;
            }

            if (data.button[i].hasOwnProperty("sub_button")) {
                for (var j = data.button[i].sub_button.list.length - 1; j >= 0; j--) {
                    delete data.button[i].sub_button.list[j].style;

                    if (data.button[i].sub_button.list[j].type == 'newItem') {
                        data.button[i].sub_button.list.splice(j, 1);
                        continue;
                    }

                    if (data.button[i].sub_button.list[j].hasOwnProperty('$$hashKey')) {
                        delete  data.button[i].sub_button.list[j].$$hashKey;
                    }
                }
                data.button[i].sub_button = data.button[i].sub_button.list;
            }

            delete data.button[i].style;
            delete data.button[i].id;
            if (data.button[i].hasOwnProperty('newButton')) {
                delete data.button[i].newButton;
            }
            if (data.button[i].hasOwnProperty('$$hashKey')) {
                delete data.button[i].$$hashKey;
            }
        }
        return data;
    };

    /**
     * get menu data and draw on the page
     */
    WechatMenuServiceGlobal.getMenu($http, drawMenuFnc);

    var MenuTypeGlobal = ['parent', 'sub', 'origin', 'subNew'];
    var addMenuGlobalParam = {
        "type": MenuTypeGlobal[0],     // parent,sub,origin
        "column": 0,
        "row": 0
    };

    $scope.addNewParentButton = function (index) {
        $scope.isParentButton = true;

        addMenuGlobalParam.type = MenuTypeGlobal[0];
        addMenuGlobalParam.column = index;
        resetLayoutFnc('2row');

        var dat = $scope.menuDat.selfmenu_info.button[index];
        $scope.inputName = dat.name;
        var datType = $scope.setInputType(dat.type);
        $scope.inputValue = dat[datType];
        $scope.isAddNew = false;

        if (dat.hasOwnProperty('sub_button')) {
            $scope.parentButtonChecked = true;
        } else {
            $scope.parentButtonChecked = false;
            var datType = $scope.setInputType(dat.type);
            $scope.inputValue = dat[datType];
            $scope.isAddNew = false;
        }

        if (dat.type == 'new') {
            $scope.isAddNew = true;
            $scope.inputName = '';
        }
        $scope.inputValue = 'http://';
    };

    $scope.addNewSubButton = function (column, row) {
        $scope.parentButtonChecked = false;
        $scope.isParentButton = false;

        addMenuGlobalParam.type = MenuTypeGlobal[1];
        addMenuGlobalParam.column = column;
        addMenuGlobalParam.row = row;
        resetLayoutFnc('2row');

        var dat = $scope.menuDat.selfmenu_info.button[column].sub_button.list[row];
        $scope.inputName = dat.name;
        var datType = $scope.setInputType(dat.type);
        $scope.inputValue = dat[datType];
        $scope.isAddNew = false;

        if (dat.type == 'newItem') {
            $scope.isAddNew = true;
            $scope.inputName = '';
            $scope.inputValue = 'http://';
        }
    };

    $scope.addNewButtonWhenEmpty = function (index) {
        $scope.parentButtonChecked = false;
        $scope.isParentButton = false;

        addMenuGlobalParam.type = MenuTypeGlobal[3];
        addMenuGlobalParam.column = index;
        resetLayoutFnc('2row');

        $scope.inputName = '';
        $scope.inputValue = 'http://';
    };

    $scope.addOriginButton = function () {
        $scope.isParentButton = true;

        addMenuGlobalParam.type = MenuTypeGlobal[2];
        resetLayoutFnc('2row');

        $scope.inputName = '';
        $scope.inputValue = 'http://';
    };

    /**
     * confirm new button and commit to WeChat server
     *
     * considered 4 different situation
     * and 2 different actions(add/edit or delete)
     *
     * @constructor
     */
    $scope.MenuInputConfirm = function (action) {
        var column = addMenuGlobalParam.column;
        var row = addMenuGlobalParam.row;

        var doSend = function () {
            var outDat = getPureDat($scope.menuDat.selfmenu_info);
            WechatMenuServiceGlobal.setMenu($http, outDat, function (data) {
                if (data.hasOwnProperty('errcode')) {
                    if (data.errorCode != 0 && data.errorCode != '0') {
                        showResultDialog(data.errmsg);
                    }
                }
                WechatMenuServiceGlobal.getMenu($http, drawMenuFnc);
            });
        };

        if (action == 'add') {
            if (addMenuGlobalParam.type == MenuTypeGlobal[1]) {
                var dat = $scope.menuDat.selfmenu_info.button[column].sub_button.list[row];
                dat = {
                    "type": $scope.inputTypeDat.type,
                    "name": $scope.inputName
                };
                dat[$scope.inputTypeDat.inputType] = $scope.inputValue;
                $scope.menuDat.selfmenu_info.button[column].sub_button.list[row] = dat;
                doSend();
            } else if (addMenuGlobalParam.type == MenuTypeGlobal[0]) {
                var parentButtonDat = $scope.menuDat.selfmenu_info.button[column];
                if ($scope.parentButtonChecked) {
                    console.log(parentButtonDat);
                    if (parentButtonDat.hasOwnProperty('sub_button')) {
                        parentButtonDat.name = $scope.inputName;
                        $scope.menuDat.selfmenu_info.button[column] = parentButtonDat;
                        doSend();
                    } else {
                        parentButtonDat = {
                            "id": column,
                            "name": $scope.inputName,
                            "sub_button": {
                                "list": [
                                    {
                                        "type": "newItem",
                                        "name": "+ 新按钮",
                                        "style": "btn-default"
                                    }
                                ]
                            }
                        };
                        $scope.menuDat.selfmenu_info.button[column] = parentButtonDat;
                    }
                } else {
                    parentButtonDat = {
                        "name": $scope.inputName,
                        "type": $scope.inputTypeDat.type
                    };
                    parentButtonDat[$scope.inputTypeDat.inputType] = $scope.inputValue;
                    $scope.menuDat.selfmenu_info.button[column] = parentButtonDat;
                    doSend();
                }
            } else if (addMenuGlobalParam.type == MenuTypeGlobal[3]) {
                var parentBtnDat = $scope.menuDat.selfmenu_info.button[column];
                var datx = {
                    "type": $scope.inputTypeDat.type,
                    "name": $scope.inputName
                };
                datx[$scope.inputTypeDat.inputType] = $scope.inputValue;
                parentBtnDat = {
                    "id": column,
                    "name": parentBtnDat.name,
                    "sub_button": {
                        "list": [datx]
                    }
                };
                $scope.menuDat.selfmenu_info.button[column] = parentBtnDat;
                doSend();
            } else if (addMenuGlobalParam.type == MenuTypeGlobal[2]) {
                if ($scope.parentButtonChecked) {
                    // need to do
                } else {
                    var parentDatx = {
                        "type": $scope.inputTypeDat.type,
                        "name": $scope.inputName
                    };
                    parentDatx[$scope.inputTypeDat.inputType] = $scope.inputValue;
                    $scope.menuDat = {
                        "button": [parentDatx]
                    };
                    WechatMenuServiceGlobal.setMenu($http, $scope.menuDat, function (data) {
                        if (data.hasOwnProperty('errcode')) {
                            if (data.errorCode != 0 && data.errorCode != '0') {
                                showResultDialog(data.errmsg);
                            }
                        }
                        WechatMenuServiceGlobal.getMenu($http, drawMenuFnc);
                    });
                }
            }
        } else if (action == 'delete') {
            if (addMenuGlobalParam.type == MenuTypeGlobal[1]) {
                $scope.menuDat.selfmenu_info.button[column].sub_button.list.splice(row, 1);
                doSend();
            } else if (addMenuGlobalParam.type == MenuTypeGlobal[0]) {
                $scope.menuDat.selfmenu_info.button.splice(column, 1);
                doSend();
            }
        }

        resetLayoutFnc('1row');
    };

    $scope.MenuInputCancel = function () {
        resetLayoutFnc('1row');
    };

    $scope.sendSourceCode = function () {
        $('#codeModel').modal('hide');
        var outDat = $('#codeDat').val();
        console.log(outDat);
        WechatMenuServiceGlobal.setMenu($http, JSON.parse(outDat), function (data) {
            if (data.hasOwnProperty('errcode')) {
                if (data.errorCode != 0 && data.errorCode != '0') {
                    showResultDialog(data.errmsg);
                }
            }
            WechatMenuServiceGlobal.getMenu($http, drawMenuFnc);
        });
    };

    var resetLayoutFnc = function (param) {
        if (param == '2row') {
            $scope.editPanelShow = true;
            $scope.MainPanelStyle = "col-xs-7 col-sm-8";
        } else if (param == '1row') {
            $scope.editPanelShow = false;
            $scope.MainPanelStyle = "col-xs-12";
        }
    };
    resetLayoutFnc('1row');


    $scope.openCodeModel = function () {
        window.location.href = '#contantHead';
        window.location.href = '#head';
        $('#codeModel').modal('show');
    };

    var showResultDialog = function (msg) {
        $.alert({
            theme: "material",
            title: "处理结果",
            content: '<b>' + msg + '</b>',
            confirmButtonClass: 'btn-info',
            autoClose: 'confirm|10000'
        });
    };

});


/**
 * format json string
 *
 * @param jsonData
 * @returns {*}
 */
function doJsonFormat(jsonData) {
    var data = jsonData;
    var getTab = function (num) {
        var out = '';
        for (var i = 0; i < num; i++) {
            out += '\t'
        }
        return out;
    };

    var tagNumber = 0;
    var jsonLength = data.length;
    var i = 0;

    var doInsert = function (addStr, position) {
        var st, ed;
        if (position == 'back') {
            st = data.substring(0, i + 1);
            ed = data.substring(i + 1, jsonLength);
            data = st + addStr + ed;
            i += addStr.length - 1;
            jsonLength += addStr.length;
        } else if (position == 'front') {
            st = data.substring(0, i);
            ed = data.substring(i, jsonLength);
            data = st + addStr + ed;
            i += addStr.length;
            jsonLength += addStr.length;
        }
    };

    for (; i < jsonLength; i++) {
        var addStr;
        if (data.charAt(i) == '{' || data.charAt(i) == '[') {
            tagNumber++;
            addStr = '\n' + getTab(tagNumber);
            doInsert(addStr, 'back');
        } else if (data.charAt(i) == ',') {
            addStr = '\n' + getTab(tagNumber);
            doInsert(addStr, 'back');
        } else if (data.charAt(i) == '}' || data.charAt(i) == ']') {
            tagNumber--;
            addStr = '\n' + getTab(tagNumber);
            doInsert(addStr, 'front');
        } else if (data.charAt(i) == ':') {
            if (data.charAt(i - 1) == "\"" && data.charAt(i + 1) == "\"") {
                addStr = ' ';
                doInsert(addStr, 'front');
                doInsert(addStr, 'back');
            }
        }
    }

    return data;
}
