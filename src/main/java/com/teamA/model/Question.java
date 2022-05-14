package com.teamA.model;


/*
* model for question object
*
* author: M.Chechel
* */


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content; //the actual question will be stored here
    private int points; // amount of points for the question
    private String correctAnswer;
    private String answers; // at the moment it is just a string...


    public Question(String content, int points, String correctAnswer, String answers) {
        this.content = content;
        this.points = points;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public Question() {
    }
}
