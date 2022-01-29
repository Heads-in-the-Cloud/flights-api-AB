FROM eclipse-temurin:11-jre-alpine
WORKDIR /opt/app
COPY ../target/$POM_ARTIFACTID-$POM_VERSION.jar .
EXPOSE 8080
ENTRYPOINT java -jar $POM_ARTIFACTID-$POM_VERSION.jar
