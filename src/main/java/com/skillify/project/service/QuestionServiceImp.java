package com.skillify.project.service;

import com.skillify.project.interfaces.QuestionService;
import com.skillify.project.model.ForumTopic;
import com.skillify.project.model.Question;
import com.skillify.project.repository.ForumTopicRepository;
import com.skillify.project.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImp implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ForumTopicRepository forumTopicRepository;

    public QuestionServiceImp(QuestionRepository questionRepository, ForumTopicRepository forumTopicRepository) {
        this.questionRepository = questionRepository;
        this.forumTopicRepository = forumTopicRepository;
    }

    @Override
    public List<Question> getQuestionsByForumTopicId(Long topicId) throws Exception {
        if (topicId == null) {
            throw new IllegalArgumentException("Forum topic ID cannot be null.");
        }

        List<Question> questions = questionRepository.findByForumTopicId(topicId);
        if (questions.isEmpty()) {
            throw new Exception("No questions found for the given forum topic ID: " + topicId);
        }

        return questions;
    }

    @Override
    public Question askQuestion(Question question) throws Exception {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }

        if (question.getContent() == null || question.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Question content cannot be empty.");
        }

        if (question.getForumTopicId() == null) {
            throw new IllegalArgumentException("Forum topic ID for the question cannot be null.");
        }

        // Check if the forum topic exists
        Optional<ForumTopic> forumTopic = forumTopicRepository.findById(question.getForumTopicId());
        if (forumTopic.isEmpty()) {
            throw new Exception("Forum topic with ID " + question.getForumTopicId() + " does not exist.");
        }

        return questionRepository.save(question);
    }

}
