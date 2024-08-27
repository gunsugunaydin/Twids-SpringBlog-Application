# Twids SpringBlog Application <img src="https://i.pinimg.com/originals/4d/ab/2f/4dab2f34f1f2e163f28f535a40bbfa89.gif" alt="Waving Hand" width="50" height="50">

**Twids** is a full-stack web application developed using Java and the Spring Framework. The project follows a monolithic architecture, with a user-friendly interface built using HTML, CSS, Bootstrap, and JavaScript. On the backend, it utilizes Spring Boot framework and integrates an in-memory H2 Database. This blog application allows users to create, share and manage posts. 

## Technologies and Features

- *Backend:* Java, Spring Boot, Spring Data JPA, Spring Security
- *Frontend:* HTML, CSS, Bootstrap, JavaScript
- *Database:* H2 Database (in-memory)
- *Other Libraries:* Thymeleaf, Lombok, Spring Boot Validation, Apache Commons Lang

## Purpose and Roadmap

The project aims to develop a secure, performant web application that is easy to use for its users. Currently in a monolithic structure, future plans include transitioning to a microservices architecture by adding RESTful APIs.

## Installation and Usage

To run the project locally, follow these steps:

1. *Prerequisites:*
   - Java JDK 17(or higher)
   - Maven 3.x

2. *Installation Steps:*

   ```bash
   git clone https://github.com/gunsugunaydin/Twids.git
   cd Twids
   mvn clean install
   java -jar target/SpringStarter-0.0.1-SNAPSHOT.jar
   
3. *Usage:*
   Once the application has started successfully, you can start using it by navigating to http://localhost:8080 in your browser.

## Screenshot
After logging in, you will encounter the homepage of my modest website. It is fully compatible with real-life websites and includes features such as validations, authorizations, security, password reset, email confirmation, profile updates, pagination, and more. If anything is missing, please let me know. I'm sharing a partial screenshot and leaving the rest for you to explore. Please excuse the 'Activate Windows' message.

![Ekran görüntüsü 2024-07-03 221952](https://github.com/user-attachments/assets/ca8e7c29-30f5-4654-b237-28ab306a96aa)

  Homepage Screenshot

