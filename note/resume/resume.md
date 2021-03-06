# 简历

# 联系方式

- 手机：13488675604 
- Email：nicelife729@126.com
- 微信号：xuruijie729

---

# 个人信息

 - 许瑞杰/男/1984 
 - 本科/华中科技大学 通信工程 / 2002.09 ~ 2006.07
 - 硕士/北京工业大学 软件工程 / 2007.09 ~ 2010.07
 - 工作年限：7年
 
 - 期望职位：Java高级程序员
 - 期望城市：北京

---

# 工作经历

## 北京京东尚科信息技术有限公司（互联网公司 2015年6月～ ）

在该公司的主营平台-京东商城工作，主要负责用户管理和用户大数据方向的开发工作。

### 用户行为日志系统
此项目是功能是统一管理周边系统产生的用户行为日志。

此项目中我负责系统的设计和开发工作。

这个项目是基于hbase，主要功能是实时接收商城所有用户行为消息并插入hbase，并提供多索引的查询服务。通过该系统可以按用户id、手机号、qq、微信等条件，毫秒级内的查询某时段的用户行为。
为处理用户纠纷、排查其他用户系统bug提供依据。同时该系统还按日生成数据文件，供运营部门抓取后，提供给hive系统等下游系统做用户行为的数据挖掘用。

### 订单查询系统
此项目是实时记录所有京东商城的历史订单数据供周边系统查询使用。

此项目中我负责系统的设计和开发工作。

该项目主要是为了解决之前solr存订单数据，占用内存高，横向扩展性差的问题。
利用hbase的特性，使得历史数据的录入和查询速度都得到提升，满足了海量数据毫秒级查询需求。

### 账户安全系统
此项目是web应用，主要功能是维护用户的安全信息，比如绑定\解绑手机和邮箱、管理用户登录密码、支付密码等。
在该项目中我负责重构了之前的旧代码，提高系统的可维护性。主要工作是梳理并优化原有业务流程，规范化异常处理，统一业务错误代码、关键位置增加行为消息发送和监控埋点等。

## 友友天宇科技有限公司（创业公司 - 大数据、存储领域） （ 2012年8月 ~ 2015年6月 ）

在该公司先后从事前端和分布式应用系统开发工作;

### 智维系统项目 
此项目是统一管理服务器集群的监控系统，服务客户是美特丝邦威公司。

此项目中我主要负责中间服务层和前端页面的开发工作。

这个项目中主要挑战在前端设计方面，大量的监控图表和系统状态需要满足局部刷新页面，页面资源分离，以及扩展性的要求，技术栈上采用了html+jquery+requireJs+undercore架构，将页面、js业务脚本、js第三方包分类管理，使前台页面的逻辑更加清晰，不仅实现了业务要求的功能，而且相似页面、逻辑的重用度也得到提升，降低了调试难度，提高了开发效率。

项目上线后得到用户的好评。


### 廊坊智能交通系统项目 
此项目是智能城市建设方案中的重要组成部分，主要功能是实时采集和分析全城的过车和违法数据，然后存储到数据仓库，以备后期查询和离线分析。

此项目中我主要负责数据源接入、数据查询、流式分析、批量离线分析的开发工作。

此项目面临几方面的技术挑战，一是大数据存储规模，二是数据接入可靠性，三是数据实时分析，四是后期数据离线分析。

1.在数据存储规模方面，此项目要求能够存储三年的过车数据（30亿量级）并实现分钟级查询，而oracle等传统结构化数据库无法满足要求，另外通过比对hbase与公司自主研发的Datacell系统，最终选择了DataCell作为数据仓库，实际应用下来性能达到十亿量级数据秒级查询，精确车牌数据的毫秒级查询。

2.在数据接入方面，数据源端面临的挑战是保证24小时不间断接收数据。因此采用了LVS管理多台数据接收服务器，提供虚拟ip，负载均衡，保证了数据接收可靠性。另一方面，保证已接收还未落地的数据不丢失，采用了双重保证机制。首先，在数据接收服务器上采用Protobuf将数据文本做本地化保存。其次，采用kafka作为数据总线，利用其多副本、有落地缓存的特点来保证数据不丢。

3。在数据实时分析方面，采用了可以与kafka对接的Storm流式分析框架，将诸如违章报警，图片转发，实时布控，区间测速等业务功能，按照bolt节点连接到数据流上。这样即保证了扩展性，又保证了实时性。另外，对于流量统计类的应用场景，将storm与redis缓存结合起来，实现了边接数据边统计的工作模式。减小了后期做批处理统计的工作量。

4.在后期离线分析方面，采用了spark作为MapReduce的分析方案，通过Datacell Api拉取过车数据，然后通过Spark计算相应结果。


### 光大银行poc项目

此项目是针对光大银行业务中存在的多张GB级大表的场景下做交互式SQL查询的POC项目。

此项目中我主要负责数据导入开发，impala安装配置等工作;

在开发数据导入程序时，为了提到导入速度，借鉴了分布式的方法，采用多节点多线程的方式导入。具体来说是将每个表的源数据分散到各个节点，然后由管理程序通过Jzmq的发布订阅模式发出任务请求到所有节点。各个节点上的数据插入程序接收到任务后，由读取线程读该节点上的文件，然后将每条记录通过本地消息队列传递给多个插入线程入库。

通过这种方式，10台服务器下数据导入速度可以达到800GB/每小时左右，满足了该项测试要求。

 
## 大唐电信集团大唐微电子公司 （ 2010年7月 ~ 2012年8月 ）

在该公司系统解决方案部担任应用开发工程师并兼任项目经理工作。

主要工作内容是需求评估、建设方案制定、详细设计撰写、程序编写、项目进度与人员的日常管理。

### 中国电信远程写卡系统项目
 
在此项目中，我主要负责与局方讨论需求、订立规范与接口开发等，系统上线成功后，还负责日常维护与故障排除工作；目前系统仍在商用中。通过此项目我对实际商用系统的开发流程以及项目管理有了更深认识。

### 中国移动空中写卡系统项目

在此项目中，我主要负责选号、写卡、远程数据更新等模块的服务器端程序编写工作，并在系统上线的过程中，负责现场协调局方人员推动项目进度、搭建系统环境、配置网络环境、调试SIM卡、写卡终端等工作。系统按时成功上线，工作态度得到局方一致肯定；目前系统仍商用中。

此项目上我最大的收获是，当项目遇到外部困难时，采取积极主动的交流方式和认真负责的工作态度会对项目的成功实施带来很大的作用。


### 中国电信UIM卡管理系统项目

在此项目中，我主要负责协助部门经理订立业务规范，并根据规范撰写建设方案、详细设计，编写代码、及协调管理等工作。目前该系统在电信系统中担任着维护15个省及集团总部卡资源数据的重大责任。

通过实践，我从此项目上学习到了如何将项目管理方面的基本知识运用到项目中去，从项目的范围订立、进度计划到分解至人天的工作任务等一系列工作有了更好的把握。

---

# 个人作品
  
  项目描述：通过麦克风采集噪声环境（工厂噪声、汽车噪声、战斗机噪声、白噪声等）下的语音，通过消噪处理后过滤掉噪声，实时输出还原语音，同时将语音数据通过USB接口发送至PC端显示音频波形。
  
  项目成果：该系统消噪效果明显，还原语音清晰逼真，同时可自适应环境噪声。
  
  个人职责：撰写和论证设计方案，安排工作计划、实施设计开发。
  
  主要工作包括:
  
  1）MATLAB环境下仿真噪声估计算法、语音增强算法。
  
  2）DSP平台上实现噪声估计算法、语音增强、SFT。并实现硬件控制：I2C总线控制CODEC、Mcbsp+DMA数据传输、以中断方式管理模块运作、ping-pong双缓存、USB固件调用；
  
  3) VC开发音频波形显示界面。
  
  技术&平台：TLV320AIC23B(CODEC)+VC5509A(DSP)+USB固件+CCS+VC；

# 技能清单

在过去工作中常用的开发技术、框架等：

- 开发语言：JAVA/javascript/JQuery
- NOSQL: hadoop/hbase
- 消息队列：kafka/jzmq
- 流式处理：storm
- 批量处理:spark
- cache：redis
- Web框架：Spring/MyBatis
- 前端工具：JQueryMobile/requireJs/underscore
- 数据库相关：MySQL/Oracle
- 版本管理、文档和自动化部署工具：Svn/Git/Maven
- 脚本语言： bash
- 单元测试：Junit

---

# 致谢
感谢您花时间阅读我的简历，期待能有机会和您共事。