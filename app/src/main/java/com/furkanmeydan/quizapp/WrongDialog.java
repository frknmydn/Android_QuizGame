package com.furkanmeydan.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WrongDialog {

    private TextView textViewCorrectAnswer;
    private Context mContext;
    private Dialog wrongDialog;

    private QuizActivity mQuizActivity;

    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void wrongDialog(String correctAnswer, final QuizActivity quizActivity){
        mQuizActivity=quizActivity;

        wrongDialog = new Dialog(mContext);
        wrongDialog.setContentView(R.layout.wrong_dialog);
        Button btnWrongDialog = wrongDialog.findViewById(R.id.btn_wrong_dialog);
        TextView textViewCorrectAnswer = wrongDialog.findViewById(R.id.text_correct_ans);

        textViewCorrectAnswer.setText("Correct Answer :" + correctAnswer);

        btnWrongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongDialog.dismiss();
                mQuizActivity.showQuestions();
            }
        });

        wrongDialog.show();
        wrongDialog.setCancelable(false);
        wrongDialog.setCanceledOnTouchOutside(false);


    }

}
