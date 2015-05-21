#核心功能

- 允许POJO（Plain Old Java Objects）对象 作为Action
- Action的execute 方法不再与Servlet API耦合，更易测试
- 支持更多视图技术（JSP、FreeMarker、Velocity）
- 基于Spring AOP思想的拦截器机制，更易扩展
- 更强大、更易用输入校验功能
- 整合Ajax支持

Web层框架都会使用前端控制器模式(JavaEE模式)

javaWeb 编写的程序, 一次请求对应一个servlet, 此时servlet完成请求处理
使用框架, 所有访问通过 前端控制器, 前端控制器已经实现了部分代码功能(通用代码), 再交给不同 Action 来处理(请求分发), 一次请求, 对应一个Action

Struts2 前端控制器: ServletPrepareAndExecuteFilter

#运行流程图
用户请求 -> StrutsPrepareAndExecuteFilter 核心控制器 -> Interceptors 拦截器(实现代码功能) -> Action 的execuute -> 结果页面 Result

- 拦截器 在 struts-default.xml 定义
- 执行拦截器 是 defaultStack 中引用拦截器


#struts2 常见配置

##配置文件的加载顺序
由核心控制器加载 StrutsPrepareAndExecuteFilter (预处理，执行过滤) 
1. default.properties 该文件保存在 struts2-core-2.3.7.jar 中 org.apache.struts2包里面 (常量的默认值) 
2. struts-default.xml 该文件保存在 struts2-core-2.3.7.jar(Bean、拦截器、结果类型) 
3. struts-plugin.xml 该文件保存在struts-Xxx-2.3.7.jar(在插件包中存在 ，配置插件信息) 
4. struts.xml 该文件是web应用默认的struts配置文件(实际开发中，通常写struts.xml) 
5. struts.properties 该文件是Struts的默认配置文件 (配置常量) 
6. web.xml 该文件是Web应用的配置文件 (配置常量)

后加载的文件会覆盖之前加载的文件常量内容

###Action配置

### package标签 ###

必须要为`<action>`元素 配置`<package>`元素  (struts2 围绕package进行Action的相关配置)

必须直接或间接地继承自struts-default的包.

作用: 方便管理我们的动作(struts-default是核心配置文件)

属性：
* abstract：可选值为true|false。说明他是一个抽象包。抽象包中没有action元素的。(默认为false)
* name：包名。不能重复。方便管理动作的。
* namespace：名称空间
* extends：继承什么

```
<!-- name 包名称，在struts2的配置文件文件中 包名不能重复 ，name并不是真正包名，只是为了管理Action  -->
<!-- namespace 和 <action>的name属性，决定 Action的访问路径  （以/开始 ） -->
<!-- extends 继承哪个包，通常开发中继承 struts-default 包 （struts-default包在 struts-default.xml定义 ） -->
<package name="default" namespace="/" extends="struts-default"></package>
```

## action标签 ##

* name: 必须的, 动作名称
```
<package name="p2" extends="struts-default">
    <!-- 只要找不到的action的name，找act4。默认动作名称 -->
    <default-action-ref name="act4"></default-action-ref>
</package>
```
* class：可选的. 默认值是com.opensymphony.xwork2.ActionSupport
```
<package name="p2" extends="struts-default">
    <!-- 只要找不到的action的class，找com.opensymphony.xwork2.ActionSupport。默认class -->
    <default-class-ref name="com.opensymphony.xwork2.ActionSupport"></default-class-ref>
</package>
```
* method: 可选. 默认值是 `public String execute(){return "success"}`

## result标签 ##

type：默认值dispatcher. 转发，目标JSP

name：默认值是success.

```
<package name="default" namespace="/test" extends="struts-default">
    <action name="hello" class="com.itheima.action.HelloAction" method="execute">
        <result name="female">/female.jsp</result>
        <result name="male">/male.jsp</result>
    </action>
</package>
```

访问包中带有名称空间的动作时：
`http://localhost:8080/day22_01_strutsHello/test/hello.action`
`http://localhost:8080/day22_01_strutsHello/test/aaa/bbb/hello.action`

动作有搜索顺寻：
1. 从/test/aaa/bbb找，不存在
2. 从/test/aaa找，不存在
3. 从/test，找到了
4. 一旦找到就不向上找了

## 默认Action 和 Action的默认处理类 ##

默认Action ， 解决客户端访问Action不存在的问题 ，
客户端访问Action， Action找不到，默认Action 就会执行
```
<default-action-ref name="action元素的name" />
```

默认处理类 ，客户端访问Action，已经找到匹配`<action>`元素，
但是`<action>`元素没有class属性，执行默认处理类
```
<!-- 在struts-default.xml 配置默认处理类 ActionSupport  -->
<default-class-ref class="完成类名" />
```


package action result

```
<package name="default" namespace="/test" extends="struts-default">
    <action name="hello" class="com.itheima.action.HelloAction" method="execute">
        <result name="female">/female.jsp</result>
        <result name="male">/male.jsp</result>
    </action>
</package>
```

###常量配置

在 struts2-core-*.jar 的org.apache.struts2的 default.properties文件中存在一些内置常量

可以在 struts.properties, struts.xml, web.xml

###struts2 配置文件分离

通过 <include file="struts-part1.xml"/> 将struts2 配置文件 拆分

##Action 的访问

// Action接口中，定义默认五种 逻辑视图名称
// 五种逻辑视图，解决Action处理数据后，跳转页面
public static final String SUCCESS = "success";  // 数据处理成功 （成功页面）
public static final String NONE = "none";  // 页面不跳转  return null; 效果一样
public static final String ERROR = "error";  // 数据处理发送错误 (错误页面)
public static final String INPUT = "input"; // 用户输入数据有误，通常用于表单数据校验 （输入页面）
public static final String LOGIN = "login"; // 主要权限认证 (登陆页面)

三种方式：

1. Action可以是 POJO (PlainOldJavaObjects)简单的Java对象, 不需要继承任何父类，实现任何接口
2. 编写Action 实现Action接口
```
public class MyAction implements com.opensymphony.xwork2.Action {
    public String execute(){
        return SUCCESS; // Action 中定义的常量, 匹配配置文件 struts.xml 中的 action.name
    }
}
```
3. 编写Action, 继承ActionSupport(推荐), 在Action中使用 表单校验、错误信息设置、读取国际化信息 三个功能
```
public class MyAction extends com.opensymphony.xwork2.ActionSupport {
    public String execute() { }
}
```

##Action的方法调用

1. 在配置 <action> 元素时，没有指定method属性， 默认执行 Action类中 execute方法

2. 在 <action> 元素内部 添加 method属性，指定执行Action中哪个方法
3. 使用通配符* ，简化struts.xml配置
4. 动态方法调用
    - 在工程中使用 动态方法调用 ，必须保证 struts.enable.DynamicMethodInvocation = true 常量值 为true
    - 在action的访问路径 中 使用 "!方法名"
    
##Action中使用 Servlet

1. 使用ActionContext 对象, 解耦合方式
```
actionContext = ActionContext.getContext();
// 获得所有请求参数Map集合
actionContext.getParameters();
// actionContext.get("company") 对request范围存取数据
actionContext.put("company", "传智播客");
// 获得session数据Map，对Session范围存取数据
actionContext.getSession();
// 获得ServletContext数据Map，对应用访问存取数据
actionContext.getApplication();
 ```
2. ServletActionContext的静态方法可以得到Servlet相关的对象
```
//用Servlet相关的对象request response servletContext HttpSession
HttpServletRequest request = ServletActionContext.getRequest();
HttpServletResponse response = ServletActionContext.getResponse();
ServletContext sc = ServletActionContext.getServletContext();
HttpSession session = request.getSession();
```
3. 使用接口注入的方式，操作Servlet API(耦合)
Action实现如下接口，struts框架则会为其注入相应的Servlet API对象： ServletRequestAware, ServletResponseAware, ServletContextAware, 实现其他对象或者功能，参考拦截器servletConfig

##结果页面的配置

Action处理请求后， 返回字符串(逻辑视图名), 需要在struts.xml 提供 <result>元素定义结果页面

局部结果页面 和 全局结果页面

```
<action name="result" class="cn.itcast.struts2.demo6.ResultAction">
    <!-- 局部结果  当前Action使用 -->
    <result name="success">/demo6/result.jsp</result>
</action>
<global-results>
    <!-- 全局结果 当前包中 所有Action都可以用-->
    <result name="success">/demo6/result.jsp</result>
</global-results>
```

###struts2 结果类型

1. 结果类型其实就是一个实现com.opensymphony.xwork2.Result的类，用来输出你想要的结果
2. 在struts-default.xml文件中已经提供了内置的几个结果类型

####chain
转发到另一个动作

<result-type name="chain" class="com.opensymphony.xwork2.ActionChainResult"/>

1. 如果转发的动作在一个名称空间中

```
<action name="testChain1" class="com.itheima.action.CaptchaAction" method="download">
    <result name="success" type="chain">testChain2</result>
</action>
<action name="testChain2" class="com.itheima.action.CaptchaAction" method="download">
    <result name="success" type="dispatcher">/2.jsp<result>
</action>
```

2. 如果转发的动作不在一个名称空间中

```
<package>
    <action name="testChain1" class="com.itheima.action.CaptchaAction" method="download">
        <result name="success" type="chain">
        <!-- 如果需要转发的动作不在一个名称空间内，则需要进行参数的设置（原理看chain源码） -->
        <!-- 源码中有setNamespace和setActionName方法，去掉set，第一个字母改小写 -->
            <param name="namespace">/result1</param>
            <param name="actionName">testChain2</param>
        </result>
    </action>
</package>
<package name="p2" namespace="/result1" extends="base">
    <action name="testChain2" class="com.itheima.action.CaptchaAction" method="download">
        <result name="success" type="dispatcher">/2.jsp<result>
    </action>
</package>
```

####dispatcher
请求转发（地址栏不会变）

<result-type name="dispatcher" class="org.apache.struts2.dispatcher.ServletDispatcherResult" default="true"/>(默认的)

```
<action name="testChain2" class="com.itheima.action.CaptchaAction" method="download">
    <result name="success" type="dispatcher">/2.jsp<result>
    <!-- 这两个设置效果相同 -->
    <!--
        <result name="success" type="dispatcher">
            <param name="location">/2.jsp<result>
        </result>
    -->
</action>
```

####redirectAction
请求重定向到另一个动作

<result-type name="redirectAction" class="org.apache.struts2.dispatcher.ServletActionRedirectResult"/>

```
<action name="testRedirect1" class="com.itheima.action.CaptchaAction" method="download">
    <result name="success" type="redirectAction">testRedirect2</result>
</action> 
```

####redirect
请求重定向（地址栏会变）

<result-type name="redirect" class="org.apache.struts2.dispatcher.ServletRedirectResult"/>

```
<action name="testRedirect" class="com.itheima.action.CaptchaAction" method="download">
    <result name="success" type="redirect">/2.jsp<result>
</action> 
```
####stream
结果类型为流，例如用于文件下载(具体原理需要看源码，而源码的核心就是execute方法） struts.xml的配置(属性参数对应的都是类中的set方法）

<result-type name="stream" class="org.apache.struts2.dispatcher.StreamResult"/>

```
<action name="testStream" class="com.itheima.action.CaptchaAction" method="download">
    <!-- 不需要转向或重定向的页面，因为直接下载就可以了 -->
    <result name="success" type="stream"><!-- 在文档中拷贝以下参数 -->
    <!-- 为了能让所有类型的文件都能下载，在Tomcat/conf/web.xml里面搜索bin，找到以下mapping参数 -->
       <param name="contentType">application/octet-stream</param>
    <!-- 查看Stream对应的类源代码,对应里面的字符串变量，根据这个字符串找输入流-->
       <param name="inputName">imageStream</param>
    <!-- 消息头的设置，指定下载，且指定下载文件名称 -->
       <param name="contentDisposition">attachment;filename="1.jpg"</param>
    <!-- 设置缓存的大小 -->
       <param name="bufferSize">1024</param>
    </result>
</action>
```

实现文件下载
```
public class CaptchaAction {
    //设置一个流，并生成set，get方法。
    private InputStream imageStream;
    public InputStream getImageStream() {
        return imageStream;
    }
    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }
    public String download() throws FileNotFoundException{
        //获得文件的真实路径
        String realPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/111.jpg");
        //获得文件输入流
        imageStream = new FileInputStream(realPath);
        return "success";
    }
    public String method1(){
        try{
            //int i=1/0;//人为制造异常，可以让catch转向全局结果集，error.jsp

return "success";
        }catch(Exception e){
            return "error";
        }
    }
}
```

####plainText
显示指定页面的源代码（不好用，只有java语句才能显示源代码）

<result-type name="plainText" class="org.apache.struts2.dispatcher.PlainTextResult" />

```
<action name="testPlanText" class="com.itheima.action.CaptchaAction" method="showPlanText">
    <result name="success" type="plainText">/1.jsp<result>
</action>
```

####其他结果类型
<result-type name="httpheader" class="org.apache.struts2.dispatcher.HttpHeaderResult"/>
<result-type name="freemarker" class="org.apache.struts2.views.freemarker.FreemarkerResult"/>显示模板。。。需要实验。
<result-type name="velocity" class="org.apache.struts2.dispatcher.VelocityResult"/>显示模板。。。需要实验。
<result-type name="xslt" class="org.apache.struts2.views.xslt.XSLTResult"/>(显示样式？）

####自定义结果类型

如果提供的结果类型不够用，就需要自定义了(注意需要实现Result接口)

自定义完的结果类型，需要先声明，才能使用：（随机验证码图片结果类型，实际应用的案例）

```
<package name="base" extends="struts-default">
    <!-- 配置局部结果视图 -->
    <result-types>
        <result-type name="captchaResults" class="com.itheima.action.CaptchaResults"></result-type>
    </result-types>
    <!-- 配置全局结果视图, 只能配置在package里, 但是可以通过继承来使用 -->
    <global-results>
        <result name="error">/error.jsp</result>
    </global-results>
</package>
<!-- 需继承base,因为自定义的局部结果视图配置在base里面,且base继承了核心配置文件 -->
<package name="p1" namespace="/results" extends="base">
    <action name="captcha" class="com.itheima.action.CaptchaAction" method="genImage">
        <result type="captchaResults" name="success">
            <!-- 调用结果处理类的setter方法，注入参数的值-->
            <param name="width">600</param>
            <param name="height">400</param>
        </result>
    </action>
</package>
```