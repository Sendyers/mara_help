package com.sendyit.mara.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
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
import com.bumptech.glide.Glide;
import com.sendyit.mara.R;
import com.sendyit.mara.classes.SendyHelpConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MaraArticle extends AppCompatActivity implements View.OnClickListener {

    //Actions
    private static final int RESPONSE_TYPE_ACTION = 1;
    private static final int RESPONSE_TYPE_FORM = 2;

    //Views
    private LinearLayout llActions, llForm;
    private TextView tvTitle, tvDescription, tvFormTitle, tvFormDescription, tvSubmit;
    private ImageView ivImage, ivYes, ivNo;

    //Others
    private RequestQueue queue;
    private JSONObject form;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.article_view);

        setUp();
        getData();
    }

    private void setUp() {
        queue = Volley.newRequestQueue(MaraArticle.this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        llActions = (LinearLayout) findViewById(R.id.llActions);
        llForm = (LinearLayout) findViewById(R.id.llForm);
        tvFormTitle = (TextView) findViewById(R.id.tvFormTitle);
        tvFormDescription = (TextView) findViewById(R.id.tvFormDescription);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivYes = (ImageView) findViewById(R.id.ivYes);
        ivNo = (ImageView) findViewById(R.id.ivNo);

        tvSubmit.setOnClickListener(this);
        ivYes.setOnClickListener(this);
        ivNo.setOnClickListener(this);
    }

    //Get article data bundled from Mara
    private void getData() {
        try {
            Bundle b = getIntent().getExtras();
            JSONObject article = new JSONObject(b.getString("article"));
            setData(article);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Set article data to views
    private void setData(JSONObject article) {
        try {
            //Get article text
            tvTitle.setText(article.getString("title"));
            tvDescription.setText(article.getString("description"));

            //Get article image
            if (article.has("image")) {
                String image = article.getString("image");
                if (image != null && !image.isEmpty()) {
                    ivImage.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(image).into(ivImage);
                }
            }

            //Get intended actions and forms
            int responseType = article.getInt("responseType");

            if (article.has("actions") && responseType == RESPONSE_TYPE_ACTION) {
                JSONArray actions = article.getJSONArray("actions");
                processActions(actions);
            }

            if (article.has("form") && responseType == RESPONSE_TYPE_FORM) {
                form = article.getJSONObject("form");
                processForm(form);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Process clickable actions with links
    private void processActions(JSONArray actions) {
        try {
            llActions.setVisibility(View.VISIBLE);

            for (int i = 0; i < actions.length(); i++) {
                final JSONObject action = actions.getJSONObject(i);
                String actionName = action.getString("actionName");

                //Create clickable TextViews with links
                TextView textView = new TextView(MaraArticle.this);
                textView.setId((int) System.currentTimeMillis());
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                textView.setText(actionName);
                textView.setPadding(0, 0, 0, 48);
                textView.setTextSize(18f);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setHighlightColor(Color.TRANSPARENT);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(action.getString("actionUrl")));
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llActions.addView(textView, layoutParams);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Create a from using instructions from the JSON
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

                //Create relevant EditTexts
                EditText editText = new EditText(MaraArticle.this);
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
        try {
            JSONObject data = new JSONObject();
            data.put("data", formData);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(SendyHelpConstants.POST_FORM, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MaraArticle.this, "Data submitted.", Toast.LENGTH_SHORT).show();
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

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(SendyHelpConstants.POST_ARTICLE_REVIEW, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MaraArticle.this, "Feedback submitted.", Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.tvSubmit) {
            try {
                //Get and validate data from EditTexts
                boolean hasError = false;

                JSONArray formData = new JSONArray();
                JSONArray fields = form.getJSONArray("fields");

                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    EditText etField = (EditText) llForm.findViewWithTag(field.getString("fieldName"));

                    String data = etField.getText().toString().trim();
                    if (!data.isEmpty() || data.length() > 3) {
                        formData.put(data);
                    } else {
                        hasError = true;
                        etField.setError("Please enter valid " + field.getString("fieldText"));
                    }
                }

                if (!hasError) {
                    submitForm(formData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.ivYes) {
            submitArticleReview(true);
        } else if (v.getId() == R.id.ivNo) {
            submitArticleReview(false);
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
