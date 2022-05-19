package com.teamA.repository;


import com.teamA.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
 * setting up repository  for question object
 *
 * author: M.Chechel
 * */
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {


}
