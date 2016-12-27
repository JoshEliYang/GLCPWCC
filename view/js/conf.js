//////////////////////////////// http //////////////////////////////////////
var loginUrl = "http://localhost:8080/GLCPWCC/login/login";

var basicUrl = "http://localhost:8080/GLCPWCC/basic/inusing";
var basicUrlAll = "http://localhost:8080/GLCPWCC/basic/all";
var basicSetUsingUrl = "http://localhost:8080/GLCPWCC/basic/setUsing";
var basicSetDefaultUrl = "http://localhost:8080/GLCPWCC/basic/setDefault";
var basicInsertUrl = "http://localhost:8080/GLCPWCC/basic/insert";
var basicEditUrl = "http://localhost:8080/GLCPWCC/basic/edit";
var basicDeleteUrl = "http://localhost:8080/GLCPWCC/basic/delete/";
var selectTokenServerUrl = "http://localhost:8080/GLCPWCC/basic/setTokenServer";

var userInfoUrl = "http://localhost:8080/GLCPWCC/admin/info";
var userUrlAll = "http://localhost:8080/GLCPWCC/admin/all";
var userLevelsUrlAll = "http://localhost:8080/GLCPWCC/admin/allLevels";
var userInsertUrl = "http://localhost:8080/GLCPWCC/admin/insert";
var userEditUrl = "http://localhost:8080/GLCPWCC/admin/edit";
var userDeleteUrl = "http://localhost:8080/GLCPWCC/admin/delete/";
var resetPasswdUrl = "http://localhost:8080/GLCPWCC/admin/resetPasswd";
var levelRightUrl = "http://localhost:8080/GLCPWCC/admin/levelRight";
var addAdminLevelUrl = "http://localhost:8080/GLCPWCC/admin/addLevel";
var adminLevelEditUrl = "http://localhost:8080/GLCPWCC/admin/userLevel";

var buttonGroupUrl = "http://localhost:8080/GLCPWCC/button/group";
var buttonsUrl = "http://localhost:8080/GLCPWCC/button/button/";

var msgTypeUrl = "http://localhost:8080/GLCPWCC/msgtype/all";
var keyWordsUrl = "http://localhost:8080/GLCPWCC/keywords/all";
var keyWordsInsertUrl = "http://localhost:8080/GLCPWCC/keywords/insert";
var keyWordsSetInUsing = "http://localhost:8080/GLCPWCC/keywords/setInUsing";
var keyWordsEdit = "http://localhost:8080/GLCPWCC/keywords/keywordEdit";
var deleteKeyWordsUrl = "http://localhost:8080/GLCPWCC/keywords/";
var subscribeReplyUrl = "http://localhost:8080/GLCPWCC/keywords/subscribe";
var insertSubscribeUrl = "http://localhost:8080/GLCPWCC/keywords/subscribe";
var setSubscribeInUsingUrl = "http://localhost:8080/GLCPWCC/keywords/subscribe/setInUsing";

var tagGetUrl = "http://localhost:8080/GLCPWCC/tag/get";
var tagGetUserUrl = "http://localhost:8080/GLCPWCC/tag/getUser";
var tagCreateUrl = "http://localhost:8080/GLCPWCC/tag/create";
var tagDeleteUrl = "http://localhost:8080/GLCPWCC/tag/delete";
var tagUpdateUrl = "http://localhost:8080/GLCPWCC/tag/update";
var tagCreateWithQr = "http://localhost:8080/GLCPWCC/tag/tagAndQrcode";

var qrcodeGetUrl = "http://localhost:8080/GLCPWCC/Qrcode/select";
var qrcodeCreateUrl = "http://localhost:8080/GLCPWCC/Qrcode/create";
var getResourceListUrl = "http://localhost:8080/GLCPWCC/resource/";
var getImageUrl = "http://localhost:8080/GLCPWCC/test/wechatimage";

//operationLog
var queryOperationLog = "http://localhost:8080/GLCPWCC/log/operate";
var exloreLog = "http://localhost:8080/GLCPWCC/log/operate/export";
var clearLog = "http://localhost:8080/GLCPWCC/log/operate";
//errorLog
var queryErrorLog = "http://localhost:8080/GLCPWCC/log/error";
var clearErrorLog = "http://localhost:8080/GLCPWCC/log/error";
var exloreErrorLog = "http://localhost:8080/GLCPWCC/log/error/export";
//debugLog
var queryDebugLog = "http://localhost:8080/GLCPWCC/log/debug";
var clearDebugLog = "http://localhost:8080/GLCPWCC/log/debug";
var exploreDebugLog = "http://localhost:8080/GLCPWCC/log/debug/export";
var tureDebugLog = "http://localhost:8080/GLCPWCC/log/setDebug/true";
var falseDebugLog = "http://localhost:8080/GLCPWCC/log/setDebug/false";
//ifDebug
var getDebug = "http://localhost:8080/GLCPWCC/log/DebugMode";


var MessageExcelUrl = "http://localhost:8080/GLCPWCC/message/upload/SendTemplateMessage";
var TemplateMessageButtonsUrl = "http://localhost:8080/GLCPWCC/message/templates";
var TicketExpiredUrl = "http://localhost:8080/GLCPWCC/message/upload/TicketExpiredMessage";

var socketUrl = "ws://localhost:8080/GLCPWCC/progress";

var getMenuUrl = "http://localhost:8080/GLCPWCC/wechatMenu";

// subscribe info
var getSubscribeInfoByDay = "http://localhost:8080/GLCPWCC/subscribe_report";


var addItem = "http://localhost:8080/GLCPWCC/tag/addTag";

var deleteItem = "http://localhost:8080/GLCPWCC/tag/deleteTag";

var usersFilter = "http://localhost:8080/GLCPWCC/voucher/user";
var restVoucher = "http://localhost:8080/GLCPWCC/voucher";
var bindChosen = "http://localhost:8080/GLCPWCC/voucherBinding/binding";
var bindVoucher = "http://localhost:8080/GLCPWCC/voucher/bindingall";

var getUserUrl = "http://localhost:8080/GLCPWCC/admin/admins";
var changeLevelUrl = "http://localhost:8080/GLCPWCC/admin/changeAdmins";

var voucherConfig = "http://120.26.54.131:8080/GLCPWCC/voucher/voucherConfig";
