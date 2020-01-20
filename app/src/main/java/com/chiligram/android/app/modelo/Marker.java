package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marker {

    @SerializedName("m.fully.read")
    @Expose
    private String fullyRead;

    @SerializedName("m.read")
    @Expose
    private String read;

    public Marker(){
    }
    public Marker(String fullyRead,String read){
        this.fullyRead = fullyRead;
        this.read = read;
    }

    public String getFullyRead() {
        return fullyRead;
    }

    public void setFullyRead(String fullyRead) {
        this.fullyRead = fullyRead;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
