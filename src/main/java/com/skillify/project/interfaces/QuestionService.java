package com.skillify.project.interfaces;

import com.skillify.project.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsByForumTopicId(String topicId) throws Exception;
    Question askQuestion(Question question) throws Exception;
}
