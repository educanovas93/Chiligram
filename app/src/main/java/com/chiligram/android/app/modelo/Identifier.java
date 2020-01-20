package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Identifier {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user")
    @Expose
    private String user;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}