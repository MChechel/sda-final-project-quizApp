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

//    @OneToMany
//    private List<Questions> questions;

    // date of time

    // unique link

//    @OneToOne
//    private User user;

    public Survey(String title, String description) {
        this.title = title;
        this.description = description;
//        this.questions = questions;
    }

    public Survey() {
    }
}
