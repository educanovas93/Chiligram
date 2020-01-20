package com.chiligram.android.app.modelo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName(){
        return this.userId.split("@")[1].split(":")[0];
    }
}