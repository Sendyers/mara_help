package com.toe.peebee.constructors;

import android.content.Context;

public class StoreListItem {

    private Context context;
    private String user;
    private String plain;
    private String tag;
    private int likes;
    private boolean isAdmin;
    private boolean isTribeOwner;
    private boolean isReplain;
    private String timestamp;
    private String key;

    public StoreListItem(Context context, String user, String plain, String tag, int likes, boolean isAdmin, boolean isTribeOwner, boolean isReplain, String timestamp, String key) {
        this.context = context;
        this.user = user;
        this.plain = plain;
        this.tag = tag;
        this.likes = likes;
        this.isAdmin = isAdmin;
        this.isTribeOwner = isTribeOwner;
        this.isReplain = isReplain;
        this.timestamp = timestamp;
        this.key = key;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isTribeOwner() {
        return isTribeOwner;
    }

    public void setIsTribeOwner(boolean isTribeOwner) {
        this.isTribeOwner = isTribeOwner;
    }

    public boolean isReplain() {
        return isReplain;
    }

    public void setIsReplain(boolean isReplain) {
        this.isReplain = isReplain;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}