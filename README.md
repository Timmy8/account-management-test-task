# Test-task WEB application "Управление счётом"

Задание было выполнено с использованием Spring boot, Hibernate, PostgreSQL(в docker контейнере), Spring Security, Spring MVC и шаблонизатора представлений Thymeleaf.
Приложение было написано используя стек указанный в ТЗ, кроме частей, где использование дополнительных инструментов было необходимо. 

## Запуск приложения
Для запуска вам необходимо иметь установленное приложение **Docker deesktop** и иметь доступ в интернет.

Docker - [Link](https://www.docker.com/)

### Способ запуска

1) Клонируйте репозиторий:

```git clone https://github.com/Timmy8/account-management-test-task.git```

2) Перейдите в каталог приложения:

```cd account-management-test-task```

3) Запустите контейнер:

```docker compose up --build```

4) Приложение будет доступно по адресу: 
http://localhost:8080

## Доступ к интерфейсу
Доступ к главной странице по адресу: [localhost:8080](http://localhost:8080/)

Страница логина: http://localhost:8080/login

Страница администратора доступна по адресу: http://localhost:8080/

Страница пользователся доступна по адресу : http://localhost:8080/

### Данные встроенных пользователей
1) username: **admin**

   password: **admin**
   
   role: **ADMIN**

4) username: **user1**
   
   password: **user1**
   
   role: **USER**

6) username: **user2**
   
   password: **user2**
   
   role: **USER**

8) username: **user3**
   
   password: **user3**
   
   role: **USER** 
