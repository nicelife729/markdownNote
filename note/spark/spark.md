#Index

#一、简介
spark是一款开源的集群计算框架。最初是由berkeley的AMPLab开发。与hadoop的基于硬盘的两阶段mapreduce不同，采用内存的方式，允许用户将数据加载到集群内存中，并运行反复查询。

spark非常适合机器学习算法。
spark支持许多文件或存储系统作为数据源，如hdfs、cassandra、HBase、amazon S3
spark可以运行于Hadoop YARN或者Apache Mesos上。

- spark特点
    - 支持java、scala、pythonAPI
    - 被证明生产环境中可以超过8000个节点
    - 通过SparkSQL实现结构化查询
    - 通过Spark Streaming提供流式处理的库
    - 提供机器学习和图像处理的库

- spark的组件：
    1. spark core 和 Resilient distriuted dataset(RDDs)
        - RDDs是基础的逻辑抽象，是一种跨机器的数据分片的集合。这种集合可以外部存储系统中相关的资料组来创建。也可以由已存在的RDDs对象经过变换（map,filter, reduce, join）得到
        - RDDs在API层面中操作时，就想操作本地集合一样，降低了编程的复杂度。
    2. spark SQL
        - spark SQL是在spark core上的一个组件。该组件中引入了一个新的数据抽象叫SchemaRDD，它提供对结构化半结构化数据的支持。
        它提供用Scala，java，python语言实现的domain specific Langurage 对SchemaRDD操作。
        它也提供SQL语言方式，通过命令行或者ODBC/JDBC访问。
    3. spark Streaming
        - spark Streaming杠杆化了sparkCore的快速调度能力来做流式分析。
        它吞入小批量的数据，然后对这些数据做RDD变换。这种设计可以在一个引擎上用批处理的代码做流式分析。
    4. MLlib Machine Learning Library
        - MLLib是在Spark Core上的一种分布式机器学习框架。由于spark基于内存的架构要10倍于hadoop这种基于硬盘的架构，而且规模上也好于Vowpal Wabbit。
        - 它实现了许多机器学习和统计算法：
            - summary statistics, correlations, stratified sampling, hypothesis testing, random data generation
            - classification and regress: SVMs, logistic regression, linear regression, decision trees, naive Bayes
            - collaborative filtering: alternating least squares (ALS)
            - clustering: k-means
            - dimensionality reduction: singular value decomposition (SVD), principal component analysis (PCA)
            - feature extraction and transformation
            - optimization primitives: stochastic gradient descent, limited-memory BFGS (L-BFGS)
    5. GraphX
        - GraphX是在Sprark上的一种分布式图像处理框架。
        
- 工作原理：
     
     spark Application作为一组相互独立的进程运行在集群上，在driverProgram中有一个SparkContext对象，这个对象负责协调这些进程，并负责与cluster manager（负责跨应用的分配资源）通信。
     一旦通信建立之后，spark会获取节点上的executors。然后它会把用户代码提交给executors。最后，sparkContext将tasks发给executors去执行。
     
     ![cluster-overview](https://github.com/nicelife729/markdownNote/raw/master/note/spark/image/cluster-overview.png)
     
     - Warn: 
        1. 每个application有自己的executor进程。每个application间不能共享数据，出副将其写到外部存储系统中。
        2. 由于是driver来计划task的，所以它至少应该运行于cluster所在的局域网内。
     
     - spark支持的cluster manager类型：
       - standLone [standalone Mode](https://spark.apache.org/docs/latest/spark-standalone.html)
       - Apache Mesos
       - Hadoop YARN
       
     - 术语
        - Application：用户在spark上建立的程序，由driver program 和集群上的executors组成。
        - Application jar： 含有用户程序的jar，务必不要包含spark或hadoop的库
        - Driver program ：	跑main方法的进程，同时也负责创建SparkContext
        - Cluster ：在集群上获取资源的外部服务
        - Deploy mode ：cluster/client; 用于区分在哪里运行driver进程。在cluster模式下，框架在集群内启动driver。在client模式下，提交者在集群外启动driver
        - Worker node	： 集群中跑application的机器
        - Executor ： 在一个工作节点上开始运行的进程。它负责运行很多task，并持有这些任务中传递的数据。每一个application有一个exectuor。
        - Task	：发送给某个executor来执行的工作单元
        - Job	： 由许多任务组成的一个并行计算，最终输出为一个Spark action （eg：save，collect）log中可见
        - Stage	：每个job分为更小粒度的一组任务，称为stage。log中可见
        
     - 进一步了解spark工作方式：[A-Deeper-Understanding-of-Spark-Internals](http://spark-summit.org/wp-content/uploads/2014/07/A-Deeper-Understanding-of-Spark-Internals-Aaron-Davidson.pdf)

##1)优缺点

1. 比MR更有效率
    - 支持迭代和cache
    - 迭代次数更多
2. 支持常见的机器学习算法（via MLlib）

##2)适用场景
  
##3)同类对比

  //TODO
  
#二、部署和使用

##1. 集群部署

  - 运行已编译包
    - 下载地址：http://d3kbcqa49mib13.cloudfront.net/spark-1.1.1-bin-hadoop2.4.tgz
          
  - 从原代码编译安装
    1. 下载源代码，地址：http://d3kbcqa49mib13.cloudfront.net/spark-1.1.1.tgz
    2. 配置mvn编译环境，注意：若不是jdk8需要在环境变量中加入
    `export MAVEN_OPTS="-Xmx2g -XX:MaxPermSize=512M -XX:ReservedCodeCacheSize=512m"`
    3. mvn编译
    
      ```mvn -Pyarn -Phadoop-2.4 -Dhadoop.version=2.4.0 -Phive -DskipTests clean package```
    
    4. 安装hadoop
        1. 下载地址： https://archive.apache.org/dist/hadoop/core/hadoop-2.4.0/hadoop-2.4.0.tar.gz ,解压到/home/spark/hadoop/hadoop-2.4.0
        2. 配置无密码登录
           1. 在～/.ssh/下执行`ssh-keygen -t rsa`
           2. 然后执行`ssh-copy-id -i id_rsa.pub spark@172.16.236.44`将公钥拷贝集群中所有的机器上
        3. 配置host        
          ```
          sudo vim /etc/hostname #给每台机器分配主机名
          sudo vim /etc/hosts #在每台主机的hosts文件中加入集群中所有的主机名
          ```
        4. 增加环境变量
          ```
          export HADOOP_HOME=/home/spark/hadoop/hadoop-2.4.0
          export HADOOP_MAPRED_HOME=$HADOOP_HOME
          export HADOOP_COMMON_HOME=$HADOOP_HOME
          export HADOOP_HDFS_HOME=$HADOOP_HOME
          export YARN_HOME=$HADOOP_HOME
          export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
          ```
        5. 创建nameNode和dataNode目录
          ```
          mkdir -p $HADOOP_HOME/yarn/yarn_data/hdfs/datanode
          mkdir -p $HADOOP_HOME/yarn/yarn_data/hdfs/namenode
          mkdir -p $HADOOP_HOME/yarn/tmp
          ```
        6. 修改配置文件（slaves、core-site.xml、hdfs-site.xml、mapred-site.xml、yarn-site.xml ）
              1. yarn-site.xml
                  `vi $HADOOP_HOME/etc/hadoop/yarn-site.xml`
                  在<configuration>标签中加入以下内容：
                  ```
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
                      <value>impala43:8032</value>
                  </property>
                  <property>
                      <name>yarn.resourcemanager.scheduler.address</name>
                      <value>impala43:8030</value>
                  </property>
                  <property>
                      <name>yarn.resourcemanager.resource-tracker.address</name>
                      <value>impala43:8031</value>
                  </property>
                  <property>
                      <name>yarn.resourcemanager.admin.address</name>
                      <value>impala43:8033</value>
                  </property>
                  <property>
                      <name>yarn.resourcemanager.webapp.address</name>
                      <value>impala43:8088</value>
                  </property>
                  ```
              2. core-site.xml
                  ```vi $HADOOP_HOME/etc/hadoop/core-site.xml```
                  在<configuration>标签中加入下面内容在配置：
                  ```
                  <property>
                      <name>fs.defaultFS</name>
                      <value>hdfs://impala43:9000</value>
                  </property>
                  <property>
                      <name>io.file.buffer.size</name>
                      <value>131072</value>
                  </property>
                  <property>
                      <name>hadoop.tmp.dir</name>
                      <value>file:/home/spark/hadoop/hadoop-2.4.0/yarn/tmp</value>
                      <description>Abase for other temporary directories.</description>
                  </property>
                  <property>
                      <name>hadoop.proxyuser.hduser.hosts</name>
                      <value>*</value>
                  </property>
                  <property>
                      <name>hadoop.proxyuser.hduser.groups</name>
                      <value>*</value>
                  </property>
                   ` ``
              3. hdfs-stie.xml
              ```
                  vi $HADOOP_HOME/etc/hadoop/hdfs-site.xml
              ```
                  在<configuration>标签中加入下面内容在配置：
                  ```
                  <property>
                      <name>dfs.namenode.secondary.http-address</name>
                      <value>impala43:9001</value>
                  </property>
                  <property>
                      <name>dfs.namenode.name.dir</name>
                      <value>file:/home/spark/hadoop/hadoop-2.4.0/yarn/yarn_data/hdfs/namenode</value>
                  </property>
                  <property>
                      <name>dfs.datanode.data.dir</name>
                      <value>file:/home/spark/hadoop/hadoop-2.4.0/yarn/yarn_data/hdfs/datanode</value>
                  </property>
                  <property>
                      <name>dfs.replication</name>
                      <value>1</value>
                  </property>
                  <property>
                      <name>dfs.webhdfs.enabled</name>
                      <value>true</value>
                  </property>
                  ```
              4. mapred-site.xml
                  ```
                  mv mapred-site.xml.template mapred-site.xml 
                  vim mapred-site.xml
                  ```
                  在<configuration>标签中加入下面内容在配置：
                  ```
                  <property>
                      <name>mapreduce.framework.name</name>
                      <value>yarn</value>
                  </property>
                  <property>
                      <name>mapreduce.jobhistory.address</name>
                      <value>impala43:10020</value>
                  </property>
                  <property>
                      <name>mapreduce.jobhistory.webapp.address</name>
                      <value>impala43:19888</value>
                  </property>
                  ```
              5. slaves
                  `vi $HADOOP_HOME/etc/hadoop/slaves`将原来localhost删除，把所有Slave的主机名写上，每行一个
    5.运行hadoop
        1. 初始化hadoop `bin/hdfs namenode -format       # 首次运行需要执行一次文件系统的初始化，后面不再需要`
        2. 启动hadoop
        ```
        sbin/start-dfs.sh
        sbin/start-yarn.sh
        ```
        
##2.使用

- 通过SHELL执行任务（scala）:`./bin/spark-shell`

- 通过提交任务jar包:`./spark-submit`
  - 用法：
    ```
      ./bin/spark-submit 
        --class <main-class>
        --master <master-url> 
        --deploy-mode <deploy-mode> 
        --conf <key>=<value> 
        ... 
        <application-jar> 
        [application-arguments]
    ```  
  - 注释：
    - master表示要集群地址或者local
  - eg:  
    ```
    SPARK_JAR=./assembly/target/scala-2.10/spark-assembly-1.1.1-hadoop2.4.0.jar
    HADOOP_CONF_DIR=/home/spark/hadoop/hadoop-2.4.0/etc/hadoop 
    ./bin/spark-submit --master yarn --deploy-mode cluster --class org.apache.spark.examples.SparkPi --num-executors 3 --driver-memory 4g --executor-memory 2g --executor-cores 1 examples/target/scala-2.10/spark-examples-1.1.1-hadoop2.4.0.jar
    ```
        
##3.监控

- 当使用shell方式运行任务时，监控task、executors、storage使用状况，使用地址：http://nodeIp:4040/
- 当使用spark On Yarn时，使用地址：http://172.16.236.43:8088/cluster

##4.java编程指导


  1. 初始化spark
      - eg:  
      ```
      SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
      JavaSparkContext sc = new JavaSparkContext(conf);
      ```
  
  2. 构建RDD对象
    - 法1 Parallelized Collections
      - eg :   
      ```
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);//sc.parallelize(data, 10); 10 是手工指定的slice（切片）数
      ```
    - 法2 External Datasets
      - eg：  
      ```
        JavaRDD<String> distFile = sc.textFile("data.txt");//sc.textFile("data.txt", 128); 128 是手工指定的slice（切片）数,对于HDFS默认值是64M
      ```
  
  3. 操作RDD对象
      两种方式：transformation、action
          - transformation : 从存在的RDD对象创建新的RDD对象（eg：map，reduceByKey）
          - action ： driver Program运行的最后执行的动作，返回最终的RDD对象 （eg： reduce）
   
      特点： 所有的transformation都是lazy的，即，当action操作需要返回结果时transformation才开始计算，优点是提高计算效率。即计算完成时将最终的结果返回给driverProgram，而不是每一步的中间结果。
      
      - transfomration操作：
        - map(func)： Return a new distributed dataset formed by passing each element of the source through a function func.
        - filter(func)：	 Return a new dataset formed by selecting those elements of the source on which func returns true.
        - flatMap(func)：	 Similar to map, but each input item can be mapped to 0 or more output items (so func should return a Seq rather than a single item).
        - mapPartitions(func)：	 Similar to map, but runs separately on each partition (block) of the RDD, so func must be of type Iterator<T> => Iterator<U> when running on an RDD of type T.
        - mapPartitionsWithIndex(func)：	 Similar to mapPartitions, but also provides func with an integer value representing the index of the partition, so func must be of type (Int, Iterator<T>) => Iterator<U> when running on an RDD of type T.
        - sample(withReplacement, fraction, seed)：	 Sample a fraction fraction of the data, with or without replacement, using a given random number generator seed.
        - union(otherDataset)：	 Return a new dataset that contains the union of the elements in the source dataset and the argument.
        - intersection(otherDataset)：	 Return a new RDD that contains the intersection of elements in the source dataset and the argument.
        - distinct([numTasks]))：	 Return a new dataset that contains the distinct elements of the source dataset.
        - groupByKey([numTasks])：	 When called on a dataset of (K, V) pairs, returns a dataset of (K, Iterable<V>) pairs. 
            - Note: If you are grouping in order to perform an aggregation (such as a sum or average) over each key, using reduceByKey or combineByKey will yield much better performance. 
            - Note: By default, the level of parallelism in the output depends on the number of partitions of the parent RDD. You can pass an optional numTasks argument to set a different number of tasks.
        - reduceByKey(func, [numTasks])：	 When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function func, which must be of type (V,V) => V. Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.
        - aggregateByKey(zeroValue)(seqOp, combOp, [numTasks])：	 When called on a dataset of (K, V) pairs, returns a dataset of (K, U) pairs where the values for each key are aggregated using the given combine functions and a neutral "zero" value. Allows an aggregated value type that is different than the input value type, while avoiding unnecessary allocations. Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.
        - sortByKey([ascending], [numTasks])：	 When called on a dataset of (K, V) pairs where K implements Ordered, returns a dataset of (K, V) pairs sorted by keys in ascending or descending order, as specified in the boolean ascending argument.
        - join(otherDataset, [numTasks])：	 When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (V, W)) pairs with all pairs of elements for each key. Outer joins are also supported through leftOuterJoin and rightOuterJoin.
        - cogroup(otherDataset, [numTasks])：	 When called on datasets of type (K, V) and (K, W), returns a dataset of (K, Iterable<V>, Iterable<W>) tuples. This operation is also called groupWith.
        - cartesian(otherDataset)：	 When called on datasets of types T and U, returns a dataset of (T, U) pairs (all pairs of elements).
        - pipe(command, [envVars])：	 Pipe each partition of the RDD through a shell command, e.g. a Perl or bash script. RDD elements are written to the process's stdin and lines output to its stdout are returned as an RDD of strings.
        - coalesce(numPartitions)：	 Decrease the number of partitions in the RDD to numPartitions. Useful for running operations more efficiently after filtering down a large dataset.
        - repartition(numPartitions)：	 Reshuffle the data in the RDD randomly to create either more or fewer partitions and balance it across them. This always shuffles all data over the network.
      
      - action操作：
        - reduce(func)	 Aggregate the elements of the dataset using a function func (which takes two arguments and returns one). The function should be commutative and associative so that it can be computed correctly in parallel.
        - collect()	 Return all the elements of the dataset as an array at the driver program. This is usually useful after a filter or other operation that returns a sufficiently small subset of the data.
        - count()	 Return the number of elements in the dataset.
        - first()	 Return the first element of the dataset (similar to take(1)).
        - take(n)	 Return an array with the first n elements of the dataset. Note that this is currently not executed in parallel. Instead, the driver program computes all the elements.
        - takeSample(withReplacement, num, [seed])	 Return an array with a random sample of num elements of the dataset, with or without replacement, optionally pre-specifying a random number generator seed.
        - takeOrdered(n, [ordering])	 Return the first n elements of the RDD using either their natural order or a custom comparator.
        - saveAsTextFile(path)	 Write the elements of the dataset as a text file (or set of text files) in a given directory in the local filesystem, HDFS or any other Hadoop-supported file system. Spark will call toString on each element to convert it to a line of text in the file.
        - saveAsSequenceFile(path) 	 Write the elements of the dataset as a Hadoop SequenceFile in a given path in the local filesystem, HDFS or any other Hadoop-supported file system. This is available on RDDs of key-value pairs that either implement Hadoop's Writable interface. In Scala, it is also available on types that are implicitly convertible to Writable (Spark includes conversions for basic types like Int, Double, String, etc).
        - saveAsObjectFile(path) 	 Write the elements of the dataset in a simple format using Java serialization, which can then be loaded using SparkContext.objectFile().
        - countByKey()	 Only available on RDDs of type (K, V). Returns a hashmap of (K, Int) pairs with the count of each key.
        - foreach(func)	 Run a function func on each element of the dataset. This is usually done for side effects such as updating an accumulator variable (see below) or interacting with external storage systems.
        
  4. 持久化RDD对象
     当持久化一个RDD对象时，会在每个节点的内存上存储该对象的一部分（partition）。当你采用迭代算法时，缓存过的RDD对象会大幅提升计算效率。
     
     spark的cache的另一个特点是，它是fault-tolerant的。即当一个partition丢失的话，会从原始的对象开始通过transformation重建它。
     - 持久化方法：
        - persist（） 当第一次运行完成后，调用该方法的rdd对象会保存在内存中
        - cache（） 是持久化的一种特例，即
  5. 给spark传递一个函数
    - 方法： 给操作RDD对象的方法传递一个Function匿名内部类，其中的call方法实现计算逻辑  
    ```
    JavaRDD<String> lines = sc.textFile("data.txt");
    JavaRDD<Integer> lineLengths = lines.map(new Function<String, Integer>() {
      public Integer call(String s) { return s.length(); }
    });
    int totalLength = lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
      public Integer call(Integer a, Integer b) { return a + b; }
    });
    ```
    
  6. 操作键值对
    用scala.Tuple2对象来包装一个键值对，`new Tuple2(a,b)`创建；tuple._1(),tuble._2()访问键和值
    
    RDD采用键值对对象为JavaPairRDD
    - eg：统计一个文件中同样的行出现的次数  
    ```
      JavaRDD<String> lines = sc.textFile("data.txt");
      JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1));
      JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
    ```

#三、常见问题
