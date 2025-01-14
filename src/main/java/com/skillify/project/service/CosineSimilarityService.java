package com.skillify.project.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CosineSimilarityService {

    // Cosine Similarity hesaplama metodu
    public double calculateSimilarity(String text1, String text2) {
        // Metinleri tokenize ederek kelime frekanslarını hesaplayın
        Map<String, Integer> vector1 = getWordFrequency(text1);
        Map<String, Integer> vector2 = getWordFrequency(text2);

        // Vektörler arasındaki iç çarpımı hesaplayın
        double dotProduct = vector1.keySet().stream()
                .filter(vector2::containsKey)
                .mapToDouble(word -> vector1.get(word) * vector2.get(word))
                .sum();

        // Her iki vektörün büyüklüğünü hesaplayın
        double magnitude1 = Math.sqrt(vector1.values().stream().mapToDouble(val -> val * val).sum());
        double magnitude2 = Math.sqrt(vector2.values().stream().mapToDouble(val -> val * val).sum());

        // Cosine Similarity hesaplayın
        return magnitude1 > 0 && magnitude2 > 0 ? dotProduct / (magnitude1 * magnitude2) : 0.0;
    }

    // Kelime frekanslarını hesaplayan yardımcı metot
    private Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        String[] words = text.toLowerCase().split("\\s+"); // Boşluklara göre ayır
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        return frequencyMap;
    }
}
