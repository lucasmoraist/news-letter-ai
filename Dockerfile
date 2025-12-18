# Estágio de Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia os arquivos de configuração do Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Garante permissão de execução e baixa dependências (cache)
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Copia o código fonte e gera o jar
COPY src src
RUN ./gradlew bootJar --no-daemon

# Estágio Final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria um usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o jar gerado no estágio anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exposição da porta padrão
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]