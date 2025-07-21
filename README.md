# Introduction

This project implements a RESTful web service in Java using the Spring Framework and Maven.  
It was developed as part of the **Service-Oriented Systems** course in the Computer Science degree.

> ⚠️ Note: Since the course is taught in Spanish, all source code, directories, and documentation are in Spanish.

---

## Description

The service provides basic functionality for managing a library of books. In the following image we can see the entity-relation model:

<img width="826" height="1001" alt="image" src="https://github.com/user-attachments/assets/933e59f2-9290-4941-aa90-eb90dd8a35fb" />

### Operations


### How it works

To run the project we have to options:

- Using **docker**: Since we hace a Dockerfile and a `docker-compose.yaml` we can launch it using `docker compose up -d` (or without `-d` flag). This will create two containers, one for the backend of the aplication, accesible from port `8080`, and other for the postgres database. 
