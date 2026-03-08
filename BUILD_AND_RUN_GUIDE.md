# Build and Run Guide for Book-My-Show Project

## Project Structure

This is a multi-module Maven project with the following modules:

```
book-my-show (Root)
├── commons              - Shared utilities and validation
├── exception            - Exception handling
├── database             - Database connection and configuration
├── user                 - User management module
├── booking              - Movie booking and ticket management
├── cine-feature         - Feature management
└── server               - Main Spring Boot application
```

## Prerequisites

Before building and running the project, ensure you have:

1. **Java 17** or higher installed
   ```bash
   java -version
   ```

2. **Maven 3.8.1** or higher installed
   ```bash
   mvn --version
   ```

3. **PostgreSQL 12+** installed and running
   - Host: localhost
   - Port: 5432
   - Username: postgres
   - Password: 123
   - Database: postgres

4. **Git** (optional, for version control)

## Step 1: Database Setup

Before building, set up the PostgreSQL database with the provided schema.

### Using Docker (Recommended)

```bash
# Create and start PostgreSQL container
docker run --name book-my-show-db \
  -e POSTGRES_PASSWORD=123 \
  -e POSTGRES_USER=postgres \
  -p 5432:5432 \
  -d postgres:15

# Wait for container to be ready (about 10 seconds)
sleep 10

# Execute schema script
docker exec -i book-my-show-db psql -U postgres -d postgres < database/src/main/resources/schema.sql
```

### Using Local PostgreSQL Installation

```bash
# Linux/Mac
psql -U postgres -h localhost -p 5432 -d postgres -f database/src/main/resources/schema.sql

# Windows (PowerShell)
psql -U postgres -h localhost -p 5432 -d postgres -f 'database/src/main/resources/schema.sql'
```

### Verify Database Creation

```bash
psql -U postgres -h localhost -p 5432 -d postgres -c "\dt"
```

You should see 8 tables listed: user, movie, theatre, screen, movie_schedule, seat, ticket, ticket_seat

## Step 2: Build Order (IMPORTANT)

Build the modules in the following order (bottom-up dependency order):

### Step 2.1: Build Commons Module
```bash
cd /home/mk/Program/old/prog/book-my-show/commons
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.2: Build Exception Module
```bash
cd /home/mk/Program/old/prog/book-my-show/exception
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.3: Build Database Module
```bash
cd /home/mk/Program/old/prog/book-my-show/database
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.4: Build User Module
```bash
cd /home/mk/Program/old/prog/book-my-show/user
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.5: Build Booking Module
```bash
cd /home/mk/Program/old/prog/book-my-show/booking
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.6: Build Cine-Feature Module (Optional)
```bash
cd /home/mk/Program/old/prog/book-my-show/cine-feature
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.7: Build Server Module (Main Application)
```bash
cd /home/mk/Program/old/prog/book-my-show/server
mvn clean install
```

Expected output: `BUILD SUCCESS`

### Step 2.8: Build Root Project (Optional, for aggregation)
```bash
cd /home/mk/Program/old/prog/book-my-show
mvn clean install
```

Expected output: `BUILD SUCCESS`

## Step 3: Alternative - Build All Modules at Once

If you prefer to build everything from the root directory:

```bash
cd /home/mk/Program/old/prog/book-my-show
mvn clean install -DskipTests
```

This will:
- Clean previous builds
- Compile all modules in dependency order
- Run tests (remove -DskipTests to skip tests)
- Install artifacts in local Maven repository

## Step 4: Run the Application

After successful build, run the Spring Boot application:

```bash
cd /home/mk/Program/old/prog/book-my-show
java -jar server/target/server-1.0-SNAPSHOT.jar
```

### Expected Output

You should see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

2026-02-18 13:12:35 - org.cine.launcher.Application - Starting Application v1.0-SNAPSHOT
2026-02-18 13:12:35 - org.cine.launcher.Application - Running with Spring Boot v3.2.4
...
2026-02-18 13:12:37 - o.s.b.w.e.tomcat.TomcatWebServer - Tomcat started on port(s): 8080 (http)
2026-02-18 13:12:37 - org.cine.launcher.Application - Started Application in X.XXX seconds
```

The application is now running on: **http://localhost:8080**

## Step 5: Test the Application

### Using cURL

**Test User Creation:**
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

**Test Get All Movies:**
```bash
curl -X GET http://localhost:8080/movie
```

**Test Get Movie Schedules:**
```bash
curl -X GET http://localhost:8080/schedule/shows
```

**Test Get Available Seats:**
```bash
curl -X GET http://localhost:8080/schedule/1
```

### Using Postman

1. Download and install [Postman](https://www.postman.com/downloads/)
2. Import the provided API collection (if available)
3. Create requests for each endpoint
4. Test with sample data

### Using Browser

Visit: **http://localhost:8080/actuator** (if actuator endpoints are enabled)

## Troubleshooting

### Issue 1: "Could not find or load main class"
**Solution:** Ensure the server module is built correctly
```bash
cd server && mvn clean install
```

### Issue 2: "Connection refused" when connecting to database
**Solution:**
- Verify PostgreSQL is running
- Check connection string in application.properties
- Verify credentials (username: postgres, password: 123)

```bash
# Check PostgreSQL status
pg_isready -h localhost -p 5432 -U postgres
```

### Issue 3: "Conflicting bean definition" error
**Solution:** This was already fixed in the migration. Ensure you have the latest code.

The error was due to two classes both named "Application":
- org.cine.launcher.Application (Main server application)
- org.cine.booker.Application (Booking module application)

This has been resolved in the current codebase.

### Issue 4: Build fails with "expressly dependency missing version"
**Solution:** This was already fixed. Update your pom.xml in the root project.

### Issue 5: "Tables don't exist" when running application
**Solution:** Ensure schema.sql was executed successfully
```bash
psql -U postgres -h localhost -p 5432 -d postgres -c "SELECT * FROM information_schema.tables WHERE table_schema='public';"
```

### Issue 6: Port 8080 already in use
**Solution:** Either kill the process using port 8080 or change the port in application.properties
```properties
server.port=8081
```

## Build Verification Checklist

After each build step, verify:

- [ ] Build completed with "BUILD SUCCESS"
- [ ] No compilation errors
- [ ] No test failures (if tests are run)
- [ ] JAR/WAR file created in target/ directory
- [ ] Check target/classes directory exists

## Performance Optimization

### For faster builds, skip tests:
```bash
mvn clean install -DskipTests
```

### For faster clean builds on subsequent runs:
```bash
mvn clean install --offline
```

### For parallel builds (faster on multi-core systems):
```bash
mvn clean install -T 1C
```
Where 1C means 1 thread per core (adjust C as needed)

## Development Build

For development with automatic reload:

```bash
cd /home/mk/Program/old/prog/book-my-show/server
mvn spring-boot:run
```

This will:
- Start the Spring Boot application
- Enable automatic restart on file changes
- Allow for faster development cycles

## Production Build

For production deployment:

```bash
mvn clean install -DskipTests -Pprod
```

This creates an optimized JAR file suitable for production.

## Clean Up

To remove all build artifacts and start fresh:

```bash
cd /home/mk/Program/old/prog/book-my-show
mvn clean
```

This removes all target/ directories and compiled classes.

## Useful Maven Commands

| Command | Description |
|---------|-------------|
| `mvn clean` | Remove build artifacts |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR/WAR |
| `mvn install` | Install in local repository |
| `mvn deploy` | Deploy to remote repository |
| `mvn dependency:tree` | View dependency tree |
| `mvn dependency:resolve` | Resolve all dependencies |

## Application Properties

Key application.properties settings:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/
spring.application.name=book-my-show

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Logging
logging.level.root=INFO
logging.level.org.cine=DEBUG

# Jackson Configuration
spring.jackson.serialization.write-dates-as-timestamps=false
```

## Next Steps

1. ✓ Database Setup - DONE (schema.sql executed)
2. ✓ Build Modules - Build in order above
3. ✓ Run Application - Start the server
4. Test APIs - Use provided API documentation
5. Monitor Logs - Check application logs for issues
6. Deploy - Deploy to production environment

## Support and Debugging

### Enable Debug Logging
```properties
logging.level.org.cine=DEBUG
logging.level.org.springframework.web=DEBUG
```

### Check Application Startup Logs
```bash
# Redirect logs to file
java -jar server/target/server-1.0-SNAPSHOT.jar > app.log 2>&1
```

### Monitor Running Application
```bash
# Check if application is running
lsof -i :8080

# View real-time logs
tail -f app.log
```

---

**Last Updated:** February 18, 2026
**Maven Version:** 3.8.1+
**Java Version:** 17+
**Spring Boot Version:** 3.2.4

