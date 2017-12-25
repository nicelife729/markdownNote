###坑
@pcornelissen Zuul by itself should allow chunked responses through without buffering. And I just verified this still works (using zuul-simple-webapp).
Ensure you don't have request-debug enabled (ie. the zuul.debug.request property in zuul-simple-webapp) as that would cause response buffering.


接口设计：
用户有一个openId,appsecret密钥
access_token接口凭证，2小时有效，过期重获，1天获取次数有限。次数可清零

提供调试工具

错误信息示例：
{"errcode":45009,"errmsg":"api freq out of limit"}


公众号调用或第三方平台帮公众号调用对公众号的所有api调用（包括第三方帮其调用）次数进行清零：
```
HTTP请求：POST
HTTP调用：
https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=ACCESS_TOKEN
```
调用示例：
```
{
“appid”:“APPID”
}
```
参数说明：
参数	是否必须	说明
access_token
是	调用接口凭据
appid
是	公众号的APPID

返回：


```
{
"errcode":0,
"errmsg":"ok"
}
```

```
{
"errcode":48006,
"errmsg":"forbid to clear quota because of reaching the limit"
}
```

返回码定义：
```
返回码
说明
-1
系统繁忙，此时请开发者稍候再试
0
请求成功
40001
获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口
40002
不合法的凭证类型
```