package com.sendyit.mara.constructors;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryListItem {

    public static final int TYPE_FINAL = 1;
    public static final int TYPE_ARTICLE = 2;

    private int id;
    private String title;
    private String description;
    private String subCategories;
    private String articles;
    private int type;
    private String article;

    public CategoryListItem(String article) {
        //This processes list of articles
        try {
            JSONObject articleJson = new JSONObject(article);

            this.article = article;
            this.title = articleJson.getString("title");
            this.type = articleJson.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CategoryListItem(JSONObject category) {
        //This processes lists of sub-categories
        try {
            this.id = category.getInt("id");
            this.title = category.getString("title");
            this.description = category.getString("description");
            this.subCategories = category.getString("subCategories");
            this.articles = category.getString("articles");
            this.type = category.getInt("type");
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}