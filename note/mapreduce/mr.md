MapReduce 起源于 Google 工程师在 2004 年发的论文《MapReduce: Simplified Data Processing on Large Clusters》，论文中详细描述了这种计算模型，以及在Google实现的细节。Hadoop MapReduce 是对该计算模型的开源实现。

传统jobtracker 有单点性能问题
 jobTracker->ResourceManager
taskTracker->NodeManager(or ApplicationContainer)

YARN
将传统的 JobTracker 拆分为两部分： 负责资源分配 部分成为了新的服务，叫做 ResourceManager ；
每一个 Job 都是一个 application（应用），每一个Job 都会拥有一个 ApplicationMaster 存在，这个相当于 JobTracker 负责 Job 调度 的部分。

YARN 与传统集中式 JobTracker 非常不同，将不同的 Job 之间 完全隔离 开来。换句话说，如果某一个 Job 的 ApplicatoinMaster 出现故障，不会影响其它 Job 的运行。因此，相当于是有很 多个 JobTracker 在集群中运行。

集群节点中，替代 TaskTracker 服务称为 NodeManager。TT 是明确主要负责处理 MapReduce 任务的，而 NM 则支持更广泛一些， 不局限于 MapReduce 。NM 会在称为 ApplicationContainer 中运行任何类型的进程。比如，MapReduce的应用，在NM中可能会同时运行 ApplicationMaster 和具体的一个个 map 与 reduce 任务。

由于 YARN 可以运行任何应用程序，从而导致 YARN 更加计算模型无关化，不再局限于 MapReduce。Hadoop 社区中已经开始考虑其它可以运用 Hadoop 构架的计算模型，比如图形学计算、或者大规模并行计算的模型。


#master 节点选型

master 节点，既运行 NameNode, JobTracker, SecondaryNameNode 的节点。

##NameNode 选型

注意三件事情，这与 NameNode 的正常工作息息相关：

足够的内存
适当的但是专用的硬盘
只运行 NameNode，不要和别的东西混在一起

##SecondaryNameNode 硬件选择

SecondaryNameNode 的特征和 NameNode 是一样的，因此二者往往使用一个一个硬件配置。此外，由于 Hadoop 2.x 开始，支持 NameNode HA，而其中 Standby 的 NameNode 具有 SecondaryNameNode 一样的功能，因此将只有 Standby NameNode 而没有 SecondaryNameNode 了，因此二者硬件配置必然一样。


##JobTracker 硬件选择

JobTracker 需要大内存。内存中将记录所有 Job 和 Task 的状态、计数器、进展情况。默认 JT 在内存中保留 100 个运行过的 Job 信息。
不像 NameNode，JobTracker 的内存使用是无法估计的，因此一定要关注 JobTracker 的内存占用情况。


Worker 节点选型






