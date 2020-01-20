
package com.chiligram.android.app.modelo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateContent {

    @SerializedName("join_rule")
    @Expose
    private String joinRule;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("ban")
    @Expose
    private Integer ban;
    @SerializedName("events")
    @Expose
    private List<Event> events;
    @SerializedName("events_default")
    @Expose
    private Integer eventsDefault;
    @SerializedName("kick")
    @Expose
    private Integer kick;
    @SerializedName("redact")
    @Expose
    private Integer redact;
    @SerializedName("state_default")
    @Expose
    private Integer stateDefault;
    @SerializedName("users_default")
    @Expose
    private Integer usersDefault;

    public String getJoinRule() {
        return joinRule;
    }

    public void setJoinRule(String joinRule) {
        this.joinRule = joinRule;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Integer getEventsDefault() {
        return eventsDefault;
    }

    public void setEventsDefault(Integer eventsDefault) {
        this.eventsDefault = eventsDefault;
    }

    public Integer getKick() {
        return kick;
    }

    public void setKick(Integer kick) {
        this.kick = kick;
    }

    public Integer getRedact() {
        return redact;
    }

    public void setRedact(Integer redact) {
        this.redact = redact;
    }

    public Integer getStateDefault() {
        return stateDefault;
    }

    public void setStateDefault(Integer stateDefault) {
        this.stateDefault = stateDefault;
    }

    public Integer getUsersDefault() {
        return usersDefault;
    }

    public void setUsersDefault(Integer usersDefault) {
        this.usersDefault = usersDefault;
    }

}