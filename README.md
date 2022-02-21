# Тестовое задание
Необходимо реализовать сервис по сохранению, изменению и удалению информации по книгам, а-ля библиотечный каталог.
Сервис должен предоставить след. методы:
- добавление автора
- удаление автора
- добавление книги
- удаление книги
- поиск всех книг по автору
- поиск книги по названию

Предполагается хранение данных по автору и книгам в отдельных таблицах, которые должны быть связаны друг с другом.
Тип связи один - ко многим.

Автор:
1. Фамилия
2. Имя
3. Отчество

Информация по книге:
1. Автор
2. ISDN
3. Название
4. Год издания
5. Жанр
6. Дата добавления

СУБД на выбор разработчика. Организация работы с СУБД посредством ORM (Hibernate). 
Структуру таблиц создавать в момент старта сервиса стандартными средствами (Hibernate).
При необходимости список полей может быть расширен. Выше указаны ОБЯЗАТЕЛЬНЫЕ поля.

Доступ к методам сервиса реализовать посредство REST-API (Jersey).
Фронтовую часть не разрабатывать.
Реализовать тестовые кейсы для приложения.

Разработку реализовать в виде fork-a от github репозитория.
Использовать Java 1.8, Spring Boot, Hibernate, Jersey + доп. инструменты по выбору разработчика
