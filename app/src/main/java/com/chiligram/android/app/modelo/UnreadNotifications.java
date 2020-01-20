package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnreadNotifications {

    @SerializedName("highlight_count")
    @Expose
    private int highLightCount;
    @SerializedName("notification_count")
    private int notificationCount;

    public int getHighLightCount() {
        return highLightCount;
    }

    public void setHighLightCount(int highLightCount) {
        this.highLightCount = highLightCount;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}
