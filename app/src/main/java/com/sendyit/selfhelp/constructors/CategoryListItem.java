package com.sendyit.selfhelp.constructors;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryListItem {

    private int id;
    private String title;
    private String description;
    private String subCategories;
    private String articles;

    public CategoryListItem(JSONObject category) {
        try {
            this.id = category.getInt("id");
            this.title = category.getString("title");
            this.description = category.getString("description");
            this.subCategories = category.getString("subCategories");
            this.articles = category.getString("articles");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }
}