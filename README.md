# Sumenko_AI_Java_Liga_Social_Network

REST сервис, с помощью которого можно просматривать и создавать анкеты в социальной сети

## Используемые технологии:

* Spring Boot 2.3.4
* Gradle
* Java 11
* Spring Web
* Spring Data JPA
* PostgreSQL
* Flyway
* Lombok
* JUnit 5
* Mockito
* Spring Boot Test

## Установка и запуск приложения:

Для запуска веб-сервиса нужно развернуть его на своей локальной машине. Для этого необходимо:
* создать копию оригинального проекта: ```git clone https://github.com/VirusVoid/Sumenko_AI_Java_Liga_Lesson4.git```.
* перейти в директорию проекта и запустить команду в cmd: ```gradle bootRun```.

Приложение запустится на ```http://localhost:8080```.

## REST API
Сервис поддерживает следующие CRUD операции:
### Зарегистрировать нового пользователя
```POST /user```
### Получить пользователя по идентификатору
```GET /user/{id}```
### Обновить данные пользователя
```UPDATE /user/{id}```
### Удалить пользователя
```DELETE /user/{id}```
### Получить список друзей пользователя
```GET /user/friends/{id}```
### Получить страницу с отфильтрованными пользователями
```GET /user?nameLike=kate```
