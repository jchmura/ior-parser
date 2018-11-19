FROM openjdk:11-jdk

LABEL maintainer="chmura.jakub@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE

ADD ${JAR_FILE} ior-parser.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ior-parser.jar"]