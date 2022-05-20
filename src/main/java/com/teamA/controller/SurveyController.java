package com.teamA.controller;


import com.teamA.model.Survey;
import com.teamA.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/surveys")
    public ResponseEntity<List<Survey>> getAllSurveys(){
        List<Survey> surveys= surveyService.getAllSurveys();
        if (surveys.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        try{
            return new ResponseEntity<>(surveys,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/surveys/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable("id") Long id){
        Optional<Survey> survey = surveyService.getSurveyById(id);
        try{
            return new ResponseEntity<Survey>(survey.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/survey")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey newSurvey){
        try{
            return new ResponseEntity<>(surveyService
                    .createSurvey(newSurvey), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/surveys/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id,@RequestBody Survey updatedSurvey){
        if(surveyService.updateSurvey(id, updatedSurvey)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/surveys/{id}")
    public ResponseEntity<HttpStatus> deleteSurveyById(@PathVariable("id") Long id){
        try{
            Survey survey = surveyService.getSurveyById(id).orElseThrow(() -> new IllegalArgumentException("Invalid survey id: " + id));
            surveyService.deleteSurveyById(survey);
            return new ResponseEntity<>(HttpStatus.GONE);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
