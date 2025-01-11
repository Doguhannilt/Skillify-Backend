package com.skillify.project.service;

import com.skillify.project.interfaces.CourseService;
import com.skillify.project.model.Course;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public CourseServiceImp(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;

    }

    @Override
    public Course createCourse(Course course) throws Exception {
        try {
            Optional<Course> existingCourse = courseRepository.findByName(course.getName());
            if (existingCourse.isPresent()) {
                throw new Exception("This course name is already taken");
            }

            // instructorId'yi ObjectId'ye dönüştür
            ObjectId instructorObjectId = new ObjectId(String.valueOf(course.getInstructorId()));
            Optional<User> instructor = userRepository.findById(instructorObjectId);  // findById(ObjectId id) kullanılıyor

            if (instructor.isEmpty()) {
                throw new IllegalArgumentException("Invalid instructor");
            }

            Course newCourse = new Course();
            newCourse.setName(course.getName());
            newCourse.setInstructorId(course.getInstructorId());  // instructorId burada String olarak kalabilir

            return courseRepository.save(newCourse);
        } catch (Exception e) {
            System.out.println("Error while creating course: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<String> deleteCourse(Course course) throws Exception {
        try {
            String courseId = String.valueOf(course.getId());
            if(courseId.isEmpty()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is not valid"); }
            courseRepository.deleteById(course.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateCourse(Course course) {
        try {
            Optional<Course> existingCourse = courseRepository.findById(course.getId());
            if (existingCourse.isEmpty()) {throw new Exception("Course not found");}

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