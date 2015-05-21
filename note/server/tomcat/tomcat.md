#Tomcat调优

1.在Server.xml中Connector配置如下： 

个人解释：
在现网环境中在Connector中的protocol一定要配置成apr，对性能的提升有很大帮助。目前还没有掌握确切的性能提升百分比，后期补充上测试数据。
官方对protocol解释：
Sets the protocol to handle incoming traffic. The default value is HTTP/1.1 which uses an auto-switching mechanism to select either a non blocking Java based connector or an APR/native based connector. If the PATH (Windows) or LD_LIBRARY_PATH (on most unix systems) environment variables contain the Tomcat native library, the APR/native connector will be used. If the native library cannot be found, the non blocking Java based connector will be used. Note that the APR/native connector has different settings for HTTPS than the Java connectors.

个人解释：
对于maxThreads 和 acceptCount 以及 maxConnections 官方文档都有详细的说明，maxThreads则是Tomcat最多可以创建的线程数， 如果创建的线程数达到最大值，则把请求缓存到 acceptCount队列中。如果线程已满，队列已满，那么其他请求进来会收到连接拒绝的响应。

官方英文解释：
Each incoming request requires a thread for the duration of that request. If more simultaneous requests are received than can be handled by the currently available request processing threads, additional threads will be created up to the configured maximum (the value of the maxThreads attribute). If still more simultaneous requests are received, they are stacked up inside the server socket created by the Connector, up to the configured maximum (the value of the acceptCount attribute). Any further simultaneous requests will receive "connection refused" errors, until resources are available to process them.

export JAVA_OPTS="-server -Xms512m -Xmx4096m  -XX:PermSize=64M -XX:MaxPermSize=512m"
			

--------------------

1. JVM

1.1. 使用 Server JRE 替代JDK。

服务器上不要安装JDK，请使用 Server JRE. 服务器上根本不需要编译器，代码应该在Release服务器上完成编译打包工作。

理由：一旦服务器被控制，可以防止在其服务器上编译其他恶意代码并植入到你的程序中。

1.2. JAVA_OPTS

export JAVA_OPTS="-server -Xms512m -Xmx4096m  -XX:PermSize=64M -XX:MaxPermSize=512m"
			
-Xms 指定初始化时化的栈内存

-Xmx 指定最大栈内存

2. Tomcat 优化

2.1. maxThreads 连接数限制

maxThreads 是 Tomcat 所能接受最大连接数。一般设置不要超过8000以上，如果你的网站访问量非常大可能使用运行多个Tomcat实例的方法。

即，在一个服务器上启动多个tomcat然后做负载均衡处理。

			
<Connector port="8080" address="localhost"
	maxThreads="2048" maxHttpHeaderSize="8192"
	emptySessionPath="true" protocol="HTTP/1.1"
	enableLookups="false" redirectPort="8181" acceptCount="100"
	connectionTimeout="20000" disableUploadTimeout="true" />

			
			
提示

很多做过php运维的朋友在这里会犯一个大错误，php优化服务器通常怎做法是安装cpu以及内存的情况配置连接数，连接数过万都很正常，但java不同jvm配置要非常小心，稍有差错就会崩溃。

maxThreads 配置要结合 JVM -Xmx 参数调整，也就是要考虑内存开销。

maxThreads  		客户请求最大线程数
minSpareThreads    	初始化时创建的 socket 线程数
maxSpareThreads   	连接器的最大空闲 socket 线程数
			
2.2. 虚拟主机

不要使用Tomcat的虚拟主机，每个站点一个实例。即，启动多个tomcat.

这也是PHP运维在这里常犯的错误，PHP的做法是一个Web下面放置多个虚拟主机，而不是每个主机启动一个web服务器。Tomcat 是多线程,共享内存，任何一个虚拟主机中的应用出现崩溃，会影响到所有应用程序。采用多个实例方式虽然开销比较大，但保证了应用程序隔离与安全。

2.3. 压错传输

通常所说的gzip压缩，Tomcat通过在server.xml配置设置压缩的选项。

			
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
               compression="on"
               compressionMinSize1="2048"
               noCompressionUserAgents="gozilla, traviata"
               compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain,,application/octet-stream"/>
			
			
提示

压缩会增加Tomcat负担，最好采用Nginx + Tomcat 或者 Apache + Tomcat 方式，压缩交由Nginx/Apache 去做。

compression 			打开压缩功能   
compressionMinSize   	启用压缩的输出内容大小，这里面默认为2KB
compressableMimeType 	压缩类型			
			
3. Tomcat 安全配置

3.1. 安装后初始化配置

当Tomcat完成安装后你首先要做的事情如下：

首次安装完成后立即删除webapps下面的所有代码

rm -rf /srv/apache-tomcat/webapps/*
			
注释或删除 tomcat-users.xml 所有用户权限，看上去如下：

			
# cat conf/tomcat-users.xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
</tomcat-users>
			
			
3.1.1. 隐藏版本信息

隐藏Tomcat版本信息

				
vim $CATALINA_HOME/conf/server.xml

    <Connector port="80" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
				maxThreads="8192"
				minSpareThreads="64"
				maxSpareThreads="128"
				acceptCount="128"
				enableLookups="false"
                server="Neo App Srv 1.0"/>

# curl -I http://localhost:8080/
HTTP/1.1 400 Bad Request
Transfer-Encoding: chunked
Date: Thu, 20 Oct 2011 09:51:55 GMT
Connection: close
Server: Neo App Srv 1.0
				
				
服务器信息已经被改为 Server: Neo App Srv 1.0

3.1.2. 应用程序安全

关闭war自动部署 unpackWARs="false" autoDeploy="false"。防止被植入木马等恶意程序

关闭 reloadable="false" 也用于防止被植入木马

3.1.3. JSESSIONID

修改 Cookie 变量 JSESSIONID， 这个cookie 是用于维持Session关系。建议你改为PHPSESSID。

3.2. 启动用户与端口

不要使用root用户启动tomcat，Java程序与C程序不同。nginx,httpd 使用root用户启动守护80端口，子进程/线程会通过setuid(),setgid()两个函数切换到普通用户。即父进程所有者是root用户，子进程与多线程所有者是一个非root用户，这个用户没有shell，无法通过ssh与控制台登陆系统，Java 的JVM 是与系统无关的，是建立在OS之上的，你使用什么用户启动Tomcat，那麽Tomcat 就会继承该所有者的权限。

这造成了一个问题，Linux系统小于1024的端口只有root可以使用，这也是为什么Tomcat默认端口是8080。如果你想使用80端口只能使用root启动Tomcat。这有带来了很多安全问题。

解决方案是创建一个不同用户，如：

groupadd -g 80 daemon
adduser -o --home /daemon --shell /sbin/nologin --uid 80 --gid 80 -c "Web Server" daemon
			
注意 /sbin/nologin , 意味着该用户不能登录，同时我也没有给它指定密码，这个用户只能用于启动tomcat

chown daemon:daemon -R /srv/*
su - daemon -c "/srv/apache-tomcat/bin/startup.sh"
			
接下来解决80端口问题, 思路就是80去调用8080，或者映射端口。

下面是影射方案,80 跳转 8080

iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080

取消跳转
iptables -t nat -D PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080

查看规则
iptables -t nat -L
			
另一个就是从80请求去调用8080的方案

这个方案可以在 Tomcat 前段增加反向代理，例如：Nginx,Apache,Squid,Varnish或者F5， Array这类设备等等

4. 如何部署应用程序

应用程序部署与tomcat启动,不能使用同一个用户。

我的tomcat 安装在 /srv目录下，Tomcat启动用户为daemon; 应用程序放在/www目录下www所有者是www用户。这样的目的是一旦tomcat被植入web shell程序，它将不能创建或编辑/www目录下面的任何内容。

adduser --home /www -c "Web Application" www
		
我的Tomcat安装在/srv目录下，但应用程序放在/www目录下，一般是这样的结构。

/www/example.com/www.example.com
		
每次升级将压错包解压到 /www/example.com/目录下，www.example.com 是符号连接，连接到刚刚解压的目录。

这个可以实现通过符号连接在多个版本之间快速切换。