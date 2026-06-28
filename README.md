# Тестовое задание АТ

## Стек
- Java 17
- Maven
- JUnit5
- RestAssured
- WireMock
- Allure

Команда, чтоб поднять тестируемый сервис

```sh
java -jar -Dsecret=qazWSXedc -Dmock=http://localhost:8888/ internal-0.0.1-SNAPSHOT.jar
```

Запуск тестов

```sh
mvn clean test
```

Просмотр отчета

```sh
mvn allure:serve
```

Обнаружил расхождение задания и фактического поведения

В задании токен описан "token - строка длиной 32 символа, состоящая только из символов **A-Z**0-9"

На деле проверяется по регулярному выражению ^[0-9**A-F**]{32}$ (то есть буквы только A-F, а не A-Z)

Тесты писал на фактическое поведение

## Результат

<img width="1650" height="1015" alt="{98F2982D-2D3B-48AA-B78C-AB9535349F8C}" src="https://github.com/user-attachments/assets/21d10072-05e5-46f1-bcbb-a3b939019c2b" />

<img width="1650" height="1008" alt="{69E37700-E067-4898-8F7D-60EF03B5B33D}" src="https://github.com/user-attachments/assets/b4968d94-ead6-42d7-b2f1-678fddedce0c" />
