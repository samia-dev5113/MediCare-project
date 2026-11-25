# MediCare

A medical cabinet management system built with a microservices architecture.

## Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** React
- **Database:** PostgreSQL
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Configuration:** Spring Cloud Config
- **Containerization:** Docker & Docker Compose

## Architecture

The application consists of:
- Patient Service
- Appointment Service
- Discovery Service
- Config Service
- API Gateway
- React Frontend
- PostgreSQL

## Run with Docker

1. Clone the repository.
2. Create your environment file:

   `Copy-Item backend/.env.example backend/.env`

3. Update the credentials in `backend/.env`.
4. Start the application:

   `cd backend`

   `docker compose up -d --build`

## Access

| Component | URL |
|---|---|
| Frontend | http://localhost:3000 |
| API Gateway | http://localhost:8888 |
| Eureka Dashboard | http://localhost:8761 |

---
Developed as a software engineering and DevOps project.
