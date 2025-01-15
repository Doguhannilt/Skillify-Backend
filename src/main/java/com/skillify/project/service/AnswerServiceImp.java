package com.skillify.project.service;

import com.skillify.project.interfaces.AnswerService;
import com.skillify.project.model.Answer;
import com.skillify.project.model.Question;
import com.skillify.project.repository.AnswerRepository;
import com.skillify.project.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImp implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImp(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Answer answerQuestion(Answer answer) throws Exception {
        Question question = questionRepository.findById(String.valueOf(answer.getQuestionId())).orElse(null);

        if (answer.getContent() == null || answer.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Answer content cannot be empty.");
        }
        if (answer.getInstructorId() == null) {
            throw new IllegalArgumentException("Instructor ID cannot be null.");
        }
        if (question == null) {
            throw new Exception("Question not found.");
        }
        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer;
    }

}

