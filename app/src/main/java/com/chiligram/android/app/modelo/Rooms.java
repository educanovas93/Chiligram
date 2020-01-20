
package com.chiligram.android.app.modelo;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rooms {

    @SerializedName("joined_rooms")
    @Expose
    private List<String> joinedRooms = null;

    @SerializedName("join")
    @Expose
    private Map<String,JoinedRoom> join;

    public Map<String, JoinedRoom> getJoin() {
        return join;
    }

    public void setJoin(Map<String, JoinedRoom> join) {
        this.join = join;
    }

    public List<String> getJoinedRooms() {
        return joinedRooms;
    }

    public void setJoinedRooms(List<String> joinedRooms) {
        this.joinedRooms = joinedRooms;
    }

}
