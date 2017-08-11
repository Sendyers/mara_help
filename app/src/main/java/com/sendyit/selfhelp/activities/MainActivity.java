package com.sendyit.selfhelp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.sendyit.selfhelp.R;
import com.sendyit.selfhelp.adapters.CategoriesAdapter;
import com.sendyit.selfhelp.classes.CacheRequest;
import com.sendyit.selfhelp.classes.Constants;
import com.sendyit.selfhelp.classes.RecyclerItemClickListener;
import com.sendyit.selfhelp.constructors.CategoryListItem;
import com.sendyit.selfhelp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    //Views
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Lists
    private ArrayList<CategoryListItem> categories = new ArrayList<>();

    //Others
    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setUpToolbar();
        setUp();
        getData();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Utils.setUpStatusBar(this);
    }

    private void setUp() {
        queue = Volley.newRequestQueue(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CategoriesAdapter(MainActivity.this, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {

                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {

                    }

                    @Override
                    public void onDoubleTap(View childView, int position) {

                    }
                }));
    }

    private void getData() {
        CacheRequest cacheRequest = new CacheRequest(0, Constants.GET_COLLECTION, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONObject jsonObject = new JSONObject(jsonString);
                    processResponse(jsonObject);
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


    private void processResponse(JSONObject collection) {
        categories.clear();

        try {
            JSONArray categoriesArray = collection.getJSONArray("categories");

            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject category = categoriesArray.getJSONObject(i);
                categories.add(new CategoryListItem(category));
            }

            if (categories.size() > 0) {
                Collections.reverse(categories);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
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

