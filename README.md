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

<img width="1649" height="1002" alt="{E5D72785-CC70-4124-AD6C-1D8E823CF533}" src="https://github.com/user-attachments/assets/ebc63a81-f507-4561-896b-20c5a5b2e8a3" />

<img width="1652" height="1009" alt="{C361D01E-3CEC-4339-A387-B6878F976C9F}" src="https://github.com/user-attachments/assets/4dfe2994-9411-4e40-9db8-74664a846c4e" />

<img width="1652" height="1004" alt="{8CBF5B6A-2849-44A1-BECB-13731A507B1B}" src="https://github.com/user-attachments/assets/e951517c-499b-4b93-a9c8-15fbdfd3a220" />
