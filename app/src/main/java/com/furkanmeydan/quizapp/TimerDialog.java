package com.furkanmeydan.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerDialog {


    private Context mContext;
    private Dialog timerdialog;

    private TextView textViewTimeisUp;


    public TimerDialog(Context mContext) {
        this.mContext = mContext;

    }

    public void timerDialog(){

         timerdialog= new Dialog(mContext);
        timerdialog.setContentView(R.layout.timer_dialog);
        final Button btnTimer = (Button) timerdialog.findViewById(R.id.btn_timer);




        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerdialog.dismiss();

                Intent intent = new Intent(mContext,PlayActivity.class);
                mContext.startActivity(intent);
            }
        });

        timerdialog.show();
        timerdialog.setCancelable(false);
        timerdialog.setCanceledOnTouchOutside(false);

    }


}
