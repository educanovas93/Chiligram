package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinedRoom {

    public UnreadNotifications getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(UnreadNotifications unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }

    @SerializedName("unread_notifications")
    @Expose
    private UnreadNotifications unreadNotifications;

}
