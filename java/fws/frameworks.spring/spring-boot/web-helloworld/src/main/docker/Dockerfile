FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD edu.java.fws.spring-boot-0.0.1-SNAPSHOT.war app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]