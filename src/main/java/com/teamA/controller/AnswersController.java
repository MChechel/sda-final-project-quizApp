package com.teamA.controller;

import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.service.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * setting up Answers controller
 *
 * author: M.Chechel
 * */

@RestController
@RequestMapping("/api/questions")
public class AnswersController {
    private AnswersService answersServiceImplementation;
    @Autowired
    public AnswersController(AnswersService answersService) {
        this.answersServiceImplementation = answersService;
    }

//
//    INSERT INTO ANSWERS (ID, CONTENT,IS_CORRECT,QUESTION_ID  )
//    VALUES (1,'test',1,1);
//    INSERT INTO ANSWERS (ID, CONTENT,IS_CORRECT,QUESTION_ID  )
//    VALUES (2,'test',0,1);
//    INSERT INTO ANSWERS (ID, CONTENT,IS_CORRECT,QUESTION_ID  )
//    VALUES (3,'test',0,1);
//    INSERT INTO ANSWERS (ID, CONTENT,IS_CORRECT,QUESTION_ID  )
//    VALUES (4,'test',0,1);
    @GetMapping("/{questionId}/answer")
    public ResponseEntity<Answers> getCorrectAnswer(@PathVariable("questionId") Long id){
        Answers answer = answersServiceImplementation.getCorrectAnswer(id);
        if (answer==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{questionId}/answers")
    public ResponseEntity<List<Answers>> getAnswerAll(@PathVariable("questionId") Long id){
        List<Answers> answers = answersServiceImplementation.getAllAnswers(id);
        try{
            return new ResponseEntity<List<Answers>>(answers, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{questionId}/answer")
    public ResponseEntity<Answers> createAnswer(@PathVariable("questionId") Long id,@RequestBody Answers newAnswer){
        newAnswer.setQuestionId(id);
        try{
            return new ResponseEntity<>(answersServiceImplementation
                    .createAnswer(newAnswer), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{questionId}/{id}")
    public ResponseEntity<Question> updateTask(@PathVariable Long questionId,@PathVariable Long id,@RequestBody Answers updateAnswer){
        if(answersServiceImplementation.updateAnswer(questionId,id, updateAnswer)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
