package com.chiligram.android.app.modelo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("duration")
    @Expose
    private Long duration;
    @SerializedName("h")
    @Expose
    private int height;
    @SerializedName("mimetype")
    @Expose
    private String mimetype;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("thumbnail_info")
    @Expose
    private ThumbnailInfo thumbnailInfo;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("w")
    @Expose
    private int width;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
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

    public ThumbnailInfo getThumbnailInfo() {
        return thumbnailInfo;
    }

    public void setThumbnailInfo(ThumbnailInfo thumbnailInfo) {
        this.thumbnailInfo = thumbnailInfo;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        this.width = w;
    }

}
