FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY target/onlineshopping.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
