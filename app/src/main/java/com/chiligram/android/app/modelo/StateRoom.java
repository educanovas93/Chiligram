package com.chiligram.android.app.modelo;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateRoom{

    @SerializedName("state_room")
    @Expose
    private List<Event> event = null;

    public List<Event> getStateRoom() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

}