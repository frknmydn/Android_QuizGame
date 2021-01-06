package com.furkanmeydan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelsActivity extends AppCompatActivity {

    Button btnLevel1,btnLevel2,btnLevel3;

    String categoryValue = "";

    int CL1, CL2, CL3;

    TextView txtLevel1, txtLevel2, txtLeve3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        btnLevel1=findViewById(R.id.btnLevel1);
        btnLevel2=findViewById(R.id.btnLevel2);
        btnLevel3=findViewById(R.id.btnLevel3);

        txtLevel1 = findViewById(R.id.textView1);
        txtLevel2= findViewById(R.id.textView2);
        txtLeve3=findViewById(R.id.textView3);
    }

    public void LoadData(View view){

    }
}