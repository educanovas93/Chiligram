package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomMessages {

    @SerializedName("chunk")
    @Expose
    private List<Message> MessageList = null;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("end")
    @Expose
    private String end;

    public List<Message> getMessageList() {
        return MessageList;
    }

    public void setJoinedRooms(List<Message> MessageList) {
        this.MessageList = MessageList;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
