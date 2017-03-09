var deploy = "120.26.54.131:8080";
var localTest = "localhost:8080";

var prefixUrl = "http://" + deploy + "/GLCPWCC";

var socketUrl = "ws://" + deploy + "/GLCPWCC/progress";

//////////////////////////////// http //////////////////////////////////////
var loginUrl = prefixUrl + "/login/login";

var basicUrl = prefixUrl + "/basic/inusing";
var basicUrlAll = prefixUrl + "/basic/all";
var basicSetUsingUrl = prefixUrl + "/basic/setUsing";
var basicSetDefaultUrl = prefixUrl + "/basic/setDefault";
var basicInsertUrl = prefixUrl + "/basic/insert";
var basicEditUrl = prefixUrl + "/basic/edit";
var basicDeleteUrl = prefixUrl + "/basic/delete/";
var selectTokenServerUrl = prefixUrl + "/basic/setTokenServer";

var userInfoUrl = prefixUrl + "/admin/info";
var userUrlAll = prefixUrl + "/admin/all";
var userLevelsUrlAll = prefixUrl + "/admin/allLevels";
var userInsertUrl = prefixUrl + "/admin/insert";
var userEditUrl = prefixUrl + "/admin/edit";
var userDeleteUrl = prefixUrl + "/admin/delete/";
var resetPasswdUrl = prefixUrl + "/admin/resetPasswd";
var levelRightUrl = prefixUrl + "/admin/levelRight";
var addAdminLevelUrl = prefixUrl + "/admin/addLevel";
var adminLevelEditUrl = prefixUrl + "/admin/userLevel";

var buttonGroupUrl = prefixUrl + "/button/group";
var buttonsUrl = prefixUrl + "/button/button/";

var msgTypeUrl = prefixUrl + "/msgtype/all";
var keyWordsUrl = prefixUrl + "/keywords/all";
var keyWordsInsertUrl = prefixUrl + "/keywords/insert";
var keyWordsSetInUsing = prefixUrl + "/keywords/setInUsing";
var keyWordsEdit = prefixUrl + "/keywords/keywordEdit";
var deleteKeyWordsUrl = prefixUrl + "/keywords/";
var subscribeReplyUrl = prefixUrl + "/keywords/subscribe";
var insertSubscribeUrl = prefixUrl + "/keywords/subscribe";
var setSubscribeInUsingUrl = prefixUrl + "/keywords/subscribe/setInUsing";

var tagGetUrl = prefixUrl + "/tag/get";
var tagGetUserUrl = prefixUrl + "/tag/getUser";
var tagCreateUrl = prefixUrl + "/tag/create";
var tagDeleteUrl = prefixUrl + "/tag/delete";
var tagUpdateUrl = prefixUrl + "/tag/update";
var tagCreateWithQr = prefixUrl + "/tag/tagAndQrcode";

var qrcodeGetUrl = prefixUrl + "/Qrcode/select";
var qrcodeCreateUrl = prefixUrl + "/Qrcode/create";
var getResourceListUrl = prefixUrl + "/resource/";
var getImageUrl = prefixUrl + "/test/wechatimage";

//operationLog
var queryOperationLog = prefixUrl + "/log/operate";
var exloreLog = prefixUrl + "/log/operate/export";
var clearLog = prefixUrl + "/log/operate";
//errorLog
var queryErrorLog = prefixUrl + "/log/error";
var clearErrorLog = prefixUrl + "/log/error";
var exloreErrorLog = prefixUrl + "/log/error/export";
//debugLog
var queryDebugLog = prefixUrl + "/log/debug";
var clearDebugLog = prefixUrl + "/log/debug";
var exploreDebugLog = prefixUrl + "/log/debug/export";
var tureDebugLog = prefixUrl + "/log/setDebug/true";
var falseDebugLog = prefixUrl + "/log/setDebug/false";
//ifDebug
var getDebug = prefixUrl + "/log/DebugMode";


var MessageExcelUrl = prefixUrl + "/message/upload/SendTemplateMessage";
var TemplateMessageButtonsUrl = prefixUrl + "/message/templates";
var TicketExpiredUrl = prefixUrl + "/message/upload/TicketExpiredMessage";

var getMenuUrl = prefixUrl + "/wechatMenu";

// subscribe info
var getSubscribeInfoByDay = prefixUrl + "/subscribe_report";
var getSubscribeCount = prefixUrl + "/subscribers";

var addItem = prefixUrl + "/tag/addTag";

var deleteItem = prefixUrl + "/tag/deleteTag";

var usersFilter = prefixUrl + "/voucher/user";
var restVoucher = prefixUrl + "/voucher";
var bindChosen = prefixUrl + "/voucherBinding/binding";
var bindVoucher = prefixUrl + "/voucher/bindingall";

var getUserUrl = prefixUrl + "/admin/admins";
var changeLevelUrl = prefixUrl + "/admin/changeAdmins";

var voucherConfig = prefixUrl + "/voucher/voucherConfig";
