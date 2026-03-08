# Book-My-Show API Documentation

## Base URL
```
http://localhost:8080
```

---

## 1. USER MANAGEMENT APIs

### 1.1 Create User Profile
**Endpoint:** `POST /user`

**Description:** Creates a new user account in the system.

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "name": "John Doe",
  "phoneNumber": "9876543210",
  "password": "Password@123",
  "emailId": "john@example.com"
}
```

**Validation Rules:**
- `name`: Required, 4-21 characters, must start with letter, can contain spaces
- `phoneNumber`: Required, valid Indian phone number (starting with 6-9)
- `password`: Required, 8-15 characters, must contain uppercase, lowercase, digit, and special character (@#$%^&+=)
- `emailId`: Optional, must be a valid email format

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "User created successfully",
  "data": {
    "id": 4,
    "name": "John Doe",
    "phoneNumber": "9876543210",
    "emailId": "john@example.com"
  }
}
```

**Response (Error - 400):**
```json
{
  "statusCode": 400,
  "statusMessage": "Validation failed",
  "errors": {
    "name": "Enter a valid name",
    "phoneNumber": "Enter a valid phone number"
  }
}
```

**HTTP Status Codes:**
- 200: User created successfully
- 400: Validation error
- 409: User already exists (phone number or email)

---

### 1.2 User Login
**Endpoint:** `POST /user/login`

**Description:** Authenticates a user with phone number and password.

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "phoneNumber": "9876543210",
  "password": "Password@123"
}
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Login successful",
  "data": {
    "id": 1,
    "name": "John Doe",
    "phoneNumber": "9876543210",
    "emailId": "john@example.com"
  }
}
```

**Response (Error - 401):**
```json
{
  "statusCode": 401,
  "statusMessage": "Invalid phone number or password"
}
```

**HTTP Status Codes:**
- 200: Login successful
- 401: Invalid credentials
- 404: User not found

---

### 1.3 Get User by ID
**Endpoint:** `GET /user/{userId}`

**Description:** Retrieves user profile information by user ID.

**Path Parameters:**
- `userId` (required, long): The unique identifier of the user

**Request Headers:**
```
Accept: application/json
```

**Example Request:**
```
GET /user/1
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "phoneNumber": "9876543210",
    "emailId": "john@example.com"
  }
}
```

**Response (Error - 404):**
```json
{
  "statusCode": 404,
  "statusMessage": "User not found"
}
```

**HTTP Status Codes:**
- 200: User retrieved successfully
- 404: User not found
- 400: Invalid user ID

---

### 1.4 Update User Profile
**Endpoint:** `PUT /user`

**Description:** Updates an existing user's profile information.

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "phoneNumber": "9876543210",
  "emailId": "john.updated@example.com"
}
```

**Validation Rules:**
- `id`: Required, must be positive
- `name`: Optional, 4-21 characters if provided
- `phoneNumber`: Optional
- `emailId`: Optional, valid email format if provided

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "User updated successfully",
  "data": {
    "id": 1,
    "name": "John Doe Updated",
    "phoneNumber": "9876543210",
    "emailId": "john.updated@example.com"
  }
}
```

**Response (Error - 404):**
```json
{
  "statusCode": 404,
  "statusMessage": "User not found"
}
```

**HTTP Status Codes:**
- 200: User updated successfully
- 404: User not found
- 400: Validation error
- 409: Phone number or email already in use

---

## 2. MOVIE APIs

### 2.1 Get All Movies
**Endpoint:** `GET /movie`

**Description:** Retrieves a list of all available movies.

**Request Headers:**
```
Accept: application/json
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Movies retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Movie 1",
      "description": "Action packed adventure",
      "rating": 8.5,
      "votes": 1200,
      "language": "English",
      "status": "ACTIVE"
    },
    {
      "id": 2,
      "name": "Movie 2",
      "description": "Romantic comedy",
      "rating": 7.8,
      "votes": 950,
      "language": "English",
      "status": "ACTIVE"
    }
  ]
}
```

**HTTP Status Codes:**
- 200: Movies retrieved successfully
- 500: Server error

---

### 2.2 Get Filtered Movies
**Endpoint:** `GET /movie/filtered`

**Description:** Retrieves movies based on applied filters (genre, language, format).

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "filterType": "LANGUAGE",
  "filterValues": ["English", "Hindi"]
}
```

**Or for Genre:**
```json
{
  "filterType": "GENRE",
  "filterValues": ["Action", "Comedy"]
}
```

**Or for Format:**
```json
{
  "filterType": "FORMAT",
  "filterValues": ["2D", "3D"]
}
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Filtered movies retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Movie 1",
      "description": "Action packed adventure",
      "rating": 8.5,
      "votes": 1200,
      "language": "English",
      "status": "ACTIVE"
    }
  ]
}
```

**HTTP Status Codes:**
- 200: Filtered movies retrieved successfully
- 400: Invalid filter type
- 500: Server error

---

### 2.3 Get All Available Filters
**Endpoint:** `GET /movie/filters`

**Description:** Retrieves all available movie filter types and their values.

**Request Headers:**
```
Accept: application/json
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Filters retrieved successfully",
  "data": {
    "filters": [
      {
        "filterType": "LANGUAGE",
        "filterValues": ["English", "Hindi", "Tamil", "Telugu"]
      },
      {
        "filterType": "GENRE",
        "filterValues": ["Action", "Comedy", "Drama", "Thriller"]
      },
      {
        "filterType": "FORMAT",
        "filterValues": ["2D", "3D", "IMAX"]
      }
    ]
  }
}
```

**HTTP Status Codes:**
- 200: Filters retrieved successfully
- 500: Server error

---

### 2.4 Get Filter Values by Type
**Endpoint:** `GET /movie/filters/{filterType}`

**Description:** Retrieves available values for a specific filter type.

**Path Parameters:**
- `filterType` (required, string): One of LANGUAGE, GENRE, or FORMAT

**Request Headers:**
```
Accept: application/json
```

**Example Request:**
```
GET /movie/filters/LANGUAGE
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Filter values retrieved successfully",
  "data": {
    "filterType": "LANGUAGE",
    "filterValues": ["English", "Hindi", "Tamil", "Telugu"]
  }
}
```

**Response (Error - 400):**
```json
{
  "statusCode": 400,
  "statusMessage": "Invalid filter type"
}
```

**HTTP Status Codes:**
- 200: Filter values retrieved successfully
- 400: Invalid filter type
- 500: Server error

---

## 3. MOVIE SCHEDULE APIs

### 3.1 Get All Movie Shows/Schedules
**Endpoint:** `GET /schedule/shows`

**Description:** Retrieves all available movie schedules/shows across all theatres.

**Request Headers:**
```
Accept: application/json
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Movie schedules retrieved successfully",
  "data": [
    {
      "id": 1,
      "theatre": {
        "id": 1,
        "name": "Cinema Palace",
        "location": "123 Main Street, City Center",
        "status": "ACTIVE"
      },
      "showTime": "10:00",
      "showDate": "2026-02-20"
    },
    {
      "id": 2,
      "theatre": {
        "id": 1,
        "name": "Cinema Palace",
        "location": "123 Main Street, City Center",
        "status": "ACTIVE"
      },
      "showTime": "14:00",
      "showDate": "2026-02-20"
    }
  ]
}
```

**HTTP Status Codes:**
- 200: Schedules retrieved successfully
- 500: Server error

---

### 3.2 Get Available Seats by Movie Schedule
**Endpoint:** `GET /schedule/{movieScheduleId}`

**Description:** Retrieves available seats and pricing for a specific movie schedule.

**Path Parameters:**
- `movieScheduleId` (required, long): The unique identifier of the movie schedule

**Request Headers:**
```
Accept: application/json
```

**Example Request:**
```
GET /schedule/1
```

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Seat information retrieved successfully",
  "data": {
    "movieScheduleId": 1,
    "theatre": {
      "id": 1,
      "name": "Cinema Palace",
      "location": "123 Main Street, City Center",
      "status": "ACTIVE"
    },
    "seats": [
      {
        "id": 1,
        "name": "A1",
        "status": "AVAILABLE",
        "seatingType": "STANDARD",
        "ticketCategory": "GENERAL",
        "rate": 150.0
      },
      {
        "id": 2,
        "name": "A2",
        "status": "AVAILABLE",
        "seatingType": "STANDARD",
        "ticketCategory": "GENERAL",
        "rate": 150.0
      },
      {
        "id": 4,
        "name": "B1",
        "status": "AVAILABLE",
        "seatingType": "PREMIUM",
        "ticketCategory": "PREMIUM",
        "rate": 250.0
      },
      {
        "id": 7,
        "name": "B2",
        "status": "BOOKED",
        "seatingType": "PREMIUM",
        "ticketCategory": "PREMIUM",
        "rate": 250.0
      }
    ]
  }
}
```

**Response (Error - 404):**
```json
{
  "statusCode": 404,
  "statusMessage": "Movie schedule not found"
}
```

**Seat Status Values:**
- `AVAILABLE`: Seat is available for booking
- `BOOKED`: Seat is already booked
- `BLOCKED`: Seat is blocked from booking

**HTTP Status Codes:**
- 200: Seat information retrieved successfully
- 404: Movie schedule not found
- 400: Invalid schedule ID
- 500: Server error

---

## 4. TICKET BOOKING APIs

### 4.1 Book Ticket
**Endpoint:** `GET /book/ticket`

**Description:** Books tickets for selected seats in a movie schedule. Note: This endpoint uses GET with request body (non-RESTful pattern).

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "userId": 1,
  "movieScheduleId": 1,
  "seatList": ["A1", "A2", "B1"],
  "theatreId": 1
}
```

**Request Body Fields:**
- `userId` (required, long): ID of the user booking the ticket
- `movieScheduleId` (required, long): ID of the movie schedule
- `seatList` (required, array of strings): List of seat names to book
- `theatreId` (required, long): ID of the theatre

**Response (Success - 200):**
```json
{
  "statusCode": 200,
  "statusMessage": "Ticket booked successfully",
  "data": {
    "id": 1,
    "theatre": {
      "id": 1,
      "name": "Cinema Palace",
      "location": "123 Main Street, City Center",
      "status": "ACTIVE"
    },
    "seatList": ["A1", "A2", "B1"],
    "seatCount": 3,
    "amountPaid": 550.0
  }
}
```

**Amount Calculation:**
- Amount is calculated based on seat rates
- In the example: (150.0 + 150.0 + 250.0) = 550.0

**Response (Error - 400):**
```json
{
  "statusCode": 400,
  "statusMessage": "Booking failed - One or more seats are already booked or invalid"
}
```

**Response (Error - 404):**
```json
{
  "statusCode": 404,
  "statusMessage": "User not found or movie schedule not found"
}
```

**HTTP Status Codes:**
- 200: Ticket booked successfully
- 400: Booking failed (seats not available, invalid request)
- 404: User or movie schedule not found
- 409: Seat already booked
- 500: Server error

---

## Error Response Format

All error responses follow this standard format:

```json
{
  "statusCode": <HTTP_STATUS_CODE>,
  "statusMessage": "<ERROR_MESSAGE>",
  "timestamp": "2026-02-18T13:30:00Z",
  "errors": {
    "fieldName": "Error message for this field"
  }
}
```

---

## Common HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| 200 | OK - Request successful |
| 400 | Bad Request - Invalid input or validation error |
| 401 | Unauthorized - Invalid credentials |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Resource already exists or conflict in data |
| 500 | Internal Server Error - Server-side error |

---

## Data Types and Formats

### Date Format
- ISO 8601: `YYYY-MM-DD` (e.g., "2026-02-20")

### Time Format
- 24-hour format: `HH:MM` (e.g., "14:30")

### Phone Number Format
- Indian format: 10 digits, starting with 6, 7, 8, or 9
- Example: "9876543210"

### Password Requirements
- Minimum 8 characters, Maximum 15 characters
- Must contain at least one uppercase letter
- Must contain at least one lowercase letter
- Must contain at least one digit
- Must contain at least one special character (@#$%^&+=)
- No spaces allowed

---

## Authentication

Currently, the API does not require authentication headers. In a production environment, implement:
- JWT tokens
- Bearer token in Authorization header
- Session management

Example for future implementation:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## Rate Limiting

No rate limiting is currently implemented. It's recommended to implement rate limiting in production:
- Limit requests per user
- Implement backoff strategies
- Return 429 (Too Many Requests) when limit exceeded

---

## CORS Configuration

The application should be configured with appropriate CORS headers:
```
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Max-Age: 3600
```

---

## Example cURL Requests

### Create User
```bash
curl -X POST http://localhost:8080/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phoneNumber": "9876543210",
    "password": "Password@123",
    "emailId": "john@example.com"
  }'
```

### User Login
```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNumber": "9876543210",
    "password": "Password@123"
  }'
```

### Get All Movies
```bash
curl -X GET http://localhost:8080/movie \
  -H "Accept: application/json"
```

### Get Movie Schedules
```bash
curl -X GET http://localhost:8080/schedule/shows \
  -H "Accept: application/json"
```

### Get Seat Availability
```bash
curl -X GET http://localhost:8080/schedule/1 \
  -H "Accept: application/json"
```

### Book Ticket
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

## Testing

### Recommended Testing Tools
1. **Postman** - GUI-based REST client
2. **cURL** - Command-line tool (examples above)
3. **Insomnia** - Modern REST client
4. **RestClient (VSCode Extension)** - IDE-integrated testing

### Test Cases Checklist
- [ ] Create user with valid data
- [ ] Create user with invalid phone number
- [ ] Create user with weak password
- [ ] Login with correct credentials
- [ ] Login with incorrect credentials
- [ ] Get user by valid ID
- [ ] Get user by invalid ID
- [ ] Update user profile
- [ ] Get all movies
- [ ] Get filtered movies by language
- [ ] Get filtered movies by genre
- [ ] Get all movie schedules
- [ ] Get seats for valid schedule
- [ ] Get seats for invalid schedule
- [ ] Book tickets with available seats
- [ ] Book tickets with already booked seats
- [ ] Book tickets for invalid user

---

## API Response Time SLAs

Target response times:
- User operations: < 200ms
- Movie listing: < 300ms
- Schedule retrieval: < 250ms
- Ticket booking: < 500ms
- Filtered operations: < 400ms

---

## Future Enhancements

1. **Authentication & Authorization**
   - JWT token-based authentication
   - Role-based access control (Admin, User)

2. **Payment Integration**
   - Payment gateway integration
   - Multiple payment methods

3. **Notifications**
   - Email notifications for booking confirmation
   - SMS notifications
   - Push notifications

4. **Analytics**
   - Booking statistics
   - Popular movies/theatres
   - User engagement metrics

5. **Advanced Filtering**
   - Price range filtering
   - Show time filtering
   - Theatre distance filtering

6. **Cancellation & Modification**
   - Cancel booking endpoint
   - Modify booking endpoint
   - Refund policies

---

## Support & Contact

For API support and issues, contact:
- Email: support@bookMyShow.com
- Documentation: https://docs.bookMyShow.com
- Issue Tracker: https://github.com/cine/book-my-show/issues

---

**Document Version:** 1.0
**Last Updated:** February 18, 2026
**API Version:** 1.0

