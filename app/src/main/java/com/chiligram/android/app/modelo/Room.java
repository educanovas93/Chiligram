package com.chiligram.android.app.modelo;

import com.chiligram.android.app.Controlador.Chilligram;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Room implements Comparable<Room>{


    private String name;
    private String id;
    private Message lastMessage;
    private List<Member> members;
    private UnreadNotifications unreadNotifications;

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public UnreadNotifications getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(UnreadNotifications unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }

    public Room(String name, String id) {
        this.members = new LinkedList<>();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        if (!this.name.equals("")) {
            return this.name;
        } else {
            String displayNameOther = Chilligram.getUnicaInstancia().getCurrentUser().getDisplayName();
            for (Member m: this.members){
                if(!m.getDisplayName().equals(displayNameOther)) {
                    return m.getDisplayName();
                }
            }
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void addMember(Member member) {
        this.members.add(member);
    }
    /*
    public Message getLastMessage(){
        APIUtils.getAPIService().getRoomMessages(this.id,"b", Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<RoomMessages>() {
            @Override
            public void onResponse(Call<RoomMessages> call, Response<RoomMessages> response) {
                List<Message> msg = new LinkedList<>(response.body().getMessageList());
                Collections.sort(msg);
                lastMessage = ((LinkedList<Message>) msg).getFirst();
            }

            @Override
            public void onFailure(Call<RoomMessages> call, Throwable t) {
                System.out.println(t.fillInStackTrace());
            }
        });
        return lastMessage;
    }
    */
    public Message getLastMessage(){
        return this.lastMessage;
    }
    public void setLastMessage(Message message){
        this.lastMessage = message;
    }



    @Override
    public int compareTo(Room o) {
        if (this.getLastMessage().getUnsigned().getAge() < o.getLastMessage().getUnsigned().getAge()) {
            return -1;
        }
        else if (this.getLastMessage().getUnsigned().getAge() > o.getLastMessage().getUnsigned().getAge()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}