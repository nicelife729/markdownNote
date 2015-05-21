#复习大原则
问了一大堆问题，都是深层次的问题。要注意加强原理是什么，是怎么实现的

#技巧
自己尽量多说，如果问得浅就自己引申，展示知识面


#JVM
##ClassLoader的工作机制（jvm类加载机制）ok

##JVM的体系结构、工作方式 

class loader
execution engine

##JVM的内存管理 （Minor GC和Full GC分别触发的是哪些GC）
minor GC 触发新生代中初生池被干掉，延长幸存的对象的存在周期放入幸存池
major GC(Full GC) 触发老生代干掉，挂住所有应用线程

堆大小动态，堆扩展时也会导致应用短暂停止。


##系统静态变量是存在JVM那个内存块中。-》方法区的常量池里 ok
##垃圾回收的机制是什么 ok
##Java虚拟机原理 ok
##分析内存溢出的方法 ok
##JVM性能调优 ok
##7种GC的区别  


#J2SE
##反射的机制 //1/2
Class
Method
invoke
##java中的多态

##常用的集合类以及其实现原理 ok
Iterator接口
的子接口ListIterator（从不同方向遍历）


Iterator接口 （next（），hasNext（））
依赖于^
Collection接口 （基本集合操作）
  |--Queue--Deque 
  |--List （顺序，可重复）
  |--Set （不可重复）
依赖于^
Map接口 （三种视图，key集合，value集合，kv映射）


List接口
LinkedList （底层双向链表，可为null）
ArrayList （底层是数组，可为null， 区别是可以扩充大小）
Vector （类似ArrayList，不过线程安全）
继承自^
Stack () 

Set接口 
HashSet  （底层HashMap， 用key存）
TreeSet   （底层TreeMap，对对象调用compareTo，比较后升序排列，对象必须实现Comparable接口）

Map接口
HashMap （底层散列表，线程不安全，影响性能的因素（初始容量《桶数量》，加载因子（当出现条目数大于0.75×总容量时，再hash）），解决散列冲突：（开链法：key找到后，Entry原值向后一个，指向新值））
TreeMap  (底层红黑数，)
WeakHashMap （当只有自身的弱引用时，丢弃值）

HashTable （继承自dictionary类，实现map接口，线程安全，k和v都不能为null）

##arraylist怎么实现list接口的，说java机制是怎么实现那个接口的 ok

##多线程线程同步以及JUC中的类读过JUC中的哪些源码 ,它们的实现机制 

#算法 //TODO
##了解多少种排序算法 以及它们的时间和空间复杂度

#java常用框架 (基本java web面试必考项目)
##Spring/SpringMVC/Struts2/Hibernate/MyBatis/Spring Security 
1. 是否阅读过它们的源码 
2. 它们实现的原理是什么 
3. Servlet的原理

###spring



#设计模式：
24种设计模式 都了解哪些 (我偷了个懒用源码来解释了一下)
   
#关于任务调度:
quartz使用原理
以及分布式集群下的使用
   
#前端方面
##javascript的原生API的熟悉程度

##jQuery的使用 
其中还问了我一个动画效果的底层实现orz
   
##session与cookie的区别
   
#缓存方面：
##ehcache和memcached的实现原理以及区别 如何进行分布式 (因为我就用过这两种缓存)

#数据库方面：
##关系型：
###Mysql、Oracle的使用
###考察了一下数据库表的设计技巧
###Mysql  用过哪些Mysql的存储引擎 他们的区别以及原理
###数据库索引的原理 算法

##非关系型：
NoSQL、MongoDB的使用 原理
    
#中间件/驱动方面：
    考察了一下Mysql驱动的源码 实现原理 MysqlIO怎么回事儿
#操作系统方面：
    对于linux的使用  常用的负载均衡操作 以及linux指令
#协议方面：
    Http协议 常见的Http报文头
     web服务器
##nginx和apache的区别 nginx的原理 如何做到负载均衡
    应用服务器
    用过哪些应用服务器  我一般用tomcat/weblogic 是否阅读过tomcat源码 tomcat的原理
#项目管理工具
    SVN 、GIT 基本使用
#项目构建工具
    ANT、MAVEN 基本使用以及 MAVEN的原理 是否搭建过Maven的私服
    
#session共享,如何处理负载均衡

-----------------------------------------







#项目
介绍下你的项目，画下构架图。
项目中的改善的地方是什么
好在哪 不足在哪 怎么改进

#java基础
java 8个基本变量和所占字节。: boolean byte 1; short, char 2; int, float 4; double, long 8;
final,exceptin
重写，重载，:override 同名（不同返回值，不同参数）
Collections类
String 的修饰符是什么？为什么？ final
String的内部是如何实现的？ char[] int count

map、treemap；hashset与hashmap的区别 ok

hashmap怎么实现的，如果要求线程安全的可以用什么代替 ok

静态内部类与非静态内部类区别。 ok

/*
 * Java中的类可以包含方法、成员变量、语句块，甚至还可以包含类，这种定义在另一个类内部的类称为内部类。
 * 内部类根据使用情况分为4种：成员内部类、局部内部类、静态内部类、匿名内部类。
 * 
 * 
 * 成员内部类
 * 成员内部类，是一个可以看成是外部类的非静态成员的内部类。
 * 成员内部类可以访问外部类的私有成员或属性。
 * 外部类不能访问成员内部类中的私有属性。
 * 成员内部类是一个编译时的概念，一旦编译成功，就会成为与外部类完全不同的类。
 * 内部类和外部类在编译时是两个不同的类，内部类对外部类没有任何依赖。
 * 
 * 在外部类的内部创建一个内部类的对象可以直接使用inner i = new inner();(inner代表内部类的类名）
 * 在外部类的外部创建一个内部类的对象，需要首先建立一个外部类对象，然后再生成一个内部类对象。
 * 
 * 
 * 局部内部类
 * 局部内部类，是一个只有在局部有效的内部类。
 * 局部内部类被定义在方法中。
 * 局部内部类前不加修饰符public和private，其范围为定义它的代码块。
 * 局部内部类可以访问外部类的私有实例变量和局部常量（即final型的）。
 * 在外部类的外部不能直接访问局部内部类，即局部内部类对外是不可见的。
 * 为解决类外不能访问局部内部类的问题，可以通过局部内部类和接口达到一个强制的弱耦合关系，用局部内部类来实现接口，并在方法中返回接口类型，这样既可以使局部内部类不可见，屏蔽实现类的可见性，又可以在类的外部访问到内部类的方法。
 * 
 * 
 * 静态内部类
 * 静态内部类，其实已经脱离了外部类，实质是一个放置在别的类内部的普通类，关键字static只是说明其在创建对象时不依赖于外部类对象的存在，并不是说这个类本身是静态的。
 * 静态内部类不可以用private进行定义，在内部类的声明前面要加上static关键字。
 * 静态内部类只能访问外部类的静态成员，不能访问外部类的非静态成员。
 * 静态内部类的对象可以直接生成，而不需要外部类的对象来生成。
 * 非静态内部类是外部类对象组成的一部分，主要是辅助外部类对象工作的，与外部类对象存在着对成员变量的共享关系。
 * 
 * 
 * 匿名内部类
 * 匿名内部类，是一种特殊的局部内部类，这种内部类没有类名。该类适用于只使用一次且不需要多次创建对象的类。
 * 匿名内部类在声明的同时创建对象，匿名内部类的声明要么是基于继承，要么是实现接口。
 * 匿名内部类是唯一一种没有构造方法的类。
 * 大部分匿名内部类用于接口回调。
 * 匿名内部类在编译时由系统自动起名为Out $1.class。
 * 匿名内部类用于继承其他类或是实现接口，且不需要增加额外的方法，只是对继承方法的实现或是重写。
 */

#反射
获取父类属性: super
通过类加载器
#注解
怎样理解annotation 


#算法
用java写一个栈的操作，交换算法排序
平衡二叉树
b树和B+树
笔试的编程题是约瑟夫环
电梯调度实现程序
冒泡排序
快速排序、堆排序、时间复杂度
二分查找的算法
链表翻转和O(1)删除链表节点
算法题，15分钟给出思路
递归用法：函数自调用
双向链表
两个顺序数组合并还是顺序
找中间数，要时间最少
两个大文件求相同数
把0到99这一百个数随即的放到一个数组中。



算法：写一个n*n的矩阵 顺时针移动
他提示我用递归
1 2
3 4
变成
3 1
4 2

#设计模式
Abstract Factory：提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。
Adapter：将一个类的接口转换成客户希望的另外一个接口。A d a p t e r模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
Bridge：将抽象部分与它的实现部分分离，使它们都可以独立地变化。
Builder：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
Chain of Responsibility：为解除请求的发送者和接收者之间耦合，而使多个对象都有机会处理这个请求。将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它。
Command：将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或记录请求日志，以及支持可取消的操作。

#servlet
Servlet 有哪些方法 doget dopost
Http的method有哪几个？
forward和 dispcter，dispcter返回的状态码是多少

#通信协议
##OSI七层模型:

应用层
表示层
会话层
传输层 - 段 tcp，udp
网络层 - 数据包 ip
数据链路层 - 帧 arp
物理层 - 比特流

##rest和soap区别 ok

区别：SOAP提供了完整性；REST则提供了简单性。
SOAP是基于XML信息的一种协议规范，SOAP+WSDL（Web服务描述语言）的复杂性和限制
REST是基于客户端-服务器的架构风格，它是由一组CRUD（创建、读取、更新、删除）的操作（在REST的世界里，分别是POST，GET，PUT和DELETE）构成，并且只使用一种寻址方式（基于URI，或统一资源标识符）。REST在架构上加入了一些限制：

##TCP为什么比UDP可靠？
tcp 面向连接，通过三次握手和四次挥手保证传输的可靠性。

TCP下的网络层提供的是不可靠传输。
因此，
TCP做了两件事：
1.出错时发送方重传 （超时重传，(报头中的序号字段（本报文中第一个字节序号）和确认号字段（期望收到对方下一个报文段的第一个字节序号）解决乱序，丢报文段问题）
2.接收速度不够时，让发送方流控 (报头中的window字段，由接受方告诉发送方还有多少缓存)
TCP报头20byte

tcp建立，三次握手

tcp释放，四次挥别

UDP的特点，
不面向连接，不可靠， 面向报文（只做加头，不拆分或合并上层报文）， 不做流控， 支持11,1n，n1,nn通信
报头小，只有8字节

##http无状态的意思 ok
tcp管理连接，http不知道上次发生连接的情况

#orm框架
Java、Spring、ibtais、Struts等的原理 ok
MVC的理解 : 职责分离 ok

三大框架（SSH）源码看过没

orm技术都有什么  
spring源码或相关开源项目的源码阅读情况
Spring实现方式 
Spring AOP内部是如何实现的？ CGLIB
spring 顶级类是什么 ？？
Spring事务 JTA怎么实现的？　　！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
现在为什么很多开发者选择SpringMVC？SpringMVC比Struts 2好吗？
1. 机制：spring mvc的入口是servlet，而struts2是filter（这里要指出，filter和servlet是不同的。
2. 性能：spring会稍微比struts快。spring mvc是基于方法的设计，而sturts是基于类。
3. 参数传递：struts是在接受参数的时候，可以用属性来接受参数，这就说明参数是让多个方法共享的。
4. 设计思想上：struts更加符合oop的编程思想， spring就比较谨慎，在servlet上扩展。
5. intercepter的实现机制：struts有以自己的interceptor机制，spring mvc用的是独立的AOP方式。
6. 另外，spring mvc的验证也是一个亮点，支持JSR303，处理ajax的请求更是方便，只需一个注解@ResponseBody ，然后直接返回响应文本即可。
7. struts2采用的传统的配置文件的方式，并没有使用传说中的0配置；spring mvc可以认为已经100%零配置了（除了配置spring mvc-servlet.xml外）。


#Struts1 和 2 的区别？ ：没有关系， Struts2 全新框架，引入WebWork很多技术和思想， Struts2 保留Struts1 类似开发流程
#ibtais

#resultMap 
优点：使用嵌套查询的话（association@select）多表不用写JOIN这种复杂SQL。 
缺点：“N+1 查询问题”，会导致成百上千的 SQL 语句被执行，不过可以通过延迟加载一部分解决这个性能问题。另一种根治的方法就是用嵌套的resultMap，不过这样写出来的resultMap就更复杂了。 


#Strust2 拦截器怎么定义，默认拦截器有哪些 ：实现Interceptor接口，接intercept方法中invoke前，做一些操作，之后做一些操作。

默认拦截器有：
<!-- 模型驱动 -->
<interceptor-ref name="modelDriven"/>当某action实现了ModelDriven接口，负责把getModel（）的结果推入ValueStack中
<!-- 文件上传 -->
<interceptor-ref name="fileUpload"/>负责解析表单中文件域的内容
<!-- 参数解析封装 -->
<interceptor-ref name="params"> 将request参数转为action中的param
<!-- 类型转换错误 -->
<interceptor-ref name="conversionError"/>将错误从actioncontext中取出，转换成action中的FieldError
<!-- 请求参数校验 -->
<interceptor-ref name="validation"> 调用配置好的校验xml，完成校验
<!-- 拦截跳转 input 视图 -->
<interceptor-ref name="workflow"> 负责调用action中的validate(),若校验失败，返回input视图

#ibtais二级缓存的内部实现机制

dubbo框架从消息接收消息到分配消息到消费者的执行过程!

#通信模型
常见的通信模型，各种socket模型之间的区别



#事务
事务： JTA事务，局部事务

#网络
网络性能

#缓存
memcache原理、
怎样存储、
memcache的集群搭建以及原理、
缓存满了怎么办？
memcached使用。

redis的持久化
REDIS应用场景
REDIS和MEMORYCATCH区别。

#调优
代码调优，

#服务器
tomcat的调优 ok 
tomcat调优很多底层的东西
tomcat假死，

系统压力大，怎么扩容？
nginx
nginx怎么保证各各web容器之间不会串


#NoSQL
storm的原理，用了什么nio框架
是否会一致性哈希算法？　会
nosql数据库的使用。

#MQ
ActiveMQ的原理是什么

#高并发
高并发如何解决？
高并发网站的优化问题。
要设计一个网站，网站需要处理大量并发用户的请求，并且不希望这些用户得到超时的回复，如何去设计
高并发的使用场景
多个实例同时访问一条数据的并发问题的解决方案。


#大数据
海量数据的处理方法

#多线程 ！！！！！！！！！！！！！！！！！
nio与多线程相关
IO和NIO的同步，阻塞。

线程和并发问的是项目
lock有哪些？ 互斥锁 读写锁
线程池有哪些？ cacheed fixed single
java线程如何编写
如何暂停一个线程 wait sleep join



#设计模式
单例模式

#数据库！！！！！！！！！！！！！
SQL
sql左连接右连接　
数据库索引的原理 ：b+树 ok
Mysql数据库引擎 ok
数据库调优的相关技术
数据库事务 ok
数据库事务级别 ok

数据库联合索引
oracle索引种类
寻峰算法
数据库主从、读写分离等方面
MySQL 内置引擎，ｏｋ
索引优化
数据量大的时候如何提高查询速度，
各种索引的原理以及实用场景
索引为什么使用b-树而不使用红黑树 ：b树的一个节点子女更多，树的高度低，因此b数的查找时间复杂度为O(logn)，查询更快。 ok

hash索引的原理
拓展到hashtable的处理冲突方式
以及hashtable开练法由于数据过多导致冲突频发该如何解决
由此延伸hashtable开练方式下的并发访问，也就是互斥问题





#定时任务的解决方案


#开放式
工作中最有成就感的事情：独立开发DSP
你对京东电子商务的理解：B2C行业老大
java中哪些技术令我最感到提高最多？
人生中最与众不同的瞬间。小时候去偷瓜的故事。

#linux
Linux查看内存的命令 free -m
网络IO的命令 ： top sar vmstat mpstat iostat netstat sar -n DEV 1
jvm : jps jstack
gc : Gchisto, PrintGCStats

僵尸进程

#maven

#其他
设计内存池
文件读取排序输出的问题
支付宝对接是什么协议？
最擅长的技术是什么？
做过什么。


#分布式
多节点之间的数据同步
解决同步问题的方法有哪些？

#UML
给定了一个场景，抽象出实现结构
给定了一个场景，抽象出实现结构，并画出UML；
自己人为的京东商城网站结构，可分几部分等。

模拟购物车，说一下具体购物车系统应该如何设置，如何部署及优化。
数据缓存如何做？

#ajax
ajax技术底层实现机制
js的同源策略

#机器学习
什么是监督学习与非监督学习

#性能安全架构
什么是接口安全？


#应用
设计模块、系统的问题，涉及分布式、高并发、大数据方面，还有关于团队协作的问题：假设你带了n个人的团队，在分配任务、检查成果、团队出现问题等方面该如何处理；



Mysql 读写分离

分库

acid

CAP/BASE

一致性
可有性
网络隔离忍耐度

基本可用
软状态
最终一致性

数据库减负-》只做存数据，单条查询


#分库
垂直分库：按功能模块拆分
水平分库：吧同一个表分块保存到多个数据库中

问题：
db必须提供分库线索
不能执行跨库关联查询
不能保证数据一致性/完整性

#分表

#读写分离（binlog）

#数据访问层
  负载均衡
  
应用开源技术：
nginx
luncene
hadoop

jvm调优
减少majorGC 
合理分配堆大小：新生代占堆的30%～50% 新生代扩大
选参数G1

计算最佳并发线程数