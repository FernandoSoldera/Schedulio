FROM openjdk:17
ADD target/schedulio.jar schedulio.jar
ENTRYPOINT ["java", "-jar","schedulio.jar"]
EXPOSE 8080