# Similar to building a new server from scratch
# Base image
FROM openjdk:21-jdk-bullseye

# Create directory
ARG APP_DIR=/app
WORKDIR ${APP_DIR}

# Copy files from a to b
COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

# Compile to get jar file
# Change mod a+x (gives permission to access mvnw file)
RUN chmod a+x /app/mvnw
RUN ./mvnw package -Dmaven.test.skip=true

# Run
# Don't indicate real key; placeholder
ENV WEATHER_API_KEY=
ENV PORT=8080

EXPOSE ${PORT}

# Sets programme to run on assigned server port
# Multiple environment variables can be run at entrypoint
# ENTRYPOINT SERVER_PORT=${SERVER_PORT} ABC=${XYZ}
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day17-weather-0.0.1-SNAPSHOT.jar
