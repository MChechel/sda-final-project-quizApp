package com.teamA.model;


import lombok.Data;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Data
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String hashCode;

    private long usageCounter;

    public Survey(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.hashCode =  new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.US_ASCII)));
        this.usageCounter = 0;
        this.user = user;
    }

    @OneToOne
    private User user;


    public Survey() {
    }

    public void setHashCode(){
        this.hashCode =  new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.US_ASCII)));
    }

    private void increaseUsageCounter(){
        this.usageCounter++;
    }
}
