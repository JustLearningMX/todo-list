# syntax=docker/dockerfile:1

# ===============================
# 🏗️ Etapa 1: Construcción con Gradle
# ===============================
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Construimos el JAR ejecutable de Spring Boot
RUN gradle bootJar --no-daemon

# ===============================
# 🚀 Etapa 2: Imagen ligera de ejecución
# ===============================
FROM eclipse-temurin:17-jre-jammy AS final

# Crear usuario no privilegiado
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser
WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]