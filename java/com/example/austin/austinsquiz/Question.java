package com.example.austin.austinsquiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 4/3/2018.
 */

public class Question {

    private String question;
    private ArrayList<String> choices;
    private String answer;
    private String info;

    public Question(String question, ArrayList<String> choices, String answer, String info) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.info = info;
    }

    public Question() {};

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public String getInfo() {
        return info;
    }
}
