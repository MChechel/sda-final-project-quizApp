package com.teamA.controller;


/*
 * setting up controller  for question object
 *
 * author: M.Chechel
 * */

import com.teamA.model.Question;
import com.teamA.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;

    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions(){

        List<Question> questions= new ArrayList<>();

        questions = questionRepository.findAll();
        if (questions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        try{
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionWithId(@PathVariable("id") Long id){

//        Question question= new Question();

        Optional<Question> question = questionRepository.findById(id);

        try{
            return new ResponseEntity<Question>(question.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/questions")
    public ResponseEntity<Question> createTask(@RequestBody Question newQuestion){

        try{
            return new ResponseEntity<>(questionRepository
                    .save(new Question(newQuestion.getContent(),newQuestion.getPoints(),newQuestion.getAnswers(),newQuestion.getCorrectAnswer())), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateTask(@PathVariable Long id,@RequestBody Question updatedQuestion){

        Optional<Question> foundQuestion = Optional.of(questionRepository.getById(id));
        if(foundQuestion.isPresent()){
            Question question = foundQuestion.get();
            question.setContent(updatedQuestion.getContent());
            question.setAnswers(updatedQuestion.getAnswers());
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            question.setPoints(updatedQuestion.getPoints());

            return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/questions/{id}")
    public ResponseEntity<HttpStatus> deleteQuestionById(@PathVariable("id") Long id){
        try{
            questionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/questions")
    public ResponseEntity<HttpStatus> deleteQuestions(){
        try{
            questionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
