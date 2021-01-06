package com.furkanmeydan.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1,rb2,rb3,rb4;

    private Button buttoncomfirmNext;


    private TextView textViewQuestions, textViewScore, textViewQuestionCount,
    textViewCountDown;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestions;
    private boolean answered;

    private Handler handler = new Handler();

    private ColorStateList textColor;

    private int correctAns = 0, wrongAns = 0;

    private TimerDialog timerDialog;

    private  int totalSizeOfQuiz=0;

    private CorrectDialog correctDialog;
    int score = 0;

    private WrongDialog wrongDialog;

    private PlayAudioForAnswers playAudioForAnswers;

    int FLAG = 0; // play audio kısmında swtich case için belirteceğimiz bir değer.
    //1 için correctsound 2 için wrongsound 3 için tik tak sound efekti çalacak


    private static final long COUNTDOWN_IN_MILLIS = 30000; // her soru için verilen süre
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private long backPressedTime;  // 2 saniye içinde arka arkaya 2 kere basılırsa onBackPressed aktivitesi içinde çağırılması için

    private String categoryValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setUpUI(); // gerekli initiatelerin yapıldığı metot

        Intent intentCategory = getIntent();
        categoryValue = intentCategory.getStringExtra("Category"); //category'nin intentden gelen değerinii aldık



        fetchDB(); //sqlite'dan soruların çekildiği metot

        textColor = rb1.getTextColors();

        timerDialog = new TimerDialog(this);
        correctDialog = new CorrectDialog(this);
        wrongDialog = new WrongDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);

    }

    private void setUpUI() {


        textViewCountDown = findViewById(R.id.txtViewTimer);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount=findViewById(R.id.txtTotalQuestion);
        textViewQuestions=findViewById(R.id.QuestionCantainer);


        rbGroup=findViewById(R.id.readio_group);
        rb1=findViewById(R.id.radio_button1);
        rb2=findViewById(R.id.radio_button2);
        rb3=findViewById(R.id.radio_button3);
        rb4=findViewById(R.id.radio_button_4);
        buttoncomfirmNext = findViewById(R.id.button);


    }

    private void fetchDB(){
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        //questionList = dbHelper.getAllQuestionsWithCategoryAndLevels(1,"Computers"); // db'den fetch ederken  intent ile gelen category valuesuna göre kategorilerin soruları gelir.
        questionList=dbHelper.getAllQuestionsWithCategory(categoryValue);
        startQuiz();
    }

    private void startQuiz() {

        questionTotalCount = questionList.size();
        Collections.shuffle(questionList); //soruların sıralamasını karışık hale getirmek için Arraytlist'e atılan sorular shufflelanır

        showQuestions();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){

                    case R.id.radio_button1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;


                    case R.id.radio_button2:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;


                    case R.id.radio_button3:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;




                    case R.id.radio_button_4:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        break;

                }
            }
        });


            buttoncomfirmNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!answered){
                        if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){



                            quizOperations();
                        }
                        else{
                            Toast.makeText(QuizActivity.this,"Lütfen bir şık seçin", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });



    }

    public void showQuestions(){
        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

        //Yeni soru geldiğinde soruların yazılarının renginin tekrar siyah olmasını sağlıyoruz.
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        if(questionCounter<questionTotalCount){
            currentQuestions = questionList.get(questionCounter);
            textViewQuestions.setText(currentQuestions.getQuestion());
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++;
            answered = false;

            buttoncomfirmNext.setText("Confirm");

            textViewQuestionCount.setText("Questions :" + questionCounter + "/" + questionTotalCount);


            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();


        }

        else{
            Toast.makeText(this,"Quiz Finished",Toast.LENGTH_LONG).show();
            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttoncomfirmNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Result activity
                    finalResult();

                }
            },1000);

        }
    }



    private void quizOperations() {

        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answeNr = rbGroup.indexOfChild(rbSelected) +1;

        checkSolution(answeNr, rbSelected);


    }

    private void checkSolution(int answeNr, RadioButton rbSelected) {

        switch (currentQuestions.getAnswerNr()){

            case 1:
                if(currentQuestions.getAnswerNr() == answeNr){
                    rb1.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;
                    textViewScore.setText("Score: " + score);

                    correctDialog.correctDialog(score,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);



                }
                else{
                    changeToInCorrectColor(rbSelected);
                    wrongAns++;


                    String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                    FLAG=2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                break;

            case 2:
                if(currentQuestions.getAnswerNr() == answeNr){
                    rb2.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;
                    textViewScore.setText("Score: " + score);

                    correctDialog.correctDialog(score,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                else{
                    changeToInCorrectColor(rbSelected);
                    wrongAns++;


                    String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    FLAG=2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                }
                break;



            case 3:
                if(currentQuestions.getAnswerNr() == answeNr){
                    rb3.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;
                    textViewScore.setText("Score: " + score);

                    correctDialog.correctDialog(score,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                else{
                    changeToInCorrectColor(rbSelected);
                    wrongAns++;


                    String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    FLAG=2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);

                }
                break;




            case 4:
                if(currentQuestions.getAnswerNr() == answeNr){
                    rb4.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);
                    correctAns++;


                    score +=10;
                    textViewScore.setText("Score: " + score);

                    correctDialog.correctDialog(score,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                else{
                    changeToInCorrectColor(rbSelected);
                    wrongAns++;


                    String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    FLAG=2;
                    playAudioForAnswers.setAudioForAnswer(FLAG);


                }
                break;


        }//end of the switch case statement

        if(questionCounter<questionTotalCount){
            buttoncomfirmNext.setText("Onaylayın ve Geçin");
        }


    }

    private void changeToInCorrectColor(RadioButton rbSelected) {
        rbSelected.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_wrong));
        rbSelected.setTextColor(Color.WHITE);
    }



    //zamanlayıcı kodları



    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
            }
        }.start();


    }


    private void updateCountDownText(){

        int minutes = (int) (timeLeftInMillis/1000) / 60;
        int seconds = (int) (timeLeftInMillis/1000) % 60;

        //String  timeForMatted = String.format(Locale.getDefault(),"02d:%02d",minutes,seconds);


        String timeForMatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeForMatted);

        if(timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);

            FLAG = 3;
            playAudioForAnswers.setAudioForAnswer(FLAG);
        }
        else{
            textViewCountDown.setTextColor(textColor);

        }


        if(timeLeftInMillis == 0){
            Toast.makeText(this,"Times UP!",Toast.LENGTH_LONG).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                timerDialog.timerDialog();

                }
            },2000);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer!=null){
            countDownTimer.cancel();
        }

    }

    private void finalResult(){
        Intent resultData = new Intent(QuizActivity.this,ResultActivity.class);
        resultData.putExtra("UserScore",score);
        resultData.putExtra("TotalQuestion",questionTotalCount);
        resultData.putExtra("CorrectAnswer",correctAns);
        resultData.putExtra("WrongAnswer",wrongAns);

        resultData.putExtra("Category",categoryValue);





        startActivity(resultData);
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis()){

            Intent intent = new Intent(QuizActivity.this,PlayActivity.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}