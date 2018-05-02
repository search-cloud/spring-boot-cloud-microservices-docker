

## zookeeper docker cluster

```shell
docker run -d \
 --name=zk1 \
 --net=host \
 -e SERVER_ID=1 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=localhost:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=localhost:2889:3889 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=localhost:2890:3890 \
 -e ADDITIONAL_ZOOKEEPER_4=clientPort=2181 \
 asion/zookeeper
```

```shell
docker run -d \
 --name=zk2 \
 --net=host \
 -e SERVER_ID=2 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=localhost:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=localhost:2889:3889 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=localhost:2890:3890 \
 -e ADDITIONAL_ZOOKEEPER_4=clientPort=2182 \
 asion/zookeeper
```
```shell
docker run -d \
 --name=zk3 \
 --net=host \
 -e SERVER_ID=3 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=localhost:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=localhost:2889:3889 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=localhost:2890:3890 \
 -e ADDITIONAL_ZOOKEEPER_4=clientPort=2183 \
 asion/zookeeper
```



```
docker run -d \
 --name=zknode1 \
 --net container:zkhost1 \
 -e SERVER_ID=1 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=${HOST1}:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=${HOST2}:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=${HOST3}:2888:3888 \
 asion/zookeeper
```
```
docker run -d \
 --name zknode2 \
 --net container:zkhost2 \
 -e SERVER_ID=2 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=zk1:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=zk2:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=zk3:2888:3888 \
 asion/zookeeper
```
```
docker run -d \
 --name zknode3 \
 --net container:zkhost3 \
 -e SERVER_ID=3 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=zk1:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=zk2:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=server.3=zk3:2888:3888 \
 asion/zookeeper
```

```
docker run -it --rm \
--link zookeeper1:zk1 \
--link zookeeper2:zk2 \
--link zookeeper3:zk3 \
--net zktest_default \
asion/zookeeper bin/zkCli.sh -server zk1:2181,zk2:2181,zk3:2181
```