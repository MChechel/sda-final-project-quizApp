package com.teamA.controller;


import com.teamA.dtos.SurveyListDto;
import com.teamA.model.Survey;
import com.teamA.service.SurveyService;
import com.teamA.utils.QuizUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @GetMapping()
    public ResponseEntity<SurveyListDto> getSurveysByPage(
            @RequestParam(name="page", defaultValue = "0") int pageNum,
            @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "order", defaultValue = "desc") String order){
        Page<Survey> surveyPage = surveyService.getAllSurveysByPage(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)));
        List<Survey> surveys = new ArrayList<>();
        surveyPage.forEach(surveys::add);
        SurveyListDto surveyListDto = new SurveyListDto(surveys, pageNum, surveyPage.getTotalElements());
        return ResponseEntity.ok(surveyListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable("id") Long id) {
        Optional<Survey> survey = surveyService.getSurveyWithId(id);
        try {
            return new ResponseEntity<Survey>(survey.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    //TODO: needs to be connected with question?
    public ResponseEntity<Survey> addSurvey(@RequestBody Survey newSurvey) {
        try {
            return new ResponseEntity<>(surveyService
                    .addSurvey(newSurvey), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey updatedSurvey) {
        if (surveyService.updateSurvey(id, updatedSurvey) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/{id}/questions")
//    public ResponseEntity<Survey> updateSurveyCreateQuestion(@PathVariable Long id, @RequestBody Question questions){
//        Survey s = surveyService.getSurveyWithId(id).get();
//        questionService.createQuestion(questions);
//        List<Question> optionalQuestions = questionService.getAllQuestions();
//        Question newQuestion = optionalQuestions.stream()
//                .max(Comparator.comparing(Question::getId))
//                .get();
//        List<Question> questionList = s.getQuestions();
//        questionList.add(newQuestion);
//        s.setQuestions(questionList);
//        if(SurveyService.updateSurvey(id, s)!=null){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSurveyById(@PathVariable("id") Long id) {
        try {
            Survey survey = surveyService.getSurveyWithId(id).orElseThrow(() -> new IllegalArgumentException("Invalid survey id: " + id));
            surveyService.deleteSurveyById(survey);
            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//
//    @GetMapping("/surveys")
//    public ResponseEntity<List<Survey>> getAllSurveys(){
//        List<Survey> surveys= surveyService.getAllSurveys();
//        if (surveys.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        try{
//            return new ResponseEntity<>(surveys,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @GetMapping("/surveys/{id}")
//    public ResponseEntity<Survey> getSurveyById(@PathVariable("id") Long id){
//        Optional<Survey> survey = surveyService.getSurveyById(id);
//        try{
//            return new ResponseEntity<Survey>(survey.get(),HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @PostMapping("/survey")
//    public ResponseEntity<Survey> createSurvey(@RequestBody Survey newSurvey){
//        try{
//            return new ResponseEntity<>(surveyService
//                    .createSurvey(newSurvey), HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/surveys/{id}")
//    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id,@RequestBody Survey updatedSurvey){
//        if(surveyService.updateSurvey(id, updatedSurvey)!=null){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/surveys/{id}")
//    public ResponseEntity<HttpStatus> deleteSurveyById(@PathVariable("id") Long id){
//        try{
//            Survey survey = surveyService.getSurveyById(id).orElseThrow(() -> new IllegalArgumentException("Invalid survey id: " + id));
//            surveyService.deleteSurveyById(survey);
//            return new ResponseEntity<>(HttpStatus.GONE);
//        }catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
