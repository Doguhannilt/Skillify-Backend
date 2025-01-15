

# Online Course Management Platform


1. [Overview](#overview)  
2. [Urls](#urls)  
3. [Course Recommendation Service](#course-recommendation-service)
4. [Forum Feature](#forum-feature-for-online-course-platform)
5. [Tests](#tests)  
6. [Notes](#notes)  
7. [Features](#features)  
8. [Technologies Used](#technologies-used)  
9. [Database Schema](#database-schema)  
10. [Getting Started](#getting-started)  
11. [License](#license)
    
## Overview

This repository contains the code for an Online Course Management Platform developed using **Spring Boot** and various technologies. The platform allows users (students, instructors, and admins) to interact with courses, lessons, and other features like authentication, course management, and payment integration. 

## Urls

1. <a href="https://doguhannilt.github.io/Skillify-Backend/">Track Tasks</a>
2. <a href="https://hub.docker.com/repository/docker/doguhannilt/myapp">Docker</a>

# Forum Feature for Online Course Platform

This document describes the Forum feature implemented in the Online Course Platform. The feature allows students and instructors to interact through forum topics, enabling a collaborative learning environment.

The Forum feature includes the following entities and their relationships:

1. **ForumTopic**: Represents a topic in the forum created by an instructor.
   - Fields:
     - `id`: Unique identifier.
     - `title`: Title of the topic.
     - `description`: Description of the topic.
     - `date`: Creation date.
     - `keywords`: Related keywords.
     - `instructorId`: ID of the instructor who created the topic.

2. **Question**: Represents questions asked by students under a specific topic.
   - Fields:
     - `id`: Unique identifier.
     - `content`: The question content.
     - `studentId`: ID of the student asking the question.
     - `forumTopicId`: ID of the related topic.

3. **Answer**: Represents answers provided by instructors to questions.
   - Fields:
     - `id`: Unique identifier.
     - `content`: The answer content.
     - `instructorId`: ID of the instructor answering the question.
     - `questionId`: ID of the related question.

4. **Comment**: Represents comments on answers, which can be liked and viewed.
   - Fields:
     - `id`: Unique identifier.
     - `content`: The comment content.
     - `userId`: ID of the user making the comment.
     - `answerId`: ID of the related answer.
     - `date`: Date of the comment.
     - `likesCount`: Number of likes on the comment.

## Key Features

### 1. Forum Topic Management
- **Retrieve All Topics**:
  - Endpoint: `GET /api/forum/topics`
  - Caching: Uses Redis with `@Cacheable` for improved performance.

- **Create New Topic**:
  - Endpoint: `POST /api/forum/topics`
  - Validation: Ensures the instructor exists before creating a topic.
  - Caching: Updates Redis cache with `@CachePut`.

### 2. Question Management
- **Retrieve Questions by Topic**:
  - Endpoint: `GET /api/forum/questions/topic/{topicId}`
  - Retrieves all questions related to a specific forum topic.

- **Ask a Question**:
  - Endpoint: `POST /api/forum/questions`
  - Validation: Ensures the topic exists before saving the question.

### 3. Answer Management
- **Provide an Answer**:
  - Endpoint: `POST /api/forum/answers`
  - Validation: Ensures the question exists before saving the answer.

### 4. Comment Management
- **Retrieve Comments for an Answer**:
  - Endpoint: `GET /api/forum/comments/answer/{answerId}`
  - Retrieves all comments related to a specific answer.

- **Add a Comment to an Answer**:
  - Endpoint: `POST /api/forum/comments`
  - Validation: Ensures the answer exists before adding a comment.

## Implementation Details

### Models
- Each entity (ForumTopic, Question, Answer, Comment) is represented as a MongoDB document.
- Example of the `Comment` model:
  ```java
  @Document(collection = "comments")
  public class Comment {
      @Id
      private String id;
      private String content;
      private String userId;
      private String answerId;
      private Date date;
      private int likesCount = 0;
  }
  ```

### Services
- Implemented service classes to encapsulate business logic.
- Example method for adding a comment:
  ```java
  public Comment addCommentToAnswer(Comment comment) {
      if (!answerRepository.existsById(comment.getAnswerId())) {
          throw new IllegalArgumentException("Answer does not exist.");
      }
      return commentRepository.save(comment);
  }
  ```

### Controllers
- RESTful APIs are exposed for managing topics, questions, answers, and comments.
- Example of creating a new topic:
  ```java
  @PostMapping("/topics")
  @Operation(summary = "Create Forum Topic")
  @CachePut(value = "forumTopicsCache", key = "#forumTopic.id")
  public ResponseEntity<ForumTopic> createForumTopic(@RequestBody ForumTopic forumTopic) throws Exception {
      return ResponseEntity.status(HttpStatus.CREATED).body(forumTopicService.createForumTopic(forumTopic));
  }
  ```


## Course Recommendation Service


**Description**  
The platform provides a feature to recommend similar courses based on the description of a given course. This is implemented using the **TF-IDF** (Term Frequency - Inverse Document Frequency) technique for text vectorization and **Cosine Similarity** for measuring similarity between course descriptions.

**How It Works**  
1. Each course description is converted into a frequency vector using TF-IDF.  
2. Cosine Similarity is calculated between the target course and other courses in the database.  
3. Courses are ranked based on similarity scores, and the top 5 most similar courses are recommended.

**Key Features**  
- Dynamically analyzes course descriptions to provide personalized recommendations.  
- Scans all courses in the database to ensure relevant suggestions.  
- Optimized for performance by processing only the required data.

**Usage**  
The feature is accessible through a dedicated endpoint:

- **Endpoint:** `GET /api/courses/{courseId}/recommendations`  
- **Input:** Course ID of the target course.  
- **Output:** A list of the top 5 similar courses, including their details.

**Example Response:**  
```json
[
  {
    "id": 102,
    "name": "Introduction to Machine Learning",
    "description": "Learn the basics of machine learning with hands-on projects.",
    "price": 49,
    "language": ["English"],
    "status": "Available"
  },
  {
    "id": 205,
    "name": "Advanced Data Science",
    "description": "Master advanced data science techniques and tools.",
    "price": 79,
    "language": ["English"],
    "status": "Available"
  }
]
```

**Implementation**  
- The `CosineSimilarityService` calculates similarity scores between course descriptions.  
- The `CourseRecommendationService` ranks courses and returns the most relevant ones.  
- Fully integrated with the existing course management system.

This feature enhances user engagement by offering personalized course recommendations, improving the overall user experience.

## Tests

The following tests have been successfully passed:

### 1. **Create Lesson Tests**
#### `createLesson_Success`
- **Description**: The lesson was successfully created.
- **Test Details**: 
  - A new lesson was created and successfully added to the system.
  - The relevant course was found and the lesson was successfully associated.

#### `createLesson_CourseNotFound`
- **Description**: Appropriate error message is returned when the course is not found.
- **Test Details**: 
  - When the provided course name was not found, an appropriate error message was returned to the user.

---

### 2. **Update Lesson Tests**
#### `updateLesson_Success`
- **Description**: The lesson was successfully updated.
- **Test Details**:
  - The lesson details were successfully updated.

---

### 3. **Delete Lesson Tests**
#### `deleteLesson_Success`
- **Description**: The lesson was successfully deleted.
- **Test Details**: 
  - The lesson was successfully removed from the system.

---

### 4. **Create Course Tests**
#### `createCourse_Success`
- **Description**: The course was successfully created and an email was sent to the instructor.
- **Test Details**:
  - The course was successfully created.
  - An email was sent to the instructor notifying them about the course creation.

#### `createCourse_CourseAlreadyExists`
- **Description**: An exception was thrown when attempting to create a course with an already taken name.
- **Test Details**:
  - When attempting to create a course with an already existing name, an appropriate exception was thrown.

---

### 5. **Delete Course Tests**
#### `deleteCourse_Success`
- **Description**: The course was successfully deleted and an email was sent to the instructor.
- **Test Details**:
  - The course was successfully deleted from the system.
  - An email was sent to the instructor notifying them about the course deletion.

#### `deleteCourse_CourseNotFound`
- **Description**: An appropriate error message was returned when trying to delete a non-existent course.
- **Test Details**:
  - When attempting to delete a course that did not exist, an error message was returned to the user.

---

### 6. **Update Course Tests**
#### `updateCourse_Success`
- **Description**: The course was successfully updated.
- **Test Details**:
  - The course details were successfully updated.

#### `updateCourse_CourseNotFound`
- **Description**: An error message was returned when trying to update a non-existent course.
- **Test Details**:
  - When attempting to update a course that did not exist, an appropriate error message was returned.

---

### 7. **Course Recommendation Test**
#### `testRecommendCourses`
- **Description**: Cosine Similarity is successfully running.
- **Test Details**:
  - The recommendation system for courses based on user preferences successfully ran using the Cosine Similarity algorithm.

---

## Additional Tests

### 8. **Gamification Tests**
#### `addPointsToUser_Success`
- **Description**: Points were successfully added to the user and appropriate badges were awarded.
- **Test Details**:
  - Points from an event were successfully added to the user’s total points.
  - Appropriate badges were awarded to the user based on their points.

#### `addPointsToUser_UserNotFound`
- **Description**: Appropriate error message was returned when the user was not found.
- **Test Details**:
  - When the provided user ID was not found in the system, an error message was returned.

---

### 9. **Email Service Tests**
#### `sendEmail_Success`
- **Description**: The email was successfully sent.
- **Test Details**:
  - The email was successfully sent and delivered to the recipient with the appropriate content.

#### `sendEmail_NullValues`
- **Description**: Appropriate error message was returned when null values were passed.
- **Test Details**:
  - When trying to send an email with empty or null values (such as recipient email or subject), an error message was returned.

---

### 10. **SearchService Tests**
#### `getAllCourses_Success`
- **Description**: All courses were successfully retrieved.
- **Test Details**:
  - All courses in the system were successfully retrieved.

#### `filterByName_Success`
- **Description**: Courses were successfully listed by name filter.
- **Test Details**:
  - Courses were successfully filtered and listed based on the given name.

#### `filterByInstructorName_Success`
- **Description**: Courses were successfully filtered by instructor name.
- **Test Details**:
  - Courses were successfully filtered and listed based on the given instructor name.

---

### 11. **AdminService Tests**
#### `getAllUsers_Success`
- **Description**: All users were successfully retrieved.
- **Test Details**:
  - All users in the system were successfully retrieved.

#### `getUserById_Success`
- **Description**: User was successfully retrieved by user ID.
- **Test Details**:
  - The user was successfully retrieved from the system using the given user ID.

#### `deleteUserById_Success`
- **Description**: User was successfully deleted.
- **Test Details**:
  - The user was successfully deleted from the system using the provided user ID.

#### `getAllCourses_Success`
- **Description**: All courses were successfully retrieved.
- **Test Details**:
  - All courses in the system were successfully retrieved.

#### `getCourseById_Success`
- **Description**: Course was successfully retrieved by course ID.
- **Test Details**:
  - The course was successfully retrieved from the system using the given course ID.

#### `deleteLessonById_Success`
- **Description**: Lesson was successfully deleted.
- **Test Details**:
  - The lesson was successfully removed from the system.

#### `getEnrollmentsWithinRange_Success`
- **Description**: Enrollments within the specified date range were successfully retrieved.
- **Test Details**:
  - Enrollments for users within the provided date range were successfully retrieved.

---

### 12. **Cosine Similarity Tests**
#### `testCosineSimilarity_Success`
- **Description**: Cosine Similarity algorithm ran successfully.
- **Test Details**:
  - The recommendation system for courses based on user preferences was successfully executed using the Cosine Similarity algorithm.


**Release Notes**:

1. **Gamification Implemented**  
   - A gamification system has been integrated into the platform, allowing users to earn rewards and badges for specific achievements, such as completing a course or reaching learning milestones.

2. **Course Purchase Feature Added**  
   - A purchase system has been implemented where users can buy courses. Payment processing is handled through Stripe, ensuring secure and seamless transactions. Once purchased, the course is unlocked, and the user gains full access to its content.

3. **Favorite Section Added**  
   - A favorite section has been introduced, enabling users to mark courses as favorites. This feature allows easy access to preferred courses and enhances the user experience by saving and organizing favorite content.

4. **Redis and Caching System Integrated**  
   - Redis caching has been implemented across several services to improve performance and reduce database load. Popular queries, such as fetching all courses, filtering by name or instructor, and course recommendations, are now cached, resulting in faster responses for users. The caching layer significantly improves user experience by providing quicker data access and reducing redundant database queries.

## **Features**

### **Tasks for Online Course Management Platform**

1. **User Registration and Authentication** 
   - **Task**: Implement user registration functionality with email and password.
   - **Description**: Users can sign up by providing their email, password, and name. The system stores this information securely, with passwords encrypted using a secure algorithm (e.g., BCrypt).

2. **User Role Management** 
   - **Task**: Implement role-based authentication for different users.
   - **Description**: Supports roles like "Instructor", "Student", and "Admin". Based on roles, users have different access levels to platform features.

3. **Login and JWT Authentication** 
   - **Task**: Implement login system using JWT for stateless authentication.
   - **Description**: After logging in with credentials, users receive a JWT token for secure communication in subsequent requests.

4. **Course Creation and Management** 
   - **Task**: Allow instructors to create and manage their courses.
   - **Description**: Instructors can create courses, edit them, and manage content such as course title, description, and category.

5. **Course Enrollment** 
   - **Task**: Enable students to enroll in available courses.
   - **Description**: Students can browse courses, enroll, and check if a course is full.

6. **Lesson Management** 
   - **Task**: Allow instructors to add lessons to their courses.
   - **Description**: Instructors can add lessons with titles, content, and optional video URLs.

7. **Course List and Search** 
   - **Task**: Display courses with search and filter functionality.
   - **Description**: Students can search courses by name, category, or instructor, and the list is paginated for scalability.

8. **Student Dashboard** 
   - **Task**: Create a dashboard for students to view their enrolled courses and lessons.
   - **Description**: Students can track their progress and mark lessons as completed.

9. **Instructor Dashboard** 
   - **Task**: Provide a dashboard for instructors to manage courses and students.
   - **Description**: Instructors can view courses they’ve created, manage lessons, and track student enrollments.

10. **Admin Panel** 
    - **Task**: Implement an admin panel for managing users, courses, and enrollments.
    - **Description**: Admins can manage users, approve/reject courses, enroll students manually, and delete accounts.

11. **Course Completion Tracking** 
    - **Task**: Implement a system for tracking course completion.
    - **Description**: Students can track their course completion status, marking lessons as completed.

12. **Payment System Integration** 
    - **Task**: Integrate a payment gateway for course purchases.
    - **Description**: Integrate Stripe or PayPal to handle course payments and issue receipts.

13. **Course Rating and Review System** 
    - **Task**: Implement a rating and review system for courses.
    - **Description**: Students can rate and review courses after completing them.

14. **Notification System** 
    - **Task**: Implement notifications for course updates, enrollment, and other important actions.
    - **Description**: Students and instructors receive notifications via email or platform notifications for important updates.

15. **Responsive Design** 
    - **Task**: Ensure the platform is responsive and mobile-friendly.
    - **Description**: The platform is optimized for both desktop and mobile devices.

16. **Analytics and Reports for Admins** 
    - **Task**: Implement analytics and reporting for admins.
    - **Description**: Admins can view reports on active users, popular courses, and payments, displayed in graphs or tables.

17. **Security and Data Protection** 
    - **Task**: Ensure secure storage and transmission of user data.
    - **Description**: Implement encryption, secure storage practices, and authorization checks to protect user data.

---

## **Technologies Used**

1. **Backend Framework** 
   - **Spring Boot**: A Java-based framework for developing the backend, managing RESTful APIs, and handling user authentication and authorization.

2. **Database** (MongoDB provided) 
   - **MySQL** or **PostgreSQL**: Relational databases for storing user, course, lesson, and enrollment data. These databases offer scalability and robust querying capabilities.

3. **Authentication and Authorization** 
   - **Spring Security**: Provides authentication and authorization features for role-based access control (RBAC).
   - **JWT (JSON Web Token)**: Used for stateless authentication, securely transmitting information between client and server.
     
4. **Payment System** 
   - **Stripe** or **PayPal**: Integrated payment gateways for handling course purchases securely.

5. **Notification System**
   - **Spring Integration** or **Spring Kafka**: Frameworks for asynchronous messaging to send notifications.
   - **JavaMail API**: Used to send emails like course enrollment confirmations.

6. **API Documentation** 
   - **Swagger/OpenAPI**: Used for API documentation and testing, providing an interactive interface for exploring and testing platform APIs.

7. **Logging** 
   - **SLF4J with Logback**: For logging events and errors, aiding in debugging and production monitoring.

8. **Version Control** 
   - **Git**: For version control and collaboration on source code.
   - **GitHub** or **GitLab**: Platforms for hosting the repository and collaborating.

9. **Deployment** 
   - **Docker**: Containerizes the application for portability across environments.

10. **Development Tools** 
   - **IntelliJ IDEA** or **VS Code**: IDEs for writing and debugging Java code.
   - **Postman**: A tool for testing APIs during development.

11. **Unit and Integration Testing** 
   - **Mockito**: For mocking external dependencies in tests.

12. **Data Protection and Encryption** 
   - **BCrypt**: Securely hashes user passwords to protect against unauthorized access.
   - **TLS/SSL**: Encrypts data in transit to protect sensitive information.

13. **CI/CD Pipeline** 
   - **GitHub Actions**: For automating testing and deployment processes.

---

## **Schemas**

![Database Schema](https://github.com/user-attachments/assets/dcbed160-e82a-4da7-ac6c-9ea43e60e240)

![Forum](https://github.com/user-attachments/assets/a7c01568-21f8-4879-8b7d-7855ab1398c3)

---

## **Getting Started**

### Prerequisites

- Java **17**
- Spring Boot
- MongoDB
- Docker
- Stripe/PayPal for payment integration (Optional)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/doguhannilt/skillify-backend
   cd doguhannilt/skillify-backend
   ```

2. Set up the database (MySQL/PostgreSQL):
   - Create a database for the platform and update the `application.properties` file with the database connection details.

```
spring.application.name=
spring.data.mongodb.uri=
spring.data.mongodb.database=


cloudinary.cloud_name=
cloudinary.api_key=
cloudinary.api_secret=

stripe_api_key=

logging.level.org.springframework.security=
logging.level.org.springframework=
logging.level.com.skillify.project=

spring.mail.host=
spring.mail.port=
spring.mail.username=
spring.mail.password=
spring.mail.protocol=
spring.mail.smtp.auth=
spring.mail.smtp.starttls.enable=
spring.mail.smtp.ssl.protocols=

```

3. Build and run the application:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. Access the platform in your browser at `http://localhost:8080`.

This project is built with **Java 17 (LTS)**, which provides long-term support and various performance and language improvements.



## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

