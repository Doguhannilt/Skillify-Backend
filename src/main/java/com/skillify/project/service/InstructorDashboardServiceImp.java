package com.skillify.project.service;

import com.skillify.project.interfaces.InstructorDashboardService;
import com.skillify.project.model.Course;

import com.skillify.project.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
public class InstructorDashboardServiceImp implements InstructorDashboardService {

    private final CourseRepository courseRepository;

    public InstructorDashboardServiceImp(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getInstructorCourses(String instructorId) throws Exception {
        return courseRepository.findByInstructorId(instructorId);
    }
}

