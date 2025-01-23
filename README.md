# Hotel Booking API

A simple RESTful API for managing hotel bookings, built with **Spring Boot**, **Java 11**, and **H2 database**.

## Features
- Create a new hotel booking.
- Retrieve a list of all bookings.
- Cancel an existing booking.
- Interactive API documentation with Swagger UI.
- Unit tests for Controller, Service, and Repository layers.

## Technologies Used
- **Java 11**: Core language for development.
- **Spring Boot**: Framework for building REST APIs.
- **H2 Database**: Lightweight in-memory and file-based database.
- **JUnit 5**: Testing framework for unit tests.
- **MockMvc**: Testing tool for REST controllers.
- **Swagger UI**: For API documentation and testing.

## API Endpoints

### 1. Create a Booking
- **URL**: `POST /bookings/create`
- **Request Body**:
  ```json
  {
    "customerName": "John Doe",
    "hotelName": "Hotel California"
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "customerName": "John Doe",
    "hotelName": "Hotel California"
  }
  ```

### 2. Get All Bookings
- **URL**: `GET /bookings/list`
- **Response**:
  ```json
  [
    {
      "id": 1,
      "customerName": "John Doe",
      "hotelName": "Hotel California"
    },
    {
      "id": 2,
      "customerName": "Jane Doe",
      "hotelName": "Grand Hyatt"
    }
  ]
  ```

### 3. Cancel a Booking
- **URL**: `DELETE /bookings/{id}`
- **Response** (Success):
  ```
  Booking cancelled successfully
  ```
- **Response** (Failure):
  ```
  404 Not Found
  ```

## How to Run

### Prerequisites
- Java 11+
- Maven

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/fakhriafrasiyab/hotel-booking.git
   cd hotel-booking
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - **JDBC URL**: `jdbc:h2:file:~/hotel_booking_db`
     - **Username**: `sa`
     - **Password**: (leave blank)

## Project Structure
```
src/main/java/org/booking
├── controller          # REST controllers
│   └── BookingController.java
├── model               # JPA entities
│   └── Booking.java
├── repository          # Spring Data JPA repositories
│   └── BookingRepository.java
├── service             # Business logic layer
│   └── BookingService.java
```

## Testing

### Run Tests
Execute the unit tests with:
```bash
mvn test
```

### Test Coverage
- **Controller Tests**:
  - Validates API behavior and endpoints using `MockMvc`.
- **Service Tests**:
  - Covers business logic with mocked dependencies.
- **Repository Tests**:
  - Tests database operations using `@DataJpaTest`.

## Libraries Justification
- **Spring Boot**: Simplifies REST API development and configuration.
- **H2 Database**: Lightweight and ideal for local development and testing.
- **Swagger UI**: Provides an interactive way to test APIs.
- **JUnit 5**: Ensures robust and reliable code through unit testing.
- **Mockito**: Mocks dependencies for isolated testing.

## Future Enhancements
- Add input validation for request payloads.
- Support advanced search and filtering for bookings.
- Implement user authentication and authorization.
- Deploy the application using Docker or cloud platforms.

