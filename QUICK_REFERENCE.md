# Quick Reference - Spring Boot Migration & API Guide

## 🚀 Quick Start (5 Minutes)

### 1. Setup Database
```bash
# Using Docker (easiest)
docker run --name book-my-show-db -e POSTGRES_PASSWORD=123 \
  -e POSTGRES_USER=postgres -p 5432:5432 -d postgres:15

# Wait 10 seconds, then execute schema
docker exec -i book-my-show-db psql -U postgres -d postgres < database/src/main/resources/schema.sql
```

### 2. Build & Run
```bash
# Build all modules (from root directory)
mvn clean install -DskipTests

# Run application
java -jar server/target/server-1.0-SNAPSHOT.jar

# Or run with different port
java -jar server/target/server-1.0-SNAPSHOT.jar --server.port=8081
```

### 3. Test API
```bash
# Get all movies
curl http://localhost:8080/movie

# Create user
curl -X POST http://localhost:8080/user \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","phoneNumber":"9876543210","password":"Pass@123","emailId":"test@test.com"}'
```

## 📌 What Changed

### File Changes
```
✅ ROOT pom.xml                          - packaging: pom → jar
✅ ALL module pom.xml files              - packaging: bundle → jar
✅ ALL module pom.xml files              - removed: maven-bundle-plugin
✅ booking/src/main/resources/           - added: application.properties
✅ All Activator.java files              - OSGi → Spring @Configuration
✅ Application.java                      - added: @ComponentScan
```

### What Was Removed
- ❌ org.osgi.* dependencies
- ❌ org.apache.karaf.* dependencies
- ❌ org.apache.felix.maven-bundle-plugin
- ❌ JAX-RS (jakarta.ws.rs.*) REST framework
- ❌ Apache CXF (org.apache.cxf.*)
- ❌ Bundle Activator implementations

### What Was Added
- ✅ Spring Boot 3.2.4 parent
- ✅ spring-boot-starter-web
- ✅ spring-boot-starter-logging
- ✅ spring-boot-starter-validation
- ✅ Jackson JSON processor
- ✅ application.properties config file

## 🔄 Code Changes

### Controllers - JAX-RS → Spring MVC

```java
// REMOVE THESE IMPORTS:
import javax.ws.rs.*;
import javax.ws.rs.core.*;

// ADD THESE IMPORTS:
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

// BEFORE:
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieController {
    @GET
    @Path("/{id}")
    public Movie getMovie(@PathParam("id") Long id) { }
}

// AFTER:
@RestController
@RequestMapping("/movies")
public class MovieController {
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) { }
}
```

### Services - Singleton → Spring Bean

```java
// REMOVE THIS:
public class MovieService {
    private static MovieService instance;
    public static MovieService getInstance() { return instance; }
}

// USE THIS:
@Service
public class MovieService {
    // Spring manages the bean lifecycle
}
```

### Dependency Injection

```java
// BEFORE:
MovieService service = MovieService.getInstance();

// AFTER - Constructor Injection (Recommended):
@RestController
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }
}

// OR - Field Injection:
@RestController
public class MovieController {
    @Autowired
    private MovieService service;
}
```

## 📋 REST Annotation Mapping

| JAX-RS | Spring | Use Case |
|--------|--------|----------|
| `@Path` | `@RequestMapping` | URL path |
| `@GET` | `@GetMapping` | GET request |
| `@POST` | `@PostMapping` | POST request |
| `@PUT` | `@PutMapping` | PUT request |
| `@DELETE` | `@DeleteMapping` | DELETE request |
| `@PathParam("id")` | `@PathVariable("id")` | URL path param |
| `@QueryParam("q")` | `@RequestParam("q")` | Query param |
| `@Produces(JSON)` | produces="application/json" | Response type |
| `@Consumes(JSON)` | consumes="application/json" | Request type |

## 🔌 Spring Annotations Reference

```java
// Component Registration
@Component          // Generic component
@Service            // Business logic
@Repository         // Data access
@Controller         // MVC controller
@RestController     // REST controller
@Configuration      // Configuration class

// Dependency Injection
@Autowired          // Field/Constructor injection
@Inject             // Alternative to @Autowired

// Request Handling
@RequestMapping     // Map HTTP requests
@GetMapping         // GET requests
@PostMapping        // POST requests
@PutMapping         // PUT requests
@DeleteMapping      // DELETE requests

// Request Parameters
@PathVariable       // URL path variable
@RequestParam       // Query parameter
@RequestBody        // Request body
@ResponseBody       // Response body

// Scope
@Scope("singleton") // Default
@Scope("prototype") // New instance each time
```

## 📁 Project Structure

```
book-my-show/
├── pom.xml                    ← Root configuration
├── booking/                   ← Main application module
│   ├── pom.xml
│   └── src/main/resources/
│       └── application.properties  ← Server configuration
├── commons/                   ← Shared utilities
├── exception/                 ← Exception handling
├── database/                  ← DB connectivity
├── user/                      ← User management
└── server/                    ← Server bootstrap
```

## ⚙️ Configuration (application.properties)

```properties
# Server
server.port=8080
server.servlet.context-path=/

# Logging
logging.level.root=INFO
logging.level.org.cine=DEBUG

# Database (Uncomment and configure)
# spring.datasource.url=jdbc:postgresql://localhost:5432/bookmyshow
# spring.datasource.username=postgres
# spring.datasource.password=password
```

## ✅ Pre-Deployment Checklist

- [ ] All controllers use `@RestController` and `@RequestMapping`
- [ ] All services use `@Service` annotation
- [ ] All DAOs use `@Repository` annotation
- [ ] Removed all OSGi imports (`org.osgi.*`)
- [ ] Removed all JAX-RS imports (`javax.ws.rs.*`, `jakarta.ws.rs.*`)
- [ ] Removed all singleton patterns (use Spring beans)
- [ ] `mvn clean package` succeeds without errors
- [ ] Application starts: `java -jar target/book-my-show-1.0-SNAPSHOT.jar`
- [ ] Endpoints respond: `curl http://localhost:8080/`
- [ ] Database connected (if applicable)

## 🐛 Common Issues

| Issue | Solution |
|-------|----------|
| "No qualifying bean" | Add `@ComponentScan` or `@Component` annotations |
| Port already in use | `java -jar ... --server.port=9090` |
| Import errors | Replace `javax.ws.rs.*` with `org.springframework.web.bind.annotation.*` |
| Class not found at startup | Run `mvn clean package` to rebuild |
| Database connection failed | Check PostgreSQL is running and credentials in `application.properties` |

## 📚 Documentation Files

- **MIGRATION_SUMMARY.md** - Detailed overview of all changes
- **SPRING_BOOT_MIGRATION.md** - Step-by-step migration guide

## 🎯 Key Differences at a Glance

| Feature | Karaf (Old) | Spring Boot (New) |
|---------|----------|----------|
| **Startup** | Karaf container | Java JVM |
| **Bundles** | Multiple OSGi bundles | Single JAR |
| **Configuration** | MANIFEST.MF | application.properties |
| **REST Server** | CXF/JAX-RS | Spring MVC/Tomcat |
| **Logging** | Log4j (custom) | SLF4J/Logback |
| **Deployment** | Deploy to Karaf | Run JAR directly |
| **Port** | Custom in features.xml | application.properties |

---

**Ready to deploy?** Follow the Build & Run section above! ✨

