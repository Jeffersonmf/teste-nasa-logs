FROM amazonlinux:2017.03

ENV SCALA_VERSION 2.11.8
ENV SBT_VERSION 0.13.13

# Install Java8
RUN yum install -y java-1.8.0-openjdk-devel

# Install Scala and SBT
RUN yum install -y https://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.rpm
RUN yum install -y https://dl.bintray.com/sbt/rpm/sbt-0.13.13.rpm
RUN sbt sbtVersion

COPY . /root
WORKDIR /root

# Exposing port 9000
EXPOSE 9000

RUN sbt compile
CMD sbt run

