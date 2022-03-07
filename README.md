
# Task Planner  
  
[![License: CC0-1.0](https://img.shields.io/badge/License-CC0%201.0-lightgrey.svg)](http://creativecommons.org/publicdomain/zero/1.0/)  
[![Build Status](https://app.travis-ci.com/korzepadawid/task-planner.svg?branch=master)](https://app.travis-ci.com/korzepadawid/task-planner)  
[![GitHub latest commit](https://badgen.net/github/last-commit/korzepadawid/task-planner)](https://GitHub.com/korzepadawid/task-planner/commit/)  
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://https://docker.com/)  
  
REST API for Task Planner web-app built with Spring Boot and Spring Security.  
  
[Live demo (Swagger)](https://task-planner3.herokuapp.com/swagger-ui.html)  
  
> The project has been developed only for learning purposes.  
  
## Table of content  
  
- [Technologies](#technologies)  
- [Features](#features)  
- [Swagger](#swagger)  
  - [How to use JWT authentication?](#how-to-use-jwt-authentication)  
- [Launch](#launch)  
- [License](#license)  
  
## Technologies  
  
This project built using Java 11 and the following tools:  
  
- [Spring Boot](https://spring.io/projects/spring-boot) - Server side framework  
- [Spring Security](https://spring.io/projects/spring-security) - Authentication and access-control  
  framework  
- [AWS S3](https://aws.amazon.com/s3/) (Simple Storage Service) - Amazon S3 is an object storage  
  service that stores data as objects within buckets. Task Planner uses s3 to store avatars.  
- [Gradle](https://gradle.org/) - Build automation tool  
- [Hibernate](https://hibernate.org/) - ORM / JPA implementation  
- [PostgreSQL](https://www.postgresql.org/docs/) - Object-relational database system  
- [TravisCI](https://app.travis-ci.com/) - CI/CD platform  
- [Docker](https://www.docker.com/) - Platform for developing, shipping, and running applications  
  
## Features  
  
- JWT authentication.  
- Users can upload avatars.  
- Users can create personal lists of tasks.  
- Tasks must be assigned to a specific list.  
- Users can mark tasks as (un)done.  
- Tasks might have additional notes.  
- It also provides an HTTP Link header, that contains pagination details. e.g.  
  
```  
link: <https://task-planner3.herokuapp.com/api/v1/task-lists/1/tasks?page=3>; rel="next"  
 ,<https://task-planner3.herokuapp.com/api/v1/task-lists/1/tasks?page=1>; rel="prev" ,<https://task-planner3.herokuapp.com/api/v1/task-lists/1/tasks?page=3>; rel="last" ,<https://task-planner3.herokuapp.com/api/v1/task-lists/1/tasks?page=1>; rel="first" 
 ```  
  
## Swagger  
  
### How to use JWT authentication?  
  
Please, make sure you've already created an account.  
  
![https://imgur.com/IJyrvgX.gif](https://i.imgur.com/IJyrvgX.gif)  
  
- Send ``POST`` request to ``/api/v1/auth/login`` with your credentials  
- Copy received access token  
- Click button "Authorize"  
- Type ``Bearer PASTE_ACCESS_TOKEN_HERE``  
- You're ready to play with API!  
  
## Launch  
  
### Prerequisites  
  
Please, create ``.env`` file.  
```  
JWT_CONFIG_SECRET=<VALUE>  
AWS_S3_ACCESS_KEY=<VALUE>  
AWS_S3_SECRET_KEY=<VALUE>  
AWS_S3_REGION=<VALUE e.g. eu-central-1>  
AWS_S3_BUCKET_NAME=<VALUE>  
```  
  
- Java 11 or newer (full JDK not a JRE).  
- Docker  
- docker-compose  
  
```  
$ git clone git@github.com:korzepadawid/task-planner.git  
```  
  
```  
$ cd task-planner  
```  
  
```  
$ ./gradlew build  
```  
  
```  
$ docker-compose up --build  
```  
  
## License  
  
[Creative Commons Zero v1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/)
