package com.skillify.project.service;

import com.skillify.project.model.Answer;
import com.skillify.project.model.Question;
import com.skillify.project.repository.AnswerRepository;
import com.skillify.project.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerServiceImp answerService;

    private Answer answer;
    private Question question;

    @BeforeEach
    void setUp() {
        answer = new Answer();
        answer.setQuestionId("1");
        answer.setInstructorId("2L");
        answer.setContent("Test Answer");

        question = new Question();
        question.setId("1");
        question.setContent("Test Question");
    }

    @Test
    void testAnswerQuestion_Success() throws Exception {
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.of(question));
        when(answerRepository.save(answer)).thenReturn(answer);

        Answer savedAnswer = answerService.answerQuestion(answer);

        assertNotNull(savedAnswer);
        assertEquals("Test Answer", savedAnswer.getContent());
        verify(questionRepository, times(1)).findById("1");
        verify(answerRepository, times(1)).save(answer);
    }

    @Test
    void testAnswerQuestion_EmptyContent() {
        answer.setContent("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> answerService.answerQuestion(answer));
        assertEquals("Answer content cannot be empty.", exception.getMessage());
    }

    @Test
    void testAnswerQuestion_NullInstructorId() {
        answer.setInstructorId(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> answerService.answerQuestion(answer));
        assertEquals("Instructor ID cannot be null.", exception.getMessage());
    }

    @Test
    void testAnswerQuestion_QuestionNotFound() {
        // Soru bulunamazsa, empty döndürüyoruz
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> answerService.answerQuestion(answer));
        assertEquals("Question not found.", exception.getMessage());
    }
}
