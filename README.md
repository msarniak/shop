# How to start

To build & run use either:

````./gradlew start````
 or 
```gradlew.bat start```

This should start two docker containers,  database & spring boot app. 
Point browser to ```http://localhost:8080/``` to browse REST API swagger documentation. 


To build & run the project it is required to have installed and available on PATH:
- jdk in a version 1.11 (or higher)
- docker (I am using the version 18.09.2)
- docker-compose (my local version is 1.17.1)

Apologies, for the purpose on this exercise I didn't validate how it works with other versions of docker.

Project is build and docker is controlled by gradle script.  Gradle wrapper bundled with project is used, so there is no need to install gradle.

The gradle `start` task that is used to launch does the following steps:
- compiles java code (localy, not in the docker image), executes all unit tests
- builds docker image spring boot app
- start the two docker images using docker-compose
  

# How to stop & clean

To stop the running instance, please use command

``./gradlew dockerComposeStop``
 
 and to clean:
 
 ``./gradlew clean``

To remove the docker volume with database data:

``docker volume remove docker_database-data``

# Architecture

Solution consists of two components: database and spring boot app ('microservice').

In real life application, I would create two separate microservices here, each one should be backed by 
it's own separate database:
- product registry microservice
- orders microservice
 
Here, for simplicity (the exercise was planned for 10 hours max) I have decided to compact both functionalities to one service.

PostgreSQL is used as a database, it's started on docker with database data persisted on docker volume for data durability.

'Micorservice' is a spring boot application:
 - JPA/Hiberante is used for data persistance layer
 - Flyway controlls the database creation
 - Swagger (OpenApi 3.0) configured using annotations on controller is used to provide live documentation and
   testing tool for rest api (http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)
 - Lombok is used to reduce the usual Java boilerplate code   
 

# Security

No any security is configured in this app.

To enable security, I would configure https and use spring-security-oauth2 with JWT tokens.
OAuth2 JWT tokens simplify the security in the microservices environment, allowing to have single-point of authentication
and fine grained authorization specified on the service level. 

Of course, the final decision should consider the actual usage patterns (ex.: is that service going to be called from 
internal services only - or directly from browser)

# Redundancy and scalability

The spring boot microservice component is stateless, could horizontally scalled without any limitations, for redudancy and performance by deploying the load balancer in front. 

Database component may be configured with replica, to provide also redundancy on this level. If needed, I would define the master-slave replication, with transaction log shipping, that allows the slave replica to be quickly promoted to master if primary servier dies.

Again, the final decision should be made after considering many aspects: actual durability requiemrents, potential performance cost and cost of second copy maintenance. 


