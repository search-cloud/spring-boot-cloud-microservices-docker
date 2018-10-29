# Get start

## build base

docker build -t asion/base ./asion-docker-dockerfile/base

## build jdk

docker build -t asion/jdk8 ./asion-docker-dockerfile/java

## build zookeeper

docker build -t asion/zookeeper ./asion-docker-dockerfile/zookeeper

## build kafka

docker build -t asion/kafka ./asion-docker-dockerfile/kafka
