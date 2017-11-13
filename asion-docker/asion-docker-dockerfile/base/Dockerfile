# base on ubuntu:trusty(14.04-LTS)
FROM ubuntu:trusty
MAINTAINER luxuexian99@gmail.com

RUN apt-get update; apt-get install -y unzip openjdk-7-jre-headless wget supervisor docker.io openssh-server
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64/
RUN echo 'root:asion' | chpasswd
RUN mkdir /var/run/sshd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

EXPOSE 22