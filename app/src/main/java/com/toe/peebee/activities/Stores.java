package com.toe.peebee.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.toe.peebee.R;
import com.toe.peebee.adapters.StoresRecyclerViewAdapter;
import com.toe.peebee.classes.RecyclerItemClickListener;
import com.toe.peebee.constructors.StoreListItem;
import com.toe.peebee.utils.FirebaseUtils;
import com.toe.peebee.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Stores extends AppCompatActivity {

    //Variables related to the RecyclerViews
    private RecyclerView plainsRecyclerView;
    private RecyclerView.Adapter plainsAdapter;
    private RecyclerView.LayoutManager plainsLayoutManager;

    //Views contained in the various pages
    private FloatingActionButton fabPlains;
    private ImageView ivNothingFoundPlains;

    //ArrayLists
    private ArrayList<StoreListItem> plains = new ArrayList<>();

    //Items initiated when selected
    private StoreListItem selectedPlain;

    //Others
    private Intent i;
    private SharedPreferences sp;
    private Firebase firebase;
    private String key;
    private int members;
    private Menu menu;
    private String name, description, tribeOwner;
    private Parcelable recyclerViewState;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stores);

        FirebaseUtils.setUpFirebase(this);
        Utils.setUpToolbar(this, true);
        setUp();
        getPlains();
    }

    private void setUp() {
        activity = this;
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        firebase = new Firebase(getString(R.string.firebase_url));
        
        ivNothingFoundPlains = (ImageView) findViewById(R.id.ivNothingFoundPlains);
        ivNothingFoundPlains.setVisibility(View.INVISIBLE);

        plainsLayoutManager = new LinearLayoutManager(getApplicationContext());
        plainsRecyclerView = (RecyclerView) findViewById(R.id.rvPlains);
        plainsRecyclerView.setLayoutManager(plainsLayoutManager);
        plainsRecyclerView.setHasFixedSize(true);

        plainsAdapter = new StoresRecyclerViewAdapter(Stores.this, plains);

        fabPlains = (FloatingActionButton) findViewById(R.id.fabPlains);
        fabPlains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getPlains() {
        firebase.child("tribes/" + key + "/plains").limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                recyclerViewState = plainsRecyclerView.getLayoutManager().onSaveInstanceState();

                plains.clear();
                System.out.println("There are " + snapshot.getChildrenCount() + " plains");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Plain plain = postSnapshot.getValue(Plain.class);
//                    try {
//                        plains.add(new StoreListItem(getApplicationContext(), plain.getUser(), plain.getPlain(), plain.getTag(), plain.getLikes(), plain.isAdmin(), plain.isReplain(), plain.isTribeOwner(), plain.getTimestamp(), postSnapshot.getKey()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

                populatePlains(plains);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void populatePlains(final ArrayList<StoreListItem> plains) {
        if (plains.size() > 0) {
            ivNothingFoundPlains.setVisibility(View.INVISIBLE);
            Collections.reverse(plains);

            plainsAdapter.notifyDataSetChanged();
            plainsRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

            plainsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
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
        } else {
            plainsAdapter.notifyDataSetChanged();
            ivNothingFoundPlains.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}

