package com.toe.peebee.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toe.peebee.R;
import com.toe.peebee.constructors.StoreListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class StoresRecyclerViewAdapter extends RecyclerView.Adapter<StoresRecyclerViewAdapter.ViewHolder> {

    private ArrayList<StoreListItem> dataset;
    private Activity fragment;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPlain, tvTag, tvLikes;
        public TextView tvTime;
        public ImageView ivReplain, ivLikes, ivOpen;

        public ViewHolder(View v) {
            super(v);
            tvPlain = (TextView) v.findViewById(R.id.tvPlain);
            tvTag = (TextView) v.findViewById(R.id.tvTag);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvLikes = (TextView) v.findViewById(R.id.tvLikes);
            ivReplain = (ImageView) v.findViewById(R.id.ivReplain);
            ivLikes = (ImageView) v.findViewById(R.id.ivLikes);
            ivOpen = (ImageView) v.findViewById(R.id.ivOpen);
        }
    }

    public StoresRecyclerViewAdapter(Activity fragment, ArrayList<StoreListItem> dataset) {
        this.fragment = fragment;
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Set data
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
