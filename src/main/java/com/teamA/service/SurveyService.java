package com.teamA.service;

import com.teamA.model.Question;
import com.teamA.model.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SurveyService {

    Page<Survey> getAllSurveysByPage(Pageable pageable);
    Optional<Survey> getSurveyWithId(Long Id);
    Survey addSurvey(Survey survey);

    Survey updateSurvey(Long id, Survey updatedSurvey);

    void deleteSurveyById(Survey survey);


}
