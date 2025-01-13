package com.skillify.project.interfaces;

import org.springframework.http.ResponseEntity;

public interface FavoriteService {
    ResponseEntity<String> addFavorite(Long courseId) throws Exception;
    ResponseEntity<String> removeFavorite(Long courseId) throws Exception;
}
