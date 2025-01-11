package com.skillify.project.service;

import com.skillify.project.interfaces.AdminService;
import com.skillify.project.model.*;
import com.skillify.project.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImp implements AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;

    public AdminServiceImp(UserRepository userRepository, CourseRepository courseRepository, LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;
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
        Optional<Course> byId = courseRepository.findById(String.valueOf(course.getId()));
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
    public ResponseEntity<List<Enrollment>> getAllEnrollments() throws Exception {
        List<Enrollment> all = enrollmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }

    @Override
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> all = reviewRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(all);
    }

    @Override
    public ResponseEntity<List<Course>> getPopularCourses() throws Exception {
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        Map<Long, Long> courseEnrollmentCount = new HashMap<>();
        for (Enrollment enrollment : enrollments) {
            courseEnrollmentCount.put(enrollment.getCourseId(),
                    courseEnrollmentCount.getOrDefault(enrollment.getCourseId(), 0L) + 1);
        }
        List<Course> popularCourses = courseRepository.findAll().stream()
                .filter(course -> courseEnrollmentCount.containsKey(course.getId()))
                .sorted(Comparator.comparingLong(course -> courseEnrollmentCount.get(course.getId())))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(popularCourses);
    }

    @Override
    public ResponseEntity<List<User>> getActiveUsers() throws Exception {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(10);
        List<User> activeUsers = userRepository.findByLastLoginAfter(thirtyDaysAgo);
        return ResponseEntity.status(HttpStatus.OK).body(activeUsers);
    }

    @Override
    public ResponseEntity<List<Enrollment>> getEnrollmentsWithinRange(LocalDate startDate, LocalDate endDate) throws Exception {
        List<Enrollment> enrollments = enrollmentRepository.findByEnrollmentDateBetween(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(enrollments);
    }


}
