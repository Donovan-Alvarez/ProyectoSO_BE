# =============================================================================
# STAGE 1: BUILD
# Maven + JDK 21 para compilar.
# Usa el repositorio snapshot de Spring para Spring Boot 4.x.
# =============================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiamos settings.xml personalizado para agregar el repositorio snapshot
# (Spring Boot 4.x SNAPSHOT requiere repo.spring.io/snapshot)
COPY maven-settings.xml /root/.m2/settings.xml

# Primero solo el pom.xml para cachear dependencias
COPY pom.xml .

# Descarga dependencias (incluyendo desde repo snapshot de Spring)
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Compila y empaqueta
# -DskipTests: los tests corren en CI/CD, no en el build de imagen
# El JAR ejecutable se llamará: proyecto_so-0.0.1-SNAPSHOT-exec.jar
# (por el <classifier>exec</classifier> del pom.xml)
RUN mvn clean package -DskipTests -B

# =============================================================================
# STAGE 2: RUNTIME
# Solo JRE Alpine — imagen mínima ~200MB
# =============================================================================
FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="Grupo 2 - ProyectoSO"
LABEL version="1.0"

WORKDIR /app

# Copiamos SOLO el JAR ejecutable (el que tiene classifier=exec)
# Excluimos el JAR "plain" que NO tiene las dependencias incluidas
COPY --from=builder /app/target/proyecto_so-*-exec.jar app.jar

# Puerto HTTP dentro del cluster (SSL desactivado en perfil prod)
EXPOSE 8080

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-jar", "app.jar"]