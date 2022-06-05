package com.teamA.service.implementation;

import com.teamA.model.Results;
import com.teamA.repository.ResultsRepository;
import com.teamA.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResultsServiceImplementation implements ResultsService {
    private ResultsRepository resultsRepository;

    @Autowired
    public ResultsServiceImplementation(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }


    @Override
    public Results addResults(Results results) {
        return this.resultsRepository.save(results);
    }

    @Override
    public Page<Results> getAllResultsForUserLogin(Pageable pageable,String login) {
        return this.resultsRepository.getResultsByUserLogin(pageable,login);
    }

    @Override
    public Page<Results> getAllResultsBySurveyUniqueCode(Pageable pageable, Long id) {
      return this.resultsRepository.getResultsBySurveyUniqueCode(pageable,id);
    }
}
