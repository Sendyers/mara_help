package com.sendyit.marasample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sendyit.mara.activities.Mara;
import com.sendyit.sendyhelp.R;
import com.sendyit.marasample.classes.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bShowHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add end point to pull collection from, end point to post article reviews to and the end point to post form data to
                Mara mara = new Mara(getApplicationContext(), Constants.GET_COLLECTION, Constants.POST_ARTICLE_REVIEW, Constants.POST_FORM);
                mara.showHelp();
            }
        });
    }
}