package com.teamA.dtos;

import com.teamA.model.Question;
import com.teamA.model.Results;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PersonalResultsDTO {
    private List<Results> resultsList;
    private int pageNum;
    private long totalItems;
}

