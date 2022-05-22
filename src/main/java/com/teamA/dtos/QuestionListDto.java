package com.teamA.dtos;

import com.teamA.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionListDto {
    private List<Question> questionList;
    private int pageNum;
    private long totalItems;
}
