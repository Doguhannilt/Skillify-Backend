package com.skillify.project.interfaces;

import com.skillify.project.model.Course;
import com.skillify.project.model.User;

public interface CourseService {
    Course createCourse(Course course) throws Exception;
}
