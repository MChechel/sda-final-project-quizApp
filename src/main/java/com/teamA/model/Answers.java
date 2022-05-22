package com.teamA.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Answers {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    private Long questionId;
    private String content;
    private boolean isCorrect;

    public Answers(Long questionId, String content, boolean isCorrect) {
//        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;

    }

    public Answers() {
    }
}
