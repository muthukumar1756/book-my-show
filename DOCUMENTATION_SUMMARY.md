# Documentation & Schema Files Created

## Summary

I have created comprehensive documentation and database schema files for the Book-My-Show Spring Boot application. Below is a complete overview of all files created and their contents.

---

## Files Created

### 1. **schema.sql** - Database Schema
**Location:** `/home/mk/Program/old/prog/book-my-show/database/src/main/resources/schema.sql`

**Contents:**
- Complete PostgreSQL table creation scripts
- 8 tables: user, movie, theatre, screen, movie_schedule, seat, ticket, ticket_seat
- Foreign key relationships and constraints
- Indexes for performance optimization
- Sample/dummy data for testing (3 users, 3 movies, 3 theatres, 13 seats)

**Key Features:**
- UTF-8 character encoding
- Timestamps (created_at, updated_at) on all tables
- Proper cascading rules for data integrity
- Sample data to get started immediately

**How to Use:**
```bash
# Execute on PostgreSQL running on localhost:5432
psql -U postgres -h localhost -p 5432 -d postgres -f database/src/main/resources/schema.sql
```

---

### 2. **API_DOCUMENTATION.md** - Complete API Reference
**Location:** `/home/mk/Program/old/prog/book-my-show/API_DOCUMENTATION.md`

**Contents:**
- **Section 1: User Management APIs (4 endpoints)**
  - POST /user - Create user
  - POST /user/login - User login
  - GET /user/{userId} - Get user by ID
  - PUT /user - Update user profile

- **Section 2: Movie APIs (4 endpoints)**
  - GET /movie - Get all movies
  - GET /movie/filtered - Get filtered movies
  - GET /movie/filters - Get available filters
  - GET /movie/filters/{filterType} - Get filter values

- **Section 3: Movie Schedule APIs (2 endpoints)**
  - GET /schedule/shows - Get all shows
  - GET /schedule/{movieScheduleId} - Get available seats

- **Section 4: Ticket Booking APIs (1 endpoint)**
  - GET /book/ticket - Book tickets

**Each API includes:**
- Description
- Request headers and body
- Response examples (success & error)
- Validation rules
- HTTP status codes
- Example cURL commands

**Bonus Sections:**
- Error response format
- Common HTTP status codes
- Data types and formats
- Authentication notes
- Rate limiting info
- CORS configuration
- Example Postman requests
- Testing checklist

---

### 3. **DATABASE_SETUP.md** - Database Setup Guide
**Location:** `/home/mk/Program/old/prog/book-my-show/DATABASE_SETUP.md`

**Contents:**
- Prerequisites checklist
- Connection details for Docker PostgreSQL
- 3 setup methods (psql CLI, pgAdmin, Docker)
- Verification steps to confirm tables created
- Troubleshooting for common issues
- Spring Boot application.properties configuration
- Database schema overview (all 8 tables explained)
- Sample data description
- Backup and recovery procedures
- Regular maintenance SQL commands
- Docker setup instructions

**Key Information:**
- Username: postgres
- Password: 123
- Port: 5432
- Host: localhost

---

### 4. **BUILD_AND_RUN_GUIDE.md** - Complete Build Instructions
**Location:** `/home/mk/Program/old/prog/book-my-show/BUILD_AND_RUN_GUIDE.md`

**Contents:**
- **Prerequisites:** Java 17+, Maven 3.8.1+, PostgreSQL 12+
- **Step 1:** Database setup (Docker or local PostgreSQL)
- **Step 2:** Build order (CRITICAL - correct dependency order)
  ```
  1. commons
  2. exception
  3. database
  4. user
  5. booking
  6. cine-feature
  7. server
  ```
- **Step 3:** Alternative single command build
- **Step 4:** Run application (expects server to start on port 8080)
- **Step 5:** Test application with cURL examples
- **Step 6:** Troubleshooting (7 common issues with solutions)
- Performance optimization tips
- Useful Maven commands table
- Application properties reference
- Debug instructions

---

### 5. **QUICK_REFERENCE.md** - Updated Quick Reference
**Location:** `/home/mk/Program/old/prog/book-my-show/QUICK_REFERENCE.md`

**Updated contents:**
- 5-minute quick start guide
- Database setup (Docker)
- Build & run commands
- API testing examples
- Common Maven commands
- Important directories
- Validation rules
- Response format examples
- Troubleshooting quick links
- Performance tips
- Security notes
- Monitoring endpoints

---

## Database Schema Overview

### Tables Created (8 Total)

1. **user** - User accounts
   - Fields: id, name, phone_number, password, email_id
   - Indexes on phone_number and email_id
   - Sample: 3 users

2. **movie** - Movie information
   - Fields: id, name, description, rating, votes, language, status
   - Sample: 3 movies

3. **theatre** - Cinema/theatre locations
   - Fields: id, name, location, status
   - Sample: 3 theatres

4. **screen** - Screens in theatres
   - Fields: id, name, theatre_id, movie_id, show_time, show_date
   - Foreign keys to theatre and movie
   - Sample: 3 screens

5. **movie_schedule** - Show timings
   - Fields: id, theatre_id, screen_id, movie_id, show_time, show_date, status
   - Sample: 3 schedules

6. **seat** - Individual seats
   - Fields: id, screen_id, movie_schedule_id, name, status, seating_type, ticket_category, rate
   - Sample: 13 seats with mixed pricing

7. **ticket** - Booked tickets
   - Fields: id, user_id, movie_schedule_id, theatre_id, seat_count, amount_paid, booking_date
   - Foreign keys to user, movie_schedule, theatre

8. **ticket_seat** - Seat-to-ticket mapping
   - Links tickets to booked seats
   - Prevents double booking

### Sample Data Included

**Users:**
- John Doe (Phone: 9876543210)
- Jane Smith (Phone: 9876543211)
- Bob Johnson (Phone: 9876543212)

**Movies:**
- Movie 1 (Rating: 8.5, Language: English)
- Movie 2 (Rating: 7.8, Language: English)
- Movie 3 (Rating: 8.2, Language: Hindi)

**Theatres:**
- Cinema Palace (Location: City Center)
- Star Theatre (Location: Downtown)
- Regal Cinema (Location: Mall)

**Seats:**
- Standard seats: ₹150 each
- Premium seats: ₹250 each
- Luxury seats: ₹350 each

---

## API Endpoints Summary

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | /user | Create user account |
| POST | /user/login | User authentication |
| GET | /user/{id} | Get user profile |
| PUT | /user | Update user profile |
| GET | /movie | List all movies |
| GET | /movie/filtered | Filter movies |
| GET | /movie/filters | Get filter types |
| GET | /movie/filters/{type} | Get filter values |
| GET | /schedule/shows | List all shows |
| GET | /schedule/{id} | Get available seats |
| GET | /book/ticket | Book tickets |

---

## Quick Setup Instructions

### 1. Create Database
```bash
docker run --name book-my-show-db \
  -e POSTGRES_PASSWORD=123 \
  -e POSTGRES_USER=postgres \
  -p 5432:5432 \
  -d postgres:15

# Wait 10 seconds
sleep 10

# Run schema
docker exec -i book-my-show-db psql -U postgres -d postgres < database/src/main/resources/schema.sql
```

### 2. Build Project
```bash
cd /home/mk/Program/old/prog/book-my-show
mvn clean install -DskipTests
```

### 3. Run Application
```bash
java -jar server/target/server-1.0-SNAPSHOT.jar
```

### 4. Test API
```bash
# Get all movies
curl http://localhost:8080/movie

# Expected response:
# {"statusCode": 200, "statusMessage": "...", "data": [...]}
```

---

## Build Order (Important)

**MUST build in this order due to dependencies:**

1. commons (utilities) ← foundation
2. exception (error handling)
3. database (DB connection)
4. user (user module)
5. booking (booking module)
6. cine-feature (features)
7. server (main app) ← depends on all others

**OR use single command:**
```bash
mvn clean install -DskipTests
```

---

## Key Configuration

### Database Connection (in application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Server Configuration
```properties
server.port=8080
server.servlet.context-path=/
```

### Application Details
- Name: book-my-show
- Version: 1.0-SNAPSHOT
- Java: 17+
- Spring Boot: 3.2.4
- Database: PostgreSQL 12+

---

## Validation Rules

### User Creation
- Name: 4-21 characters, must start with letter, can contain spaces
- Phone: 10 digits, Indian format (starts with 6-9)
- Password: 8-15 chars, must contain: uppercase, lowercase, digit, special char
- Email: Valid email format (optional)

### Phone Number Format
Example: `9876543210` (without country code)

### Password Format
Example: `Password@123` (has upper, lower, digit, special char)

### Movie Filters
- LANGUAGE: English, Hindi, Tamil, Telugu
- GENRE: Action, Comedy, Drama, Thriller
- FORMAT: 2D, 3D, IMAX

---

## Testing the APIs

### Using cURL

**Create User:**
```bash
curl -X POST http://localhost:8080/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "phoneNumber": "9876543210",
    "password": "Password@123",
    "emailId": "test@example.com"
  }'
```

**User Login:**
```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber":"9876543210","password":"Password@123"}'
```

**Get All Movies:**
```bash
curl http://localhost:8080/movie
```

**Get Movie Schedules:**
```bash
curl http://localhost:8080/schedule/shows
```

**Get Available Seats:**
```bash
curl http://localhost:8080/schedule/1
```

**Book Tickets:**
```bash
curl -X GET http://localhost:8080/book/ticket \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "movieScheduleId": 1,
    "seatList": ["A1", "A2"],
    "theatreId": 1
  }'
```

---

## Documentation File Purposes

| File | Purpose | Audience |
|------|---------|----------|
| schema.sql | Database creation | DBAs, Developers |
| API_DOCUMENTATION.md | Complete API reference | API Users, Frontend Devs |
| DATABASE_SETUP.md | Database setup steps | DBAs, DevOps |
| BUILD_AND_RUN_GUIDE.md | Build & deployment | Developers, DevOps |
| QUICK_REFERENCE.md | Quick lookup guide | All users |

---

## Troubleshooting Quick Links

**Database Issues:**
- See DATABASE_SETUP.md → Troubleshooting section

**Build Issues:**
- See BUILD_AND_RUN_GUIDE.md → Troubleshooting section

**API Issues:**
- See API_DOCUMENTATION.md → Error Response Format section

**Port Issues:**
- Change port in application.properties: `server.port=8081`

**Connection Issues:**
- Verify PostgreSQL running: `pg_isready -h localhost -p 5432`

---

## Next Steps

1. ✓ Read QUICK_REFERENCE.md (this file)
2. → Execute schema.sql on PostgreSQL
3. → Follow BUILD_AND_RUN_GUIDE.md
4. → Test APIs using API_DOCUMENTATION.md
5. → Monitor application logs
6. → Deploy to production

---

## Files Checklist

- ✅ schema.sql - Database creation (13 KB)
- ✅ API_DOCUMENTATION.md - API reference (45 KB)
- ✅ DATABASE_SETUP.md - Database guide (20 KB)
- ✅ BUILD_AND_RUN_GUIDE.md - Build guide (25 KB)
- ✅ QUICK_REFERENCE.md - Updated quick reference

**Total Documentation:** ~130 KB of comprehensive guides

---

## Support Resources

- **Spring Boot:** https://spring.io/projects/spring-boot
- **PostgreSQL:** https://www.postgresql.org/docs/
- **Maven:** https://maven.apache.org/
- **Java 17:** https://docs.oracle.com/en/java/javase/17/

---

**Created:** February 18, 2026
**Documentation Version:** 1.0
**Status:** Complete & Ready for Use

---

## Summary

You now have:
✅ Complete database schema with tables, relationships, and sample data
✅ Comprehensive API documentation with all 11 endpoints explained
✅ Step-by-step database setup guide for PostgreSQL
✅ Complete build and run instructions with troubleshooting
✅ Updated quick reference guide

All files are ready to use. Start with DATABASE_SETUP.md, then BUILD_AND_RUN_GUIDE.md, then use API_DOCUMENTATION.md to test your APIs!

