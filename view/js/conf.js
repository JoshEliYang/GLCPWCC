//////////////////////////////// http //////////////////////////////////////
var loginUrl = "http://120.26.54.131:8080/GLCPWCC/login/login";

var basicUrl = "http://120.26.54.131:8080/GLCPWCC/basic/inusing";
var basicUrlAll = "http://120.26.54.131:8080/GLCPWCC/basic/all";
var basicSetUsingUrl = "http://120.26.54.131:8080/GLCPWCC/basic/setUsing";
var basicSetDefaultUrl = "http://120.26.54.131:8080/GLCPWCC/basic/setDefault";
var basicInsertUrl = "http://120.26.54.131:8080/GLCPWCC/basic/insert";
var basicEditUrl = "http://120.26.54.131:8080/GLCPWCC/basic/edit";
var basicDeleteUrl = "http://120.26.54.131:8080/GLCPWCC/basic/delete/";
var selectTokenServerUrl = "http://120.26.54.131:8080/GLCPWCC/basic/setTokenServer";

var userInfoUrl = "http://120.26.54.131:8080/GLCPWCC/admin/info";
var userUrlAll = "http://120.26.54.131:8080/GLCPWCC/admin/all";
var userLevelsUrlAll = "http://120.26.54.131:8080/GLCPWCC/admin/allLevels";
var userInsertUrl = "http://120.26.54.131:8080/GLCPWCC/admin/insert";
var userEditUrl = "http://120.26.54.131:8080/GLCPWCC/admin/edit";
var userDeleteUrl = "http://120.26.54.131:8080/GLCPWCC/admin/delete/";
var resetPasswdUrl = "http://120.26.54.131:8080/GLCPWCC/admin/resetPasswd";
var levelRightUrl = "http://120.26.54.131:8080/GLCPWCC/admin/levelRight";
var addAdminLevelUrl = "http://120.26.54.131:8080/GLCPWCC/admin/addLevel";
var adminLevelEditUrl = "http://120.26.54.131:8080/GLCPWCC/admin/userLevel";

var buttonGroupUrl = "http://120.26.54.131:8080/GLCPWCC/button/group";
var buttonsUrl = "http://120.26.54.131:8080/GLCPWCC/button/button/";

var msgTypeUrl = "http://120.26.54.131:8080/GLCPWCC/msgtype/all";
var keyWordsUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/all";
var keyWordsInsertUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/insert";
var keyWordsSetInUsing = "http://120.26.54.131:8080/GLCPWCC/keywords/setInUsing";
var keyWordsEdit = "http://120.26.54.131:8080/GLCPWCC/keywords/keywordEdit";
var deleteKeyWordsUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/";
var subscribeReplyUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/subscribe";
var insertSubscribeUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/subscribe";
var setSubscribeInUsingUrl = "http://120.26.54.131:8080/GLCPWCC/keywords/subscribe/setInUsing";

var tagGetUrl = "http://120.26.54.131:8080/GLCPWCC/tag/get";
var tagGetUserUrl = "http://120.26.54.131:8080/GLCPWCC/tag/getUser";
var tagCreateUrl = "http://120.26.54.131:8080/GLCPWCC/tag/create";
var tagDeleteUrl = "http://120.26.54.131:8080/GLCPWCC/tag/delete";
var tagUpdateUrl = "http://120.26.54.131:8080/GLCPWCC/tag/update";
var tagCreateWithQr = "http://120.26.54.131:8080/GLCPWCC/tag/tagAndQrcode";

var qrcodeGetUrl = "http://120.26.54.131:8080/GLCPWCC/Qrcode/select";
var qrcodeCreateUrl = "http://120.26.54.131:8080/GLCPWCC/Qrcode/create";
var getResourceListUrl = "http://120.26.54.131:8080/GLCPWCC/resource/";
var getImageUrl = "http://120.26.54.131:8080/GLCPWCC/test/wechatimage";

//operationLog
var queryOperationLog = "http://120.26.54.131:8080/GLCPWCC/log/operate";
var exloreLog = "http://120.26.54.131:8080/GLCPWCC/log/operate/export";
var clearLog = "http://120.26.54.131:8080/GLCPWCC/log/operate";
//errorLog
var queryErrorLog = "http://120.26.54.131:8080/GLCPWCC/log/error";
var clearErrorLog = "http://120.26.54.131:8080/GLCPWCC/log/error";
var exloreErrorLog = "http://120.26.54.131:8080/GLCPWCC/log/error/export";
//debugLog
var queryDebugLog = "http://120.26.54.131:8080/GLCPWCC/log/debug";
var clearDebugLog = "http://120.26.54.131:8080/GLCPWCC/log/debug";
var exploreDebugLog = "http://120.26.54.131:8080/GLCPWCC/log/debug/export";
var tureDebugLog = "http://120.26.54.131:8080/GLCPWCC/log/setDebug/true";
var falseDebugLog = "http://120.26.54.131:8080/GLCPWCC/log/setDebug/false";
//ifDebug
var getDebug = "http://120.26.54.131:8080/GLCPWCC/log/DebugMode";


var MessageExcelUrl = "http://120.26.54.131:8080/GLCPWCC/message/upload/SendTemplateMessage";
var TemplateMessageButtonsUrl = "http://120.26.54.131:8080/GLCPWCC/message/templates";
var TicketExpiredUrl = "http://120.26.54.131:8080/GLCPWCC/message/upload/TicketExpiredMessage";

// var socketUrl = "ws://localhost:8080/GLCPWCC/progress";
var socketUrl = "ws://120.26.54.131:8080/GLCPWCC/progress";

var getMenuUrl = "http://120.26.54.131:8080/GLCPWCC/wechatMenu";

// subscribe info
var getSubscribeInfoByDay = "http://120.26.54.131:8080/GLCPWCC/subscribe_report";


var addItem = "http://120.26.54.131:8080/GLCPWCC/tag/addTag";

var deleteItem = "http://120.26.54.131:8080/GLCPWCC/tag/deleteTag";

var usersFilter = "http://localhost:8080/GLCPWCC/voucher/user";
var restVoucher = "http://localhost:8080/GLCPWCC/voucher";
var bindChosen = "http://localhost:8080/GLCPWCC/voucherBinding/binding";
var bindVoucher = "http://localhost:8080/GLCPWCC/voucher/bindingall";

var getUserUrl = "http://localhost:8080/GLCPWCC/admin/admins";
var changeLevelUrl = "http://localhost:8080/GLCPWCC/admin/changeAdmins";

var voucherConfig = "http://120.26.54.131:8080/GLCPWCC/voucher/voucherConfig";
