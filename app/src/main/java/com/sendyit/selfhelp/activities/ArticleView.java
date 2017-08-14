package com.sendyit.selfhelp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sendyit.selfhelp.R;
import com.sendyit.selfhelp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ArticleView extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);


        setUp();
        getData();
    }

    private void setUp() {
        toolbar = Utils.setUpToolbar(this, true);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
    }

    private void getData() {
        try {
            Bundle b = getIntent().getExtras();
            JSONObject article = new JSONObject(b.getString("article"));
            setData(article);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData(JSONObject article) {
        try {
            TextView tvToolBarText = (TextView) toolbar.findViewById(R.id.tvToolbarText);
            tvToolBarText.setText(article.getString("title"));
            tvTitle.setText(article.getString("title"));
            tvDescription.setText(article.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
