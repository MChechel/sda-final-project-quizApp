package com.teamA.repository;


import com.teamA.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * setting up repository  for question object
 *
 * author: M.Chechel
 * */
public interface QuestionRepository extends JpaRepository<Question, Long> {


}
