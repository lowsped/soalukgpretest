package com.soalukg.pretest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by neha5 on 09-02-2017.
 */

public class Parser {
    static class QuestionsParser{

        public static ArrayList<Question> parseQuestions(String jsonString) throws JSONException {
            ArrayList<Question> questions = new ArrayList<Question>();
            JSONObject questionsArrayObj = new JSONObject(jsonString);
            JSONArray questionsArray = questionsArrayObj.getJSONArray("questions");


            for(int i=0; i<questionsArray.length();i++){
                JSONObject questionsObj = questionsArray.getJSONObject(i);
                Question question = new Question();
                question.setQuestionText(questionsObj.getString("text"));
                question.setId(questionsObj.getString("id"));
                if(questionsObj.has("image"))
                    question.setImageUrl(questionsObj.getString("image"));
                else
                    question.setImageUrl("null");
                JSONObject choicesObj = questionsObj.getJSONObject("choices");
                question.setAnswer(choicesObj.getString("answer"));
                JSONArray choicesArray = choicesObj.getJSONArray("choice");
                String[] choices = new String[choicesArray.length()];
                for(int j=0; j<choicesArray.length();j++){
                    choices[j] = choicesArray.get(j).toString();
                }
                question.setChoices(choices);
                questions.add(question);
            }


            return questions;
        }

    }
}
