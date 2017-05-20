FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app.cloud.services.echo.frontend.springboot-0.0.1-SNAPSHOT.war app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8080