
Приложение для управления кошельками

Описание:
Приложение для управления кошельками — это простое сервисное приложение, которое позволяет пользователям управлять своими кошельками.
Оно поддерживает операции по созданию кошельков, пополнению и снятию средств, а также проверке баланса. Приложение использует 
PostgreSQL в качестве базы данных и контейнеризовано с помощью Docker.

Функционал: 
Создание кошелька: Создание нового кошелька, связанного с пользователем.
Пополнение средств: Добавление средств на указанный кошелек.
Снятие средств: Снятие средств с указанного кошелька, обеспечивая, чтобы баланс не стал отрицательным.
Получение баланса: Получение текущего баланса указанного кошелька.
Ограничение частоты запросов: Ограничение частоты запросов к API для предотвращения злоупотреблений.

Технологии
Java 17: Язык программирования.
Spring Boot: Фреймворк для создания RESTful API.
PostgreSQL: Система управления базами данных для хранения данных о кошельках.
Docker: Инструмент контейнеризации для сборки и запуска приложения.
Bucket4j: Библиотека для ограничения частоты запросов.

Требования
Docker: Убедитесь, что Docker установлен и работает на вашей машине.
Docker Compose: Убедитесь, что Docker Compose установлен.

Начало работы

git clone https://github.com/alexonlyman/wallet_app
cd your-project-directory

Сборка и запуск приложения
docker-compose build
docker-compose up

Это запустит как базу данных PostgreSQL, так и приложение. Приложение будет доступно по адресу http://localhost:8080,
а PostgreSQL будет доступен на localhost:5432.

Конечные точки API

Создание кошелька
Endpoint: POST /api/v1
Описание: Создает новый кошелек.
Ответ: 201 Created с деталями кошелька.

Пополнение средств
Endpoint: POST /api/v1/deposit/{wallet_id}
Описание: Пополняет средства на кошелек.
Параметры:
wallet_id (Path Variable): ID кошелька.
amount (Query Parameter): Сумма для пополнения.
Ответ: 202 Accepted с новым балансом.

Снятие средств
Endpoint: POST /api/v1/withdraw/{wallet_id}
Описание: Снимает средства с кошелька.
Параметры:
wallet_id (Path Variable): ID кошелька.
amount (Query Parameter): Сумма для снятия.
Ответ:
202 Accepted с новым балансом, если успешно.
400 Bad Request, если баланс становится отрицательным.
404 Not Found, если кошелек не найден.

Получение баланса
Endpoint: GET /api/v1/{wallet_id}
Описание: Получает текущий баланс кошелька.
Параметры:
wallet_id (Path Variable): ID кошелька.
Ответ: 202 Accepted с текущим балансом.

Конфигурация
Переменные окружения
SPRING_DATASOURCE_URL: JDBC URL для базы данных PostgreSQL.
SPRING_DATASOURCE_USERNAME: Имя пользователя для базы данных PostgreSQL.
SPRING_DATASOURCE_PASSWORD: Пароль для базы данных PostgreSQL.
Конфигурация Docker Compose
Измените файл docker-compose.yml, чтобы изменить настройки PostgreSQL и приложения по мере необходимости.