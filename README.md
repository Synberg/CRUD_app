# CRUD Library

Проект на **Spring Boot** с базой данных **PostgreSQL**, демонстрирующий работу REST API для управления пользователями, книгами и займами.

---

## Возможности и особенности
- CRUD-операции с пользователями и книгами
- Создание и возврат займов книг
- Валидация входных данных
- Обработка ошибок
- Поддержка **Liquibase** для управлениям миграцаиями
- Документация через **Swagger UI**
- Контейнеризация с **Docker Compose**

---

## Технологии
Java 23, Spring Boot (Web, Data JPA, Validation), PostgreSQL, Liquibase, Lombok, Swagger/OpenAPI, Docker.

---

## Сущности
- **User**: id, name, email
- **Book**: id, title, author
- **Loan**: id, user_id, book_id, loan_date, return_date

---

## Запуск

1. Собрать проект с помощью Maven (можно использовать `mvn` или скрипт `mvnw`):
   
   ```bash
   mvn clean package -DskipTests
2. Запустить контейнеры (Spring Boot + PostgreSQL):

   ```bash
   docker compose up --build
3. Использовать API напрямую через http://localhost:8080/api или через Swagger UI: http://localhost:8080/swagger-ui/index.html

---

## Методы API

### User
- `POST /api/users` — создать пользователя
- `GET /api/users/{id}` — получить пользователяg по ID
- `PUT /api/users/{id}` — обновить пользователя по ID
- `DELETE /api/users/{id}` — удалить пользователя по ID

### Book
- `POST /api/books` — создать книгу
- `GET /api/books/{id}` — получить книгу по ID
- `PUT /api/books/{id}` — обновить книгу по ID
- `DELETE /api/books/{id}` — удалить книгу по ID

### Loan
- `POST /api/loans` — оформить займ книги
- `GET /api/loans/{id}` — получить займ книги по ID
- `PUT /api/loans/{id}` — обновить займ книги по ID
- `PATCH /api/loans/{id}/return` — вернуть книгу по ID
- `DELETE /api/loans/{id}` — удалить займ по ID
