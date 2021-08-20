FROM adoptopenjdk/openjdk16
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]