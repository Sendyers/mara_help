package com.sendyit.selfhelp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sendyit.selfhelp.R;
import com.sendyit.selfhelp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleView extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private LinearLayout llActions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);

        Utils.setUpToolbar(this, true);
        setUp();
        getData();
    }

    private void setUp() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        llActions = (LinearLayout) findViewById(R.id.llActions);
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
            tvTitle.setText(article.getString("title"));
            tvDescription.setText(article.getString("description"));

            JSONArray actions = article.getJSONArray("actions");
            for (int i = 0; i < actions.length(); i++) {
                final JSONObject action = actions.getJSONObject(i);

                TextView textView = new TextView(ArticleView.this);
                textView.setId((int) System.currentTimeMillis());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                String actionName = action.getString("actionName");
                SpannableString spannableString = new SpannableString(actionName);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(action.getString("actionUrl")));
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                        ds.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryText));
                    }
                };

                spannableString.setSpan(clickableSpan, 0, actionName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
                textView.setPadding(0, 0, 0, 48);
                textView.setTextSize(18f);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setHighlightColor(Color.TRANSPARENT);

                llActions.addView(textView, layoutParams);
            }
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
