
#错误的回显
this.addActionError("");  // 业务逻辑错误
this.addFieldError("");   // 验证错误

#Action 如何接受请求参数

##struts2 和 MVC 定义关系
  
StrutsPrepareAndExecuteFilter : 控制器
JSP : 视图
Action : 可以作为模型，也可以是控制器

###属性驱动一

Action 本身作为model对象，通过成员setter封装

```
// 用户名  <input type="text" name="username" /> <br/>
public class RegistAction1 extends ActionSupport {
    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
}
```
问题一： Action封装数据，会不会有线程问题 ？

struts2 Action 是多实例 ，为了在Action封装数据(struts1 Action 是单例的)
问题二：在使用第一种数据封装方式，数据封装到Action属性中，不可能将Action对象传递给 业务层

需要再定义单独JavaBean ，将Action属性封装到 JavaBean

###属性驱动二

创建独立model对象，页面通过ognl表达式封装

```
//  用户名  <input type="text" name="user.username" /> <br/> 
public class RegistAction2 extends ActionSupport {
    private User user;
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
```
问题： 谁来完成的参数封装 <interceptor name="params" class="com.opensymphony.xwork2.interceptor.ParametersInterceptor"/>

###模型驱动

使用ModelDriven接口，对请求数据进行封装(主流)

```
// 用户名  <input type="text" name="username" /> 
public class RegistAction3 extends ActionSupport implements ModelDriven<User> {
    private User user = new User(); // 必须手动实例化
    public User getModel() {
        return user;
    }
}
```


struts2 有很多围绕模型驱动的特性, 为模型驱动提供了更多特性 <interceptor name="modelDriven" class="com.opensymphony.xwork2.interceptor.ModelDrivenInterceptor"/>

####模型驱动与属性二驱动区别
模型驱动只能在Action中指定一个model对象，属性驱动二可以在Action中定义多个model对象

###封装复杂类型参数

####Collection 对象

```
 // <input type="text" name="products[0].name" /><br/>
public class ProductAction extends ActionSupport {
    private List<Product> products;
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
```

####Map 对象

```
// <input type="text" name="map['one'].name" /><br/>  =======  one是map的键值
public class ProductAction2 extends ActionSupport {
    private Map<String, Product> map;
    public Map<String, Product> getMap() {
        return map;
    }
    public void setMap(Map<String, Product> map) {
        this.map = map;
    }
}
```

##类型转换
struts2 内部提供大量类型转换器，用来完成数据类型转换问题

- boolean 和 Boolean
- char和 Character
- int 和 Integer
- long 和 Long
- float 和 Float
- double 和 Double
- Date 可以接收 yyyy-MM-dd格式字符串
- 数组 可以将多个同名参数，转换到数组中
- 集合 支持将数据保存到 List 或者 Map 集合

//TODO

##请求数据有效性校验

1. 客户端数据校验: 通过JavaScript 完成校验()改善用户体验，使用户减少出错)
2. 服务器数据校验, 使用框架内置校验功能(struts2 内置校验功能)

###代码校验

what：在服务器端通过编写java代码，完成数据校验

前提：动作类一般要求继承ActionSupport

struts.xml配置文件中,对要验证的动作,需要提供name="input"的结果视图(回显)
```
<action name="RegUser" class="com.itheima.action.UserAction" method="RegUser">
    <result type="dispatcher" name="success">/WEB-INF/pages/main.jsp</result>
    <result type="dispatcher" name="error">/WEB-INF/pages/commons/error.jsp</result>
    <!-- 出现错误时转向的页面：回显 -->
    <result name="input">/WEB-INF/pages/regist.jsp</result>
</action>
 ```
 
1. 针对所有动作进行验证：需要覆盖 public void validate()方法， 方法内部如果不满足要求，调用addFieldError填充信息.

```
@SkipValidation//用在不需要验证的动作方法上
public String RegUserUI() {
    return SUCCESS;
}
 //对所有的动作方法进行校验
public void validate(){
//写你的校验代码ActionSupport里面有addFieldError()方法,把错误信息存起来.
    if(StringUtils.isEmpty(user.getUsername())){
        addFieldError("username", "请输入用户名");//向一个Map中存储错误消息。何时返回input视图，是由该Map中有无信息决定的。
    }
} 
```

2. 针对某个动作方法进行校验 public String regUser(){}
```
public void validateRegUser() {
    // 写你的校验代码
    if (user.getUsername() == null || user.getUsername().equals("")) {
        addFieldError("username", "请输入用户名");
    }
}
```

###配置校验
1. XML配置校验(主流)
2. 注解配置校验

//TODO

####编写一个自定义校验器
//TODO

###国际化信息显示

what：同一款软件 可以为不同用户，提供不同语言界面

how:需要一个语言资源包(很多properties文件，每个properties文件 针对一个国家或者语言 ，通过java程序根据来访者国家语言，自动读取不同properties文件)

//TODO
https://github.com/zhpooer/blog/blob/master/_posts/%E4%BC%A0%E6%99%BA%E6%92%AD%E5%AE%A2day29-struts%E8%AF%B7%E6%B1%82%E6%95%B0%E6%8D%AE%E5%A4%84%E7%90%86.md
