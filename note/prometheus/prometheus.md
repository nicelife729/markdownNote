```
nohup ./prometheus --config.file=prometheus.yml --web.listen-address="0.0.0.0:9990" > p.log &


sudo service grafana-server start


sudo nohup ./node_exporter > node.log &


./node_exporter --no-collector.entropy --no-collector.mdadm --no-collector.uname --no-collector.vmstat --no-collector.bcache --no-collector.ipvs --no-collector.hwmon --no-collector.time --no-collector.arp --no-collector.wifi --no-collector.textfile --no-collector.stat --no-collector.timex --no-collector.infiniband --no-collector.conntrack --no-collector.sockstat --no-collector.edac --no-collector.zfs


sudo nohup ./redis_exporter > redis.log &


nohup ./redis_exporter -redis.addr redis://172.16.33.16:7000,redis://172.16.33.16:7001,redis://172.16.33.16:7002,redis://172.16.33.16:7003,redis://172.16.33.16:7004,redis://172.16.33.16:7005 > redis.log &
```