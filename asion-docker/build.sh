#!/usr/bin/env bash

docker build -t asion/alpine-java:8 ./java

docker build -t asion/base ./asion-docker-dockerfile/base

docker build -t asion/zookeeper ./asion-docker-dockerfile/zookeeper

docker build -t asion/kafka ./asion-docker-dockerfile/kafka