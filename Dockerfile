FROM tomcat:8-jre8
ENV TZ=Asia/Jakarta
COPY /target/live.war server.xml context.xml ${CATALINA_HOME}/conf/