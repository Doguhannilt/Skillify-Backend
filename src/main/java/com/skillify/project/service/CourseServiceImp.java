package com.skillify.project.service;

import com.skillify.project.interfaces.CourseService;
import com.skillify.project.model.Course;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.UserRepository;
import com.skillify.project.utils.MailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CourseServiceImp implements CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final MailSender mailSender;

    public CourseServiceImp(UserRepository userRepository, CourseRepository courseRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.mailSender = mailSender;
    }

    @Override
    public Course createCourse(Course course) throws Exception {
        try {
            // Check if the course already exists
            Optional<Course> existingCourse = courseRepository.findByName(course.getName());
            if (existingCourse.isPresent()) {
                throw new Exception("This course name is already taken");
            }

            String instructorObjectId = course.getInstructorId();
            System.out.println("Instructor ID: " + instructorObjectId);

            Optional<User> instructor = userRepository.findById(instructorObjectId);
            System.out.println("Instructor found: " + instructor.isPresent());

            if (instructor.isEmpty()) {
                throw new IllegalArgumentException("Invalid instructor");
            }

            Course newCourse = new Course();
            newCourse.setName(course.getName());
            newCourse.setInstructorId(course.getInstructorId());

            String instructorEmail = instructor.get().getEmail();
            mailSender.sendEmailToInstructor(instructor, course, instructorEmail, "Your course has been successfully created.");

            return courseRepository.save(newCourse);
        } catch (IllegalArgumentException e) {
            // Log and rethrow specific error
            System.out.println("Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log unexpected errors
            System.out.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while creating course", e);
        }
    }


    @Override
    public ResponseEntity<String> deleteCourse(Course course) throws Exception {
        try {
            String courseId = String.valueOf(course.getId());
            if (courseId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is not valid");
            }
            courseRepository.deleteById(String.valueOf(course.getId()));

            Optional<User> instructor = userRepository.findById(course.getInstructorId());
            String instructorEmail = instructor.get().getEmail();
            // Send email to instructor upon course deletion
            mailSender.sendEmailToInstructor(instructor, course,instructorEmail, "Your course has been deleted.");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateCourse(Course course) {
        try {
            Optional<Course> existingCourse = courseRepository.findById(String.valueOf(course.getId()));
            if (existingCourse.isEmpty()) {
                throw new Exception("Course not found");
            }

            Course courseToUpdate = existingCourse.get();
            courseToUpdate.setName(course.getName());
            courseToUpdate.setInstructorId(course.getInstructorId());
            courseRepository.save(courseToUpdate);

            return ResponseEntity.status(HttpStatus.OK).body("Course updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
