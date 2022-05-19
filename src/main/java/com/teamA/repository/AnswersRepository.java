package com.teamA.repository;

import com.teamA.model.Answers;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
 * setting up  repository for Answers object
 *
 * author: M.Chechel
 * */

public interface AnswersRepository extends PagingAndSortingRepository<Answers, Long> {

}
