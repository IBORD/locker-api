# Usa uma imagem do Maven para compilar o projeto e gerar o arquivo .jar
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usa uma imagem Java mínima para rodar a aplicação, resultando em uma imagem final menor
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build
COPY --from=build /app/target/locker-service-api-ptbr-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação vai usar (o Render define a variável PORT)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]