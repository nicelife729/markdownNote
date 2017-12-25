# install hadoop
1. groupadd hadoop
2. adduser hadoop -g hadoop --password hadoop
3. 
chmod +w /etc/sudoers
vi /etc/sudoers

root    ALL=(ALL)       ALL
hadoop  ALL=(ALL)       ALL
chomd 0440 /etc/sudoers

4.su - hadoop

5.sudo vi /etc/sysconfig/network
NETWORK=nodexxx

6.sudo vi /etc/hosts

192.168.153.113 node113
192.168.153.114 node114
192.168.153.115 node115

7.
> vim /etc/ssh/sshd_config
RSAAuthentication yes // 启用RSA 认证
PubkeyAuthentication yes // 启用公用和私钥配对认证方式
AuthorizedKeysFile .ssh/authorized_keys // 所有目标主机公用文件路径

> >service sshd restart

8. 
all node
>ssh-keygen -t rsa

on node113
scp hadoop@192.168.153.115:/home/hadoop/.ssh/id_rsa.pub ./115.pub
scp hadoop@192.168.153.114:/home/hadoop/.ssh/id_rsa.pub ./114.pub


cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
cat ~/114.pub >> ~/.ssh/authorized_keys
cat ~/115.pub >> ~/.ssh/authorized_keys

scp ~/.ssh/authorized_keys hadoop@192.168.153.114:/home/hadoop/.ssh/authorized_keys
scp ~/.ssh/authorized_keys hadoop@192.168.153.115:/home/hadoop/.ssh/authorized_keys


all node
chmod 700 /home/hadoop/.ssh/
chmod 600 /home/hadoop/.ssh/authorized_keys

9 ntp

all node
yum install ntp

chkconfig ntpd on

chkconfig –list ntpd

on node113
vi /etc/ntp.conf
#restrict default kod nomodify notrap nopeer noquery
restrict default nomodify notrap

#server 0.centos.pool.ntp.org
#server 1.centos.pool.ntp.org
#server 2.centos.pool.ntp.org

server  127.127.1.0 prefer      # local clock
fudge   127.127.1.0 stratum 10

service ntpd start

other node
/usr/sbin/ntpdate 192.168.153.113

10 
service iptables stop
chkconfig iptables off

11

all node

mkdir /opt/hadoop
chown -R hadoop:hadoop /opt/hadoop/

mkdir /home/hadoop/datas/
chown -R hadoop:hadoop /home/hadoop/datas/

mkdir /home/hadoop/datas/tmp

master
mkdir /home/hadoop/datas/namedir

slave

mkdir /home/hadoop/datas/datadir

12
/etc/profile

export HADOOP_HOME=/opt/hadoop/hadoop-2.5.2
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop


core-site.xml

<property>   
	<name>fs.defaultFS</name>   
	<value>hdfs://node113:9000</value>   
</property>
<property>
	<name>hadoop.tmp.dir</name>
	<value>/home/hadoop/datas/tmp</value> 
</property>

hdfs-site.xml

<property>
	<name>dfs.namenode.secondary.http-address</name>
	<value>node113:9001</value>
</property>

<property>
	<name>dfs.namenode.name.dir</name>
	<value>/home/hadoop/datas/namedir</value> 
</property>

<property>
	<name>dfs.datanode.name.dir</name>
	<value>/home/hadoop/datas/datadir</value> 
</property>

<property>   
	<name>dfs.replication</name>   
	<value>2</value>   
</property>

<property>
	<name>dfs.webhdfs.enabled</name>
	<value>true</value>
</property>

mv mapred-site.xml.template mapred-site.xml 
vim mapred-site.xml

mapred-site.xml

<property>
	<name>mapreduce.framework.name</name>
	<value>yarn</value>
</property>
<property>
	<name>mapreduce.jobhistory.address</name>
	<value>node113:10020</value>
</property>
<property>
	<name>mapreduce.jobhistory.webapp.address</name>
	<value>node113:19888</value>
</property>

yarn-site.xml

<property>
	<name>yarn.nodemanager.aux-services</name>
	<value>mapreduce_shuffle</value>
</property>
<property>
	<name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
	<value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
<property>
	<name>yarn.resourcemanager.address</name>
	<value>node113:8032</value>
</property>
<property>
	<name>yarn.resourcemanager.scheduler.address</name>
	<value>node113:8030</value>
</property>
<property>
	<name>yarn.resourcemanager.resource-tracker.address</name>
	<value>node113:8031</value>
</property>
<property>
	<name>yarn.resourcemanager.admin.address</name>
	<value>node113:8033</value>
</property>
<property>
	<name>yarn.resourcemanager.webapp.address</name>
	<value>node113:8088</value>
</property>



slaves

node114
node115

13

scp -r hadoop-2.5.2/ hadoop@192.168.153.114:/opt/hadoop
scp -r hadoop-2.5.2/ hadoop@192.168.153.115:/opt/hadoop

14

格式化：bin/hdfs namenode -format

格式化成功后在/vgopbak/hadoop/namedir下面会有文件生成

启动：
sbin/start-dfs.sh
sbin/start-yarn.sh

15

bin/hadoop fs -mkdir /test
bin/hadoop fs -put /opt/hadoop/hadoop-2.5.2/README.txt /test

#install hbase
1.
export HBASE_MANAGES_ZK=false
export HBASE_HOME=/opt/hadoop/hbase-0.98.13-hadoop2
export PATH=$PATH:$HBASE_HOME/bin

2.
hbase-site.xml

<property>
	<name>hbase.master</name>
	<value>hdfs://node113:60000</value>
</property>
<property>
	<name>hbase.rootdir</name>
	<value>hdfs://node113:9000/hbase</value>
</property>
<property>
	<name>hbase.cluster.distributed</name>
	<value>true</value>
</property>
<property>
	<name>hbase.zookeeper.quorum</name>
	<value>node113,node114,node115</value>
</property>

3.
regionservers

node114
node115

4. 
./bin/start-hbase.sh

5.
hbase shell 进入HBASE SHELL,提示符会变成类似:hbase(main):002:0>以下命令都在HBASE SHELL执行.

查看状态 :status
查看版本:version
创建表：create 'mtable','cf' 指定表名和列族,多个列族用“,”分隔如：create 'mtable','cf','cf2'
插入数据: put '表名','rowKey','列名','列值'
put 'mtable','rowKey1','cf:acc_nbr','18797384480'
put 'mtable','rowKey1','cf:name','migle'
列可以动态创建: 列格式是:列族:列名
查看数据:
单行:get ‘mtable’ , ‘rowKey1’
get ‘mtable’,’rowKey1’,’cf:acc_nbr’
get ‘mtable’,’rowKey1’,’cf’
所有:scan ‘mtable’
行数：count 'mtable'
更新数据：put 'mtable','1','cf:acc_nbr','18797384481'
删除数据：delete 'mtable','1','cf:name'
清空表： truncate 'mtable'
查看表结构：describe 'mtable'
修改表结构:

添加一个列族：

		disable 'mtable'  
		alter 'mtable', NAME => 'cf2'  
		enable 'mtable'
删除列族：

		disable 'mtable'   
		alter 'mtable', 'delete' => 'cf2'  
		enable 'mtable'
删除表：
		disable 'mtable'
		drop 'mtable'


#install zookeeper
1.
all node
mkdir /home/hadoop/datas/zookeeper

2.
/opt/hadoop/zookeeper-3.4.6/conf
cp zoo_sample.cfg zoo.cfg

vi zoo.cfg
dataDir=/home/hadoop/datas/zookeeper
autopurge.snapRetainCount=100
autopurge.purgeInterval=1
server.1=node113:2888:3888
server.2=node114:2888:3888
server.3=node115:2888:3888

3.
vi /home/hadoop/datas/zookeeper/myid

4.
~/.bashrc
export ZOOKEEPER_HOME=/opt/hadoop/zookeeper-3.4.6
export PATH=$PATH:$ZOOKEEPER_HOME/bin

5.
scp -r zookeeper-3.4.6/ hadoop@192.168.153.114:/opt/hadoop
scp -r zookeeper-3.4.6/ hadoop@192.168.153.115:/opt/hadoop

6.
/opt/hadoop/zookeeper-3.4.6/bin/zkServer.sh start


#client

Configuration config = HBaseConfiguration.create();
config.set("hbase.zookeeper.quorum", "localhost");  //可以不读hdfs-site.xml 直接硬编码

#优化

##硬盘

推荐ext4，并关闭关闭延时分配特性

##交换区

    避免使用swap，因为会引起服务器性能下降

     echo 0 > /proc/sys/vm/swappiness #即时生效
 	echo “vm.swappiness = 0” >> /etc/sysctl.conf //永久生效
    编辑/etc/sysctl.conf调整
    ```
    vm.swappiness=5 #0~5
    ```
    重启
    sysctl -p

##os

1. unlimit
	

    解决too many open files
    文件句柄数估算方法：
        region数* 列族数 * 每个列族的hfile数
        ```    
        lsof -p REGIONSERVER_PID
        ```

    进程限制
    /etc/security/limits.conf设置nproc，避免java出现outOFMemoryError

    vim /etc/security/limits.conf

    hadoop  -       nofile  65530
	hadoop  -       nproc   4096

	查看
	cat /proc/{pid}/limits |grep 'Max open files'
	ulimit -a
2. 编辑/etc/sysctl.conf,调整fs.file-max
 


3. WARN 注意修改配置的用户是运行程序的用户


##hadoop

1.
hdfs-site.xml

A Hadoop HDFS DataNode has an upper bound on the number of files that it can serve at any one time

<property>
	<name>dfs.datanode.max.xcievers</name>
	<value>4096</value>
</property>

<property>
	<name>dfs.datanode.max.transfer.threads</name>
	<value>4096</value>
</property>


##zookeeper
1.
避免部署在slave上
给jvm 1g内存

2.
zoo.cfg
maxClientCnxns=1000
##hbase

1.
hbase-env.sh
    java堆大小，垃圾回收策略

change 
export HBASE_HEAPSIZE=4000
export HBASE_REGIONSERVER_OPTS="$HBASE_REGIONSERVER_OPTS -Xms8000m -Xmx8000m"

export HBASE_OPTS="$HBASE_OPTS -server -XX:+HeapDumpOnOutOfMemoryError -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:NewSize=64m -XX:MaxNewSize=64m -XX:+CMSIncrementalMode -Djava.net.preferIPv4Stack=true"

2. 
hbase-site.xml

一般来言，更少的Region可以使你的集群运行更加流畅。
	hbase.hregion.max.filesize #默认256M
	1G

<property>
	<name>hbase.regionserver.lease.period</name>
	<value>1200000</value>
</property>
<property>
	<name>hbase.rpc.timeout</name>
	<value>1200000</value>
</property>
<property>
	<name>zookeeper.session.timeout</name>
	<value>20000</value>
</property>
<property>
	<name>hbase.regionserver.handler.count</name>
	<value>50</value>
</property>
<property>
	<name>hbase.client.scanner.caching</name>
	<value>100</value>
</property>
<property>
	<name>hbase.hregion.max.filesize</name>
	<value>10737418240</value>
</property>
<property> 
   <name>hbase.hregion.majorcompaction</name>
   <value>0</value>
</property>
<property>
	<name>hbase.hregion.memstore.flush.size</name>
	<value>134217728</value>
</property>
<property>
	<name>hbase.hregion.memstore.block.multiplier</name>
	<value>4</value>
</property>
<property>
	<name>hbase.hstore.blockingStoreFiles</name>
	<value>30</value>
</property>


##windows client

1. 
get hadoop window dll

https://github.com/Zutai/HadoopOnWindows
put into hadoop/bin/

2. code 
System.setProperty("hadoop.home.dir", "D:\\myWork\\source\\hadoop-2.5.2");
conf=HBaseConfiguration.create();//hbase的配置信息
conf.set("hbase.zookeeper.quorum", "192.168.153.113,192.168.153.114,192.168.153.115");  //zookeeper的地址

3. hosts
192.168.153.113 node113
192.168.153.114 node114
192.168.153.115 node115
