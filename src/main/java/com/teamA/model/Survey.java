package com.teamA.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    // date of time

    // unique link

    @OneToMany
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    private List<Question> questions;

    public Survey(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public Survey() {
    }
}
