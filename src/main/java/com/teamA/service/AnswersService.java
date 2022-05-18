package com.teamA.service;

import com.teamA.model.Answers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * setting up Answers service interface
 *
 * author: M.Chechel
 * */
@Service
public interface AnswersService {

    List<Answers> getAllAnswers(Long questionId);

    Answers getCorrectAnswer(Long questionId);

    Answers createAnswer(Answers answers);

    Answers updateAnswer(Long questionId,Long id, Answers updateAnswers);

    void deleteAnswerById(Long questionId,Answers answers);

    void deleteAllAnswers(Long questionId);

}
