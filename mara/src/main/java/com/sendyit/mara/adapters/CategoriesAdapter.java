package com.sendyit.mara.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sendyit.mara.R;
import com.sendyit.mara.constructors.CategoryListItem;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<CategoryListItem> categories;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        }
    }

    public CategoriesAdapter(Activity activity, ArrayList<CategoryListItem> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CategoryListItem category = categories.get(position);
        holder.tvTitle.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
