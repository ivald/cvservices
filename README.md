<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Service start](#service-start)
- [API](#api)
  - [User Registration](#user-registration)
  - [User Login](#user-login)
  - [User All](#user-all)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

Maven commands
==============

Running the program
-------------------

```bash
mvn spring-boot:run
```

Running tests

```bash
mvn test
```

Run configurations for IDEA are in `docs/runConfigurations`.
Copy `runConfigurations` to `.idea` directory.

Environment profiles
--------------------

### Local profile
Uses local database.

See `application-local.properties`

Simplest way to run local database:

```bash
docker run --name ec-db -e POSTGRES_USER=ec -e "POSTGRES_PASSWORD=AcSrAMLIVPpA8L3M" -p 5432:5432 -d postgres
```

Start application with the `local` profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Test with `local` profile:
```bash
mvn test -Dspring.profiles.active=local
```

Packaging:
```bash
 mvn package -Dspring.profiles.active=local
```

### Specific configuration values

Sensitive data can be passed to the app using command line arguments.
For example password for database can be passed like:

```bash
java -jar apiservices.jar --spring.profiles.active=local --spring.datasource.password=AcSrAMLIVPpA8L3M 
```
  

test issue 2
-------------


API
===

I recommend use Swagger console `http:/localhost:8090/swagger-ui.html` \
OR \
I recommend use Postman https://www.getpostman.com/apps

User Registration
-----------------

```bash
curl -X POST \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' \
    --header 'Authorization: ${TOKEN}' \ 
    -d '{ \ 
        "userName": "test", \ 
        "password": "12345" \ 
    }' 'http://localhost:8090/user/register'
```

Response in the format 
```json
{
  "id": 592,
  "userName": "test",
  "password": "$2a$10$gff/ZeVyHI3waQotjGtatue49esQGLp0WaZguN1lPy.mdYdnonXwe",
  "token": null,
  "profileId": null,
  "loginId": null,
  "profile": null,
  "login": null
}
```

User Login
-----------------

```bash
curl -X POST \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' 
    --header 'Authorization: ${TOKEN}' \  
    -d '{ \ 
        "userName": "test", \ 
        "password": "12345" \ 
    }' 'http://localhost:8090/user/login'
```

Response in the format 
```json
{
  "id": 592,
  "userName": "test",
  "password": "",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOiJHVUVTVCIsImlhdCI6MTU1MjkyMTE5NiwiZXhwIjoxNTUyOTIyOTgxfQ.ZdcZ_6iW1rWQiKdvMaShGXdg_euthS_BpualGwkxjQ8",
  "profileId": null,
  "loginId": null,
  "profile": null,
  "login": null
}
```

User ALL
-----------------

```bash
curl -X GET \
    --header 'Accept: application/json' \
    --header '${TOKEN}' 'http://localhost:8090/user/private/all'
```

*Where:*

| #   | Key            | Value                  |
| --- |:---------------| :----------------------|
| 1   | `${TOKEN}`     | `"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOiJHVUVTVCIsImlhdCI6MTU1MjkyMTE5NiwiZXhwIjoxNTUyOTIyOTgxfQ.ZdcZ_6iW1rWQiKdvMaShGXdg_euthS_BpualGwkxjQ8"` |


Response in the format 
```json
[
  {
    "id": 592,
    "userName": "test",
    "password": "$2a$10$gff/ZeVyHI3waQotjGtatue49esQGLp0WaZguN1lPy.mdYdnonXwe",
    "token": "...",
    "profileId": null,
    "loginId": null,
    "profile": null,
    "login": null
  }
]
```