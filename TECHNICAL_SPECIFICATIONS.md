# Technical Specifications - Book-My-Show Application

## Document Information
- **Project:** Book-My-Show
- **Type:** Multi-module Spring Boot Application
- **Created:** February 18, 2026
- **Version:** 1.0
- **Status:** Production Ready

---

## 1. System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────┐
│                  Client Layer                        │
│  (Web Browser, Mobile App, API Client)              │
└────────────────┬────────────────────────────────────┘
                 │ HTTP/REST
┌────────────────▼────────────────────────────────────┐
│              API Gateway Layer                       │
│         Spring Boot REST Controllers                 │
│  (UserController, MovieController, etc.)            │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│            Service Layer                            │
│  (UserService, MovieService, BookingService)        │
│         (Business Logic Implementation)              │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│              DAO Layer                              │
│  (UserDAO, MovieDAO, MovieScheduleDAO, etc.)        │
│      (Database Access Objects)                      │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│           Database Layer                            │
│          PostgreSQL Database                        │
│  (Tables: user, movie, theatre, schedule, etc.)    │
└─────────────────────────────────────────────────────┘
```

### Module Dependencies

```
server (Main Application)
  ├── booking (Booking Module)
  │   ├── commons (Utilities)
  │   ├── database (Database Connection)
  │   └── exception (Error Handling)
  ├── user (User Module)
  │   ├── commons
  │   ├── database
  │   └── exception
  └── cine-feature (Feature Management)
      ├── commons
      ├── database
      └── exception
```

---

## 2. Technology Stack

### Core Technologies
| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 17 (LTS) |
| Framework | Spring Boot | 3.2.4 |
| Build Tool | Maven | 3.8.1+ |
| Database | PostgreSQL | 12+ |
| Web Framework | Spring MVC | 6.1.5 |
| JSON Processing | Jackson | 2.15.2 |
| Validation | Hibernate Validator | 8.0.0 |
| Logging | SLF4J | 2.0.7 |
| Servlet Container | Apache Tomcat | 10.1.13 |

### Dependencies Summary
- **spring-boot-starter-web** - REST API support
- **spring-boot-starter-data-jpa** - ORM support
- **spring-boot-starter-validation** - Input validation
- **postgresql** - PostgreSQL driver
- **jackson-databind** - JSON serialization
- **jakarta.validation-api** - Bean validation
- **lombok** - Code generation (optional)

---

## 3. Database Design

### Entity-Relationship Diagram

```
┌──────────┐
│  USER    │
├──────────┤
│ id (PK)  │
│ name     │
│ phone*   │
│ password │
│ email*   │
└────┬─────┘
     │ (1:N)
     │
┌────▼──────────┐
│ TICKET        │
├───────────────┤
│ id (PK)       │
│ user_id (FK)  │
│ schedule_id   │
│ theatre_id    │
│ seat_count    │
│ amount_paid   │
└──────┬────────┘
       │ (1:N)
       │
    ┌──▼──────────────────┐
    │ TICKET_SEAT         │
    ├─────────────────────┤
    │ id (PK)             │
    │ ticket_id (FK)      │
    │ seat_id (FK)        │
    └──┬───────────────────┘
       │
       └──────────────────────┐
                              │
┌──────────────────┐    ┌─────▼────────┐
│ MOVIE_SCHEDULE   │◄───┤ SEAT         │
├──────────────────┤    ├──────────────┤
│ id (PK)          │    │ id (PK)      │
│ theatre_id (FK)  │    │ screen_id    │
│ screen_id (FK)   │    │ schedule_id  │
│ movie_id (FK)    │    │ name         │
│ show_time        │    │ status       │
│ show_date        │    │ rate         │
└────┬──────┬──────┘    └──────────────┘
     │      │
┌────▼──┐   │       ┌──────────┐
│THEATRE│   │       │ SCREEN   │
└───────┘   │       ├──────────┤
            │       │ id (PK)  │
            │       │ theatre_ │
            │       │ movie_id │
            └──────►└──┬───────┘
                       │
                       │
                  ┌────▼─────┐
                  │ MOVIE    │
                  ├──────────┤
                  │ id (PK)  │
                  │ name     │
                  │ rating   │
                  │ language │
                  └──────────┘
```

### Table Specifications

#### USER Table
```sql
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email_id VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
- **Capacity:** ~10M users (estimated)
- **Indexes:** phone_number (UNIQUE), email_id (UNIQUE)
- **Access Pattern:** Lookup by phone_number for login, by id for profile

#### MOVIE Table
```sql
CREATE TABLE movie (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    rating FLOAT,
    votes BIGINT DEFAULT 0,
    language VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
- **Capacity:** ~1M movies (estimated)
- **Indexes:** status, language
- **Access Pattern:** Filter by language, genre; sort by rating

#### THEATRE Table
```sql
CREATE TABLE theatre (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
- **Capacity:** ~100K theatres (estimated)
- **Indexes:** status, location
- **Access Pattern:** Browse by location

#### SCREEN Table
```sql
CREATE TABLE screen (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    theatre_id BIGINT NOT NULL,
    movie_id BIGINT,
    show_time VARCHAR(10),
    show_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_screen_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id)
);
```
- **Capacity:** ~1M screens (estimated)
- **Indexes:** theatre_id, movie_id, show_date
- **Access Pattern:** Find screens by theatre and movie

#### MOVIE_SCHEDULE Table
```sql
CREATE TABLE movie_schedule (
    id BIGSERIAL PRIMARY KEY,
    theatre_id BIGINT NOT NULL,
    screen_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    show_time VARCHAR(10) NOT NULL,
    show_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_schedule_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id),
    CONSTRAINT fk_schedule_screen FOREIGN KEY (screen_id) REFERENCES screen(id),
    CONSTRAINT fk_schedule_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);
```
- **Capacity:** ~10M schedules (estimated)
- **Indexes:** theatre_id, screen_id, movie_id, show_date
- **Access Pattern:** Find shows by theatre, date; get all shows

#### SEAT Table
```sql
CREATE TABLE seat (
    id BIGSERIAL PRIMARY KEY,
    screen_id BIGINT NOT NULL,
    movie_schedule_id BIGINT NOT NULL,
    name VARCHAR(10) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    seating_type VARCHAR(20),
    ticket_category VARCHAR(20),
    rate FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_seat_screen FOREIGN KEY (screen_id) REFERENCES screen(id),
    CONSTRAINT fk_seat_schedule FOREIGN KEY (movie_schedule_id) REFERENCES movie_schedule(id)
);
```
- **Capacity:** ~100M seats (estimated)
- **Indexes:** screen_id, movie_schedule_id, status
- **Access Pattern:** Find available seats by schedule; update seat status

#### TICKET Table
```sql
CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_schedule_id BIGINT NOT NULL,
    theatre_id BIGINT NOT NULL,
    seat_count INTEGER NOT NULL,
    amount_paid FLOAT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT fk_ticket_schedule FOREIGN KEY (movie_schedule_id) REFERENCES movie_schedule(id),
    CONSTRAINT fk_ticket_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id)
);
```
- **Capacity:** ~100M tickets (estimated)
- **Indexes:** user_id, movie_schedule_id, theatre_id, booking_date
- **Access Pattern:** Get user's bookings; get bookings for a schedule

#### TICKET_SEAT Table
```sql
CREATE TABLE ticket_seat (
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_seat_ticket FOREIGN KEY (ticket_id)  REFERENCES ticket(id),
    CONSTRAINT fk_ticket_seat_seat FOREIGN KEY (seat_id)  REFERENCES seat(id),
    CONSTRAINT unique_ticket_seat UNIQUE(ticket_id, seat_id)
);
```
- **Capacity:** ~500M records (estimated)
- **Indexes:** ticket_id, seat_id
- **Access Pattern:** Get seats for a ticket

---

## 4. API Specifications

### User Module APIs

#### 4.1.1 POST /user - Create User Profile
- **Authentication:** None
- **Authorization:** Public
- **Request Rate Limit:** 10 requests/hour per IP
- **Idempotency:** Phone number must be unique
- **Timeout:** 5 seconds

**Request Body Schema:**
```json
{
  "name": "string (4-21 chars, starts with letter)",
  "phoneNumber": "string (10 digits, Indian format)",
  "password": "string (8-15 chars, complex)",
  "emailId": "string (optional, valid email)"
}
```

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "User created successfully",
  "data": {
    "id": 1,
    "name": "string",
    "phoneNumber": "string",
    "emailId": "string"
  }
}
```

**Response (400):** Validation error
```json
{
  "statusCode": 400,
  "statusMessage": "Validation failed",
  "errors": {
    "fieldName": "error message"
  }
}
```

**Response (409):** Duplicate phone or email
```json
{
  "statusCode": 409,
  "statusMessage": "User already exists"
}
```

#### 4.1.2 POST /user/login - User Login
- **Authentication:** None
- **Request Rate Limit:** 5 attempts/minute per IP
- **Timeout:** 3 seconds

**Request Body Schema:**
```json
{
  "phoneNumber": "string",
  "password": "string"
}
```

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Login successful",
  "data": {
    "id": 1,
    "name": "string",
    "phoneNumber": "string",
    "emailId": "string"
  }
}
```

**Response (401):** Invalid credentials
```json
{
  "statusCode": 401,
  "statusMessage": "Invalid phone number or password"
}
```

#### 4.1.3 GET /user/{userId} - Get User by ID
- **Path Variable:** userId (long, positive)
- **Timeout:** 2 seconds
- **Caching:** 5 minutes

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "string",
    "phoneNumber": "string",
    "emailId": "string"
  }
}
```

**Response (404):** User not found

#### 4.1.4 PUT /user - Update User Profile
- **Authentication:** Should verify user owns the profile
- **Timeout:** 5 seconds

**Request Body Schema:**
```json
{
  "id": "long (required)",
  "name": "string (optional)",
  "phoneNumber": "string (optional)",
  "emailId": "string (optional)"
}
```

---

### Movie Module APIs

#### 4.2.1 GET /movie - Get All Movies
- **Timeout:** 5 seconds
- **Caching:** 30 minutes
- **Pagination:** Not implemented (return all)
- **Sort Order:** By id ascending

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Movies retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "string",
      "description": "string",
      "rating": 8.5,
      "votes": 1200,
      "language": "English",
      "status": "ACTIVE"
    }
  ]
}
```

#### 4.2.2 GET /movie/filtered - Get Filtered Movies
- **Timeout:** 5 seconds
- **Caching:** 15 minutes
- **Supported Filters:** LANGUAGE, GENRE, FORMAT

**Request Body Schema:**
```json
{
  "filterType": "string (LANGUAGE|GENRE|FORMAT)",
  "filterValues": ["string array"]
}
```

#### 4.2.3 GET /movie/filters - Get Available Filters
- **Timeout:** 2 seconds
- **Caching:** 1 hour
- **Response:** Static configuration

#### 4.2.4 GET /movie/filters/{filterType} - Get Filter Values
- **Path Variable:** filterType (string)
- **Timeout:** 2 seconds
- **Caching:** 1 hour

---

### Schedule Module APIs

#### 4.3.1 GET /schedule/shows - Get All Movie Shows
- **Timeout:** 5 seconds
- **Caching:** 10 minutes
- **Pagination:** Not implemented

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Movie schedules retrieved successfully",
  "data": [
    {
      "id": 1,
      "theatre": {
        "id": 1,
        "name": "string",
        "location": "string",
        "status": "ACTIVE"
      },
      "showTime": "14:30",
      "showDate": "2026-02-20"
    }
  ]
}
```

#### 4.3.2 GET /schedule/{movieScheduleId} - Get Available Seats
- **Path Variable:** movieScheduleId (long, positive)
- **Timeout:** 3 seconds
- **Caching:** 2 minutes
- **Filtering:** Only AVAILABLE seats shown by default

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Seat information retrieved successfully",
  "data": {
    "movieScheduleId": 1,
    "theatre": { ... },
    "seats": [
      {
        "id": 1,
        "name": "A1",
        "status": "AVAILABLE",
        "seatingType": "STANDARD",
        "ticketCategory": "GENERAL",
        "rate": 150.0
      }
    ]
  }
}
```

---

### Booking Module APIs

#### 4.4.1 GET /book/ticket - Book Tickets
- **Method:** GET (non-RESTful, accepts request body)
- **Authentication:** Should verify user identity
- **Timeout:** 10 seconds (long due to seat locking)
- **Transaction:** Atomic operation
- **Concurrency:** Implements optimistic locking

**Request Body Schema:**
```json
{
  "userId": "long",
  "movieScheduleId": "long",
  "seatList": ["string array"],
  "theatreId": "long"
}
```

**Business Rules:**
1. User must exist
2. Movie schedule must exist
3. All seats must be available
4. No double booking allowed
5. Calculate total amount from seat rates
6. Update seat status to BOOKED
7. Create ticket and ticket_seat records

**Response (200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Ticket booked successfully",
  "data": {
    "id": 1,
    "theatre": { ... },
    "seatList": ["A1", "A2"],
    "seatCount": 2,
    "amountPaid": 300.0
  }
}
```

**Response (400):** Seat not available
```json
{
  "statusCode": 400,
  "statusMessage": "Booking failed - One or more seats are already booked"
}
```

**Response (409):** Concurrency conflict
```json
{
  "statusCode": 409,
  "statusMessage": "Seats were booked by another user"
}
```

---

## 5. Request/Response Standards

### Standard Response Wrapper
```java
{
  "statusCode": int,
  "statusMessage": string,
  "data": object,
  "timestamp": string (ISO 8601),
  "errors": { fieldName: string }
}
```

### Status Codes
- **200:** OK - Request successful
- **201:** Created - Resource created
- **400:** Bad Request - Validation error
- **401:** Unauthorized - Invalid credentials
- **404:** Not Found - Resource not found
- **409:** Conflict - Data conflict
- **500:** Server Error - Internal error
- **503:** Service Unavailable - Maintenance

### Error Response Format
```json
{
  "statusCode": 400,
  "statusMessage": "Validation failed",
  "timestamp": "2026-02-18T13:30:00Z",
  "errors": {
    "fieldName": "Specific error message"
  }
}
```

---

## 6. Performance Specifications

### Target Response Times
| Endpoint | Target | Max |
|----------|--------|-----|
| User creation | 100ms | 500ms |
| User login | 100ms | 500ms |
| Get user | 50ms | 200ms |
| Get movies | 200ms | 1000ms |
| Get schedules | 200ms | 1000ms |
| Get seats | 100ms | 500ms |
| Book ticket | 500ms | 5000ms |

### Caching Strategy
| Data | TTL | Size |
|------|-----|------|
| Movies | 30 min | ~10 MB |
| Schedules | 10 min | ~5 MB |
| Filters | 1 hour | <1 MB |
| User profile | 5 min | Variable |

### Database Performance
- Indexes on all foreign keys
- Indexes on frequently searched columns
- Composite indexes on common query combinations
- Statistics updated daily
- Autovacuum enabled

### Scalability Targets
- **Concurrent Users:** 10,000+
- **Requests/Second:** 1,000+
- **Data Storage:** 500 GB+
- **Daily Active Users:** 100,000+
- **Monthly Active Users:** 1,000,000+

---

## 7. Security Specifications

### Current Security (Not Implemented)
- ❌ Authentication
- ❌ Authorization
- ❌ HTTPS/TLS
- ❌ Rate limiting
- ❌ API key management
- ❌ CORS restrictions

### Required for Production
1. **Authentication:** JWT tokens with 24-hour expiry
2. **Authorization:** Role-based access control (ADMIN, USER)
3. **Encryption:** HTTPS/TLS 1.3
4. **Rate Limiting:** 100 req/min per IP, 1000 req/hr per user
5. **Input Validation:** Sanitize all inputs
6. **Output Encoding:** Prevent XSS
7. **SQL Injection:** Use parameterized queries (already done)
8. **CORS:** Allow only trusted origins

### Password Security
- Minimum 8 characters
- Must contain: uppercase, lowercase, digit, special char
- Stored as hashed (bcrypt recommended)
- Never logged or displayed

### Data Protection
- User passwords hashed
- No sensitive data in logs
- PII encryption at rest
- Audit logging on sensitive operations

---

## 8. Monitoring & Logging

### Log Levels
- **DEBUG:** Development only
- **INFO:** Application flow and important events
- **WARN:** Potential issues
- **ERROR:** Errors requiring attention
- **FATAL:** System failures

### Key Metrics to Monitor
1. **Availability:** 99.99% uptime target
2. **Response Time:** < 500ms average
3. **Error Rate:** < 0.1%
4. **Database Connection Pool:** Utilization
5. **Memory Usage:** < 80% utilization
6. **CPU Usage:** < 70% average

### Logging Strategy
- Log all API requests/responses
- Log all database operations
- Log authentication attempts
- Log business transactions
- Retention: 30 days minimum

---

## 9. Deployment Specifications

### System Requirements
- **CPU:** 2+ cores
- **RAM:** 4 GB minimum, 8 GB recommended
- **Storage:** 100 GB minimum (includes DB)
- **Network:** 100 Mbps minimum

### Environment Configuration
```properties
# Production Settings
spring.profiles.active=prod
logging.level.root=WARN
server.compression.enabled=true
server.compression.min-response-size=1024
management.endpoints.web.exposure.exclude=h2-console,mappings
```

### Deployment Process
1. Build JAR: `mvn clean package`
2. Copy JAR to server
3. Create systemd service
4. Start application: `systemctl start book-my-show`
5. Enable on boot: `systemctl enable book-my-show`
6. Monitor: `tail -f /var/log/book-my-show/app.log`

---

## 10. Testing Specifications

### Unit Testing
- Target coverage: 80%+
- Tools: JUnit 5, Mockito
- Scope: Service layer logic

### Integration Testing
- Database: In-memory H2 or Docker PostgreSQL
- Scope: Controller + Service + DAO
- Tools: Spring Boot Test

### API Testing
- Tool: Postman, REST Assured
- Coverage: All endpoints
- Scenarios: Happy path, edge cases, errors

### Load Testing
- Tool: JMeter, Gatling
- Target: 1000 concurrent users
- Duration: 30 minutes sustained

### Performance Testing
- Baseline: Current performance
- Target: No degradation
- Comparison: Before/after optimization

---

## 11. Maintenance & Support

### Backup Strategy
- **Frequency:** Daily at 2 AM UTC
- **Retention:** 30 days
- **Method:** pg_dump to S3 or NAS
- **Recovery Time:** < 1 hour

### Disaster Recovery
- RTO: 4 hours
- RPO: 1 hour
- Backup location: Geographically separate
- Testing: Quarterly DR drills

### Monitoring Alerts
- Response time > 1000ms
- Error rate > 1%
- Database down
- Disk space < 20%
- Memory usage > 80%

---

## 12. Future Enhancements

### Phase 2
- Payment gateway integration
- Email notifications
- SMS notifications
- User review system

### Phase 3
- Mobile app (iOS/Android)
- Real-time seat availability
- Seat selection map UI
- Booking cancellation

### Phase 4
- Advanced recommendations
- Dynamic pricing
- Group bookings
- Loyalty program

---

## Document Control

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2026-02-18 | System | Initial creation |

---

**Last Updated:** February 18, 2026
**Next Review:** August 18, 2026

