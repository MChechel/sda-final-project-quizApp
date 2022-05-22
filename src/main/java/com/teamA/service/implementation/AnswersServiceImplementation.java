package com.teamA.service.implementation;

import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.repository.AnswersRepository;
import com.teamA.service.AnswersService;
import com.teamA.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    // temporary!
    @Override
    public List<Answers> getAllAnswers(){
        List<Answers> anw = new ArrayList<>();
        answersRepository.findAll().forEach(anw::add);
        return anw;
    }



    public Answers getCorrectAnswer(List<Answers> allAnswers) {
        Answers correctAnswer = new Answers();
        allAnswers.stream().forEach((answer)->{
            if (answer.isCorrect()){
                correctAnswer.setContent(answer.getContent());
                correctAnswer.setId(answer.getId());
                correctAnswer.setCorrect(true);
            }
        });
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
    public Answers getLastInputAnswer(){
        return answersRepository.findAll().iterator().next();
    }

    @Override
    public Answers updateAnswer(Long id, Answers updateAnswers) {
        Optional<Answers> foundAnswer = (answersRepository.findById(id));
        if(foundAnswer.isPresent()){
            Answers answer = foundAnswer.get();
            answer.setContent(updateAnswers.getContent());
            answer.setCorrect(updateAnswers.isCorrect());
            return answersRepository.save(answer);
        }else{
            return null;
        }

    }

    @Override
    public void deleteAnswerById(Long id) {

    }

    @Override
    public void deleteAllAnswers() {
    }
}
