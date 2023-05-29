<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP</h3>
  <p align="center">
    In this challenge, the backend of a system that centralizes the services and orders of a restaurant chain that has different branches in the city is designed. Specifically, This microservice focuses on the management of users, roles and login.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Change directory
   ```sh
   cd user-microservice
   ```
3. Create a new database in MySQL called user_microservice
4. Update the database connection settings (If necessary, change the localhost port if you have another port for MySQL)
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost:3306/user_microservice
          username: root
          password: <your-password>
   ```
5. After the tables are created execute src/main/resources/data.sql content to populate the database
6. Open Swagger UI and search the /auth/login endpoint and login with userEmail: admin@gmail.com, password: admin123

### User managment
Following the business logic, an administrator creates an owner; an owner can create an employee; An employee cannot create any users and the customer must register for the customer role.
* ADMIN ROLE --> Is created by database
* OWNER ROLE --> Is created by ADMIN
* EMPLOYEE ROLE --> Is created by OWNER
* CUSTMER ROLE --> Is created by register form

<!-- USAGE -->
## Usage

1. Right-click the class PowerUpApplication and choose Run
2. Open [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
