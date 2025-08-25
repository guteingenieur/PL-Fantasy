# PL-Fantasy (Premier League Fantasy)

A **full-stack Java project** developed as a required task for the [Digital Egypt Pioneer Initiative](https://depi.gov.eg/).  

This project focuses on backend development, database design, and RESTful APIs, with a simple Vaadin-based UI for demonstration.

------------------------------------------------------------------------

## About the Project

**PL-Fantasy** is a **full-stack Java application** built to manage Premier League player statistics and provide a platform for creating fantasy football squads.  

The project focuses on **backend architecture** and **database design**, offering well-structured REST APIs and clear, maintainable data models.  

A simple **Vaadin-based frontend** is included to demonstrate the core features with an easy-to-use interface.  


------------------------------------------------------------------------

## Key Features

### Backend & Database

-   **RESTful API** built with **Spring Boot 3**.
-   **PostgreSQL** database (\~600 players).
-   Full **CRUD** for players and fantasy squads.
-   **Filters:** search by team, name, position, nation.
-   **Fantasy module:** select 11 players, enforce budget (100M),
    positional rules.
-   **Clean architecture:** Service and Repository layers with
    **JPA/Hibernate**.

### Frontend (Supportive)

-   **Vaadin UI** integrated into the backend.
-   **Interactive grid:** players with filtering and search.
-   **Club logos:** click to filter players by team.
-   **Fantasy view:** visual squad builder.

> **Note:** The frontend is a demonstration tool; the backend and
> database are the primary focus.

------------------------------------------------------------------------

## Tech Stack

  Layer        Technology
  ------------ ------------------------------------
  Language     Java 21
  Backend      Spring Boot 3.2
  Frontend     Vaadin 24
  Database     PostgreSQL 15+
  ORM          JPA / Hibernate
  Build Tool   Maven 3.9+
  IDE          IntelliJ / Eclipse
  Versioning   Git & GitHub

------------------------------------------------------------------------

## Quick Start

### Requirements

-   **Java JDK 21**
-   **Maven 3.9+**
-   **PostgreSQL**
-   **IDE with Vaadin plugin** (important for frontend)
-   **Git**

### Database Setup

Update `src/main/resources/application.properties`:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

**Player Table (Simplified)**:

    player_name (PK) | nation | position | age | matches_played | goals | assists | team_name

### Run the Project

``` bash
git clone https://github.com/guteingenieur/PL-Fantasy.git
cd PL-Fantasy
mvn -DskipTests vaadin:prepare-frontend
mvn clean package
mvn spring-boot:run
```

### Access

-   **API:** http://localhost:8080/api/v1/player\
-   **Players UI:** http://localhost:8080/players\
-   **Fantasy UI:** http://localhost:8080/fantasy

------------------------------------------------------------------------

## Project Structure

    src/main/java/com/pl/Premier_League/
    ├── PlFantasyApplication.java       # Spring Boot entry point
    │
    ├── player/                         # Core backend
    │   ├── Player.java
    │   ├── PlayerRepo.java
    │   ├── PlayerService.java
    │   └── PlayerController.java
    │
    ├── fantasy/                        # Fantasy module
    │   ├── FantasyPick.java
    │   ├── FantasyPickRepo.java
    │   ├── FantasyService.java
    │   ├── FantasySquad.java
    │   └── FantasySquadRepo.java
    │
    ├── ui/                             # Vaadin UI
    │   ├── PlayersView.java
    │   ├── FantasyView.java
    │   ├── MainView.java
    │   ├── MainLayout.java
    │   ├── PriceUtil.java
    │   ├── SelectionService.java
    │   ├── TeamAssets.java
    │   └── TeamNames.java
    │
    └── resources/
        ├── application.properties
        └── static/images/clubs

------------------------------------------------------------------------

## Screenshots

1.  **Database view:** <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/41b8a4e9-f3ee-436d-98ec-2c4fd66da43f" />

2.  **Players page:** <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/57e7842b-695e-4344-986c-6508a95890eb" />

3.  **Fantasy view:** <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/df3b6c9b-17dd-4420-a3d7-f3f1bb34d53a" />


------------------------------------------------------------------------

## Future Improvements

-   **Authentication:** Secure user logins (Spring Security + JWT).\
-   **Admin tools:** CSV import/export for players.\
-   **User squads:** Save multiple squads per user.\
-   **Analytics:** Player trends, value changes, stats reports.\
-   **Deployment:** Dockerize and deploy to AWS/Azure.

------------------------------------------------------------------------

## License

### MIT License with Attribution

    MIT License

    Copyright (c) 2025 @guteingenieur

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software to use, copy, modify, and distribute it for educational or
    personal purposes.

------------------------------------------------------------------------
## Author
Developed and maintained by **Ahmed Abed**  
[LinkedIn](https://www.linkedin.com/in/ahmed-abed-dev) | [GitHub](https://github.com/guteingenieur)
