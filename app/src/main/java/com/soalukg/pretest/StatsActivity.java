package com.soalukg.pretest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    Button mNewGameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mNewGameButton = (Button) findViewById(R.id.btn_finish);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1142409875326374/6834885522");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);
//--SAMPAI SINI-->

        ProgressBar progress = (ProgressBar) findViewById(R.id.progressPerformance);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_answers);
        int noOfQuestions,correctAnswers,incorrectAnswers=0;
        double percentage;
        ArrayList<Question> answers = (ArrayList<Question>) getIntent().getExtras().getSerializable("ANSWERS_KEY");

        noOfQuestions = answers.size();
        Log.d("no of qts=",noOfQuestions+"");

        for (int i=0;i<answers.size();i++ ) {
            int answerIndex = Integer.parseInt(answers.get(i).getAnswer());
            String answer = answers.get(i).getChoices()[answerIndex-1];
            if(answers.get(i).getUserAnswer().equals("null") || !(answers.get(i).getUserAnswer().equals(answer))){
                TextView qtText = new TextView(this);
                qtText.setText(answers.get(i).getQuestionText());
                TextView yourAns = new TextView(this);
                yourAns.setText(answers.get(i).getUserAnswer());
                yourAns.setBackgroundColor(Color.RED);
                TextView correctAns = new TextView(this);
                correctAns.setText(answer);
                correctAns.setBackgroundColor(Color.CYAN);
                linearLayout.addView(qtText);
                linearLayout.addView(yourAns);
                linearLayout.addView(correctAns);
                incorrectAnswers++;
                Log.d("incorrect=",incorrectAnswers+"");
            }
        }

        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    finish();
                    System.exit(0);

                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                //Begin Game, continue with app
            }
        });


        correctAnswers = noOfQuestions - incorrectAnswers;
        Log.d("correct=",correctAnswers+"");
        percentage = (correctAnswers * 100)/noOfQuestions;
        Log.d("perc",percentage+"");
        progress.setMax(100);
        progress.setProgress((int) percentage);
        TextView percText = (TextView) findViewById(R.id.tv_percentage);
        percText.setText(String.valueOf(percentage)+"%");
    }
}
