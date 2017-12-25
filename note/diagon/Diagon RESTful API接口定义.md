>## Diagon API 接口定义 


### 1. Root URL
> https://api.diagon.ruixin.com/v1.0/

API 根URL基于Https发布，根域名后跟版本号表示特定版本的API入口

#### 1.1 异常响应定义

当出现异常时，会出现的几种响应

1. 业务错误响应
    - http代码 400

    - 响应body：
        ```
        {
            "timeStamp": 1505266885718,
            "status": 400,
            "code": 1001,
            "error": "捕获消息 exceptionMsg:",
            "more_info": "https://api.diagon.ruixin.com/error/1001"
        }
        ```

2. 服务器内部错误响应
    - http代码 500

    - 响应body:
        ```
        {
            "timeStamp": 1505267132694,
            "status": 500,
            "code": 1000,
            "error": "服务器端错误，请稍后再试",
            "more_info": "https://api.diagon.ruixin.com/error/1000"
        }
        ```

3. 鉴权错误响应
    - http代码 403

    - 响应body：
        ```
        {
            "timestamp": 1505267172577,
            "status": 403,
            "error": "Forbidden",
            "message": "Access Denied",
            "path": "<资源url>"
        }
        ```


### 2. Endpoints
#### 2.1 鉴权

##### 2.1.1 通过鉴权接口获取令牌
> **POST** https://api.diagon.ruixin.com/login

**参数解释：**
- 用户名
- 密码
- 令牌：访问资源时附在Header中,key为Authorization

**请求：**
- 请求header：
    - Content-Type : application/json

- 请求body：{"username": "`<用户名>`", "password": "`<密码>`"}


**响应：**
成功响应
- http代码：200

- 响应body：
  {
       "token": "`<令牌>`",
       "type": "Bearer"
  }


**样例：**

1. 请求：
    ```
    {"username": "admin", "password": "123"}
    ```
2. 响应：
    ```
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9LHsiYXV0aG9yaXR5IjoiQUNUVUFUT1IifV0sImV4cCI6MTUwNTMwMTExOH0.oXcohjN0f_dfPP8CqobStadXXTLRrCU3wSRljVBKAZxZH8MPieDWIbsFJa-9Zi59OctWiSf5IElMinlCeowmoA",
        "type": "Bearer"
    }
    ```


#### 2.2 实时行情订阅

##### 2.2.1 创建股票实时行情订阅关系
> **POST** https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions

**参数解释：**

uuid-of-the-subscription：订阅关系的uuid

websocket-url: websocket访问url

市场缩写：
- SH：上海市场
- SZ：深圳市场

股票代码：
- 6位纯数字

**请求：**

- 请求header：

    - Authorization : `<令牌>`
    - Content-Type : application/json

- 请求body：
    ```
    {"stockCodeArr" :[
        {
            "marketType": "<市场缩写>"，
            "stockCode": "<股票代码>"
        }，
        {
            "marketType": "<市场缩写>"，
            "stockCode": "<股票代码>"
        }]
    }
    ```

**响应：**
正常响应
- http代码：200

- 响应body：
  {
    "subscribeId": "`<uuid-of-the-subscription>`",
    "webSocketUrl": "`<websocket-url>`"
  }

**样例：**

1. 请求
    ```
    POST https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions

    HEADER: 
    Authorization : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9LHsiYXV0aG9yaXR5IjoiQUNUVUFUT1IifV0sImV4cCI6MTUwNTI4NjI5MX0.frLnlJgLBhH1B7Sthvv69UMsfalOjzyprysBW5HLLkSv7LvDTnVVDy_9WdFG7_W1TuazRO82VYCkGJpy5uTr2A
    Content-Type : application/json

    BODY:{"stockCodeArr" :[
             {
                 "marketType": "SH"，
                 "stockCode": "600438"
             }，
             {
                 "marketType": "SZ"，
                 "stockCode": "000002"
             }]
         }  
    ```

2. 响应
    ```
    {
        "subscribeId": "746013dc-5e4d-4849-bf1b-34b58da53008",
        "webSocketUrl": "http://api.diagon.ruixin.com:10000"
    }
    ```


#####2.2.2 获取某个股票实时行情订阅关系中的代码列表：
> **GET**  https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions/`<uuid-of-the-subscription>`

**参数解释：**

uuid-of-the-subscription：订阅关系的uuid


市场缩写：
- SH：上海市场
- SZ：深圳市场

股票代码：
- 6位纯数字

**请求：**

- 请求header：
    - Authorization : `<令牌>`

**响应：**
- http代码：200

- 响应body：
    ```
    {
        "subscribeId": "`<uuid-of-the-subscription>`",
        {"stockCodeArr" :[
            {
                "marketType": "<市场缩写>"，
                "stockCode": "<股票代码>"
            }，
            {
                "marketType": "<市场缩写>"，
                "stockCode": "<股票代码>"
            }]
        }
    }
    ```

**样例：**

1. 请求
    ```
    GET https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions/746013dc-5e4d-4849-bf1b-34b58da53008

    HEADER: 
    Authorization : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9LHsiYXV0aG9yaXR5IjoiQUNUVUFUT1IifV0sImV4cCI6MTUwNTI4NjI5MX0.frLnlJgLBhH1B7Sthvv69UMsfalOjzyprysBW5HLLkSv7LvDTnVVDy_9WdFG7_W1TuazRO82VYCkGJpy5uTr2A
    ```

2. 响应
    ```
    {
        "subscribeId": "746013dc-5e4d-4849-bf1b-34b58da53008",
        "stockCodeArr" :[
                         {
                             "marketType": "SH"，
                             "stockCode": "600438"
                         }，
                         {
                             "marketType": "SZ"，
                             "stockCode": "000002"
                         }
                        ]
    }
    ```

#####2.2.3 更新某个股票实时行情订阅关系中的代码列表：
> **PUT**  https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions

**参数解释：**

uuid-of-the-subscription：订阅关系的uuid


市场缩写：
- SH：上海市场
- SZ：深圳市场

股票代码：
- 6位纯数字

**请求：**

- 请求header：
    - Authorization : `<令牌>`
    - Content-Type : application/json

- 请求body：
    ```
    {
        "subscribeId": "`<uuid-of-the-subscription>`",
        {"stockCodeArr" :[
            {
                "marketType": "<市场缩写>"，
                "stockCode": "<股票代码>"
            }，
            {
                "marketType": "<市场缩写>"，
                "stockCode": "<股票代码>"
            }]
        }
    }
    ```

**响应：**

成功响应
- http代码：200

- 响应body：
    ```
    {
        "subscribeId": "`<uuid-of-the-subscription>`"
    }
    ```

**样例：**

1. 请求
    ```
    PUT https://api.diagon.ruixin.com/v1.0/quotation/stock/subscriptions

    HEADER: 
    Authorization : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9LHsiYXV0aG9yaXR5IjoiQUNUVUFUT1IifV0sImV4cCI6MTUwNTI4NjI5MX0.frLnlJgLBhH1B7Sthvv69UMsfalOjzyprysBW5HLLkSv7LvDTnVVDy_9WdFG7_W1TuazRO82VYCkGJpy5uTr2A
    Content-Type : application/json

    BODY:{
            "subscribeId" : "746013dc-5e4d-4849-bf1b-34b58da53008",
            "stockCodeArr" :[
             {
                 "marketType": "SH"，
                 "stockCode": "600438"
             }，
             {
                 "marketType": "SZ"，
                 "stockCode": "000002"
             }]
         }  
    ```

2. 响应
    ```
    {
        "subscribeId": "746013dc-5e4d-4849-bf1b-34b58da53008"
    }
    ```

#####2.2.4 实时推送股票数据：
**订阅示例代码：**

1. 依赖包
    ```
    <script src="js/socket.io/socket.io.js"></script>
            <script src="js/moment.min.js"></script>
            <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

    <script>
    ```
2. js示例

    ```
    //建立websocket连接
    var socket =  io.connect('websocket url');

    socket.on('connect', function() {
        //do something
    });

    socket.on('disconnect', function() {
        //do something
    });

    function sendDisconnect() {
        socket.disconnect();
    }

    function sendMessage() {
        var message = $('#msg').val();
        $('#msg').val('');

        var subscribeId = message;
        //发起订阅请求
        socket.emit('subscribe', subscribeId, function(ret) {
            //收到订阅响应
            console.log('subscribeId:'+ ret.subscribeId + ' isSubScribed:' + ret.isSubscribed+ ' errMsg:'+ ret.errMsg);
            //发起拉行情请求
            socket.on(ret.subscribeId, function(data) {
                //返回股票数据
                //do something
                //data是股票数组，每个值是股票bean
            });
        });
    }
    ```

3. 股票bean结构
    ```
    securityCode:股票代码（带市场代码）
    time:时间
    lastClose:昨收盘（单位：元，3位小数）
    open:今开盘（单位：元，3位小数）
    high:最高价（单位：元，3位小数）
    low:最低价（单位：元，3位小数）
    newPrice:最新成交价（单位：元，3位小数）
    totalVolume:总成交量（单位：股）手还是股数？
    totalAmount:总成交金额（单位：元，3位小数）
    buy1Price:买一价：（单位：元，3位小数）
    buy1Volume:买一量：（单位：股）
    buy2Price:买二价：（单位：元，3位小数）
    buy2Volume:买二量：（单位：股）
    buy3Price:买三价：（单位：元，3位小数）
    buy3Volume:买三量：（单位：股）
    buy4Price:买四价：（单位：元，3位小数）
    buy4Volume:买四量：（单位：股）
    buy5Price:买五价：（单位：元，3位小数）
    buy5Volume:买五量：（单位：股）
    sell1Price:卖一价：（单位：元，3位小数）
    sell1Volume:卖一量：（单位：股）
    sell2Price:卖二价：（单位：元，3位小数）
    sell2Volume:卖二量：（单位：股）
    sell3Price:卖三价：（单位：元，3位小数）
    sell3Volume:卖三量：（单位：股）
    sell4Price:卖四价：（单位：元，3位小数）
    sell4Volume:卖四量：（单位：股）
    sell5Price:卖五价：（单位：元，3位小数）
    sell5Volume:卖五量：（单位：股）

    ```

#### 2.3 静态数据表
##### 2.3.1 获取A股基本资料 
> https://api.diagon.ruixin.com/v1.0/data/stock/basic/`<市场缩写>`/`<股票代码>`

**参数解释：**

市场缩写：
- SH：上海市场
- SZ：深圳市场

股票代码：
- 6位纯数字

**请求：**
- 请求参数：
    - 市场缩写
    - 股票代码
- 请求header：
    - Authorization : 令牌

**响应：**
- http代码：200

- 响应body：
    ```
    {
        "securityCode": "证券代码",
        "shortName": "证券简称",
        "pyShortName": "简称拼音", 
        "companyName": "公司中文名称",
        "companyEnglishName": "公司英文名称",
        "isinCode": "ISIN代码",
        "currencyCode": "货币代码", 
        "tradeMarketCode": "交易市场代码",
        "listBoardCode": "上市板配置代码",
        "listBoardName": "上市板块名称",
        "listDate": "上市日期",
        "delistDate": "退市日期",                      
        "shscCode": 是否在沪股通范围
    }
    ```

**样例：**

1. 请求
    ```
    GET https://api.diagon.ruixin.com/v1.0/data/stock/basic/sh/600000
    HEADER: 
    Authorization : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9LHsiYXV0aG9yaXR5IjoiQUNUVUFUT1IifV0sImV4cCI6MTUwNTI4NjI5MX0.frLnlJgLBhH1B7Sthvv69UMsfalOjzyprysBW5HLLkSv7LvDTnVVDy_9WdFG7_W1TuazRO82VYCkGJpy5uTr2A
    ```

2. 响应
    ```
    {
        "securityCode": "000004",
        "shortName": "国农科技",
        "pyShortName": "gnkj",
        "companyName": "深圳中国农大科技股份有限公司",
        "companyEnglishName": "Shenzhen Cau Technology Co.,Ltd.",
        "isinCode": "CNE0000000Y2",
        "currencyCode": "CNY"
        "tradeMarketCode": "001003",
        "listBoardCode": "434004000",
        "listBoardName": "主板",
        "listDate": "19910114",
        "delistDate": "",
        "shscCode": 0
    }
    ```

**码表**

1. 交易市场代码(tradeMarketCode)

    | 代码     | 含义                 |
    | :----- | :----------------- |
    | 001002 | 上海证券交易所            |
    | 001003 | 深圳证券交易所            |
    | 001004 | 股份转让系统             |
    | 001005 | 银行间市场              |
    | 001008 | 上海期货交易所            |
    | 001009 | 中国金融期货交易所          |
    | 001010 | 中国外汇交易市场           |
    | 001015 | 上海黄金交易所            |
    | 001016 | 大连商品交易所            |
    | 001017 | 郑州商品交易所            |
    | 001022 | 渤海商品交易所            |
    | 001025 | 天津贵金属交易所           |
    | 001056 | 港股通                |
    | 001057 | 沪股通                |
    | 002001 | 香港证券交易所            |
    | 003002 | 台湾证券交易所            |
    | 101001 | 纽约证券交易所            |
    | 101002 | 美国纳斯达克市场           |
    | 101003 | 美国证券交易所            |
    | 101012 | 美国洲际交易所（纽约期货交易所）   |
    | 101013 | 纽约商品交易所（美国纽约金属交易所） |
    | 101014 | 纽约商业期货交易所          |
    | 101015 | 芝加哥商业交易所           |
    | 101017 | 芝加哥期货交易所           |
    | 104017 | 东京证券交易所            |
    | 105001 | 伦敦证券交易所（英国）        |
    | 105015 | 法兰克福证交所（德国证交所）     |
    | 105060 | 伦敦国际石油交易所          |
    | 105061 | 伦敦金属交易所            |
    | 106001 | 澳大利亚证券交易所          |
    | 107032 | 多伦多证券交易所           |
    | 100601 | 上海万得信息技术股份有限公司     |
    | 100602 | 上海申银万国证券研究所有限公司    |
    | 100603 | 中信标普指数信息服务有限公司     |
    | 100604 | 中证指数有限公司           |
    | 100999 | 其他                 |

2. 上市板配置代码(listBoardCode)

| 代码        | 含义     |
| :-------- | :----- |
| 434001000 | 创业板    |
| 434002000 | 三板     |
| 434003000 | 中小企业板  |
| 434004000 | 主板     |
| 434005000 | 退市整理股票 |
| 434006000 | 风险警示股票 |


3. 货币代码(currencyCode)

| 代码   | 含义   |
| :--- | :--- |
| CNY  | 人民币  |
| USD  | 美元   |
| HKD  | 香港   |
| SGD  | 新加坡  |
