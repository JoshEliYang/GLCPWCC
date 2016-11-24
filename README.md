# GLCPWCC

# ![](http://120.26.54.131:8090/GLCPWCC/image/logo.gif) 绿地微信管理后台

后台地址：[http://120.26.54.131:8090/GLCPWCC](http://120.26.54.131:8090/GLCPWCC)

***
## 2016/9/2 更新 version1.1
1. 关键字回复-完成
2. 关注自动回复-完成
3. 自定义菜单-完成
4. 模板消息推送-完成
5. 用户标签管理-完成
6. 二维码管理-完成
7. 素材管理-60%
8. 基础信息配置-完成
9. 报表-0%
10. 日志-0%
11. 通知-20%

***
## 2016/11/8 更新
1. 增加日志
2. 新模板消息（优惠券到期通知）
3. 增加用户关注统计图（尚在开发中）
4. 增加部分模糊查询功能

***
## 2016/8/12 更新
1. 可以配置ATS（Access Token Server），即可以从其他server获得Access Token。
2. 可以提供ATS服务



### 说明:
所有API请求要带上 ```?wechatAccount=[basicId]``` 
```wechatAccount``` 即微信账号id（appid、appSecret...具体在数据库中配置），项目中要使用微信相关账号直接调用 ```Consts``` 中的 ```BASIC_DATA``` 

***
## 全局错误代码
1. 101: 尚未登录/登录超时

***
## 关于作者
* [@Johnson Yu](http://weibo.com/2794870214/profile?topnav=1&wvr=6&is_all=1)
* @Wu hai
