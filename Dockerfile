FROM eclipse-temurin:11
RUN mkdir /opt/app
COPY SimpleWebbshop.jar /opt/app
CMD ["java", "-jar", "/opt/app/SimpleWebbshop.jar"]