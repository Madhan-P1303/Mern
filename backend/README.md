# EduQuest Backend API

A Spring Boot REST API for the EduQuest educational platform with JWT authentication, PostgreSQL database, and role-based access control.

## Features

- **Spring Boot 3.2.0** with Java 17
- **PostgreSQL** database integration
- **JWT Authentication** with Spring Security
- **Role-based Authorization** (STUDENT, INSTRUCTOR, ADMIN)
- **RESTful API** endpoints
- **Data Validation** with Bean Validation
- **CORS** configuration for frontend integration
- **JPA Auditing** for automatic timestamp management

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Database Setup

1. Install PostgreSQL and create a database:
```sql
CREATE DATABASE eduquest_db;
CREATE USER eduquest_user WITH PASSWORD 'eduquest_password';
GRANT ALL PRIVILEGES ON DATABASE eduquest_db TO eduquest_user;
```

2. Update `application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eduquest_db
spring.datasource.username=eduquest_user
spring.datasource.password=eduquest_password
```

## Project Structure

```
src/main/java/com/eduquest/
├── EduquestBackendApplication.java    # Main Spring Boot application
├── controller/                        # REST controllers
│   ├── AuthController.java           # Authentication endpoints
│   ├── CourseController.java         # Course management endpoints
│   ├── EnrollmentController.java     # Enrollment endpoints
│   ├── AchievementController.java    # Achievement endpoints
│   ├── DashboardController.java      # Dashboard endpoints
│   ├── SecureController.java         # Secure test endpoints
│   └── TestController.java           # Test endpoints
├── dto/                              # Data Transfer Objects
│   ├── UserRegistrationDto.java      # User registration data
│   ├── UserLoginDto.java             # User login data
│   ├── UserResponseDto.java          # User response data
│   ├── JwtResponseDto.java           # JWT response data
│   ├── CourseDto.java                # Course response data
│   ├── CourseCreateDto.java          # Course creation data
│   ├── EnrollmentDto.java            # Enrollment response data
│   ├── ProgressUpdateDto.java        # Progress update data
│   ├── AchievementDto.java           # Achievement response data
│   └── DashboardDto.java             # Dashboard response data
├── entity/                           # JPA entities
│   ├── User.java                     # User entity
│   ├── Course.java                   # Course entity
│   ├── Enrollment.java               # Enrollment entity
│   └── Achievement.java              # Achievement entity
├── repository/                       # Data access layer
│   ├── UserRepository.java           # User repository
│   ├── CourseRepository.java         # Course repository
│   ├── EnrollmentRepository.java     # Enrollment repository
│   └── AchievementRepository.java    # Achievement repository
├── security/                         # Security configuration
│   ├── WebSecurityConfig.java        # Security configuration
│   ├── JwtUtils.java                 # JWT utilities
│   ├── AuthTokenFilter.java          # JWT filter
│   ├── UserDetailsImpl.java          # User details implementation
│   ├── UserDetailsServiceImpl.java   # User details service
│   └── AuthEntryPointJwt.java        # Authentication entry point
└── service/                          # Business logic layer
    ├── UserService.java              # User service
    ├── CourseService.java            # Course service
    ├── EnrollmentService.java        # Enrollment service
    ├── AchievementService.java       # Achievement service
    └── DashboardService.java         # Dashboard service
```

## Running the Application

1. Clone the repository and navigate to the backend directory:
```bash
cd edventure-studio/backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Authentication Endpoints

- `POST /auth/signup` - Register a new user
- `POST /auth/login` - Login user
- `GET /auth/me` - Get current user info (requires authentication)

### Course Endpoints

- `GET /courses` - List all courses
- `GET /courses/{id}` - Get course details
- `POST /courses` - Create course (INSTRUCTOR/ADMIN only)
- `PUT /courses/{id}` - Update course (INSTRUCTOR/ADMIN only)
- `DELETE /courses/{id}` - Delete course (ADMIN only)
- `GET /courses/category/{category}` - Get courses by category
- `GET /courses/level/{level}` - Get courses by level
- `GET /courses/search?q={term}` - Search courses
- `GET /courses/popular` - Get popular courses
- `GET /courses/recent` - Get recent courses

### Enrollment Endpoints

- `POST /enroll/{courseId}` - Enroll in course (STUDENT only)
- `PUT /enroll/{courseId}/progress` - Update progress (STUDENT only)
- `GET /enroll/my-courses` - Get student's enrolled courses (STUDENT only)
- `GET /enroll/my-courses/{courseId}` - Get specific enrollment (STUDENT only)
- `DELETE /enroll/{courseId}` - Unenroll from course (STUDENT only)
- `GET /enroll/course/{courseId}` - Get course enrollments (INSTRUCTOR/ADMIN only)
- `GET /enroll/stats/{courseId}` - Get enrollment stats (INSTRUCTOR/ADMIN only)

### Achievement Endpoints

- `GET /achievements` - Get user's earned achievements
- `GET /achievements/user/{userId}` - Get specific user's achievements (ADMIN only)
- `GET /achievements/count` - Get user's achievement count
- `GET /achievements/types` - Get all achievement types

### Dashboard Endpoints

- `GET /dashboard` - Get user dashboard with statistics
- `GET /dashboard/user/{userId}` - Get specific user's dashboard (ADMIN only)

### Secure Endpoints (Require JWT Token)

- `GET /secure/user` - Get user info (any authenticated user)
- `GET /secure/instructor` - Instructor content (INSTRUCTOR or ADMIN)
- `GET /secure/admin` - Admin content (ADMIN only)

### Test Endpoints

- `GET /api/test/all` - Public access
- `GET /api/test/user` - Authenticated users
- `GET /api/test/instructor` - Instructors only
- `GET /api/test/admin` - Admins only

## Sample API Usage

### Register a new user:
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Get current user info:
```bash
curl -X GET http://localhost:8080/auth/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Access protected endpoint:
```bash
curl -X GET http://localhost:8080/secure/user \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create a course (as instructor):
```bash
curl -X POST http://localhost:8080/courses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Introduction to Java Programming",
    "description": "Learn Java from scratch with hands-on projects",
    "category": "Programming",
    "level": "BEGINNER",
    "duration": 40,
    "lessons": 20
  }'
```

### Enroll in a course (as student):
```bash
curl -X POST http://localhost:8080/enroll/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Update progress:
```bash
curl -X PUT http://localhost:8080/enroll/1/progress \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "progress": 50
  }'
```

### Get my enrolled courses:
```bash
curl -X GET http://localhost:8080/enroll/my-courses \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get my achievements:
```bash
curl -X GET http://localhost:8080/achievements \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get my dashboard:
```bash
curl -X GET http://localhost:8080/dashboard \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get achievement types:
```bash
curl -X GET http://localhost:8080/achievements/types
```

## Configuration

### JWT Configuration
Update `application.properties` to change JWT settings:
```properties
jwt.secret=your-secret-key
jwt.expiration=86400000  # 24 hours in milliseconds
```

### CORS Configuration
Update CORS settings in `WebSecurityConfig.java` for frontend integration:
```java
configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
```

## User Roles

- **STUDENT**: Default role for registered users
- **INSTRUCTOR**: Can access instructor-specific endpoints
- **ADMIN**: Full access to all endpoints

## Security Features

- Password encryption using BCrypt
- JWT token-based authentication
- Role-based authorization
- CORS configuration
- SQL injection protection via JPA
- Input validation with Bean Validation

## Development

### Adding New Entities

1. Create entity class in `entity/` package
2. Create repository interface in `repository/` package
3. Create service class in `service/` package
4. Create controller in `controller/` package
5. Create DTOs in `dto/` package

### Database Migrations

The application uses `spring.jpa.hibernate.ddl-auto=update` for automatic schema updates. For production, consider using Flyway or Liquibase for proper database migrations.

## Testing

Run tests with:
```bash
mvn test
```

## Production Deployment

1. Change JWT secret to a secure random string
2. Update database connection settings
3. Set `spring.jpa.hibernate.ddl-auto=validate`
4. Configure proper logging levels
5. Set up SSL/TLS certificates
6. Configure reverse proxy (nginx/Apache)

## Troubleshooting

### Common Issues

1. **Database Connection Error**: Ensure PostgreSQL is running and credentials are correct
2. **JWT Token Issues**: Check JWT secret configuration
3. **CORS Issues**: Verify CORS configuration matches frontend URL
4. **Port Already in Use**: Change `server.port` in `application.properties`

### Logs

Check application logs for detailed error information. Logging is configured to show SQL queries and security events in development mode.
