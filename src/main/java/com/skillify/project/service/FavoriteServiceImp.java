package com.skillify.project.service;

import com.skillify.project.interfaces.FavoriteService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Favorite;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.FavoriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class FavoriteServiceImp implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CourseRepository courseRepository;

    public FavoriteServiceImp(FavoriteRepository favoriteRepository, CourseRepository courseRepository) {
        this.favoriteRepository = favoriteRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ResponseEntity<String> addFavorite(Long courseId) {
        Optional<Course> isCourseIdAvailable = courseRepository.findById(String.valueOf(courseId));
        if (!isCourseIdAvailable.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID " + courseId + " is not available.");
        }

        // Kursun zaten favorilere eklenip eklenmediğini kontrol et
        Optional<Favorite> existingFavorite = favoriteRepository.findById(courseId);
        if (existingFavorite.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course is already in your favorites.");
        }

        Favorite favorite = new Favorite();
        favorite.setCourseId(Arrays.asList(isCourseIdAvailable.get()));
        favoriteRepository.save(favorite);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Course has been added to your favorite section: " + isCourseIdAvailable.get().getName());
    }

    @Override
    public ResponseEntity<String> removeFavorite(Long courseId) {
        Optional<Favorite> favorite = favoriteRepository.findById(courseId);
        if (!favorite.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite course with ID " + courseId + " not found.");
        }

        favoriteRepository.delete(favorite.get());
        return ResponseEntity.ok("Course has been removed from your favorites.");
    }
}
