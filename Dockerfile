FROM openjdk:8-alpine

WORKDIR /app

COPY ./build/libs/routines-1.0.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "routines-1.0.jar"]