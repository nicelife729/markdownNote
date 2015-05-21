#Struts2 上传下载

how:企业常用文件上传技术: 
jspSmartUpload(JSP model时代), 
fileupload(apache commons), 
servlet3.0 集成文件上传

##文件上传

how: 真正干活的是 FileUploadInterceptor

```
<s:form enctype="multipart/form-data">
    <s:file name="resource"> </s:file>
</s:form>
```

```
public class UploadAction {
// 如果是多个文件上传, 那么就用定义数组, 如果 File[] resource;
    @BeanProperty private File resource;    // 得到文件
    @BeanProperty private String resourceContentType; // 得到文件类型 MIME
    @BeanProperty private String resourceFileName; // 得到文件的名称
    public String execution(){
        String bashPath = ServletContext.getServletContext().getRealPath("WEB-INF/upload");
        File dir = new File(bashPaht+subPath);
        dir.mkdirs();
        String path  = dir + UUIDName();
        FileUtils.copyFile(resource, new File(dir)); // common-io
        return null;
    }
}
```
配置上传文件信息
```
<!-- 上传出错后, 可以配置input视图 -->
<!-- 设置全局, 文件上传的最大值, 默认大小为2M, 超出大小后, actionError -->
<!-- 错误信息提示 可以根据 struts-messages.properties 来设置 -->
<constant name="struts.multipart.maxSize" value="2097152"> </constant>
<!-- 配置解析技术, jakara或pell  -->
<constant name="struts.multipart.parser" value="cos"> </constant>
<action>
    <interceptor-ref name="defaultStack">
        <!-- 设置局部  -->
        <param name="fileUpload.maximumSize">2097152</param>
        <param name="fileUpload.allowedExtensions">txt,doc,pdf </param>
        <!-- 可以在web.xml中查看, mime类型 -->
        <param name="fileUpload.allowedTypes">application/msword </param>
    </interceptor-ref>
</action>
```

## 文件的下载 ##

```
// http://contextPath/download.action&filename=xx.mp3
/*
<action>
    <result type="stream">
        <!-- 指定下载的文件流  -->
        <param name="inputName"> inputStream </param>
        <param name="ContentType">${contentType}</param>
        <!-- 解析下载文件名字  -->
        <param name="contentDisposition"> <!-- 浏览器打开方式 -->
            attachment;filename=%{#fileName}.txt
        </param>
    </result>
</action>
*/
public class DownloadAction {
    @BeanProperty private InputStream InputStream;
    public String download(){
        String fileName = "";
        String filePath = "";
        String agent = ServletContext.getRequest().getHeader("user-agent");
        String encoded = encodeDownloadFilename(filename, agent);
        // 附件乱码问题,(IE和其他浏览器: URL编码, 火狐: Base64编码)
        ActionContext.getContext().put("fileName", encoded);
        inputStream = new FileInputStream(filePath + fileName);
    }
    // 根据下载文件动态获取 MIME 文件类型
    public String getContentType(){
        return ServletActionContext.getServletContext().getMimeType(filename);
    }
    
    /*
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * @param filename 下载文件名
	 * @param agent 客户端浏览器
	 * @return 编码后的下载附件名
	 * @throws IOException
	 */
     public String encodeDownloadFilename(String filename, String agent) throws IOException{
        if(agent.contains("Firefox")){ // 火狐浏览器
            filename = "=?UTF-8?B?"+new BASE64Encoder().encode(filename.getBytes("utf-8"))+"?=";
        }else{ // IE及其他浏览器
            filename = URLEncoder.encode(filename,"utf-8");
        }
        return filename;
    }
}
```


# 访问 struts2 静态资源 #
文档中介绍了，可以把静态资源放到org.apache.struts2.static或者template包中，
可以直接访问，例如 访问template.aaa中的bbb.css，则
`http://localhost:8080/day22_03_strutsStatics/struts/aaa/bbb.css`

或者自己对静态资源访问的地址进行设置，在web.xml中设置
```
<!-- 则访问com.itheima.statics中的资源ccc.css，的地址为 -->
<!-- http://localhost:8080/day22_03_strutsStatics/struts/ccc.css -->
<filter>
    <filter-name>struts2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
    <init-param>
        <param-name>packages</param-name>
        <param-value>com.itheima.statics</param-value>
    </init-param>
</filter>
```

# struts2 数据存储和显示 #
OGNL

#值栈
what: 接口, 是struts2 提供的一个接口, 实现类是`OgnlValueStack`,

      OGNL 是从值栈中获取数据的, 每个Action实例都有一个ValueStack 对象(一次请求,一个ValueStack对象)
      在其中保存当前Action对象保存名为 'struts.valueStack'的请求属性中, request中

st:
值栈由两部分组成 

1. ObjectStack: Struts把动作和相关对象压入 ObjectStack(List) 中

2. ContextMap: Struts把各种各样的映射关系(一些 Map 类型的对象) 压入 ContextMap 中
    Struts 会把下面这些映射压入 ContextMap 中
    parameters: 该 Map 中包含当前请求的请求参数
    request: 该 Map 中包含当前 request 对象中的所有属性
    session: 该 Map 中包含当前 session 对象中的所有属性
    application:该 Map 中包含当前 application  对象中的所有属性
    attr: 该 Map 按如下顺序来检索某个属性: request, session, application

how:
OGNL表达式, 访问root中数据时 不需要 '#', 访问 request、 session、application、 attr、 parameters 对象数据 必须写 #

##值栈对象的创建, ValueStack 和 ActionContext 是什么关系?

值栈对象 是请求时 创建的 
doFilter中`prepare.createActionContext(request, response)`
    * 创建ActionContext 对象过程中，创建 值栈对象ValueStack 
    * ActionContext对象 对 ValueStack对象 有引用的(在程序中 通过 ActionContext 获得 值栈对象)
Dispatcher类 serviceAction 方法中 将值栈对象保存到 request范围
    request.setAttribute(ServletActionContext.STRUTS_VALUESTACK_KEY, proxy.getInvocation().getStack());

##ValueStack

why:解决 Action 向 JSP 传递数据的问题

在struts2中所有的数据都在 ValueStack 中(核心)

st:
ValueStack里面有两个东西
1. 一个是根,就是CompoundRoot，这是一个List集合，
2. 还有一个contextMap

###生命周期：ValueStack 的生命周期是一次请求, 被存在 request 域"struts.ValueStack"中
###获得 ValueStack 有三种方式
```
public class ValueStackAction extends ActionSupport {
    public String testValueStack() {
        ValueStack vs1 = ActionContext.getContext().getValueStack();
        ValueStack vs2 = ServletActionContext.getContext().getValueStack();
        ValueStack vs3 = ServletActionContext.getRequest().getAttribute("struts.ValueStack");
        return "";
    }
}
```

###值栈在开发中应用

Action 向JSP 传递数据处理结果 ，结果数据有两种形式

1. 消息 String类型数据
```
// 针对某一个字段错误信息(常用于表单校验)
this.addFieldError("msg", "字段错误信息");
// 普通错误信息，不针对某一个字段 登陆失败
this.addActionError("Action全局错误信息");
// 通用消息
this.addActionMessage("Action的消息信息");

// 在jsp中使用 struts2提供标签 显示消息信息
// <s:fielderror fieldName="msg"/>
// <s:actionerror/>
// <s:actionmessage/>
```
2. 数据(复杂类型数据), 使用值栈valueStack.push(products)

####哪些数据默认会放入到值栈?

- 每次请求，访问Action对象 会被压入值栈, DefaultActionInvocation 的 init方法 stack.push(action);
Action如果想传递数据给 JSP，只有将数据保存到成员变量，并且提供get方法就可以了

- 如果Action 实现ModelDriven接口，值栈默认栈顶对象 就是model对象
```
<!-- 在拦截器中 ，将model对象 压入了 值栈 stack.push(model); -->
<interceptor name="modelDriven" class="com.opensymphony.xwork2.interceptor.ModelDrivenInterceptor"/>
```

# struts2显示(ognl)

ognl: struts2的标签, 把 valueStack 中的数据显示到页面上

常见符号 # % $的使用

结论: #用于写ognl表达式获取数据,% 控制ognl表达式是否解析, $ 用于配置文件获取值栈的数据
```
<!-- 用法一： 结合struts2 表单表单使用， 通过%通知struts，
     %{}中内容是一个OGNL表达式，进行解析  -->
<s:textfield name="username" value="%{#request.username}"/>
<!-- 用法二： 设置ognl表达式不解析 %{'ognl表达式'} -->
<s:property value="%{'#request.username'}"/>
$的使用
用法一: 用于在国际化资源文件中，引用OGNL表达式
<!-- 在properties文件 msg=欢迎您, ${#request.username} -->
 <!-- 自动将值栈的username 结合国际化配置信息显示  -->
<s:i18n name="messages">
    <s:text name="msg"></s:text>
</s:i18n>
用法二 ：在Struts 2配置文件中，引用OGNL表达式
<!-- 在Action 提供 getContentType方法 -->
<!-- 读取值栈中contentType数据,在Action提供 getContentType -->
<!-- 因为Action对象会被压入值栈,contentType是Action属性,从值栈获得 -->
<param name="contentType">${contentType}</param>
```

https://raw.githubusercontent.com/zhpooer/blog/master/_posts/%E4%BC%A0%E6%99%BA%E6%92%AD%E5%AE%A2day30-struts%E5%93%8D%E5%BA%94%E6%95%B0%E6%8D%AE.md
https://github.com/zhpooer/blog/blob/master/_posts/%E4%BC%A0%E6%99%BA%E6%92%AD%E5%AE%A2day31-struts%E6%8B%BE%E9%81%97.md