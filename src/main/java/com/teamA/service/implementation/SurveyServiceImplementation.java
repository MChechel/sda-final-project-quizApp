package com.teamA.service.implementation;

import com.teamA.model.Survey;
import com.teamA.repository.SurveyRepository;
import com.teamA.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyServiceImplementation implements SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyServiceImplementation(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey addSurvey(Survey survey) {
        surveyRepository.save(survey);
        return survey;
    }

    @Override
    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        Optional<Survey> foundSurvey = (surveyRepository.findById(id));
        if(foundSurvey.isPresent()) {
            Survey survey = foundSurvey.get();
            survey.setTitle(updatedSurvey.getTitle());
            survey.setDescription(updatedSurvey.getDescription());
            return surveyRepository.save(survey);
        }else {
        return null;
        }
    }

    @Override
    public void deleteSurveyById(Survey survey) {
        surveyRepository.delete(survey);

    }

    @Override
    public List<Survey> getAllSurveys() {
        Iterable<Survey> surveys;
        surveys = surveyRepository.findAll();
        List<Survey> results= new ArrayList<>();
        surveys.forEach(results::add);
        return results;
    }

    @Override
    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }
}
