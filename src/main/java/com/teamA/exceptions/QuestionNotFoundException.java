package com.teamA.exceptions;

public class QuestionNotFoundException extends Exception {
    public QuestionNotFoundException(Long id) {
        super(id == null ? "No questions found!" : "Question not found for id: " + id);
    }
}
