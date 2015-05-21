#what：

定义：
HBase是一个开源的、分布式的、多版本的、面向列的存储模型。它存储的是松散型数据，介于映射（key/value）和关系型数据之间。

组成：
client，zookeeper，HMaster，RegionServer


理念：
bigTable

逻辑角度：
1.hbase包含多张表
2.表里包行多行
3.表分片为regions


数据模型：

行：行键，字典升序

列族： 需要预定，不轻易改变。

列限定符： 相当与表中的key，列不用事先定义。

时间版本： 时间降序

单元： 上述四个维度确定一个

相当于一个kv数据库


列族的存储：一个列族可以有多个Hfile，一个Hfile只能存一个列族.



分布式模式

regionServer 应该与DataNode，运行在一起

查找方式，找ROOT（唯一的不可拆分）region， 再找META，再找到目标Region

工作机制：

写：

读： 先是memstore，再blockcache（读缓存区去），最后从block找。 

#how
how to write:
写特点：
1单行更新是原子操作
2多行更新非原子操作
3存多版本（默认为3）
4表通常是稀疏的

how to read:
读特点：
1总是读到last-write
2get（） 读单行
3scan（） 读多行，非常快
原因：scan定义了起止key，而row是有序的
4query predicate pushed down via server-side filters


how to Integration with MapReduce:

1Hbase table 中的数据可以作为Mrjob的数据源
2mr可以想HBase写数据
3mr可以向hdfs写数据，然后通过批量加载到hbase中

how: 数据分片

1 自动对表做可配置的分片：
  - region：由起止行键定义，相当与横向的分片，将一组行归为一类region.
  - region 是“原子”操作的
2 region由RegionServer管理（HBase cluster slaves）

how: setup 容错
1.dataNode错误由HDFS控制（replication）
2.RegionServers倒掉，由master重分配可用的Rs
3.HMaster倒掉，由多个Hmaster保证


#When
应用场景：
1大量随机请求
2大量数据

用于：日志系统

优势：
单key查询，或按key做范围scan
半结构化数据
