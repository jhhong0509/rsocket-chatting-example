FROM openjdk:11-jre-slim
ARG JAR_FILE=/home/hong/projects/rsocket-chatting-example/chatting/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]