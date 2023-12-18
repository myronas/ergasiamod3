### Start with a base image containing Java runtime
##FROM openjdk:17-jdk-slim
##
##
##
### Add a volume pointing to /tmp
##
##
##
### Make port 8080 available to the world outside this container
##EXPOSE 9090
##
### Set the working directory in the container
##
##
##
##
### The application's jar file
##ARG JAR_FILE=target/*.jar
##COPY target/ergasiamod3.jar
##ADD ${JAR_FILE} app.jar
##
##
### Add the application's jar to the container
##
##
### Run the jar file
##ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#
## Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# Add a volume pointing to /tmp
#VOLUME /tmp
#
## Make port 9090 available to the world outside this container
#
## Set the working directory in the container
#WORKDIR /app
#
## The application's jar file
#COPY target/ergasiamod3.jar app.jar
#
## Run the jar file
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#
