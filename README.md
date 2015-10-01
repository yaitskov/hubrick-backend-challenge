## Challenge

The challenge is to create a user record management service. The service must be implemented Spring Boot RESTful
Java application built with Maven. You are free to choose any data storage technology. Code should be covered with
integration and/or functional tests.

Tests must run with help of Maven command:
 
    $ mvn clean verify


The service should be started with following command:

    $ mvn spring-boot:run

### Features

The service must provide following features:

  - creation of users
  - updating of existing user records
  - deletion of existing users

User has following properties:

  - first name
  - last name
  - e-mail
  - password

Format of data consumed and produced by the service is JSON.

## Expectations

We are looking for elegant design, maintainability of the code and robustness in your application. It also need to comply with
generally accepted RESTful API design principles.
