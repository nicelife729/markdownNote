1 修改源数据库配置文件，并重启
```
[client]
port = 3306
#socket = /var/lib/mysql/mysql.sock
default-character-set=utf8

[mysqld]

datadir=/usr/local/mysql/data

log-bin=mysql-bin #添加这一行就ok
binlog-format=ROW #选择row模式
server_id=1 #配置mysql replaction需要定义，不能和canal的slaveId重复
character-set-server=utf8

[mysql]

default-character-set=utf8
```
检查是否开启binlog
```
show variables like '%binlog_format%'
```

检查是否修改好字符集
```
SHOW VARIABLES LIKE 'char%';
```
2 canal
```
CREATE USER canal IDENTIFIED BY 'canal';
GRANT ALL ON *.* TO 'canal'@'%';
FLUSH PRIVILEGES;
```

3
```
vi conf/example/instance.properties

canal.instance.master.address = 127.0.0.1:3306 #改成源机器的
```

4 manager

```
wget https://raw.github.com/alibaba/otter/master/manager/deployer/src/main/resources/sql/otter-manager-schema.sql
```
建表
```
sql> source /home/ruijie/Downloads/canal/manager/otter-manager-schema.sql
```

修改配置文件otter.properties
```
## otter manager domain name
otter.domainName = 172.16.33.2
## otter manager http port
otter.port = 8080
## jetty web config xml
otter.jetty = jetty.xml

## otter manager database config
otter.database.driver.class.name = com.mysql.jdbc.Driver
otter.database.driver.url = jdbc:mysql://172.16.33.2:3306/otter
otter.database.driver.username = canal
otter.database.driver.password = canal

## otter communication port
otter.communication.manager.port = 1099

## otter communication pool size
otter.communication.pool.size = 10

## default zookeeper address
otter.zookeeper.cluster.default = 172.16.33.2:2181

```
启动
```
./startup.sh
```

访问： http://127.0.0.1:8080/，出现otter的页面，即代表启动成功

访问：http://127.0.0.1:8080/login.htm，初始密码为：admin/admin，即可完成登录. 目前：匿名用户只有只读查看的权限，登录为管理员才可以有操作权限

建立zk集群
建立机器列表 生成nid

ps:
机器端口：对应node节点将要部署时启动的数据通讯端口，建议值：2088
下载端口：对应node节点将要部署时启动的数据下载端口，建议值：9090

5 aria2
http多线程传输
```
./configure
make
sudo make install
```
安装完成后，将bin路径加入环境变量PATH中

6 node

```
vi conf/otter.properties
   otter.manager.address =172.20.150.70:1099 
   ##对应manager中otter.properties的配置  otter.communication.manager.port
```


```
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
```

7.otter页面配置
添加数据源 mysql oracle
添加数据表 数据表
添加canal配置

添加channel
在channel中添加pipe
在pipe中定义表映射规则
在规则中配置监控

启动channel

