package com.teamA.repository;

import com.teamA.model.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResultsRepository extends PagingAndSortingRepository<Results, Long> {

    Page<Results> getResultsByUserLogin(Pageable pageable, String userLogin);

    Page<Results> getResultsBySurveyUniqueCode(Pageable pageable,String uniqueCode);



}
