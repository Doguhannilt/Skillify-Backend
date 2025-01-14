package com.skillify.project.interfaces;

import org.springframework.http.ResponseEntity;

public interface FavoriteService {
    ResponseEntity<String> addFavorite(String courseId) throws Exception;
    ResponseEntity<String> removeFavorite(String courseId) throws Exception;
}
