package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.service.CourseServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseServiceImp courseServiceImp;

    public CourseController(CourseServiceImp courseServiceImp)
    {
        this.courseServiceImp = courseServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        try {
            // Course creation
            Course createdCourse = courseServiceImp.createCourse(course);

            // Null control for price
            if (createdCourse == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course creation failed");
            }
            // Null control for price
            Integer price = createdCourse.getPrice();
            if (price == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price cannot be null");
            }

            // Check if price is valid
            if (createdCourse.getPrice() == null || createdCourse.getPrice() < 0 || createdCourse.getPrice() > 10000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price should be valid");
            }

            // Description length control
            if (createdCourse.getDescription().length() < 10) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description length must be longer than 10 characters");
            }

            // Return created course ID as a success response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestBody Course course) {
        try {
            ResponseEntity<String> deletedCourse = courseServiceImp.deleteCourse(course);
            if (deletedCourse == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course deletion failed");}

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        try {
            ResponseEntity<String> updatedCourse = courseServiceImp.updateCourse(course);
            if (updatedCourse == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course update failed");}
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been updated" + updatedCourse.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
