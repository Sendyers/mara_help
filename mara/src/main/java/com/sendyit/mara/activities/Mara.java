package com.sendyit.mara.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.sendyit.mara.R;
import com.sendyit.mara.adapters.CategoriesAdapter;
import com.sendyit.mara.classes.CacheRequest;
import com.sendyit.mara.classes.SendyHelpConstants;
import com.sendyit.mara.classes.RecyclerItemClickListener;
import com.sendyit.mara.constructors.CategoryListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.sendyit.mara.constructors.CategoryListItem.TYPE_ARTICLE;
import static com.sendyit.mara.constructors.CategoryListItem.TYPE_FINAL;

public class Mara extends AppCompatActivity {

    //Views
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Lists
    private ArrayList<CategoryListItem> categories = new ArrayList<>();

    //Others
    private RequestQueue queue;
    private Context context;

    public Mara() {
        //Default constructor
    }

    public Mara(Context context, String getCollection, String postArticleReview, String postForm) {
        this.context = context;
        SendyHelpConstants.GET_COLLECTION = getCollection;
        SendyHelpConstants.POST_ARTICLE_REVIEW = postArticleReview;
        SendyHelpConstants.POST_FORM = postForm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setUp();
        getBundle();
    }

    private void setUp() {
        queue = Volley.newRequestQueue(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CategoriesAdapter(Mara.this, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        CategoryListItem category = categories.get(position);

                        //If it's an article, go to MaraArticle View
                        if (category.getType() == TYPE_ARTICLE) {
                            Intent i = new Intent(getApplicationContext(), MaraArticle.class);
                            Bundle b = new Bundle();
                            b.putString("article", category.getArticle());
                            i.putExtras(b);
                            startActivity(i);

                            //If it's a list of articles
                        } else if (category.getType() == TYPE_FINAL) {
                            Intent i = new Intent(getApplicationContext(), Mara.class);
                            Bundle b = new Bundle();
                            b.putString("category", category.getArticles());
                            b.putInt("type", category.getType());
                            i.putExtras(b);
                            startActivity(i);

                            //If it's a list of sub categories
                        } else {
                            Intent i = new Intent(getApplicationContext(), Mara.class);
                            Bundle b = new Bundle();
                            b.putString("category", category.getSubCategories());
                            b.putInt("type", category.getType());
                            i.putExtras(b);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {

                    }

                    @Override
                    public void onDoubleTap(View childView, int position) {

                    }
                }));
    }

    //Process subcategories
    private void getBundle() {
        try {
            Bundle b = getIntent().getExtras();
            if (b == null) {
                getData();
            } else {
                String category = b.getString("category");
                int type = b.getInt("type");
                if (type == TYPE_FINAL) {
                    processArticles(new JSONArray(category));
                } else {
                    processCategories(new JSONArray(category));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        //Cache the data so it works offline, no need to make API calls every time
        CacheRequest cacheRequest = new CacheRequest(0, SendyHelpConstants.GET_COLLECTION, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONObject collection = new JSONObject(jsonString);
                    JSONArray categoriesArray = collection.getJSONArray("categories");

                    processCategories(categoriesArray);
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(cacheRequest);
    }

    private void processArticles(JSONArray articlesArray) {
        categories.clear();

        try {
            for (int i = 0; i < articlesArray.length(); i++) {
                String article = articlesArray.getString(i);
                categories.add(new CategoryListItem(article));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }

    private void processCategories(JSONArray categoriesArray) {
        categories.clear();

        try {
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject category = categoriesArray.getJSONObject(i);
                categories.add(new CategoryListItem(category));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }

    public void showHelp() {
        Intent i = new Intent(context, Mara.class);
        context.startActivity(i);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}

