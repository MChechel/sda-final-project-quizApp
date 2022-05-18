package com.teamA.service.implementation;

import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.repository.AnswersRepository;
import com.teamA.service.AnswersService;
import com.teamA.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * setting up Answers service implementation
 *
 * author: M.Chechel
 * */

@Service
public class AnswersServiceImplementation implements AnswersService {

    private AnswersRepository answersRepository;
    private QuestionService questionService;

    @Autowired
    public AnswersServiceImplementation(AnswersRepository answersRepository,QuestionService questionService) {
        this.answersRepository = answersRepository;
        this.questionService = questionService;

    }

    @Override
    public List<Answers> getAllAnswers(Long questionId) {
        List<Answers> allAnswersForOneQuestion =  questionService.getQuestionWithId(questionId).get().getAnswers().stream().collect(Collectors.toList());
        return allAnswersForOneQuestion;
    }

    @Override
    public Answers getCorrectAnswer(Long questionId) {

        List<Answers> a = questionService.getQuestionWithId(questionId).get()
                            .getAnswers();
        Answers correctAnswer = new Answers();
        a.stream().forEach((answer)->{
            if (answer.isCorrect()){
                correctAnswer.setContent(answer.getContent());
                correctAnswer.setId(answer.getId());
                correctAnswer.setQuestionId(answer.getQuestionId());
                correctAnswer.setCorrect(true);
            }
        });
//        Optional<Answers> a = questionService.getQuestionWithId(questionId).get().getAnswers().stream().findFirst();
        try{
            if(!correctAnswer.getContent().isEmpty())return correctAnswer;
        }catch (NullPointerException nullE){
            return null;
        }
        return null;
    }

    @Override
    public Answers createAnswer(Answers answers) {
       answersRepository.save(answers);
       return answers;
    }

    @Override
    public Answers updateAnswer(Long questionId, Long id, Answers updateAnswers) {
        Optional<Answers> foundAnswer = (answersRepository.findById(id));
        if(foundAnswer.isPresent()&&questionId==foundAnswer.get().getQuestionId()){
            Answers answer = foundAnswer.get();
            answer.setContent(updateAnswers.getContent());
            answer.setCorrect(updateAnswers.isCorrect());
            return answersRepository.save(answer);
        }else{
            return null;
        }

    }

    @Override
    public void deleteAnswerById(Long questionId, Answers answers) {

    }

    @Override
    public void deleteAllAnswers(Long questionId) {

    }
}
