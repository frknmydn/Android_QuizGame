package com.furkanmeydan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore,txtTotalQuizQuestions,txtCorrectQuestions,txtWrongQuestions;
    Button btnStartQuiz, btnMainMenu;

    private  int highScore;
    public static final String SHARED_PREFERENCE = "shared_preference";
    public static final String SHARED_PREFERENCE_HIGH_SCORE= "shared_preference_high_score";

     String categoryAgainValue = "";

    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtHighScore=findViewById(R.id.result_text_high_score);
        txtTotalQuizQuestions=findViewById(R.id.result_text_total_ques);
        txtCorrectQuestions= findViewById(R.id.result_text_correct_ans);
        txtWrongQuestions=findViewById(R.id.result_text_wrong_ans);

        btnStartQuiz=findViewById(R.id.result_btn_playagain);
        btnMainMenu=findViewById(R.id.result_btn_mainmenu);


        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this,PlayActivity.class);
                startActivity(i);
            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this,QuizActivity.class);
                i.putExtra("Category",categoryAgainValue);
                startActivity(i);

            }
        });

        loadHighScore();

        Intent intent = getIntent();
        int score = intent.getIntExtra("UserScore",0);
        int totalQuestion = intent.getIntExtra("TotalQuestion",0);
        int correctAns = intent.getIntExtra("CorrectAnswer",0);
        int wrongAns = intent.getIntExtra("WrongAnswer",0);
        categoryAgainValue = intent.getStringExtra("Category");



        txtTotalQuizQuestions.setText("Total Questions: " + totalQuestion);
        txtCorrectQuestions.setText("Correct Answer: " + correctAns);
        txtWrongQuestions.setText("Wrong Answer: " + wrongAns);

        if(score>highScore){
            updateHighScore(score);
        }

    }

    private void updateHighScore(int newhighScore) {
        highScore = newhighScore;

        txtHighScore.setText("High score: " + highScore);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCE_HIGH_SCORE,highScore);
        editor.apply();

    }

    private void loadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE,MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERENCE_HIGH_SCORE,0);
        txtHighScore.setText("High score: "+ highScore);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000>System.currentTimeMillis()){

            Intent intent = new Intent(ResultActivity.this,PlayActivity.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}