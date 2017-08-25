package com.soalukg.pretest;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by neha5 on 09-02-2017.
 */

public class Question implements Serializable{

    String id, questionText, imageUrl, answer, userAnswer;
    String[] choices;

    Question(){
        userAnswer = "Kamu Tidak Menjawab";
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", answer='" + answer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", choices=" + Arrays.toString(choices) +
                '}';
    }
}
