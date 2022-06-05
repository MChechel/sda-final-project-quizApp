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

    Answers getAnswerById(Long id);

    List<Answers> getAllAnswers();

    Answers getCorrectAnswer(List<Answers> answers);

    Answers createAnswer(Answers answers);

    Answers updateAnswer(Long id, Answers updateAnswers);

    Answers getLastInputAnswer();

    void deleteAnswerById(Long id);

    void deleteAllAnswers();

}
