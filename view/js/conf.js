/**
 * Created by johsnon on 2016/7/27.
 */


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
var keyWordsEdit = "http://120.26.54.131:8080/GLCPWCC/keywords/edit";
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
var qrcodeCreateUrl = "http://120.26.54.131:8080/GLCPWCC/Qrcode/create"
var getResourceListUrl = "http://120.26.54.131:8080/GLCPWCC/resource/";
var getImageUrl = "http://120.26.54.131:8080/GLCPWCC/test/wechatimage";

var MessageExcelUrl = "http://120.26.54.131:8080/GLCPWCC/message/upload";
var TemplateMessageButtonsUrl = "http://120.26.54.131:8080/GLCPWCC/message/templates";

var socketUrl = "ws://120.26.54.131:8080/GLCPWCC/progress";

var getMenuUrl = "http://120.26.54.131:8080/GLCPWCC/wechatMenu";

//////////////////////////////////////// https //////////////////////////////////////////
// var loginUrl = "https://localhost:8443/GLCPWCC/login/login";
//
// var basicUrl = "https://localhost:8443/GLCPWCC/basic/inusing";
// var basicUrlAll = "https://localhost:8443/GLCPWCC/basic/all";
// var basicSetUsingUrl = "https://localhost:8443/GLCPWCC/basic/setUsing";
// var basicSetDefaultUrl = "https://localhost:8443/GLCPWCC/basic/setDefault";
// var basicInsertUrl = "https://localhost:8443/GLCPWCC/basic/insert";
// var basicEditUrl = "https://localhost:8443/GLCPWCC/basic/edit";
// var basicDeleteUrl = "https://localhost:8443/GLCPWCC/basic/delete/";
// var selectTokenServerUrl = "https://localhost:8443/GLCPWCC/basic/setTokenServer";
//
// var userInfoUrl = "https://localhost:8443/GLCPWCC/admin/info";
// var userUrlAll = "https://localhost:8443/GLCPWCC/admin/all";
// var userLevelsUrlAll = "https://localhost:8443/GLCPWCC/admin/allLevels";
// var userInsertUrl = "https://localhost:8443/GLCPWCC/admin/insert";
// var userEditUrl = "https://localhost:8443/GLCPWCC/admin/edit";
// var userDeleteUrl = "https://localhost:8443/GLCPWCC/admin/delete/";
// var resetPasswdUrl = "https://localhost:8443/GLCPWCC/admin/resetPasswd";
//
// var buttonGroupUrl = "https://localhost:8443/GLCPWCC/button/group";
// var buttonsUrl = "https://localhost:8443/GLCPWCC/button/button/";
//
// var msgTypeUrl = "https://localhost:8443/GLCPWCC/msgtype/all";
// var keyWordsUrl = "https://localhost:8443/GLCPWCC/keywords/all";
// var keyWordsInsertUrl = "https://localhost:8443/GLCPWCC/keywords/insert";
//
// var getResourceListUrl="https://localhost:8443/GLCPWCC/resource/";
