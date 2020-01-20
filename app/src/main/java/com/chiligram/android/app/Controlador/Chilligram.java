package com.chiligram.android.app.Controlador;

import android.os.Environment;

import com.chiligram.android.app.modelo.Login;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.Room;
import com.chiligram.android.app.modelo.SyncRooms;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Chilligram {


    private static Chilligram chilligram = null;

    private Map<String,Room> userRooms;
    private String accessToken;
    private Login currentUser;
    private Room currentRoom;
    private List<Message> oldMessageList;
    private int notifications;
    private SyncRooms syncRooms;
    private boolean writed = false;
    private File user;
    private File rootDir;
    private File audios;
    private File videos;
    private File images;
    private File avatars;

    public boolean isWrited() {
        return writed;
    }

    public void setWrited(boolean writed) {
        this.writed = writed;
    }

    private Chilligram(){
        userRooms = new HashMap<String,Room>();
        oldMessageList = new LinkedList<>();
        crearDirectorios();
    }
    public static Chilligram getUnicaInstancia(){
        if(chilligram == null) {
            chilligram = new Chilligram();
        }
        return chilligram;
    }

    public String getColor(String userId){
        int hash = userId.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = hash & 0x0000FF;
        String hex = String.format("#%02x%02x%02x", r, g, b);
        return hex;
    }

    private void crearDirectorios(){
        rootDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Chiligram");
        if(!rootDir.exists()) {
            System.out.println("CREAMOS FICHERO "+rootDir.mkdirs());
        }

        images = new File(rootDir, "Images");
        if(!images.exists()) {
            images.mkdirs();
        }
        videos = new File(rootDir, "Video");
        if(!videos.exists()) {
            videos.mkdirs();
        }
        audios = new File(rootDir, "Audio");
        if(!audios.exists()){
            audios.mkdirs();
        }
        avatars = new File(rootDir,"Avatars");
        if(!avatars.exists()){
            avatars.mkdirs();
        }

    }

    public File getUser() {
        return user;
    }

    public File getRootDir() {
        return rootDir;
    }

    public File getAudios() {
        return audios;
    }

    public File getVideos() {
        return videos;
    }

    public File getImages() {
        return images;
    }

    public File getAvatars() {
        return avatars;
    }

    public SyncRooms getSyncRooms() {
        return syncRooms;
    }

    public void setSyncRooms(SyncRooms syncRooms) {
        this.syncRooms = syncRooms;
    }

    public int getNotifications(){
        return this.notifications;
    }

    public void incNotificationes(int cantidad){
        this.notifications+=cantidad;
    }

    public void setNotifications(int notifications){
        this.notifications = notifications;
    }

    public List<Message> getOldMessageList(){
        return Collections.unmodifiableList(oldMessageList);
    }
    public void setOldMessageList(List<Message> oldMessageList){
        this.oldMessageList = new LinkedList<>(oldMessageList);
    }
    public void clearOldMessageList(){
        this.oldMessageList.clear();
    }
    public List<String> getOldMessageIdList(){
        LinkedList<String> l = new LinkedList<>();
        for (Message m : this.oldMessageList) {
            l.add(m.getEventId());
        }
        return l;
    }

    public Double getTime(Message message){
        return null;
    }

    public String getAccessToken (){
        return this.accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public Map<String, Room> getUserRooms(){
        return Collections.unmodifiableMap(this.userRooms);
    }

    public void clearRooms(){
        this.userRooms.clear();
    }

    public Login getCurrentUser(){
        return this.currentUser;
    }

    public void setCurrentUser(Login currentUser){
        this.currentUser = currentUser;
    }

    public void addRoom(String roomId, Room room){
        this.userRooms.put(roomId,room);
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public void setCurrentRoom(Room currentRoom){
        this.currentRoom = currentRoom;
    }

}
