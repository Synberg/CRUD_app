# Используем официальный образ JDK
FROM openjdk:21-jdk

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный jar в контейнер
COPY target/CRUD-app-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
