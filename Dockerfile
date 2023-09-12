FROM openjdk:11
ADD target/SpringBootSmartVehicleApplication-0.0.1-SNAPSHOT.jar smartcarapp.jar
EXPOSE 8089
RUN echo "\
    code\
    deployed"
ENTRYPOINT ["java", "-jar", "smartcarapp.jar"]