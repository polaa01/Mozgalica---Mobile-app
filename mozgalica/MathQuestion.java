package com.example.mozgalica;

import java.util.ArrayList;
import java.util.List;

public class MathQuestion {
    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    private String text;
    private List<String> answers = new ArrayList<>();

    public String getText() {
        return text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    private int correctIndex;


    public MathQuestion()
    {
        super();
    }

    public MathQuestion(String text, List<String> answers, int correctIndex)
    {
        this.text = text;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }



}
