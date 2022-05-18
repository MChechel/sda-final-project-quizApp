package com.teamA.controller;


/*
 * setting up controller  for question object
 *
 * author: M.Chechel
 * */

import com.teamA.model.Question;
import com.teamA.service.QuestionService;
import com.teamA.service.implementation.QuestionServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private QuestionService questionServiceImplementation;

    @Autowired
    public QuestionController(QuestionServiceImplementation questionServiceImplementation) {
        this.questionServiceImplementation = questionServiceImplementation;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions= questionServiceImplementation.getAllQuestions();
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
        Optional<Question> question = questionServiceImplementation.getQuestionWithId(id);
        try{
            return new ResponseEntity<Question>(question.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody Question newQuestion){
        try{
            return new ResponseEntity<>(questionServiceImplementation
                    .createQuestion(newQuestion), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateTask(@PathVariable Long id,@RequestBody Question updatedQuestion){
        if(questionServiceImplementation.updateTask(id, updatedQuestion)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<HttpStatus> deleteQuestionById(@PathVariable("id") Long id){
        try{
            Question question = questionServiceImplementation.getQuestionWithId(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
            questionServiceImplementation.deleteQuestionById(question);
            return new ResponseEntity<>(HttpStatus.GONE);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/questions")
    public ResponseEntity<HttpStatus> deleteQuestions(){
        try{
            questionServiceImplementation.deleteAllQuestions();
            return new ResponseEntity<>(HttpStatus.GONE);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
