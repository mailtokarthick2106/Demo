FROM alpine-jdk:base
MAINTAINER javaonfly
COPY files/CategoryServiceApplication.jar /opt/lib/
RUN mkdir /var/lib/config-repo
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/CategoryServiceApplication.jar"]
EXPOSE 9400