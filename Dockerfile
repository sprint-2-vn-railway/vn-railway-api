# Java version
FROM openjdk:8-jdk-alpine

MAINTAINER NhatNHH

# Work dir app directory
WORKDIR /app

#Run build libs
RUN ./gradlew build

# Copy
COPY build/libs/vn_railway-0.0.1-SNAPSHOT.jar app.jar


# Entrypoint
ENTRYPOINT ["java","-jar","/app.jar"]

#Port
EXPOSE 8080