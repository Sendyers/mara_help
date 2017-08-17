package com.sendyit.selfhelp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sendyit.selfhelp.R;
import com.sendyit.selfhelp.classes.Constants;
import com.sendyit.selfhelp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleView extends AppCompatActivity implements View.OnClickListener {

    private static int RESPONSE_TYPE_NONE = 0;
    private static int RESPONSE_TYPE_ACTION = 1;
    private static int RESPONSE_TYPE_FORM = 2;

    private TextView tvTitle, tvDescription, tvFormTitle, tvFormDescription, tvSubmit;
    private LinearLayout llActions, llForm;
    private ImageView ivYes, ivNo;

    //Others
    private RequestQueue queue;
    private JSONObject form;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.article_view);

        Utils.setUpToolbar(this, true);
        setUp();
        getData();
    }

    private void setUp() {
        queue = Volley.newRequestQueue(ArticleView.this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        llActions = (LinearLayout) findViewById(R.id.llActions);
        llForm = (LinearLayout) findViewById(R.id.llForm);
        tvFormTitle = (TextView) findViewById(R.id.tvFormTitle);
        tvFormDescription = (TextView) findViewById(R.id.tvFormDescription);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        ivYes = (ImageView) findViewById(R.id.ivYes);
        ivNo = (ImageView) findViewById(R.id.ivNo);

        tvSubmit.setOnClickListener(this);
        ivYes.setOnClickListener(this);
        ivNo.setOnClickListener(this);
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
            form = article.getJSONObject("form");
            int responseType = article.getInt("responseType");

            if (responseType == RESPONSE_TYPE_ACTION) {
                processActions(actions);
            } else if (responseType == RESPONSE_TYPE_FORM) {
                processForm(form);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processActions(JSONArray actions) {
        try {
            llActions.setVisibility(View.VISIBLE);

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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
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

    private void processForm(JSONObject form) {
        try {
            llForm.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.VISIBLE);

            String title = form.getString("formTitle");
            String description = form.getString("formDescription");
            String submitText = form.getString("submitText");

            JSONArray fields = form.getJSONArray("fields");

            for (int i = 0; i < fields.length(); i++) {
                final JSONObject field = fields.getJSONObject(i);

                EditText editText = new EditText(ArticleView.this);
                editText.setId((int) System.currentTimeMillis());
                editText.setTag(field.getString("fieldName"));
                editText.setHint(field.getString("fieldText"));
                editText.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
                editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryText));
                editText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llForm.addView(editText, layoutParams);
            }

            tvFormTitle.setText(title);
            tvFormDescription.setText(description);
            tvSubmit.setText(submitText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void submitForm(JSONArray formData) {

        Log.d("YAYA", formData.toString());

        try {
            JSONObject data = new JSONObject();
            data.put("data", formData);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.POST_FORM, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("YAYA", response.toString());
                    Toast.makeText(ArticleView.this, "Data submitted.", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void submitArticleReview(boolean isArticleHelpful) {
        try {
            JSONObject data = new JSONObject();
            data.put("isArticleHelpful", isArticleHelpful);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.POST_ARTICLE_REVIEW, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("YAYA", response.toString());
                    Toast.makeText(ArticleView.this, "Feedback submitted.", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                try {
                    boolean hasError = false;
                    JSONArray formData = new JSONArray();
                    JSONArray fields = form.getJSONArray("fields");
                    for (int i = 0; i < fields.length(); i++) {
                        JSONObject field = fields.getJSONObject(i);
                        EditText etField = (EditText) llForm.findViewWithTag(field.getString("fieldName"));
                        String data = etField.getText().toString().trim();
                        if (!data.isEmpty()) {
                            formData.put(data);
                        } else {
                            hasError = true;
                            etField.setError("Please enter " + field.getString("fieldText"));
                        }
                    }

                    if (!hasError) {
                        submitForm(formData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivYes:
                submitArticleReview(true);
                break;
            case R.id.ivNo:
                submitArticleReview(false);
                break;
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
