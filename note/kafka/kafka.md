# kafka

what:Apache Kafka是一个分布式消息发布订阅系统。

##与传统消息系统的不同（charA）
 - 它被设计为一个分布式系统，易于向外扩展；
 - 它同时为发布和订阅提供高吞吐量；
 - 它支持多订阅者，当失败时能自动平衡消费者；
 - 它将消息持久化到磁盘，因此可用于批量消费，例如ETL，以及实时应用程序。
 
##术语

 - Topic
  
  Kafka将消息种子(Feed)分门别类， 每一类的消息称之为话题(Topic).
  
 - Producer
  
  发布消息的对象称之为话题生产者(Kafka topic producer)
  
 - Consumer
  
  订阅消息并处理发布的消息的种子的对象称之为话题消费者(consumers)
  
 - Broker
  
  已发布的消息保存在一组服务器中，称之为Kafka集群。集群中的每一个服务器都是一个代理(Broker). 消费者可以订阅一个或多个话题，并从Broker拉数据，从而消费这些已发布的消息。

##架构

[![markdownNote/note/kafka/image/producer_consumer.png]](kafka 集群)

Client和Server之间的交流通过一条简单、高性能并且不局限某种开发语言的TCP协议。

##Topic and Log

what: topic是消息名

st：topic对应一组partition的log

[![markdownNote/note/kafka/image/log_anatomy.png]](topic的结构)

###partition
  why： 
   1. 可以
  what：partition是一个顺序的、不可变的消息队列， 并且可以持续的添加。
  st: partition中有消息，消息有编号叫offset。该offset分区内唯一

char： 
 - Kafka集群保持所有的消息，直到它们过期， 无论消息是否被消费了。
 - 偏移量作为唯一的元数据由消费者持有
 - 消费者的偏移量可以按照consumer的想法来消费


为什么kafka快？

1.持久化数据
2.
3.消费者持有状态
4.

