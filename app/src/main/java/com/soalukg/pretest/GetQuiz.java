package com.soalukg.pretest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by neha5 on 09-02-2017.
 */

public class GetQuiz extends AsyncTask<String, Void, ArrayList<Question>> {
    IData activity;

    public GetQuiz(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!= null){
                sb.append(line);
            }
            Log.d("demo",sb+"");
            return Parser.QuestionsParser.parseQuestions(sb.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        activity.sendData(questions);
    }

    public static interface IData{
        public void sendData(ArrayList<Question> questions);
    }
}
