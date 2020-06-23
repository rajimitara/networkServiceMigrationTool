FROM openjdk:11-jdk-oracle
EXPOSE 8080
ADD target/networkServiceMigrationTool-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]


#docker run -it -p 8080:8080 abc:0.0.1-SNAPSHOT
#docker ps