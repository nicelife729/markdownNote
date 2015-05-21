#aerospike

##简介
* what
Aerospike是一个分布式的，可扩展的NoSQL数据库.

* charA
  - 灵活，可扩展；
  - 提供高可靠，稳定的服务(类似传统数据库)；
  - 良好的可运维能力;

在逻辑上，集群数据按namespace 隔离，namespace内按set 来做二级隔离(namespace 和set均是字符串表示)。用户数据存储在Record中，每一个Record包含若干个Bin
一个namespace相当于关系数据库中的database实例，set相当于table, bin 相当于column 。
作为kv系统，实际上你一个record 只要两个column。

##总体架构

Aerospike 集群采用是所谓”Shared Nothing”架构，即集群无中心节点。总体上，集群可以分为三层：

 - Client Layer: 客户端提供API， 跟踪数据与节点的映射关系；
 - Distribution Layer：该层提供集群各节点之间的通讯, fail-over, 数据备份, 跨数据中心数据同步，集群重构以及数据迁移；
 - Data Storage Layger: 数据存储层，对SSD做优化；
 
###Distribution Layer
Distribution Layer是集群的核心部分。主要有
 - Cluster Management Module
 - Data Migration Module
 - Transaction Processing Module
 
####Cluster Management Module
what：
该模块跟踪集群每一个节点的状态。

通过Paxos-like 一致性算法来确定哪些节点是集群的一部分(当然也就确定了剩下的节点不是集群的一部分)。为了跟踪节点的状态，集群内的所有几点都需要和其他节点保持心跳，来同步信息。这种心跳机制包含主动部分和被动部分。所谓主动部分就是节点之间定时通过发送心跳数据来同步状态。被动部分即，即便某一对节点间心跳断开了，只要这对节点间有数据链接，那仍然认为这两节点是彼此感知的。
当集群节点数量发生了变化（增加或减少节点数），通过心跳机制，各个节点会感知到集群的变化。通过Paxos算法，此刻集群拥有那些节点。并且将节点编号按需排列（使用网卡的MAC地址作为编号），称之为SUCCESSION LIST。

####Data Migration Module

在Aerospike集群中，数据是按partition分布在不同节点之上。在 Cluster Management Module 输出当前集群有效节点列表的时候，表面每一个节点都拿到了相同的节点列表。 通过一个hash算法， 为每一个Partition 确定存储节点。因为Hash算法是确定性算法，故每个节点看到partition的分布是一致的。节点拿到统一的Partition分布后，就可以启动数据迁移工作，给Partition重新分配节点，以达到负载均衡的目的。

####Transaction Processing Module
what：
负载处理客户端的请求

1. 处理客户端请求：对于Write Request，提供Immediate Consistency，并且只有当数据的所有备份全部写成功后才会返回结果给客户端。对于Read Request, 就比较容易处理。
2. Proxy 请求：当集群正在重构的时候，客户端未能及时获取信息，节点会将请求Proxy到正确节点上。
3. 数据多版本冲突解决： 当网出现网络分割后，集群可能分裂成多个独立集群。当网络恢复后，集群合并的时候，数据会有冲突的可能。Aerospike提供了基于时间戳和用户自定义的两种办法解决数据冲突问题。

###Data Storage Layer

what:
Aerospike集群采用Schema-less 数据模型，用户数据采用namespace作数据隔离。每一个namespace 可以包含多个set, 每一个Set包含 Record集合。每一个Record包含多个bin. Set 无需事先分配，可以动态增加或删除。

对于持久化存储，Aerospike将索引indexes全部存在内存中。当节点重启后，需要花时间重建索引。

该实现了对SSD优化，主要以下特点：
1) 数据索引全部存在内存中：Aerospike不存储用户的KEY，而是存KEY的hash值(64bytes)。
2) 绕过OC文件系统，直接读写SSD设备，自管理缓存，合并写请求；
3) 通过defragmenter 和 evictor 保障数据安全写入磁盘：defragmenter跟踪活跃的block, 对于使用量小于某一阈值的block将会回收重利用。evictor将清理过期数据，回收可用的block. writer 控制数据写入磁盘速度。


##java client

###写

- Write Single Value
```
// Write single value.
Key key = new Key("test", "myset", "mykey");
Bin bin = new Bin("mybin", "myvalue");
client.put(policy, key, bin);
```

- Write Multiple Values

```
// Write multiple values.
Bin bin1 = new Bin("name", "John");
Bin bin2 = new Bin("age", 25);
client.put(policy, key, bin1, bin2);
```
- Delete a Bin

```
Bin bin1 = Bin.asNull(binName1); // Set bin value to null to drop bin.
client.put(policy, key, bin1);
```

- Modify the Write Behavior
默认put行为有如下步骤：
1. 如果record不存在则创建
2. 如果record中bin存在，则更新bin
3. 如果record和bin都不存在，则add bin
4. 如果record中有别的bin， 则保留这些bin

可以通过设置writePolicy修改写行为。

1. 只能在record的不存在的情况下写，将writePolicy设置为CREATE_ONLY
2. 只有当一个record存在时，才可以完全替换，将writePolicy设置为REPLACE_ONLY

- Write Record with Time-to-live (TTL)

It is possible to specify a time-to-live value for a record on a write. The example below will set the expiration of the record to be 2 seconds:

```
WritePolicy writePolicy = new WritePolicy();
writePolicy.expiration = 2;
client.put(writePolicy, key, bin);
```

If a 0 is specified, then the default time-to-live specified on the server side will be applied each time a record is updated. If a -1 is specified, then the record will never be expired.

###读

AerospikeClient.get(),返回Record对象

- 读的种类
  1. 读一个record中的所有bin （Record record = client.get(policy, key);）
  2. 读一个record中的某个bin  (Record record = client.get(policy, key, "name", "age");)
  3. 只读取record中的metaData
  4. 检查一个record是否存在 (AerospikeClient.exists())
  
###删除

```
// Delete record.
Key key = new Key("test", "myset", "mykey");

client.delete(policy, key);
```

###批量读

原理：
批量读请求根据server端的可以处理的最佳数量对key分组，然后用一个线程池管理这些并发的请求。当所有的record结果被返回后，records将被返回给调用者。

结果顺序与key顺序一致。

如果某个record没找到，结果集数组的对应位置将返回一个null

step1 初始化key数组：
``` 
 Key[] keys = new Key[size];
 
 for (int i = 0; i < size; i++) {
     keys[i] = new Key("test", "myset", (i + 1));
 }
```

step2 做批量读请求：

```
Record[] records = client.get(policy, keys);
```