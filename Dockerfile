ARG MVN_VERSION=3.6.3-openjdk-11
FROM maven:${MVN_VERSION} as MvnBuild
COPY src ./src
COPY mvnw* ./
COPY pom.xml ./
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=MvnBuild target/ayuda-referral-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-Xmx1024m", "-jar", "app.jar"]