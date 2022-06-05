package com.teamA.controller;


/*
 * setting up controller  for question object
 *
 * author: M.Chechel
 * */

import com.teamA.dtos.QuestionListDto;
import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.model.Survey;
import com.teamA.service.AnswersService;
import com.teamA.service.QuestionService;
import com.teamA.service.SurveyService;
import com.teamA.utils.QuizUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswersService answersService;
    private final SurveyService surveyService;

@Autowired
    public QuestionController(QuestionService questionService, AnswersService answersService, SurveyService surveyService) {
        this.questionService = questionService;
        this.answersService = answersService;
        this.surveyService = surveyService;
}


    @GetMapping
    public ResponseEntity<QuestionListDto> getQuestionsByPage(
            @RequestParam(name="page", defaultValue = "0") int pageNum,
            @RequestParam(name = "items", defaultValue = QuizUtils.DEFAULT_ITEMS_PER_PAGE) int totItems,
            @RequestParam(name = "sort", defaultValue = "points") String sort,
            @RequestParam(name = "order", defaultValue = "desc") String order,
             @RequestParam(name = "surveyId", defaultValue = "0") Long surveyId

    ){
         Page<Question> questionPage = questionService.getAllQuestionsByPage(PageRequest.of(pageNum, totItems, QuizUtils.getSortOfColumn(sort, order)));
        List<Question> questions = new ArrayList<>();
        questionPage.forEach(questions::add);
        if(surveyId!=0){
        questions=questions.stream().filter((q)->q.getSurvey().getId()==surveyId).collect(Collectors.toList());
        }
        QuestionListDto questionListDto = new QuestionListDto(questions, pageNum, questionPage.getTotalElements());
        return ResponseEntity.ok(questionListDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionWithId(@PathVariable("id") Long id){
        Optional<Question> question = questionService.getQuestionWithId(id);
        try{
            return new ResponseEntity<Question>(question.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<Answers>> getAnswersOfQuestionWithId(@PathVariable("id") Long id){
        Optional<Question> question = questionService.getQuestionWithId(id);
        try{
            return new ResponseEntity<List<Answers>>(question.get().getAnswers(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/answers/{answerId}")
    public ResponseEntity<Answers> getAnswerWithIdOfQuestionWithId(@PathVariable("id") Long id,
                                                                   @PathVariable("answerId") Long answerId){
        Optional<Question> question = questionService.getQuestionWithId(id);
        try{
            return new ResponseEntity<Answers>(question.get().getAnswers().stream().filter(answers -> answers.getId()==answerId).findFirst().get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question newQuestion,
                                                   @RequestParam(name="answersAmount", defaultValue = "2") int answersAmount,
                                                   @RequestParam(name = "surveyId", defaultValue = "1") Long surveyId){
        List<Answers> answerList = new ArrayList<>();
        Survey survey = this.surveyService.getSurveyWithId(surveyId).get();
        System.out.println("post mapping in question - survey is "+survey+" where is is "+ surveyId);
        newQuestion.setSurvey(survey);
        try{
            return new ResponseEntity<>(questionService.createQuestion(newQuestion), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,@RequestBody Question updatedQuestion){
        if(questionService.updateQuestion(id, updatedQuestion)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{id}/answers")
    public ResponseEntity<Question> updateQuestionCreateAnswer(@PathVariable Long id,@RequestBody Answers answers){
        Question q = questionService.getQuestionWithId(id).get();
        answersService.createAnswer(answers);
        List<Answers> optionalAnswers = answersService.getAllAnswers();
        Answers newAnswer = optionalAnswers.stream()
                .max(Comparator.comparing(Answers::getId))
                .get();
        List<Answers> answerList = q.getAnswers();
        answerList.add(newAnswer);
        q.setAnswers(answerList);
        if(questionService.updateQuestion(id, q)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/answers/{answerId}")
    public ResponseEntity<Question> updateQuestionUpdateAnswer(@PathVariable Long id,@PathVariable Long answerId,@RequestBody Answers answers){
        Question q = questionService.getQuestionWithId(id).get();
        List<Answers> answerList = q.getAnswers();
        answersService.updateAnswer(answerId,answers);
        q.setAnswers(answerList);
        if(questionService.updateQuestion(id, q)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}/answers/{answerId}")
    public ResponseEntity<Question> updateQuestionDeleteAnswer(@PathVariable Long id,
                                    @PathVariable Long answerId){
        Question q = questionService.getQuestionWithId(id).get();
        List<Answers> answerList = q.getAnswers()
                .stream()
                .filter((answers1 -> !answers1.getId().equals(answerId)))
                .collect(Collectors.toList());
        q.setAnswers(answerList);
        answersService.deleteAnswerById(answerId);
        if(questionService.updateQuestion(id, q)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/answers")
    public ResponseEntity<Question> updateQuestionDeleteAllAnswers(@PathVariable Long id){
        Question q = questionService.getQuestionWithId(id).get();
        List<Answers> answerList = new ArrayList<>();
        q.getAnswers()
                .stream()
                        .forEach((answers -> answersService.deleteAnswerById(answers.getId())));
        q.setAnswers(answerList);

        if(questionService.updateQuestion(id, q)!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteQuestionById(@PathVariable("id") Long id){
        try{
            Question question = questionService.getQuestionWithId(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
            questionService.deleteQuestionById(question);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteQuestionsBySurvey(@RequestParam(name = "surveyId", defaultValue = "1") Long surveyId){
        try{
            questionService.deleteAllQuestionsBySurvey(surveyService.getSurveyWithId(surveyId).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
