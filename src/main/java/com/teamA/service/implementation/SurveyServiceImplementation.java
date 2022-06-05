package com.teamA.service.implementation;

import com.teamA.model.Survey;
import com.teamA.repository.SurveyRepository;
import com.teamA.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyServiceImplementation implements SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyServiceImplementation(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Page<Survey> getAllSurveysByPage(Pageable pageable) {
        return surveyRepository.findAll(pageable);
    }
    @Override
    public Optional<Survey> getSurveyWithId(Long id) {
        return surveyRepository.findById(id);
    }

    @Override
    public Survey addSurvey(Survey survey) {
        survey.setHashCode();
        return surveyRepository.save(survey);
    }

    @Override
    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        Optional<Survey> foundSurvey = getSurveyWithId(id);
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
    public Survey getSurveyByUniqueCode(String uniqueCode) {
        try{
        return surveyRepository.getByHashCode(uniqueCode);
        }catch(Exception e){
            throw new NullPointerException("There is no survey with such code!");
        }
    }
}
