package com.teamA.service.implementation;

import com.teamA.model.Question;
import com.teamA.repository.QuestionRepository;
import com.teamA.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * setting up Question Service Implementation
 *
 * author: M.Chechel
 * */

@Service
@Transactional
public class QuestionServiceImplementation implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Page<Question> getAllQuestionsByPage(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Optional<Question> getQuestionWithId(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question createQuestion(Question question) {
    return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long id, Question updatedQuestion) {
        Optional<Question> foundQuestion = getQuestionWithId(id);

        if(foundQuestion.isPresent()){
            Question question = foundQuestion.get();
            question.setContent(updatedQuestion.getContent());
           // question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            question.setPoints(updatedQuestion.getPoints());
            return questionRepository.save(question);
        }else{
            return null;
        }
    }

    @Override
    public void deleteQuestionById(Question q) {
        questionRepository.delete(q);
    }

    @Override
    public void deleteAllQuestions() {
        questionRepository.deleteAll();
    }
}
