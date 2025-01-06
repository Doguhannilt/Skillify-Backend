package com.skillify.project.service;

import com.skillify.project.controller.CourseController;
import com.skillify.project.interfaces.CourseService;
import com.skillify.project.model.Course;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.UserRepository;
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


            Optional<User> isIntructorValid = userRepository.findById(Long.valueOf(course.getInstructorId()));

            if (isIntructorValid.isEmpty()) {
                throw new IllegalArgumentException("Invalid");
            }


            Course newCourse = new Course();
            newCourse.setName(course.getName());
            newCourse.setInstructorId(course.getInstructorId());

            System.out.println(newCourse);
            return courseRepository.save(newCourse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }
}
