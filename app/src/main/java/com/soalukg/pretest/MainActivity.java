package com.soalukg.pretest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.soalukg.pretest.R.drawable.trivia;

public class MainActivity extends AppCompatActivity implements GetQuiz.IData{
    LinearLayout imageHolder;
    ProgressBar loadTrivia;
    TextView message;
    ImageView triviaImg;
    InterstitialAd mInterstitialAd;
    Button mNewGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNewGameButton = (Button) findViewById(R.id.btn_start_trivia);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1142409875326374/6834885522");

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    //Begin Game, continue with app
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                //Begin Game, continue with app
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);


//--SAMPAI SINI-->


        String url = "https://soalukgkuis.000webhostapp.com/soalku.json";
        imageHolder = (LinearLayout) findViewById(R.id.linear_trivia_image);
        imageHolder.removeAllViews();

        findViewById(R.id.btn_start_trivia).setEnabled(false);
        message = new TextView(this);
        message.setText("Loading Soal");
        message.setGravity(Gravity.CENTER);
        message.setTextColor(Color.BLACK);
        message.setTextSize(20);
        imageHolder.addView(message);
        loadTrivia = new ProgressBar(this);
        loadTrivia.setVisibility(View.VISIBLE);
        imageHolder.addView(loadTrivia);

        new GetQuiz(this).execute(url);

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void sendData(final ArrayList<Question> questions) {

        findViewById(R.id.btn_start_trivia).setEnabled(true);

        imageHolder.removeAllViews();
        triviaImg = new ImageView(this);
        triviaImg.setImageResource(trivia);
        imageHolder.addView(triviaImg);
        message = new TextView(this);
        message.setText("SOAL SIAP DIKERJAKAN");
        message.setGravity(Gravity.CENTER);
        message.setTextColor(Color.BLACK);
        message.setTextSize(20);
        imageHolder.addView(message);


        findViewById(R.id.btn_start_trivia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
                intent.putExtra("QUESTIONS_KEY",questions);
                startActivity(intent);
            }
        });

    }
}
