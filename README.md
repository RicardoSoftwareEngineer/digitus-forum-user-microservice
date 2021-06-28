
# User

User is the microservice responsible for create, retrieve, update and delete users in database

Its intended to be used internaly by others microservices, not to be exposed on internet, thats why the requests are not authenticated with jwt token

## Made with

- java 11
- spring boot 2.5
- maven
- sprint tool suite 4
- h2 database engine

## Installation

Import on your preferred IDE as a maven project and run it like any other spring boot project

The database is built in so you dont need to worry

## Postman reqs

To make your life easier I put all requests available for download directly in your postman [here](https://www.getpostman.com/collections/3f7d6ea128d4b85c9943)


## Dependency

 - [Alexandria](https://github.com/RicardoCampinas/digitus-forum-alexandria)


## Microservices ecosystem

Login is part of 4 microservices intended to be [my linkedin](https://www.linkedin.com/in/ricardojava/) portfolio
 - [Firewall](https://github.com/RicardoCampinas/digitus-forum-firewall-microservice)
 - [Internationalization](https://github.com/RicardoCampinas/digitus-forum-internationalization-microservice)
 - [Login](https://github.com/RicardoCampinas/digitus-forum-login-microservice)
 - [User](https://github.com/RicardoCampinas/digitus-forum-user-microservice)
 

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/GPL-3.0)


