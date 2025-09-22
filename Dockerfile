# Etapa 1: Construcción
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (cacheo)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos y compilamos el servicio REST
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]