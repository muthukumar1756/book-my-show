# Spring Boot Migration Guide - Book My Show

## Overview
This project has been successfully migrated from **Apache Karaf (OSGi)** to **Spring Boot**. All Karaf-specific configurations have been removed and replaced with Spring Boot's convention-over-configuration approach.

## Key Changes Made

### 1. **POM Configuration**
- **Removed Karaf/OSGi dependencies:**
  - `osgi.core`
  - `org.apache.karaf.tooling`
  - `org.apache.felix.maven-bundle-plugin`
  - JAX-RS and CXF (REST framework)

- **Added Spring Boot Starters:**
  - `spring-boot-starter-web` - For REST APIs
  - `spring-boot-starter-logging` - For logging
  - `spring-boot-starter-validation` - For bean validation
  - `spring-boot-starter-test` - For testing

- **Changed packaging from `bundle` to `jar`** for all modules
- **Changed root packaging from `pom` to `jar`** for executable JAR

### 2. **Bundle Activators Replaced**
All OSGi Bundle Activators have been replaced with Spring `@Configuration` classes:
- `BookingActivator.java` → Spring `@Configuration` + `@ComponentScan`
- `CommonActivator.java` → Spring `@Configuration` + `@ComponentScan`
- `ExceptionActivator.java` → Spring `@Configuration` + `@ComponentScan`
- `DBActivator.java` → Spring `@Configuration` + `@ComponentScan`
- `UserActivator.java` → Spring `@Configuration` + `@ComponentScan`
- `Activator.java` (server) → Spring `@Configuration` + `@ComponentScan`

### 3. **Main Application Class**
- Application is now initialized as a Spring Boot application in:
  - `booking/src/main/java/org/cine/booker/Application.java`
  - Contains `@SpringBootApplication` annotation
  - Uses `@ComponentScan` to auto-discover components across all modules

### 4. **Dependencies Updated**
- Removed `scope="provided"` from all internal module dependencies
- Removed JAX-RS/REST-specific annotations (JAX-RS annotations can still be used with Spring)
- Updated to use Jackson for JSON processing instead of JAX-RS providers
- Added PostgreSQL driver with `runtime` scope

### 5. **Application Configuration**
- New `application.properties` file created with:
  - Server port: 8080
  - Application name: book-my-show
  - Logging configuration
  - Jackson configuration
  - Database configuration (commented out - update with your values)

## Project Structure After Migration

```
book-my-show/
├── pom.xml (Root - now JAR packaging with Spring Boot parent)
├── booking/
│   ├── pom.xml (JAR packaging)
│   ├── src/main/java/org/cine/booker/
│   │   ├── Application.java (Main @SpringBootApplication)
│   │   ├── BookingActivator.java (Spring @Configuration)
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   └── ...
│   └── src/main/resources/application.properties
├── commons/
│   └── pom.xml (JAR packaging)
├── exception/
│   └── pom.xml (JAR packaging)
├── database/
│   └── pom.xml (JAR packaging)
├── user/
│   └── pom.xml (JAR packaging)
└── server/
    └── pom.xml (JAR packaging)
```

## Building and Running

### Build the Application
```bash
cd book-my-show
mvn clean package
```

This will create an executable JAR file at:
```
target/book-my-show-1.0-SNAPSHOT.jar
```

### Run the Application

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using Java**
```bash
java -jar target/book-my-show-1.0-SNAPSHOT.jar
```

**Option 3: Using Maven from root directory**
```bash
mvn clean spring-boot:run
```

### Verify the Application is Running
The application will start on `http://localhost:8080` by default.

## Configuration

### Server Configuration
Edit `booking/src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Context path
server.servlet.context-path=/
```

### Database Configuration
Uncomment and update the database properties in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookmyshow
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Logging Configuration
Configure logging levels in `application.properties`:

```properties
logging.level.root=INFO
logging.level.org.cine=DEBUG
```

## Converting Controllers to Spring REST

If your controllers were using JAX-RS annotations, convert them to Spring MVC:

**Before (JAX-RS):**
```java
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieController {
    @GET
    @Path("/{id}")
    public Movie getMovie(@PathParam("id") Long id) { }
}
```

**After (Spring MVC):**
```java
@RestController
@RequestMapping("/movies")
public class MovieController {
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) { }
}
```

## Key Spring Boot Annotations

Replace JAX-RS annotations with Spring equivalents:

| JAX-RS | Spring MVC |
|--------|-----------|
| `@Path` | `@RequestMapping` |
| `@GET` | `@GetMapping` |
| `@POST` | `@PostMapping` |
| `@PUT` | `@PutMapping` |
| `@DELETE` | `@DeleteMapping` |
| `@PathParam` | `@PathVariable` |
| `@QueryParam` | `@RequestParam` |
| `@Produces` | `@RequestMapping(produces=...)` |
| `@Consumes` | `@RequestMapping(consumes=...)` |
| `@RestController` | Use both `@RestController` and `@RequestMapping` |

## Dependency Injection

Spring Boot uses constructor or field injection with `@Autowired`:

**Before (Singleton pattern):**
```java
MovieController.getInstance().getMovies();
```

**After (Spring Dependency Injection):**
```java
@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }
}
```

## Testing

Spring Boot includes built-in testing support with `spring-boot-starter-test`. Create tests in:

```
src/test/java/org/cine/booker/controller/MovieControllerTest.java
```

Example:
```java
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMovies() throws Exception {
        mockMvc.perform(get("/movies"))
               .andExpect(status().isOk());
    }
}
```

## Next Steps

1. **Update all Controllers** to use Spring annotations instead of JAX-RS
2. **Convert Singleton patterns** to Spring Service beans with `@Service`
3. **Add `@Configuration`** classes for complex bean setup if needed
4. **Configure database** properties in `application.properties`
5. **Add Spring Data JPA** if you're using databases (add starter)
6. **Test thoroughly** by running the application and verifying endpoints

## Troubleshooting

### Port Already in Use
```bash
# Use a different port
java -jar target/book-my-show-1.0-SNAPSHOT.jar --server.port=8081
```

### Missing Dependencies
```bash
# Rebuild and update dependencies
mvn clean install
```

### Component Not Found
- Ensure `@ComponentScan` includes the package in `Application.java`
- Annotate classes with `@Component`, `@Service`, `@Repository`, or `@Controller`

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring MVC Documentation](https://spring.io/projects/spring-framework)
- [Spring Boot Starters](https://spring.io/projects/spring-boot#learn)
- [Baeldung Spring Boot Guides](https://www.baeldung.com/spring-boot)

## Support

For issues or questions during migration, refer to:
1. Spring Boot logs (check `logging.level.org.cine=DEBUG`)
2. ApplicationContext initialization logs
3. Spring Boot documentation on your specific use case

