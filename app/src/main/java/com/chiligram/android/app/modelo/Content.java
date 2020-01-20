package com.chiligram.android.app.modelo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("msgtype")
    @Expose
    private String msgtype;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("displayname")
    @Expose
    private String displayName;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("content_uri")
    @Expose
    private String contentUri;

    @SerializedName("geo_uri")
    @Expose
    private String geoUri;

    @SerializedName("info")
    @Expose
    private Info info;

    @SerializedName("url")
    @Expose
    private String url;

    public String getContentUri() {
        return contentUri;
    }

    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getGeoUri() {
        return geoUri;
    }

    public void setGeoUri(String geoUri) {
        this.geoUri = geoUri;
    }
}