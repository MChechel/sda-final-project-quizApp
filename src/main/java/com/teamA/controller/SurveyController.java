package com.teamA.controller;


import com.teamA.dtos.SurveyListDto;
import com.teamA.model.Question;
import com.teamA.model.Survey;
import com.teamA.model.User;
import com.teamA.service.QuestionService;
import com.teamA.service.SurveyService;
import com.teamA.service.UserService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private SurveyService surveyService;
    private QuestionService questionService;
    private UserService userService;


    @Autowired
    public SurveyController(SurveyService surveyService, UserService userService,QuestionService questionService) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.questionService = questionService;

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

    @GetMapping("/unique")
    public ResponseEntity<SurveyListDto> getSurveysByUniqueCode(
            @RequestParam(name="page", defaultValue = "0") int pageNum,
            @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "order", defaultValue = "desc") String order,
            @RequestParam(name = "uniqueCode", defaultValue = "0") String uniqueCode
            ){
        Page<Survey> surveyPage = surveyService.getAllSurveysByPage(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)));
        List<Survey> surveys = new ArrayList<>();
        surveyPage.forEach(surveys::add);


        if(uniqueCode!="0"){
            surveys=surveys.stream().filter((q)->q.getHashCode()==uniqueCode).collect(Collectors.toList());
        }
        SurveyListDto surveyListDto = new SurveyListDto(surveys, pageNum, surveyPage.getTotalElements());
        return ResponseEntity.ok(surveyListDto);
    }

    @GetMapping("/personal")
    public ResponseEntity<SurveyListDto> getSurveysByUser(
            @RequestParam(name="page", defaultValue = "0") int pageNum,
            @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "order", defaultValue = "desc") String order,
            @RequestParam(name = "user", defaultValue = "user999") String user
    ){
        Page<Survey> surveyPage = surveyService.getAllSurveysByPage(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)));
        List<Survey> surveys = new ArrayList<>();
        System.out.println("method getSurveysByUser  and printing serveyPagable size - "+surveyPage.getTotalElements());
        surveyPage.forEach(surveys::add);
        if(user!="user999"){
        //System.out.println("method getSurveysByUser  and printing the user login - "+user);
            surveys=surveys.stream().filter((q)->q.getUser().getEmail().equals(user)).collect(Collectors.toList());
        }
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
    public ResponseEntity<Survey> addSurvey(@RequestParam(name = "user", defaultValue = "user999") String user,
                                            @RequestBody Survey newSurvey) {
        newSurvey.setUser( userService.getUserByEmail(user));
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSurveyById(@PathVariable("id") Long id) {
        try {
            Survey survey = surveyService.getSurveyWithId(id).orElseThrow(() -> new IllegalArgumentException("Invalid survey id: " + id));
            questionService.deleteAllQuestionsBySurvey(survey);
            surveyService.deleteSurveyById(survey);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
