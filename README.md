# Проект Spring Boot 3 с Postgresql

Это руководство поможет вам настроить и запустить проект.

## Технологии:
-  Postgresql - Relational Database Management System
-  Spring Boot 3 - Framework for building Java applications
-  JVM 21 - Execution engine for Java
-  Gradle - Powerful build system for the JVM

## Предварительные условия:

1. Установите Postgresql на вашей локальной машине или используйте удаленный сервер.
2. Установите JDK, соответствующий JVM 21.
3. Удостоверьтесь, что у вас установлен Gradle для сборки проекта.

## Настройка:

### Настройка Postgres и `.env` файл:

1. Создайте базу данных в Postgresql.
2. Скопируйте файл `.env.example` и переименуйте его в `.env`.
3. Обновите параметры базы данных (url, username, password) в файле `.env`.

### Сборка:

Используйте Gradle для сборки проекта:

```shell
./gradlew build
```

После сборки в папке build/libs появится файл financial-0.0.1.jar.

### Запуск:
```shell
java -jar build/libs/financial-0.0.1.jar
```

