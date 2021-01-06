package com.furkanmeydan.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CorrectDialog {

    private Context mContext;
    private Dialog correctdialog;

    private QuizActivity mquizActivity;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }


    public void  correctDialog(int score, final QuizActivity quizActivity){

        mquizActivity= quizActivity;

        correctdialog = new Dialog(mContext);
        correctdialog.setContentView(R.layout.correct_dialog);

        Button btnCorrectDialog = correctdialog.findViewById(R.id.btn_correct_dialog);

        score(score);

        btnCorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctdialog.dismiss();
                mquizActivity.showQuestions();
            }
        });

        correctdialog.show();
        correctdialog.setCancelable(false);
        correctdialog.setCanceledOnTouchOutside(false);


    }

    private void score(int score) {

        TextView textViewScore = correctdialog.findViewById(R.id.text_score_correctDialog);
        textViewScore.setText("Score: " + score);

    }
}
