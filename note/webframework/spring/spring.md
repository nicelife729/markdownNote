#Spring概念

what：是分层的 JavaSE/EE 一站式轻量框架

服务器三层架构: web层, 业务层, 持久层

  - web层: Servlet + Jsp, Struts框架，SpringMvc
  - 持久化技术: JDBC 接口, Hiberante框架，mybatis
  - 业务层技术: EJB(复杂), Spring 框架(取代EJB)

Spring 框架提供了 web层(SpringMVC), 业务层 IoC, AOP, 和事务管理, 持久层JcbcTemplate,

char：
  - 方便解耦，简化开发
  
  Spring就是一个大工厂，可以将所有对象创建和依赖关系维护，交给Spring管理
  
  - AOP编程的支持
  
  Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能
  
  - 声明式事务的支持
  
  只需要通过配置就可以完成对事务的管理，而无需手动编程
  
  - 方便程序的测试
  
  Spring对Junit4支持，可以通过注解方便的测试Spring程序
  
  - 方便集成各种优秀框架
  
  Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架（如：Struts、Hibernate、MyBatis、Quartz等）的直接支持
  
  - 降低JavaEE API的使用难度
  
  Spring 对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低
  
##Spring体系结构

从Spring的规范文档中

核心技术 IoC 和 AOP , 测试
数据访问 (持久层解决方案), 事务管理, JDBCTemplate, 其他ORM框架整合
Web层解决方案 SpringMVC
集成(WebService, JavaMail, JMS, 任务调度, 缓存)  

#IOC和DI开发
下载依赖包和开发包, 最新版本是 4.1, 课程使用3.2
导入Jar包,spring-beans, spring-context, spring-core, spring-expression, commons-logging, log4j

IOC: 控制反转, 解决程序对象紧密耦合问题(工厂 + 反射 + 配置文件), 将原来程序自己构造的权利, 交给 IoC容器来构造, 需要一个对象, 找IoC容器获取(对象的构造权被反转)

DI: 依赖注入, IoC需要为程序提供依赖对象, 返回这个对象所依赖的对象, 一同可以提供.


将所有对象交给IoC容器(Spring)来管理

通过 applicationContext 配置 Spring 管理对象

在程序中通过 ApplicationContext 接口获取工厂对象

 - ClassPathXmlApplicationContext, 读取 src下的配置文件
 - FileSystemXmlApplicationContext, 读取 WEB-INF 下的配置文件

DI是IoC的延伸

```
<!-- applicationContext.xml -->
<!-- 查找文档, 添加 xsd 约束文件 -->
<beans>
    <bean id="userServlet" class="zhpooer.UserServlet">
        <!-- name: 属性对应对象中 setXXX方法  -->
        <!-- ref: IoC引用bean的id -->
        <property name="userService" ref="userService"/>
    </bean>
    <bean id="userService" class="zhpooer.UserServiceImpl"/>
    <bean id="userDao" class="zhpooer.UserDaoImpl"/>
</beans>
```

```
// web层 
public class UserServlet {
    // 方式三, DI, 依赖注入,
    // (Spring 在构造 UserServlet 对象时, 同时提供它所依赖的对象)
    @BeanProperty private UserService userService;

    public void regist() {
        println("表现层, 添加用户");

        // 方式一: 传统做法
        UserService userService = new UserService();

        // 方式二: IOC, 通过工厂
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServlet = applicationContext.getBean("userService");

        userService.regist();
    }
}
// 业务逻辑层接口
public interface UserService {
    void regist();
}

public class UserServiceImpl implements UserService {
    @Override public void regist(){
        println("业务层, 添加用户");

        // 传统做法
        UserDao userDao = new UserDao()
        userDao.saveUser();
    }
}

// 持久层接口
public interface UserDao {
    void saveUser();
}
public class UserDaoImpl implements UserDao {
    public void saveUser(){ println("持久层, 添加用户");}
}

// 测试案例
@Test public void testRegist(){
    UserServlet us = new UserServlet();
    us.regist();
}
@Test public void testIoC(){
    // 从Ioc 容器获得对象
    // 1. 获取Ioc工厂, 每一次都会产生新的Spring工厂
    ApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml");
    // 2. 从IoC的容器工厂, 获取需要对象(根据bean的id)
    UserServlet userServlet = applicationContext.getBean("userServlet");
}
```

#Spring对象工厂
[![](/note/spring/image/spring_beanfac.png)](spring)
ApplicationContext 是 BeanFactory 的子接口, BeanFactory 才是 Spring 框架最核心接口.

ApplicationContext 为提供了 BeanFactory 更多扩展, 企业不常用 BeanFactory
- 国际化处理
- 事件传递
- Bean自动装配
- 各种不同应用层的Context实现

ApplicationContext 在容器初始化时, 对其中管理Bean对象进行创建, Bean对象在获取对象时, 才进行Bean对象初始化
```
BeanFactory beanFactory =
    new XmlBeanFactory(new ClassPathResource("ApplicationContext.xml"));
beanFactory.getBean("userService");
```

#IoC 容器装配 Bean

##Spring 提供配置Bean 三种实例化方式

1. 使用类构造器实例化(默认无参数)
```
<bean id="bean1" class="cn.itcast.spring.b_instance.Bean1"></bean>
```
2使用静态工厂方法实例化(简单工厂模式)
```
// <bean id="bean2" class="cn.itcast.spring.b_instance.Bean2Factory"
//      factory-method="getBean2"></bean>
public class Bean2Factory {
    public static Bean2 getBean2(){
        return new Bean2();
    }
}
```
3. 使用实例工厂方法实例化(工厂方法模式)
```
<bean id="bean3Factory" class="cn.itcast.spring.b_instance.Bean3Factory"></bean>
<bean id="bean3" factory-bean="bean3Factory" factory-method="getBean3"></bean>
```
应用场景, 大多数情况, 可以通过构造器实例化 , 如果构造过程很复杂, 可以用工厂实例化方式

##Bean 作用域
<bean>元素scope属性

- scope="singleton" 单例 ，在Spring IoC容器中仅存在一个Bean实例(默认的scope)
- scope="prototype" 多例 ，每次从容器中调用Bean时，都返回一个新的实例，即每次调用getBean()时 ，相当于执行new XxxBean()
- scope="request" 用于web开发，将Bean放入request范围 ，request.setAttribute("xxx") ， 在同一个request 获得同一个Bean
- scope="session" 用于web开发，将Bean 放入Session范围，在同一个Session 获得同一个Bean
- scope="globalSession" 一般用于Porlet应用环境 , 分布式系统存在全局session概念 ，如果不是porlet环境，globalSession 等同于Session
在开发中主要使用 scope="singleton"、 scope="prototype

##Bean的生命周期

Bean的完整生命周期(十一步骤)：
1. instantiate bean对象实例化 
2. populate properties 封装属性 
3. 如果Bean实现BeanNameAware 执行 setBeanName 
4. 如果Bean实现BeanFactoryAware 或者 ApplicationContextAware 设置工厂 setBeanFactory 或者上下文对象 setApplicationContext 
5. 如果存在类实现 BeanPostProcessor（后处理Bean） ，执行postProcessBeforeInitialization 
6. 如果Bean实现InitializingBean 执行 afterPropertiesSet 
7. 调用<bean init-method="init"> 指定初始化方法 init 
8. 如果存在类实现 BeanPostProcessor（处理Bean） ，执行postProcessAfterInitialization 
9. 执行业务处理 
10. 如果Bean实现 DisposableBean 执行 destroy 
11. 调用 指定销毁方法 customerDestroy


配置 Spring Bean 初始化和执行方法
```
<!-- 初始化方法, 要无返回值, 和无参数 -->
<bean init-method="setup" destory-method="teardown"></bean>
``

- destroy-method 只对 scope="singleton" 有效
- 销毁方法，必须关闭ApplicationContext对象，才会被调用
```
ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
applicationContext.close();
```

##后处理器
使用BeanPostProcessor 就是钩子函数，作用用来对Bean对象进行扩展, 可以实现动态代理, 是AOP的核心
```
// 每一个对象创建都会执行
public class MyBeanPostProcessor implements BeanPostProcessor {
   // bean: Spring容器创建的 duix
   // beanName, 对象的ID
   public Object postProcessAfterInitialization(Object bean, String beanName){
       // 后处理器, 执行后
       return new Proxy().newProxyInstance();
   }
   public Object postProcessBeforeInitialization(Object bean, String beanName){
       // 后处理器,执行前
   }
}
//<bean class="MyBeanPostProcessor"></bean>, 不需要写id
```

#Spring 依赖注入（DI）

为Bean设置一个属性, 三种注入属性的方式

1. 构造器注入
2. setter 注入

3. 接口注入
```
public interface Injection{
    public void injectName(String name);
}
public class User implements Injection{
    public void injectName(String name){
        this.name = name;
    }
}
```


Spring 只支持 
构造器注入, 和 setter方法注入 

1. 构造器注入
```
<!--
public class Car { 
    public Car(String name, double price){}
}
-->
<bean id="car" class="zhpooer.Car">
    <!-- 对象的第一个参数是Stirng -->
    <constructor-arg index="0" type="java.lang.String" value="保时捷"></constructor-arg>
    <constructor-arg index="1" type="double" value="1000"></constructor-arg>
</bean>
```
2. setter注入
```
<!--  
public class Car{
    @BeanProperty public String name;
    @BeanProperty public String price;
}
public class Employee{
    @BeanProperty public String name;
    @BeanProperty public Car car; 
}
-->
<!-- 使用 ref 属性, 可以注入复杂对象 -->
<bean id="car2" class="zhpooer.Car">
    <property name="name" value="宝马"></property>
    <property name="price" value="1000"></property>
</bean>
<bean id="emplayee" class="zhpooer.Employee">
    <property name="name" value="zhangsan"> </property>
    <property name="car" ref="car2"> </property>
</bean>
```

##名称空间 p

Spring 2.5新特性, 简化属性注入
```
<!-- p:<属性名>="xxx" 引入常量值 -->
<!-- p:<属性名>-ref="xxx" 引用其它Bean对象 -->
<bean xmlns:p="http://www.springframework.org/schema/p">
    <bean id="car2" class="zhpooer.Car" p:name="宝马" p:price="1000"/>
    <bean id="employee" class="zhpooer.Emplayee" p:name="lisi" p:car2-ref="car2"/>
</bean>
```

##spEL 表达式

1. 完成对象之间注入
```
<property name="car2" ref="car2"></property>
<!-- 改为 -->
<property name="car2" value="#{car2}"></property>
```

2. 应用其他对象属性
```
<bean id="carInfo" class="cn.itcast.spring.e_di.CarInfo"></bean>
<bean id="car2_2" class="cn.itcast.spring.e_di.Car2">
    <property name="name" value="#{carInfo.name}"></property>
</bean>
```
3. 调用其他bean的方法

```
<bean id="carInfo" class="cn.itcast.spring.e_di.CarInfo"></bean>
<bean id="car2_2" class="cn.itcast.spring.e_di.Car2">
    <property name="name" value="#{carInfo.name}"></property>
    <property name="price" value="#{carInfo.caculatePrice()}"></property>
</bean>
```
##集合属性的注入

```
<!--  
public class CollectionBean{
     @BeanProperty private List<String> hobbies;
     @BeanProperty private Set<Integer> numbers;
     @BeanProperty private Map<String, String> map;
     @BeanProperty private Properties properties;
}
 -->
<bean id="collectionBean" class="zhpooer.CollectionBean">
    <!-- List注入 -->
    <property name="hobbies">
        <!-- 数组也是这么配置 -->
        <list>
           <!-- <ref></ref>, 表示引用 -->
            <value>学习</value>
        </list>
    </property>
    <!-- Set 注入 -->
    <property name="numbers">
        <set>
            <value>10 </value>
        </set>
    </property>
    <!-- Map 注入 -->
    <property name="map">
       <map>
           <entry key-ref=""/> </entry>
           <entry key="name" value="神"> </entry>
       </map>
    </property>
    <!-- Properties 注入 -->
    <property name="properties">
        <props>
            <prop key="company"> 传智播客 </prop>
        </props>
    </property>
</bean>
```
##多个XML配置文件

分开配置, 便于管理

1. 并列引入多个XML
```
ApplicationContext context =
    new ClassPathXmlApplicationContext("bean1.xml", "bean2.xml");
```
2. 引入总xml文件，在总xml文件引入 子xml文件, 常用
```
<!-- applicationContext.xml -->
<import resource="classpath:bean1.xml"/>
<import resource="classpath:bean2.xml"/>
```

##注解配置 IoC
@Component 描述 Bean
```
@Component("helloService")
// <bean id="helloService" class="..."/>
public class HelloService{
    public void SayHello(){
        println("");
    }
}
```
applicationContext.xml

```
<!-- 引入context描述文件 -->
<beans xmln:context="http://www.springframework.org /schema/context">
    <!-- 使Spring扫描到bean -->
    <context:component-scan base-package="io.zhpooer"></context:component-scan>
</beans>
```

spring2.5 引入@Component 等效三个衍生注解

@Repository 用于对DAO实现类进行标注 (持久层)
@Service 用于对Service实现类进行标注 (业务层)
@Controller 用于对Controller实现类进行标注 (表现层)

##属性依赖注入
```
@Service("userService")
public class UserService{
    // 1. 简单属性注入, set 方法都可以不用
    @Value("itcast")
    private String name;

    // 2. 复杂属性注入, 根据类型自动注入
    @Autowired 
    private UserDao userDao;

    // 3. 复杂属性注入, 结合 Qualifier, 根据名字注入
    @Autowired(required=false)  // required默认true, 若注册不成功,或报错
    @Qualifier("userDao")
    private UserDao userDao;

    // 4. @Resource和@Autowired注解功能相似, 是JSR标准
    @Resource(name="userDAO")
    private UserDAO userDAO ;
}
@Repository("userDao")
public class UserDao{
   public void saveUser();
}
```
WARN: @Value @Autowired 注解都可以修饰 成员变量 或者 setter方法, 如果修改成员变量，不需要提供setter方法

##初始化销毁
指定Bean的初始化方法和销毁方法(注解) <bean init-method="" destroy-method="" />

 - @PostConstruct 作用 init-method
 - @PreDestroy 作用 destroy-method
```
public class LifeCycleBean{
    @PostConstruct public void init(){}
    @PreDestroy public void destroy(){}
}
```

##Bean作用范围
Bean的作用范围 <bean scope="" />
  - @Scope 注解 ，默认作用域 singleton 单例
```
@Service
@Scope("prototype")
public class ScopeBean{}
```

##Spring3.0 注解
JavaConfig: 以一个Java类做配置文件

- @Configuration 指定POJO类为Spring提供Bean定义信息
- @Bean 提供一个Bean定义信息

```
public class Car {
    private String name;
    private double price;
}

public class Product {
    private String pname;
    private int pnum;
}

// 配置Bean, (工厂)
// 获得两个Bean, 相当于获取配置文件声明两个Bean
@Configuration
public class BeanConfig {
    @Bean(name="car")
    public Car initCar(){
        Car car = new Car();
        car.setName("大宗");
        return car;
    }
    @Bean(name="product")
    public Product showProduct(){
        Product p = new Product();
        return p;
    }
}
// 配置自动扫描, 让文件被扫描到
```

##xml和注解混合使用
很多企业开发者 还是采用 xml作为主流配置

- Bean 注册 通过XML完成
- 注入使用 @Autowired 注解完成

```
public class CustomerDao {}

public class OrderDao {}

public class CustomerService {
    @Autowired
    private CustomerDao cDao;
    @Autowired
    private OderDap oDao;
}
```
```
<beans>
    <bean id="customerDao" class="zhpooer.CustomerDao"> </bean>
    <bean id="orderDao" class="zhpooer.OrderDao"> </bean>
    <bean id="customerSerivce" class="zhpooer.CustomerService"> </bean>
    <!-- 启用四个注解 -->
    <!-- @Resource、@ PostConstruct、@ PreDestroy、@Autowired -->
    <context:annotation-config/>
</beans>
```

结论
1. xml配置 和 注解配置 效果完全相同
2. 如果Bean 来自第三方， 必须使用xml
3. Spring3.0 Bean注册方式， 使用比较少， 主要用于Bean 构造逻辑及其复杂

## web spring 整合开发

直接在Servlet 加载Spring 配置文件
```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
HelloService helloService = (HelloService) applicationContext.getBean("helloService");
helloService.sayHello();
```
每次请求都会加载Spring环境，初始化所有Bean ，性能问题 ！！！
* 解决方案一 ： 将代码放入Servlet init 中 ， 无法保证所有Servlet都能使用 ApplicationContext
* 解决方案二 ： ServletContext, tomcat启动时， 加载Spring配置文件，获得对象 ，放入ServletContext

1. 导入spring-web.jar
2. 配置web.xml
```
 <!-- 保存 ContextLoaderListener 完成在Servlet初始化阶段，
      加载Spring配置文件，将工厂对象放入 ServletContext -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener> 
```
3. 默认读取 WEB-INF/applicationContext.xml
```
<!-- 配置 全局参数 contextConfigLocation 指定 配置文件位置 -->
<context-param>
    <param-name>contextConfigLocation</param-name>
	<param-value>classpath:applicationContext.xml</param-value>
</context-param>
```
4. 在servlet中获取 Spring 容器
```
// 方式一:
WebApplicationContext applicationContext =
    getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
// 方式二: 
WebApplicationContext applicationContext =
    WebApplicationContextUtils.getWebApplicationContext(getServletContext());

```
# 整合Junit #

导入 spring-test.jar

```
@RunWith(SpringJUnit4ClassRunner.class)
// 指定配置文件位置
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class HelloServiceTest{
    @Autowired
	private HelloService helloService; // 注入需要测试对象
}
```

#AOP概念
思想：横向抽取代替纵向继承
Aspect Oriented Programing 面向切面编程, AOP是对OOP(面向对象编程) 思想的延伸,
AOP采取横向抽取机制，取代了传统纵向继承体系重复性代码(性能监视、事务管理、安全检查、缓存)

原理：代理
传统，继承父类，实现代码复用
aop，使用代理机制，将复用代码放入代理中

##AOP底层原理

1. JDK动态代理

原理： 针对内存中Class对象，使用类加载器 动态为目标对象实现接口的创建代理类
缺点：只能针对接口进行代理（为目标对象接口生成代理对象, 对于不使用接口的业务类, 无法使用JDK动态代理）

2. CGLIB
what:是一个强大的,高性能,高质量的Code生成类库。可以在运行期扩展Java类与实现Java接口

原理：CGlib采用非常底层字节码技术, 可以为一个类创建子类, 解决无接口代理问题

```
public ProductDao createCglibProxy(){
    // 创建代理的核心对象
    Enhancer enhancer = new Enhancer();
    // 设置被代理类, 为类创建子类
    enhancer.setSuperclass(productDao.getClass());
    enhancer.setCallback( new MethodInterceptor(){
        public Object intercept(Object proxy, Method method, Object[] rags, MethodProxy methodProxy){
            // 为 addProduct 计算运算时间
            if (method.getName().equals("addProduct")) {// 当前执行方法
                long start = System.currentTimeMillis();
                Object result = methodProxy.invokeSuper(proxy, args);
                long end = System.currentTimeMillis();
                System.out.println("addProduct方法运行时间 : " + (end - start));
                return result;
            } else {
                // 不进行增强
                return methodProxy.invokeSuper(proxy, args);
            }
        }
    });
    // 返回代理
    return (ProductDao) enhancer.create();
}
```
结论

程序中应优先对接口创建代理，便于程序解耦维护

- 若目标对象实现了若干接口.spring使用JDK的java.lang.reflect.Proxy类代理
- 若目标对象没有实现任何接口.spring使用CGLIB库生成目标对象的子类

##传统Spring AOP

AOP 开发规范: AOP联盟为通知Advice定义了org.aopalliance.aop.Interface.Advice

Spring AOP 实现 AOP 联盟定义的规范

相关术语

- Joinpoint(连接点), 在代理过程中, 可以被拦截的点(指方法)
- Pointcut(切入), 要对哪些Jointpoint进行拦截定义, 指定拦截(个别方法)
- Advice(通知, 建议), 增强的代码逻辑(日志记录), 方法级别
- Introduction(引介), 特殊类型Advice, 对原有的类对象添加一个新的属性或者方法
- Target, 被代理对象
- Weaving(织入), 把增强应用到目标对象, 来创建对象的过程
- Proxy(代理), 一个类被AOP织入增强后，就产生一个结果代理类
- Aspect(切面), 是切入点(Joinpoint)和通知(Advice)的结合, 多个切点和多个通知组成

传统Spring AOP提供五类 Advice: 

1. 前置通知(代码增强) org.springframework.aop.MethodBeforeAdvice * 在目标方法执行前实施增强 
2. 后置通知 org.springframework.aop.AfterReturningAdvice * 在目标方法执行后实施增强 
3. 环绕通知 org.aopalliance.intercept.MethodInterceptor * 在目标方法执行前后实施增强 
4. 异常抛出通知 org.springframework.aop.ThrowsAdvice * 在方法抛出异常后实施增强 
5. 引介通知 org.springframework.aop.IntroductionInterceptor （课程不讲 了解） * 在目标类中添加一些新的方法和属性

### Advisor

what:Advisor 就是对 PointCut 应用 Advise, 指一个 Point 和一个 Advise

类型：

Advisor : 代表一般切面, Advice本身就是一个切面, 对目标类所有方法进行拦截(没有切点)
PointcutAdvisor : 代表具有切点的切面, 可以指定拦截目标类哪些方法
IntroductionAdvisor : 代表引介切面，针对引介通知而使用切面(不重要)

### 普通 Advisor
使用Advice作为一个切面, 不定义切点, 拦截目标类所有方法
1. 导入jar包, `spring-aop.jar`, `com.springsource.org.aopalliance.jar`
2. 被代理接口和实现类
```
public interface CustomerDao{ public void save();}
public class CustomerDAOImpl implements CustomerDao{ public void save(){};}
```
3. 编写前置增强
```
public class MyMethodBeforeAdvice implements MethodBeforeAdvice {
    public void before(Method method, Object[] args, Object target){
        pring("方法前")
    }
}
```
4. 为目标对象配置代理
```
<beans>
    <bean id="customerDao" class="zhpooer.CusotmerDao"> </bean>
    <bean id="mybeforeadvice" class="zhpooer.MyMethodBeforeAdvice"> </bean>
    <!-- 使用代理工厂类, 创建代理 -->
    <bean id="customerDAOProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <!--
        target : 代理的目标对象
        proxyInterfaces : 代理要实现的接口, 如果多个接口可以使用以下格式赋值
        proxyTargetClass : 是否对类代理而不是接口，设置为true时，使用CGLib代理
        interceptorNames : 需要织入目标的Advice
        singleton : 返回代理是否为单实例，默认为单例
        optimize : 当设置为true时，强制使用CGLib
        -->
        <!-- 目标 -->
        <property name="target" ref="customerDAO"></property>
        <!-- 针对接口代理,如果不用接口代理, 可以不写 -->
        <property name="proxyInterfaces" value="cn.itcast.aop.c_advisor.CustomerDAO"></property>
        <!-- 增强 
            interceptorNames 表示可以运用多个 Advice, 必须写value
            value 引用增强的名字
        -->
        <property name="interceptorNames" value="mybeforeadvice"></property>
    </bean>
</beans>
```
5. 使用代理
```
CustomerDao dao = context.getBean("customerDAOProxy");
dao.save();
```

### PointcutAdvisor
带有切点的切面, 指定被代理对象哪些方法会被增强
- JdkRegexpMethodPointcut 构造正则表达式切点
- 使用正则表达式 切点切面 org.springframework.aop.support.RegexpMethodPointcutAdvisor

1. 创建被代理接口和对象 `public class OrderDaoImpl implements OrderDAO{}`
2. 环绕代码增强
```
class MyMethodInterceptor extends MethodInterceptor{}
```
3. 配置
```
<beans>
    <bean id="OrderDao" class="zhpooer.OrderDaoImpl"> </bean>
    <bean id="mymethodinterceptor" class="zhpooer.MyMethodInterceptor"> </bean>
    <!-- 定义切点切面 -->
    <bean id="myadvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
        <!-- 正则表达式规则 -->
        <!-- pattern: zhooer\.OrderDao\.add.* -->
        <property name="pattern" value=".*"></property>
        <!-- 多个规则 -->
        <property name="patterns" value="*add, *delete"></property>
        <property name="advice" ref="mymethodinterceptor"></property>
    </bean>
    <!-- 创建代理 -->
    <bean id="orderDAOProxy" class="org.springframework.aop.framework.ProxyFactoryBean" >
        <!-- 目标 -->
        <property name="target" ref="orderDAO"></property>
        <!-- 针对类代理 -->
        <property name="proxyTargetClass" value="true"></property>
        <!-- 增强 -->
        <property name="interceptorNames" value="myadvisor"></property>
	</bean>
<beans>
```

### 自动代理

使用ProxyFactoryBean 创建代理，需要为每个Bean 都配置一次, 非常麻烦

自动代理和ProxyFactoryBean本质区别:
	ProxyFactoryBean, 先有被代理对象, 传递ProxyFactoryBean, 创建代理 
	自动代理, Bean构造过程中, 使用后处理Bean 创建代理, 返回构造完成对象就是代理对象 

自动代理原理： 根据xml中配置advisor的规则，得
知切面对哪个类的哪个方法进行代理 (切面中本身就包含 被代理对象信息) ,
就不需要ProxyFactoryBean ，使用BeanPostProcessor 完成自动代理 

* BeanNameAutoProxyCreator 根据Bean名称创建代理 
* DefaultAdvisorAutoProxyCreator 根据Advisor本身包含信息创建代理
* AnnotationAwareAspectJAutoProxyCreator 基于Bean中的AspectJ 注解进行自动代理

```
<!-- 被代理对象 -->
<bean id="OrderDao" class="zhpooer.OrderDaoImpl"> </bean>
<bean id="customerDao" class="zhpooer.CusotmerDao"> </bean>
<!-- 增强 -->
<bean id="mymethodinterceptor" class="zhpooer.MyMethodInterceptor"> </bean>
<bean id="mybeforeadvice" class="zhpooer.MyMethodBeforeAdvice"> </bean>

<!-- 第一种BeanName自动代理  -->
<!-- 后处理, 不需要配置id -->
<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
    <!-- 对所有DAO结尾Bean 进行代理 -->
    <property name="beanNames" value="*DAO"></property>
    <!-- 增强 -->
    <property name="interceptorNames" value="mymethodinterceptor"></property>
</bean>

<!-- 第二种,基于切面信息自动代理 -->
<!-- 切面 -->
<bean id="myadvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <!-- 切点拦截信息 -->
    <property name="patterns" value="zhpooer\.OrderDAO\.save.*"></property>
    <!-- 增强 -->
    <property name="advice" ref="mybeforeadvice"></property>
</bean>
<!-- 后处理会自动读取切面信息   -->
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"></bean>
```

上述方法问题：使用ProxyFactoryBean 创建代理，需要为每个Bean 都配置一次, 非常麻烦

自动代理和ProxyFactoryBean本质区别: 
ProxyFactoryBean, 先有被代理对象, 传递ProxyFactoryBean, 创建代理 

自动代理, Bean构造过程中, 使用后处理Bean 创建代理, 返回构造完成对象就是代理对象

原理： 根据xml中配置advisor的规则，得 知切面对哪个类的哪个方法进行代理 (切面中本身就包含 被代理对象信息) , 就不需要ProxyFactoryBean ，使用BeanPostProcessor 完成自动代理

自动代理的三种方式：
BeanNameAutoProxyCreator 根据Bean名称创建代理
DefaultAdvisorAutoProxyCreator 根据Advisor本身包含信息创建代理
AnnotationAwareAspectJAutoProxyCreator 基于Bean中的AspectJ 注解进行自动代理


### AspectJ

what: AspectJ是一个面向切面的框架,它扩展了Java语言. AspectJ定义了AOP语法所以它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件.
优点：可以在Bean中直接定义切面

1. 基于注解

1. 导入jar包, `aspectj.weaver.jar`, `spring-aspects.jar`
2. spring配置文件, 需要 aop 名称空间, 
```
<!-- 配置自动代理 -->
<!-- <bean class="... AnnotationAwareAspectJAutoProxyCreator" /> -->
<aop:aspectj-autoproxy/>
```

常用注解

    @Aspect 定义切面 
    通知类型 
    @Before 前置通知，相当于BeforeAdvice
    @AfterReturning 后置通知，相当于AfterReturningAdvice
    @Around 环绕通知，相当于MethodInterceptor
    @AfterThrowing抛出通知，相当于ThrowAdvice
    @After 最终final通知，不管是否异常，该通知都会执行
    @DeclareParents 引介通知，相当于IntroductionInterceptor (不要求掌握)


切点使用指定哪些连接点 会被增强, 通过execution函数，可以定义切点的方法切入

    语法： execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)
	execution(public * *(..)) 匹配所有public修饰符 任何方法 
	execution(* cn.itcast.service.HelloService.*(..))  匹配HelloService类中所有方法 
	execution(int cn.itcast.service.UserService.regist(..))  匹配UserService中int返回类型 regist方法 
	execution(* cn.itcast.dao..*(..))  匹配cn.itcast.dao包下 （包含子包） 所有类 所有方法
	execution(* cn.itcast.dao.*(..)) 不包含子包  
	execution(* cn.itcast.dao.GenericDAO+.*(..))  匹配GenericDAO 所有子类 或者 实现类 所有方法

```
public class UserDao{
    public void save(){}
    public void delete(){}
    public void search(){}
}

// 自定义切面类
@Aspect
// 声明一个切面类
public class MyAspect {

    //方式一 前置通知
    @Before("execution(public * *(..))")
    public void before(){
        // 前置通知不能拦截目标方法执行
    }
    @Before("execution(public * *(..)"))
    public void before(aspactj.Joinpoint joinpoint){
        joinpoint.toString(); // 获得拦截点的信息
    }
    
    // 方式二 后置通知
    @AfterReturning("execution(public * *(..))", returning="returnValue")
    // returnValue 是代理方法参数名, 前后两个参数名必须一致
    public void afterReturning(Object returnValue){
        // 获得方法的返回值
    }

    // 方式三 环绕通知
    @Around("execution()", )
    public Object around (ProceedingJoinPoint pjp){
        // 可以阻止 search 方法执行
        Object result = pjp.proceed();
        return result;
    }

    //方式四 抛出通知 , 出现异常后, 方法得到执行
    @AfterThrowing("execution()", throwing="e")
    public void afterThrowing(Throwable e){
    }

    //方式五 最终通知, 不管代码是不是抛出代码都执行, 可以用来释放资源
    @After("execution()")
    public void after(){
    }
}
// <bean id="userDao" class="zhpooer.UserDao"></bean>
// <bean id="myAspect" class="zhpooer.MyAspect"></bean>
```

## 切点的定义
直接在通知上定义切点表达式, 会造成切点的重复, 工作量大, 不易维护

```
// 切点定义
@Pointcut("execution()")
// 方法名,就是切点名字
private void mypointcut(){}

// 应用切点
@After("MyAspect.mypointcut()")
public void after(){}
```

advisor 和 aspect 区别 ？

	advisor 是 spring 中 aop定义切面，通常由一个切点和一个通知组成
    aspect 是规范中切面 ， 允许由多个切点和 多个通知组成 

2. 基于xml

```
// 被代理对象
public class ProductDao {
    public void sell();
}

public class MyAspect {
    public void before(){
        // 前置增强
    }
    public void afterReturning(Ojbect returnValue){
        // 后置增强
    }
    public Objct around(ProceedingJoinPoint pjp){
        // 环绕增强
    }
}
```

```
<!-- 定义被代理对象 -->
<bean id="productDao" class="ProductDao"></bean>
<!-- 定义切面 -->
<bean id="myAspect" class="MyAspect"></bean>
<!-- 进行AOP配置 -->
<aop:config>
    <aop:aspect ref="myAspect">
        <aop:pointcut expression="exection" id="mypointcut"></aop:pointcut>
        <!-- 配置前置  -->
        <aop:before method="before" pointcut-ref="mypointcut"></aop:before>
        <!-- 后置增强 -->
        <aop:after-returning method="afterReturning" pointcut-ref="mypointcut"
             returning="returnValue"/>
        <!-- 环绕增强  -->
        <aop:around method="around" pointcut-ref="mypointcut"></aop:around>
        <!-- 抛出通知  -->
        <aop:after-throwing method="afterThrowing" throwing="ex"> </aop:after-throwing>
        <!-- 最终通知 -->
        <aop:after method="after" pointcut-ref="mypointcut"></aop:after>
    </aop:aspect>
</aop:config>
```

#Spring事务管理

在Java开发中, 事务管理代码放到业务层

##事务管理 API


1. PlatformTransactionManager 提供事务管理方法, 核心接口

```
  // 提交事务
  void commit(TransactionStatus status);
  // 根据事务定义信息，获得当前状态 
  TransactionStatus getTransaction(TransactionDefinition definition);
  // 回滚事务
  void rollback(TransactionStatus status)
```

2. TransactionDefinition 事务的定义信息(隔离级别, 传播行为, 超时时间, 只读)

  - ISOLATION_xxx 事务隔离级别
  - PROPAGATION_xxx 事务传播行为
  - int getTimeout() 获得超时信息
  - boolean isReadOnly() 判断事务是否只读

3. TransactionStatus 事务具体行为, 每一个时刻点, 事务具体状态信息

关系: PlatformTransactionManager 根据 TransactionDefinition 进行事务管理, 管理过程中事务存在多种状态, 每个状态信息通过 TransactionStatus 表示
  
###PlatformTransactionManager

Spring 为不同的持久化框架提供了不同的PlatformTransactionManager接口实现 , 针对不同的持久层技术, 要选用对应的事务管理器

| 不同平台事务管理器实现 | 说明 |
|-----------------|
| org.springframework.jdbc.datasource.DataSourceTransactionManager  | 使用Spring JDBC或iBatis 进行持久化数据时使用 |
| org.springframework.orm.hibernate3.HibernateTransactionManager |	使用Hibernate3.0版本进行持久化数据时使用 | 
| org.springframework.orm.jpa.JpaTransactionManager	 | 使用JPA进行持久化时使用 |
| org.springframework.jdo.JdoTransactionManager	| 当持久化机制是Jdo时使用 |
| org.springframework.transaction.jta.JtaTransactionManager | 	使用一个JTA实现来管理事务，在一个事务跨越多个资源时必须使用|

###事务的隔离级别

四大特性: ACID, 原子性, 一致性, 隔离性, 持久性

1. 隔离性引发并发问题：脏读 不可重复读, 幻读

  - 脏读 一个事务读取另一个事务 未提交数据
  - 不可重复读 一个事务读取另一个事务 已经提交 update 数据
  - 虚读 一个事务读取另一个事务 已经提交 insert 数据

2. 事务的隔离级别: 为了解决事务隔离性引发的问题

  - DEFAULT 默认级别 mysql REPEATABLE_READ 、 oracle READ_COMMITTED
  - READ_UNCOMMITED 导致所有问题发生
  - READ_COMMITTED 防止脏读、 发生不可重复读和虚读
  - REPEATABLE_READ 防止脏读、不可重复读，发生虚读
  - SERIALIZABLE 防止所有并发问题
  
###事务的传播行为

why：实际开发中, 业务层方法间相互调用, 如在删除客户信息时, 要先删除订单信息    那么删除订单出错,客户要不要删除?

what: 一个业务层事务调用令一个业务层事务, 事务间之间关系如何处理

七种传播行为:

- PROPAGATION_REQUIRED 支持当前事务, 如果不存在 就新建一个
- PROPAGATION_SUPPORTS 支持当前事务, 如果不存在，就不使用事务
- PROPAGATION_MANDATORY 支持当前事务, 如果不存在，抛出异常
- PROPAGATION_REQUIRES_NEW 如果有事务存在, 挂起当前事务, 创建一个新的事务
  - 生成订单, 发送通知邮件, 通知邮件会创建一个新的事务, 如果邮件失败, 不影响订单生成
- PROPAGATION_NOT_SUPPORTED 以非事务方式运行，如果有事务存在，挂起当前事务
- PROPAGATION_NEVER 以非事务方式运行, 如果有事务存在, 抛出异常
- PROPAGATION_NESTED 如果当前事务存在, 则嵌套事务执行
  - 依赖于 JDBC3.0 提供 SavePoint 技术
  - 删除客户 删除订单, 在删除客户后, 设置SavePoint, 执行删除订单, 删除订单和删除客户在同一个事务, 删除订单失败， 事务回滚 SavePoint , 由用户控制是事务提交 还是 回滚
  
###事务管理方式

1. 编程式事务管理

在代码中通过 TransactionTemplate 手动进行事务管理, 在实际开发中很少被用到

  ```
  CREATE TABLE `account` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    `money` double DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
  INSERT INTO `account` VALUES ('1', 'aaa', '1000');
  INSERT INTO `account` VALUES ('2', 'bbb', '1000');
  ```
  
  ```
  <!-- 最全的spring 模板 -->
  <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xmlns:tx="http://www.springframework.org/schema/tx"
      xsi:schemaLocation="http://www.springframework.org/schema/beans 
      http://www.springframework.org/schema/beans/spring-beans.xsd
      http://www.springframework.org/schema/context
      http://www.springframework.org/schema/context/spring-context.xsd
      http://www.springframework.org/schema/aop
      http://www.springframework.org/schema/aop/spring-aop.xsd
      http://www.springframework.org/schema/tx 
      http://www.springframework.org/schema/tx/spring-tx.xsd">
      <!-- 导入外部属性文件, 配置连接池 -->
      <bean id="accountService" class="zhpooer.AccountServiceImpl">
           <property name="accountDao" ref="accountDao"></property>
           <property name="transactionTemplate" ref="transactionTemplate"> </property>
      </bean>
      <bean id="accountDao" class="zhpooer.AccountDaoImpl">
      <!-- 将连接池注入给DAO, JdbcTemplate会自动 创建 -->
          <property name="dataSource" ref="dataSource"></property>
      </bean>
      <!-- 编程式事务管理配置 -->
      <!-- 事务管理模板 -->
      <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
          <property name="transactionManager" ref="tractionManager"> </property>
      </bean>
      <!-- 事务管理器 -->
      <!-- org.springframework.jdbc.datasource.DataSourceTransactionManager 用来管理jdbc事务操作 -->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSrouce"> </property>
      </bean>
  </beans>
  ```
  
  ```
  public class AccountServiceImpl implements AccountService{
      // 自动注入
      private AccountDao accountDao;
      private TransactionTemplate transactionTemplate;
      // 转账
      public void transfer(String outAccount, String inAccount, Double money) {
              // 编程式事务管理, 使用事务模板管理事务
          transactionTemplate.execute(new TransactionCallbackWithoutResult(){
              @Override
              protected void doInTransactionWithoutResult(){
                  accountDao.outMoney(outAccount, money);
                  accountDao.inMoney(inAccount, money);
              }
          });
      }
  }
  
  public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
      public void outMoney(String outAccount, Double money) {
          String sql = "update account set money = money-? where name=?";
          getJdbcTemplate().update(sql, money, outAccount);
      }
      public void inMoney(String inAccount, Double money) {
          String sql = "update account set money = money+? where name=?";
          getJdbcTemplate().update(sql, money, inAccount);
      }
  }
  ```
  
2.声明式事务管理

在配置文件中, 对 Bean 的方法进行事务管理, 基于AOP思想, 无需写代码
1> TransactionProxyFactoryBean
2> tx, 自动代理
3> 注解实现事务管理

1> TransactionProxyFactoryBean
    通过 TransactionProxyFactoryBean 对业务类创建代理, 实现声明式事务管理, 无需修改 Service 代码缺点, 需要为每个Bean 都创建单独代理对象，开发量巨大
    ```
    <!-- 事务管理器 -->
    <bean id="transactionManager" class="">
        <proerty name="dataSource" ref="dataSource">
        </proerty>
    </bean>
    <!-- 为目标Servicee创建代理 -->
    <bean name="accountServiceProxy" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
        <!-- 目标 -->
        <property name="target" ref="accountService"> </property>
        <!-- 针对接口代理 -->
        <property name="proxyInterfaces" value="zhpooer.AccountService"></property>
        <!-- 增强 事务管理 -->
        <property name="transactionManager" ref="transactionManager"></property>
        <!-- 事务管理属性 -->
        <property name="transactionAttributes">
            <props>
               <!-- key 就是方法名  -->
               <!-- value prop格式：PROPAGATION,ISOLATION,readOnly,-Exception,+Exception -->
               <!-- PROPAGATION 事务传播行为 -->
               <!-- ISOLATION, 事务隔离级别 -->
               <!-- readOnly  表示事务是只读的，不能进行修改操作  -->
               <!-- -Exception 发生这些异常事务进行回滚(默认发生任何异常事务都会回滚) -->
                <!-- +Exception 事务将忽略这些异常，仍然提交事务  -->
                <prop key="transfer"> PROPAGATION_REQUIRED, readOnly, +java.lang.ArithmeticException </prop>
            </props>
        </property>
    </bean>
    <!-- 注入代理对象: @Qualifier("accountServiceProxy") -->
    ```
2> tx, 自动代理
    ```
    <!-- 定义事务管理增强 -->
    <tx:advice id="txAdvicd" transaction-manager="transactionManager">
        <tx:attribute>
            <!--
            name="transfer" 事务管理方法名
            isolation="DEFAULT" 默认隔离级别
            qpropagation="REQUIRED"  默认传播行为
            read-only="false"  是否只读
            no-rollback-for=""  发生异常不会滚  类似+Exception
            rollback-for=""  发生异常回滚 类似-Exception
            timeout="-1"  不超时
            -->
            <tx:method name="transfer" isolatioin="DEFAULT" propagation="REQUIRED"
                       read-only="false" timeout="-1"></tx:method>
        </tx:attribute>
    </tx:advice>
    <!-- 使用Aop 进行自动代理 -->
    <aop:config>
        <!-- 定义切点 -->
        <aop:pointcut expression="execution(public * * (..))" id="mypointcut"></aop:pointcut>
        <!-- 定义切面 -->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="mypointcut"></aop:advisor>
    </aop:config>
    ```
3> 注解实现事务管理
      (1)在需要管理的类或者方法添加 @Trasactional
      ```
      // isolation 隔离级别
      // propagation 传播行为
      // readOnly 是否只读
      // noRollbackFor 发生异常不回滚
      // rollbackFor 发生异常回滚
      // timeout 超时时间 -1 不超时
      @Transactional(isolation=Isolation.DEFAULT,propagation=,
                     readyOnly=, noRollbackFor=, rollbackFor, timeout=)
      public void transfer(){}
      ```
      (2)在 applicationContext.xml
      ```
      <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
      ```
      
##延时加载问题


##事务分类
传统上，J2EE开发者有两个事务管理的选择： 
全局 或 本地事务（局部事务）。
全局事务由应用服务器管理，使用JTA。
局部事务是和资源相关的，比如一个和JDBC连接关联的事务

事务种类分为： jdbc事务（局部事务） ibtais事务（局部事务）jta事务（全局事务）



两种事务的比较

    * JDBC事务控制的局限性在一个数据库连接内，但是其使用简单。
    * ibtais事务是面向会话的，使用简单
    * JTA事务的功能强大，事务可以跨越多个数据库或多个DAO，使用也比较复杂  

随着网站(www.jzease.com)的扩大，在三个月的时间里Myslq的表格从四十几张，扩到现在的一百多张，以后肯定还会继续扩大为了更好的管理和运行，着手开始分数据库，这就涉及到跨数据库事务，我们使用的是Tomcat,它本身不支持XA,供我选择的就只有spring+JTA,JTA的实现就只有两种，一种是JOTM，另一种是AtomikosTransactionsEssentials.

1. Transaction 分两种，Local Transaction 和 Global Transaction。 
涉及到一个Connection的Commit，称为Local Transaction。 
涉及到多个Connection的Commit，称为Global Transaction。 
楼主提到的是，Global Transaction. 

2. Global Transaction 需要XA接口（包括在JTA里面）的支持。 

import javax.sql.XAConnection; 
import javax.transaction.xa.Xid; 
import javax.transaction.xa.XAResource; 
import javax.transaction.xa.XAException; 
import javax.transaction.Transaction; 
import javax.transaction.TransactionManager; 

其中的 
javax.sql.XAConnection; 
javax.transaction.xa.Xid; 
javax.transaction.xa.XAResource; 

这些XA接口的实现，需要数据库的JDBC提供。 
数据库本身要支持XA。数据库的JDBC也要提供XA的实现。 

Oracle, Sybase, DB2, SQL Server等大型数据库才支持XA, 支持Global Transaction。 
My SQL 连Local Transaction都支持不好，更别说Global Transation了。 

3. XA需要两阶段提交 -- prepare 和 commit. 
假设有两个Connection, con1, con2, 大体的过程如下， 

Java代码  收藏代码
con1 = XAResouce1.getConnection...  
con2 = XAResouce2.getConnection...  
  
con1 do some thing.  
con2 do some thing.  
after they finish.  
  
pre1 = XAResouce1.prepare();  
pre2 = XAResouce2.prepare();  
  
if( both pre1 and pre2 are OK）{  
XAResouce1 and 2 commit  
}else {  
XAResouce1 and 2 rollback  
}  


前面有人讲了，在XAResouce1 and 2 commit的时候， 
可能XAResouce1 commit() 成功了，XAResouce2 commit()失败了。 
这时候，会抛出一个 “启发式异常”。程序可以处理这个异常。比如，XAResouce.recover()之类。 
但一般情况下，还真没别的办法，需要数据管理员根据数据操作日志 undo所有的操作，或者恢复数据备份。 
有的数据库在进行数据操作的时候，会生成一个“反操作”日志。比如，insert 对 delete, 等。 


