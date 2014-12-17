#Index

#一、简介
 - spark是一款开源的集群计算框架。最初是由berkeley的AMPLab开发。与hadoop的基于硬盘的两阶段mapreduce不同，采用内存的方式，允许用户将数据加载到集群内存中，并运行反复查询。
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
        

##1)优缺点
1. 比MR更有效率
    - 支持迭代和cache
    - 代数量更多
2. 可以组为更有效的中间层：pig和scalding可以在spark上运行
3. 支持一般的机器学习（via MLlib）

##2)适用场景

##3)同类对比

#二、部署和使用
##1. 安装
- 下载地址：http://d3kbcqa49mib13.cloudfront.net/spark-1.1.1-bin-hadoop2.4.tgz

- 直接执行任务（scala）:./bin/spark-shell
- 提交任务jar包:./spark-submit
  - 用法：
    ./bin/spark-submit 
      --class <main-class>
      --master <master-url> 
      --deploy-mode <deploy-mode> 
      --conf <key>=<value> 
      ... # other options
      <application-jar> 
      [application-arguments]



##2. 集群部署
   spark Application作为一组相互独立的进程运行在集群上，在driverProgram中有一个SparkContext对象，这个对象负责协调这些进程，并负责与cluster manager（负责跨应用的分配资源）通信。
   一旦通信建立之后，spark会获取节点上的executors。然后它会把用户代码提交给executors。最后，sparkContext将tasks发给executors去执行。
   
   ![hehe](https://spark.apache.org/docs/latest/img/cluster-overview.png)
   
   Context
   - spark集群管理分类：
         - standLone
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
##3. 监控
监控task、executors、storage使用状况
- spark shell WEBUI：http://nodeIp:4040/


#三、常见问题
