package com.teamA.components;


import com.teamA.model.Answers;
import com.teamA.model.Question;
import com.teamA.model.Survey;
import com.teamA.model.User;
import com.teamA.service.AnswersService;
import com.teamA.service.QuestionService;
import com.teamA.service.SurveyService;
import com.teamA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


/**
 * Data Init component
 *
 * @author M.Chechel
 */
@Component
public class DataInit {
    private final AnswersService answersService;
    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public DataInit(AnswersService answersService, SurveyService surveyService, QuestionService questionService, UserService userService) {
        this.answersService = answersService;
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostConstruct
    public void initData() {
        initUser();
        initAnswers();
        initSurvey();
        initQuestion();
    }


    // PRIVATE METHODS //

    private void initQuestion() {

        List<Answers> optionalAnswers = answersService.getAllAnswers();
        Optional<Survey> optionalSurvey = surveyService.getSurveyWithId(5l);
        System.out.println("it is init file talking");
        Question newQuestion1 = new Question();
        newQuestion1.setPoints(500);
        newQuestion1.setContent("SecondQuestion!");
        newQuestion1.setAnswers(optionalAnswers);
        newQuestion1.setSurvey(optionalSurvey.get());
        System.out.println("it is init file talking");
        System.out.println(newQuestion1);
        questionService.createQuestion(newQuestion1);
        answersService.deleteAllAnswers();

    }

    private void initSurvey() {
        Survey survey = new Survey();
        survey.setId(1l);
        survey.setDescription("The first created survey!");
        survey.setTitle("Title will be here!");
        survey.setUser(userService.getUserByEmail("user"));
        surveyService.addSurvey(survey);
    }

    private void initAnswers() {
        Answers answer1 = new Answers();
        answer1.setCorrect(true);
        answer1.setContent("Hello there!");
        answer1.setId(1l);
        answersService.createAnswer(answer1);
        Answers answer2 = new Answers();
        answer2.setId(2l);
        answer2.setCorrect(false);
        answer2.setContent("Second answer!");
        answersService.createAnswer(answer2);
        Answers answer3 = new Answers();
        answer3.setId(3l);
        answer3.setCorrect(false);
        answer3.setContent("Third answer!");
        answersService.createAnswer(answer3);
    }


    private void initUser() {
        User newUser = new User();
        newUser.setFirstName("James");
        newUser.setLastName("Bond");
        newUser.setEmail("JB007@m6.uk");
        newUser.setPassword("007_theBest_007");
        newUser.setRoles("USER");
        userService.createUser(newUser);
        User newUser1 = new User();
        newUser1.setFirstName("Name");
        newUser1.setLastName("LastName");
        newUser1.setEmail("user");
        newUser1.setPassword("user");
        newUser1.setRoles("DOES_NOT_MATTER");
        userService.createUser(newUser1);
    }

}