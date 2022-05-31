package com.teamA.dtos;


import com.teamA.model.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;

@Getter
@AllArgsConstructor
public class SurveyListDto {
    private List<Survey> surveyList;
    private int pageNum;
    private long totalItems;
}
