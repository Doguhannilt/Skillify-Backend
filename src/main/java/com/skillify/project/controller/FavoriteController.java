package com.skillify.project.controller;

import com.skillify.project.service.FavoriteServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
@Tag(name = "Favorite", description = "Endpoints for favorite features")
public class FavoriteController {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
    private final FavoriteServiceImp favoriteService;

    public FavoriteController(FavoriteServiceImp favoriteService) {
        this.favoriteService = favoriteService;
    }

    @Operation(summary = "Add to your favorite")
    @PostMapping("/add")
    public ResponseEntity<String> addToYourFavoriteSection(@RequestParam String courseId){
        try{
            favoriteService.addFavorite(courseId);
            logger.info("Favorite added");
            return ResponseEntity.status(HttpStatus.CREATED).body("Added");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }

    @Operation(summary = "Add to your favorite")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteCourseFromYourFavoriteSection(@RequestParam String courseId){
        try{
            favoriteService.removeFavorite(courseId);
            logger.info("Favorite deleted");
            return ResponseEntity.status(HttpStatus.CREATED).body("Added");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }
}
