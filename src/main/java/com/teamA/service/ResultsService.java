package com.teamA.service;

import com.teamA.model.Question;
import com.teamA.model.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResultsService {

    Results addResults(Results results);
    Page<Results> getAllResultsForUserLogin(Pageable pageable,String login); // personal statistic
    Page<Results> getAllResultsBySurveyUniqueCode(Pageable pageable, Long id); //for creator

}
