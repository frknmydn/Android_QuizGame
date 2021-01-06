package com.furkanmeydan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnHistory, btnComputers, btnEnglish, btnMaths, btnGraphics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnHistory=findViewById(R.id.btn_history);
        btnComputers=findViewById(R.id.btn_computers);
        btnEnglish=findViewById(R.id.btn_english);
        btnMaths=findViewById(R.id.btn_maths);
        btnGraphics=findViewById(R.id.btn_graphics);

        btnHistory.setOnClickListener(this);
        btnComputers.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnMaths.setOnClickListener(this);
        btnGraphics.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_history:
                //Intent intentHistory = new Intent(CategoryActivity.this,LevelsActivity.class);
                Intent intentHistory = new Intent(CategoryActivity.this,QuizActivity.class);
                intentHistory.putExtra("Category", CategoryConstants.HISTORY);
                startActivity(intentHistory);
                break;

            case R.id.btn_computers:

                createLevelsForComp();

               // Intent intentComputer = new Intent(CategoryActivity.this,LevelsActivity.class);
                Intent intentComputer = new Intent(CategoryActivity.this,QuizActivity.class);
                intentComputer.putExtra("Category", CategoryConstants.COMPUTER);
                startActivity(intentComputer);
                break;

            case R.id.btn_english:
                Intent intentEnglish = new Intent(CategoryActivity.this,QuizActivity.class);
                //Intent intentEnglish = new Intent(CategoryActivity.this,LevelsActivity.class);
                intentEnglish.putExtra("Category", CategoryConstants.ENGLISH);
                startActivity(intentEnglish);
                break;

            case R.id.btn_graphics:
                Intent intentgraphics = new Intent(CategoryActivity.this,QuizActivity.class);
                //Intent intentgraphics = new Intent(CategoryActivity.this,LevelsActivity.class);
                intentgraphics.putExtra("Category", CategoryConstants.GRAPHICS);
                startActivity(intentgraphics);
                break;

            case R.id.btn_maths:
                Intent intentMaths = new Intent(CategoryActivity.this,QuizActivity.class);
                //Intent intentMaths = new Intent(CategoryActivity.this,LevelsActivity.class);
                intentMaths.putExtra("Category", CategoryConstants.MATHS);
                startActivity(intentMaths);
                break;
        }
    }

    // 1 = unlocked 0= locked
    private void createLevelsForComp() {

        SharedPreferences sharedPreferences =
                getSharedPreferences(getPackageName() + Constant.MY_LEVEL_PREFFILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Constant.KEY_CAT_COMP_LEVEL_1,1); // default olarak level1 unlocked olacak.
        editor.putString(Constant.KEY_CAT_COMP_LEVEL_1,"Unlock");
        editor.apply();


        if(sharedPreferences.getString(Constant.KEY_CAT_COMP_LEVEL_1,"N/A").equals("Unlock")){
            editor.putInt(Constant.KEY_COMP_LEVEL_1,1);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,0);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,0);

        }

        else if(sharedPreferences.getString(Constant.KEY_CAT_COMP_LEVEL_2,"N/A").equals("Unlock")){
            editor.putInt(Constant.KEY_COMP_LEVEL_1,1);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,1);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,0);

        }

        else if(sharedPreferences.getString(Constant.KEY_CAT_COMP_LEVEL_3,"N/A").equals("Unlock")){
            editor.putInt(Constant.KEY_COMP_LEVEL_1,1);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,1);
            editor.putInt(Constant.KEY_COMP_LEVEL_2,1);

        }





    }
}