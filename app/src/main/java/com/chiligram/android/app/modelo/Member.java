package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;

public class Member {

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    private InputStream avatarStream;

    public InputStream getAvatarStream() {
        return avatarStream;
    }

    public void setAvatarStream(InputStream avatarStream) {
        this.avatarStream = avatarStream;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


}