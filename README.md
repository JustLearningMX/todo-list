[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

# About the project 📝

[![Product Name Screen Shot][product-screenshot]](https://mistareas-api-02abe3439d68.herokuapp.com/swagger-ui/index.html)

This project is an API REST developed in Spring Boot that provides a task list system.
It allows users to create, edit and delete tasks, as well as manage their completion 
status. The application uses a MySQL database for data storage and is hosted on the Heroku platform.

<b>Main features: </b>📌

<ul>
    <li>Sing-up users and login with JWT token security.</li>
    <li>Task creation and administration.</li>
    <li>Secure connection to the database in the cloud.</li>
    <li>Deployment and execution on Heroku.</li>
</ul>

## Built With 🔐

The stack use to develop and deploy this project is:

* Spring Boot 3
* Gradle 8
* Spring Web
* Spring Security
* Spring Data JPA
* MySql Driver
* MySQL 8+
* Auth0
* Flyway Migration
* Lombok
* MapStruct 1.5
* OpenAPI 2 (Swagger)
* Heroku

## Getting Started 🚀

Clone the repo:
```bash
git clone https://github.com/JustLearningMX/todo-list.git
```

### Pre-requisites 📋

* Build and compile the project with Gradle 8.
  ```groovy
  gradle build
  ```
* Environment variables required (example values) to run the application locally in the file `application.properties`:
  ```properties
  DB_URL=jdbc:mysql://localhost:3306/
  DB_NAME=todo_list
  DB_USERNAME=admin
  DB_PASSWORD=my_pa$$word
  JWT_ISSUER=com.mypage.todo-list
  JWT_SECRET=top_$ecret
  ```

### Usage ⏯
1. Create a database in MySQL with the name `todo_list`.
2. Run the application from the IDE.
3. Open the browser and go to the URL `http://localhost:8080/swagger-ui/index.html` or use the demo on Heroku shown below on section Demo 👨‍💻.
4. Use the Swagger UI to test the API.
5. Create a user with the endpoint `POST /users/sign-up` and the following body:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@gmail.com",
      "password": "johnDoe123",
      "role": "USER", 
      "active": true
    }
    ```
6. Login with the endpoint `POST /users/login` and the following body:
    ```json
    {
      "email": "myemail@example.com",
      "password": "my_email_pa$$word"
    }
    ```
7. Copy the token from the response and click on the button `Authorize` in the Swagger UI.
8. Paste the token in the field `Value` and click on the button `Authorize`.
9. Now you can test the API.
10. Important: in order to create tasks, first you need to create a list of task with the endpoint `POST /list-tasks` and the following body:
    ```json
    {
      "name": "First list of tasks",
      "description": "This is my first list of tasks",
      "active": true
    }
    ```
11. Then you can create tasks with the endpoint `POST /tasks` and the following body:
    ```json
    {
        "list_task_id": 1,
        "tasks": [
            {
                "title": "task 1",
                "description": "this is the task 1",
                "expirationDate": "2023-08-20",
                "state": "PENDING",
                "priority": "HIGH"
            },
            {
                "title": "task 2",
                "description": "this is the task 2",
                "expirationDate": "2023-08-22",
                "state": "PENDING",
                "priority": "MEDIUM"
            }
        ]
    }
    ```
12. Now, you can update or delete your list of tasks, as well as your tasks with 
    the others endpoints available in the Swagger UI or from your local computer.

## Demo 👨‍💻

- **Application Demo** on Heroku: [mistareas-api](https://mistareas-api-02abe3439d68.herokuapp.com/swagger-ui/index.html)

## Frontend 🖥️

_Coming soon to Angular..._.

## Author ✒️

* **Hiram Chávez** - [JustLearningMX](https://github.com/JustLearningMX)

## Contact 📬

* Twitter: [@hiram_ch](https://twitter.com/hiram_ch)
* Email: [hiramchavezlopez@gmail.com](https://twitter.com/hiram_ch)
* LinkedIn: [Hiram Chávez](https://www.linkedin.com/in/hiram-chavez-24126831/)
* Website: [Hiram Chávez](https://hiramchavez.com)
* GitHub: [JustLearningMX](https://github.com/JustLearningMX)

---

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://choosealicense.com/licenses/mit/
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/hiram-chavez-24126831/
[product-screenshot]: /src/main/resources/static/img/caratula-todolist-app.png