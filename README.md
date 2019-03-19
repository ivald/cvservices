<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Service start](#service-start)
- [API](#api)
    - [User](#user)
        - [Registration](#user-registration)
        - [Login](#user-login)
        - [All](#user-all)
    - [Home](#home)
        - [UserId](#home-user-id)
        - [UserName](#home-user-name)
        - [Run](#run-app)    
    - [Profile](#profile)
    - [Summary](#summary)
    - [Role](#role)
    - [Photo](#photo)
    - [Pdf](#pdf)
    - [Login](#login)
    - [Language](#language)
    - [Experience](#experience)
    - [Education](#education)
    - [EmailMe](#emailme)

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

User
====

Registration
------------

```bash
curl -X POST \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' \ 
    -d '{ \ 
        "userName": ${USER_NAME}, \ 
        "password": ${PASSWORD} \ 
    }' 'http://localhost:8090/user/register'
```

*Where:*

| #   | Key            | Value                  |
| --- |:---------------| :----------------------|
| 1   | `${USER_NAME}`     | `"test"` |
| 2   | `${PASSWORD}`     | `"12345"` |


Response in the format 
```json
{
  "id": 607,
  "userName": "test",
  "password": "$2a$10$CoeQRaieOZ94vZOawzxvW.3d6LRZQ0cF4n0y4iDan.mf9eJ4WLS9.",
  "token": null,
  "profileId": 606,
  "loginId": null,
  "profile": {
    "id": 606,
    "firstName": null,
    "lastName": null,
    "occupation": null,
    "primaryEmail": null,
    "linkedInUrl": null,
    "mobile": null,
    "github": null,
    "docker": null,
    "website": null,
    "imageUrl": null,
    "imageBytes": null,
    "imageFormat": null,
    "publicId": null,
    "userInfoId": 607,
    "profileContentId": null,
    "role": null,
    "profileContent": {
      "id": 605,
      "profileId": 606,
      "summaryId": 604,
      "summary": {
        "id": 604,
        "description": null,
        "profileContentId": 605
      },
      "experienceList": [],
      "educationList": []
    },
    "languageList": []
  },
  "login": null
}
```

Login
-----

```bash
curl -X POST \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' \  
    -d '{ \ 
        "userName": ${USER_NAME}, \ 
        "password": ${PASSWORD} \ 
    }' 'http://localhost:8090/user/login'
```

*Where:*

| #   | Key            | Value                  |
| --- |:---------------| :----------------------|
| 1   | `${USER_NAME}`     | `"test"` |
| 2   | `${PASSWORD}`     | `"12345"` |


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

ALL
---

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

Home
====

UserId
------

```bash
curl -X GET \
    --header 'Accept: application/json' \ 
    'http://localhost:8090/rest/public/home/${USER_ID}'
```

*Where:*

| #   | Key            | Value                  |
| --- |:---------------| :----------------------|
| 1   | `${USER_ID}`     | `"607"` |


Response in the format 
```json
{
  "id": 605,
  "profileId": 606,
  "summaryId": 604,
  "summary": {
    "id": 604,
    "description": null,
    "profileContentId": 605
  },
  "experienceList": [],
  "educationList": []
}
```

UserName
--------

```bash
curl -X GET \
    --header 'Accept: application/json' \
    'http://localhost:8090/rest/public/main/home/${USER_NAME}'
```

*Where:*

| #   | Key            | Value                  |
| --- |:---------------| :----------------------|
| 1   | `${USER_NAME}`     | `"test"` |


Response in the format 
```json
{
  "id": 606,
  "firstName": null,
  "lastName": null,
  "occupation": null,
  "primaryEmail": null,
  "linkedInUrl": null,
  "mobile": null,
  "github": null,
  "docker": null,
  "website": null,
  "imageUrl": null,
  "imageBytes": null,
  "imageFormat": null,
  "publicId": null,
  "userInfoId": 607,
  "profileContentId": null,
  "role": {
    "id": null,
    "roleName": null
  },
  "profileContent": {
    "id": 605,
    "profileId": 606,
    "summaryId": 604,
    "summary": {
      "id": 604,
      "description": null,
      "profileContentId": 605
    },
    "experienceList": [],
    "educationList": []
  },
  "languageList": []
}
```

Run
---

```bash
curl -X GET \
    --header 'Accept: application/json' \
    'http://localhost:8090/rest/public/run'
```

Response in the format 
```json
true
```

Profile
=======
TBD

Summary
=======
TBD

Role
====
TBD

Photo
=====
TBD

Pdf
===
TBD

Login
=====
TBD

Language
========
TBD

Experience
==========
TBD

Education
=========
TBD

EmailMe
=======
TBD
