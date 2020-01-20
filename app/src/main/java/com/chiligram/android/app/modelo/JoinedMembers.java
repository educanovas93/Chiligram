package com.chiligram.android.app.modelo;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinedMembers {

    @SerializedName("joined")
    @Expose
    private Map<String,Member> joined;

    public Map<String,Member> getJoined(){
        return joined;
    }

    public void setJoined(Map<String, Member> joined) {
        this.joined = joined;
    }

}
