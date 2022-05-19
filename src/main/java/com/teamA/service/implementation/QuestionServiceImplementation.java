package com.teamA.service.implementation;

import com.teamA.model.Question;
import com.teamA.repository.QuestionRepository;
import com.teamA.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * setting up Question Service Implementation
 *
 * author: M.Chechel
 * */

@Service
public class QuestionServiceImplementation implements QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getAllQuestions() {
        Iterable<Question> questions;
        questions = questionRepository.findAll();
        List<Question> results= new ArrayList<>();
        questions.forEach(results::add);
       return results;
    }

    @Override
    public Optional<Question> getQuestionWithId(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question createQuestion(Question question) {
    questionRepository
            .save(new Question(question.getContent(),question.getPoints(),question.getCorrectAnswer(),question.getAnswers()));
    return question;
    }

    @Override
    public Question updateTask(Long id, Question updatedQuestion) {
        Optional<Question> foundQuestion = (questionRepository.findById(id));
        if(foundQuestion.isPresent()){
            Question question = foundQuestion.get();
            question.setContent(updatedQuestion.getContent());
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
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
