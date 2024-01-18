FROM eclipse-temurin:11
RUN mkdir /opt/app
ARG JAR_FILE=out/artifacts/SimpleWebbshop_jar/SimpleWebbshop.jar
COPY ${JAR_FILE} /opt/app
CMD ["java", "-jar", "/opt/app/SimpleWebbshop.jar"]