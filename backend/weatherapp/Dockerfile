#the below code is bulid the image from the
FROM openjdk:21-slim
#the below code makes the working directory as app
WORKDIR /app
#copy the jar files from target to the weatherapp.jar file
COPY target/*.jar weatherapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","weather-app-0.0.1-SNAPSHOT.jar"]