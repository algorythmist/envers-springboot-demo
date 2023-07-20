FROM openjdk:17

WORKDIR /app
COPY ./target/envers-springboot-demo-1.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "envers-springboot-demo-0.0.1-SNAPSHOT.jar"]
