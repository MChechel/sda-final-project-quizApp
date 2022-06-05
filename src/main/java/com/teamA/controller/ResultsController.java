package com.teamA.controller;


import com.teamA.dtos.PersonalResultsDTO;
import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.model.Results;
import com.teamA.service.AnswersService;
import com.teamA.service.QuestionService;
import com.teamA.service.ResultsService;
import com.teamA.service.SurveyService;
import com.teamA.utils.QuizUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/questions/results")
public class ResultsController {

    private ResultsService resultsService;
    private final AnswersService answerService;
    private QuestionService questionService;
    private SurveyService surveyService;

    @Autowired
    public ResultsController(ResultsService resultsService,  AnswersService answerService, QuestionService questionService,SurveyService surveyService) {
        this.resultsService = resultsService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.surveyService = surveyService;
    }


    @PostMapping
            public ResponseEntity<Results> createResultsItem(@RequestBody Results results) {
//                                                             @RequestParam(name="quizResultsQuestion", defaultValue = "1") Long quizResultsQuestion,
//                                                             @RequestParam(name = "quizResultsAnswer", defaultValue = "1") Long quizResultsAnswer,
//                                                             @RequestParam(name="userLogin", defaultValue = "user") String userLogin,
//                                                             @RequestParam(name = "surveyUniqueCode", defaultValue = "1") String surveyUniqueCode)


        StringBuffer sb = new StringBuffer("Username:"+results.getUserLogin()+"; SurveyUniqueCode: "+ results.getSurveyUniqueCode());
        String[] answerQuestionArray = results.getResults().split(",");
        System.out.println(answerQuestionArray.length + " "+ Arrays.toString(answerQuestionArray));
        Map<Long, Long> temporaryMap = new HashMap<>();
        for (String str:answerQuestionArray){
            if (str.length()<=1){
                continue;
            }else{

                //List<String> separateValues = Stream.of(str.split(":")).collect(Collectors.toList());
            List<Integer> separateValues = Stream.of(str.split(":"))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
                System.out.println(separateValues);
                temporaryMap.put((long)(separateValues.get(0)),(long)(separateValues.get(1)));

            }
        }
        temporaryMap.forEach((key, value)->{
            Question question = questionService.getQuestionWithId(key).get();
            Answers answer = answerService.getAnswerById( value);
            results.setMaxAmountOfPoints(results.getMaxAmountOfPoints()+question.getPoints());
            if(answer.isCorrect()){
            results.setAmountOfPoints(results.getAmountOfPoints()+question.getPoints());
            }

        });
//        System.out.println(quizResultsAnswer + "  answers");
//        System.out.println(quizResultsQuestion + " surveys");
//        for (int i = 0; i < theMap.size(); i++) {
//            System.out.println(quizResultsAnswerArray[i] + " the answer id");
//            System.out.println(quizResultsQuestionArray[i] + " the survey id");

//        }
        Results resultsObj = new Results(1l,results.getUserLogin(), results.getSurveyUniqueCode(), sb.toString(), results.getAmountOfPoints(), results.getMaxAmountOfPoints());
        try{
            return new ResponseEntity<>(resultsService.addResults(resultsObj), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/personal")
        public ResponseEntity<PersonalResultsDTO> getUserPersonalResults(@RequestParam(name="userLogin", defaultValue = "user") String userLogin,
        @RequestParam(name="page", defaultValue = "0") int pageNum,
        @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
        @RequestParam(name = "sort", defaultValue = "maxAmountOfPoints") String sort,
        @RequestParam(name = "order", defaultValue = "desc") String order){
            Page<Results> questionPage = resultsService.getAllResultsForUserLogin(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)),userLogin);
            List<Results> results = new ArrayList<>();
            questionPage.forEach(results::add);
            PersonalResultsDTO resultsListDto = new PersonalResultsDTO(results, pageNum, questionPage.getTotalElements());
            return ResponseEntity.ok(resultsListDto);
        }



    @GetMapping("/professional")
    public ResponseEntity<PersonalResultsDTO> getSurveyResultsStatistics(@RequestParam(name="id", defaultValue = "1") Long id,
                                                                     @RequestParam(name="page", defaultValue = "0") int pageNum,
                                                                     @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
                                                                     @RequestParam(name = "sort", defaultValue = "points") String sort,
                                                                     @RequestParam(name = "order", defaultValue = "desc") String order){
        Page<Results> questionPage = resultsService.getAllResultsBySurveyUniqueCode(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)),id);
        List<Results> results = new ArrayList<>();
        questionPage.forEach(results::add);
        PersonalResultsDTO resultsListDto = new PersonalResultsDTO(results, pageNum, questionPage.getTotalElements());
        return ResponseEntity.ok(resultsListDto);
    }



    }

