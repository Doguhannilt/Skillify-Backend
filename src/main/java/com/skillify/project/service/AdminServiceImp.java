package com.skillify.project.service;

import com.skillify.project.interfaces.AdminService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Enrollment;
import com.skillify.project.model.Lesson;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.EnrollmentRepository;
import com.skillify.project.repository.LessonRepository;
import com.skillify.project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AdminServiceImp(UserRepository userRepository, CourseRepository courseRepository, LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        List<User> all = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }

    @Override
    public ResponseEntity<Optional<User>> getUserById(User user) throws Exception {
        Optional<User> byId = userRepository.findById(Long.valueOf(user.getId()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(byId);
    }

    @Override
    public void deleteUserById(User user) throws Exception {
        userRepository.deleteById(Long.valueOf(user.getId()));
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourses(Course course) throws Exception {
        List<Course> all = courseRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }

    @Override
    public ResponseEntity<Course> getCourseById(Course course) throws Exception {
        Optional<Course> byId = courseRepository.findById(course.getId());
        return ResponseEntity.status(HttpStatus.OK).body(byId.get());
    }

    @Override
    public ResponseEntity<List<Lesson>> getAllLessons(Lesson lesson) throws Exception {
        List<Lesson> all = lessonRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }

    @Override
    public ResponseEntity<Lesson> getLessonById(Lesson lesson) throws Exception {
        Optional<Lesson> byId = lessonRepository.findById(lesson.getId());
        return ResponseEntity.status(HttpStatus.OK).body(byId.get());
    }

    @Override
    public void deleteLessonById(Lesson lesson) throws Exception {
         lessonRepository.deleteById(lesson.getId());;
    }

    @Override
    public ResponseEntity<List<Enrollment>> getAllEnrollments(Enrollment enrollment) throws Exception {
        List<Enrollment> all = enrollmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }
}
