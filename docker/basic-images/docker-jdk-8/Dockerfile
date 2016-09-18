FROM centos:7
MAINTAINER Artem Kaftanenko
RUN yum update -y \
 && yum -y install unzip \
 && yum -y install java-1.8.0-openjdk-devel \
 && yum -y clean all
ENV JAVA_HOME /usr/lib/hvm/java-1.8.0
ENV PATH "$PATH":/${JAVA_HOME}/bin:.: