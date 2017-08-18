package com.sendyit.sendyhelp.sample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sendyit.selfhelp.activities.SendyHelp;
import com.sendyit.sendyhelp.R;
import com.sendyit.sendyhelp.sample.classes.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bShowHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendyHelp sendyHelp = new SendyHelp(getApplicationContext(), Constants.GET_COLLECTION, Constants.POST_ARTICLE_REVIEW, Constants.POST_FORM);
                sendyHelp.showHelp();
            }
        });
    }
}