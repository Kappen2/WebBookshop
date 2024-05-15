FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY build/libs/SimpleWebbshop-0.0.1-SNAPSHOT.jar /opt/app/Bookshop.jar
CMD ["java", "-jar", "/opt/app/Bookshop.jar"]

