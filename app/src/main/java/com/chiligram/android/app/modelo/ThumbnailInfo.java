package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailInfo {

    @SerializedName("h")
    @Expose
    private Long height;
    @SerializedName("mimetype")
    @Expose
    private String mimetype;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("w")
    @Expose
    private int width;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long h) {
        this.height = h;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        this.width = w;
    }

}