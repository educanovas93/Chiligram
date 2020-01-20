package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncRooms {

    @SerializedName("rooms")
    @Expose
    private Rooms rooms;

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }
}
