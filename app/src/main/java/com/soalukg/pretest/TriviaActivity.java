package com.soalukg.pretest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImage.IImage{
    int index=0;
    ArrayList<Question> questions;
    Button btnPrevious,btnNext;
    LinearLayout linearImageView;
    Bitmap qtImage;
    ImageView image;
    ProgressBar imageProgress;
    TextView qtNo,timer,qtText;
    RadioGroup options;
    InterstitialAd mInterstitialAd;
    Button mNewGameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);


        mNewGameButton = (Button) findViewById(R.id.btn_finish);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1142409875326374/6834885522");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);


        questions = (ArrayList<Question>) getIntent().getExtras().getSerializable("QUESTIONS_KEY");
        btnPrevious = (Button) findViewById(R.id.btn_previous);
        btnNext = (Button) findViewById(R.id.btn_next);

        linearImageView = (LinearLayout) findViewById(R.id.linear_image);
        linearImageView.setGravity(Gravity.CENTER);

        options = (RadioGroup) findViewById(R.id.options);
        qtText = (TextView) findViewById(R.id.qtText);

        qtNo = (TextView) findViewById(R.id.questionNo);
        timer = (TextView) findViewById(R.id.timerText);
        new CountDownTimer(900, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left: " + (millisUntilFinished / 1000)+" sec");
                timer.setTextSize(20);
            }

            public void onFinish() {

                //Go to Stats Activity
                Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
                i.putExtra("ANSWERS_KEY",questions);
                startActivity(i);
            }
        }.start();

        if(index==0) {
            btnPrevious.setEnabled(false);
            display(index);
        }



        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    index--;
                    display(index);
                    if(index==0)
                        btnPrevious.setEnabled(false);
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                //Begin Game, continue with app
            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnNext.getText().toString().equals("Finish")){

                    //go to Stats activity
                    Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
                    i.putExtra("ANSWERS_KEY",questions);
                    startActivity(i);
                }else if(btnNext.getText().toString().equals("Next")){
                    Log.d("next label",btnNext.getText().toString());
                    index++;
                    display(index);
                }
            }
        });

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Checked id ",checkedId+"");

                if(qtText.getText().toString().equals(questions.get(index).getQuestionText())){
                    RadioButton btn = (RadioButton) findViewById(checkedId);
                    questions.get(index).setUserAnswer(btn.getText().toString());
                    String[] choices =questions.get(index).getChoices();
                    Log.d("Answer=",choices[Integer.parseInt(questions.get(index).getAnswer())-1]+"");
                    Log.d("selected text="," "+btn.getText());
                    /*if(btn.getText().toString().equals(choices[Integer.parseInt(questions.get(index).getAnswer())-1])){
                        Log.d("demo","inside if");
                    }*/
                }
            }
        });

    }

    public void display(int index){

        linearImageView.removeAllViews();

        if(index==questions.size()-1){
            btnNext.setText("Finish");
        }else{
            btnNext.setText("Next");
        }
        if(index>0){
            btnPrevious.setEnabled(true);
        }

        //adding Qt No
        qtNo.setText("SOAL"+(index+1));
        qtNo.setTextSize(20);

        //adding image
        image = new ImageView(this);
        String imgUrl = questions.get(index).getImageUrl();
        Log.d("image url",index+" "+imgUrl);
        if(!(imgUrl.equals("null"))) {
            imageProgress = new ProgressBar(TriviaActivity.this);
            TextView loadingText = new TextView(this);
            loadingText.setText("Loading Image");
            loadingText.setTextSize(15);
            loadingText.setGravity(Gravity.CENTER);
            linearImageView.addView(imageProgress);
            linearImageView.addView(loadingText);
            Log.d("inside if","calling GetImage");
            new GetImage(this).execute(imgUrl);
            Log.d("demo","after get image");
        }

        //adding qt text
        qtText.setText(questions.get(index).getQuestionText());

        //adding options
        options.removeAllViews();
        String[] choices = questions.get(index).getChoices();
        for (int k = 0; k < choices.length; k++) {
            RadioButton btn = new RadioButton(TriviaActivity.this);
            btn.setText(choices[k]);
            options.addView(btn);

        }
        Log.d("user's previous answer=",questions.get(index).getUserAnswer()+"");
        if(!questions.get(index).getUserAnswer().equals("null")){
            for(int i=0;i<options.getChildCount();i++) {
                RadioButton rb = (RadioButton) options.getChildAt(i);
                if(rb.getText().toString().equals(questions.get(index).getUserAnswer()))
                    rb.setChecked(true);
            }
        }


    }

    @Override
    public void sendImage(Bitmap img) {
        Log.d("demo","inside sendimage");

        qtImage = img;
        image.setMaxHeight(100);
        image.setMaxWidth(100);
        image.setImageBitmap(qtImage);
        linearImageView.removeAllViews();
        linearImageView.addView(image);
    }
}
