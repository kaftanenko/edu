FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD @project.build.finalName@.war app.war
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
EXPOSE 8080