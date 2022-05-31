package com.teamA.service;

import com.teamA.model.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyService {

    Survey createSurvey(Survey survey);

    Survey updateSurvey(Long id, Survey updatedSurvey);

    void deleteSurveyById(Survey survey);

    List<Survey> getAllSurveys();

    Optional<Survey> getSurveyById(Long id);


}
