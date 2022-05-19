package com.teamA.service;

import com.teamA.model.Question;

import java.util.List;
import java.util.Optional;

/*
 * setting up Question service
 *
 * author: M.Chechel
 * */
public interface QuestionService {

    List<Question> getAllQuestions();

    Optional<Question> getQuestionWithId(Long Id);

    Question createQuestion(Question question);

    Question updateTask(Long id, Question updatedQuestion);

    void deleteQuestionById(Question q);

    void deleteAllQuestions();

}
