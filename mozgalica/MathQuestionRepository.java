package com.example.mozgalica;

import java.util.*;


public class MathQuestionRepository {

    public static List<MathQuestion> getAllQuestions()
    {
        List<MathQuestion> questions = new ArrayList<>();

        questions.add(new MathQuestion("12 × 11?", Arrays.asList("121", "132", "144"), 1));
        questions.add(new MathQuestion("81 ÷ 9?", Arrays.asList("9", "8", "7"), 0));
        questions.add(new MathQuestion("5² + 3²?", Arrays.asList("34", "25", "18"), 0));
        questions.add(new MathQuestion("12+5*6?", Arrays.asList("42", "38", "27"), 0));
        questions.add(new MathQuestion("2³ + 4?", Arrays.asList("12", "10", "8"), 1));
        questions.add(new MathQuestion("(6 + 4) × 3?", Arrays.asList("30", "36", "24"), 0));
        questions.add(new MathQuestion("100 ÷ (5 × 2)?", Arrays.asList("5", "10", "20"), 1));
        questions.add(new MathQuestion("3 × (4 + 5)?", Arrays.asList("27", "36", "21"), 2));
        questions.add(new MathQuestion("64 ÷ 8 + 2?", Arrays.asList("10", "8", "6"), 0));
        questions.add(new MathQuestion("10 × 10 − 25?", Arrays.asList("75", "85", "95"), 0));
        questions.add(new MathQuestion("15²?", Arrays.asList("225", "255", "235"), 0));
        questions.add(new MathQuestion("(3 + 2)²?", Arrays.asList("10", "25", "15"), 1));
        questions.add(new MathQuestion("7 × 8?", Arrays.asList("56", "48", "64"), 0));
        questions.add(new MathQuestion("144 ÷ 12?", Arrays.asList("10", "12", "14"), 1));
        questions.add(new MathQuestion("14-2*4?", Arrays.asList("7", "6", "5"), 1));
        questions.add(new MathQuestion("7 × 3?", Arrays.asList("21", "28", "14"), 0));
        return questions;
    }
}
