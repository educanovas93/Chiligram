package com.chiligram.android.app.modelo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Event {

    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("content")
    @Expose
    private StateContent content;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("origin_server_ts")
    @Expose
    private Double originServerTs;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("state_key")
    @Expose
    private String stateKey;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("replaces_state")
    @Expose
    private String replacesState;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public StateContent getContent() {
        return content;
    }

    public void setContent(StateContent content) {
        this.content = content;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Double getOriginServerTs() {
        return originServerTs;
    }

    public void setOriginServerTs(Double originServerTs) {
        this.originServerTs = originServerTs;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getReplacesState() {
        return replacesState;
    }

    public void setReplacesState(String replacesState) {
        this.replacesState = replacesState;
    }

}