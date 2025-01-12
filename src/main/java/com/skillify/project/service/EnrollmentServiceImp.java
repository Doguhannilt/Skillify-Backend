package com.skillify.project.service;

import com.skillify.project.interfaces.EnrollmentService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Enrollment;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.EnrollmentRepository;
import com.skillify.project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, EmailServiceImp emailServiceImp) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ResponseEntity<String> createEnrollment(Long course_id, Long user_id) throws Exception {
        Optional<Course> isCourseIdValid = courseRepository.findById(String.valueOf(course_id));
        if (isCourseIdValid.isEmpty()) {
            throw new Exception("Course is not available with ID: " + course_id);
        }

        Optional<User> isUserIdValid = userRepository.findById(user_id);
        if (isUserIdValid.isEmpty()) {
            throw new Exception("User is not available with ID: " + user_id);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(course_id);
        enrollment.setStudentId(user_id);

        enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(enrollment.getId()));
    }

    @Override
    public ResponseEntity<String> deleteEnrollment(Enrollment enrollment) throws Exception {
        enrollmentRepository.deleteById(Long.valueOf(enrollment.getId()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.valueOf(enrollment.getId()));
    }

    @Override
    public boolean isCourseCompleted(Long enrollmentId) throws Exception {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findById(enrollmentId);
        if (enrollmentOptional.isEmpty()) {
            throw new Exception("Enrollment not found");
        }

        Enrollment enrollment = enrollmentOptional.get();

        return Enrollment.getLessonCompletionStatus().values().stream().allMatch(Boolean::booleanValue);
    }

}