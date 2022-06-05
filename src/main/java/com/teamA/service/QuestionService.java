package com.teamA.service;

import com.teamA.model.Question;
import com.teamA.model.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

/*
 * setting up Question service
 *
 * author: M.Chechel
 * */
public interface QuestionService {

    Page<Question> getAllQuestionsByPage(Pageable pageable);

    Optional<Question> getQuestionWithId(Long Id);

    Question createQuestion(Question question);

    Question updateQuestion(Long id, Question updatedQuestion);

    void deleteQuestionById(Question q);

    void deleteAllQuestions();

    void deleteAllQuestionsBySurvey(Survey survey);



}
