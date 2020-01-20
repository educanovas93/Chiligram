package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Comparable<Message> {

    @SerializedName("origin_server_ts")
    @Expose
    private Long originServerTs;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("unsigned")
    @Expose
    private Unsigned unsigned;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("room_id")
    @Expose
    private String roomId;

    public Long getOriginServerTs() {
        return originServerTs;
    }

    public void setOriginServerTs(Long originServerTs) {
        this.originServerTs = originServerTs;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Unsigned getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(Unsigned unsigned) {
        this.unsigned = unsigned;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderUserName(){
        return this.sender.split("@")[1].split(":")[0];
    }

    @Override
    public int compareTo(Message o) {
        if (this.getUnsigned().getAge() < o.getUnsigned().getAge()) {
            return 1;
        }
        else if (this.getUnsigned().getAge() > o.getUnsigned().getAge()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}

