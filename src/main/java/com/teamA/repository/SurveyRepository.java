package com.teamA.repository;

import com.teamA.model.Survey;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {

    Survey getByHashCode(String code);

}
