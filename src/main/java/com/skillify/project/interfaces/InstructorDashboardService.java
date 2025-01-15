package com.skillify.project.interfaces;

import com.skillify.project.model.Course;

import java.util.List;

public interface InstructorDashboardService {
    List<Course> getInstructorCourses(String instructorId) throws Exception;
}
