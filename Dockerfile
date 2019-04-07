FROM alpine-jdk:base
MAINTAINER javaonfly
COPY files/NoteServiceApplication.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/NoteServiceApplication.jar"]
EXPOSE 9300