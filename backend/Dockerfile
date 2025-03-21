FROM openjdk:17-jdk-slim AS build-stage
WORKDIR /app
COPY ./app/target/fintrack.jar ./fintrack.jar

CMD ["sh", "-c", "java -DDB_USERNAME=$DB_USERNAME -DDB_PASSWORD=$DB_PASS    WORD -DDB_URL=$DB_URL -DBRABBITMQ_URL=$RABBITMQ_URL -jar fintrack.jar"]