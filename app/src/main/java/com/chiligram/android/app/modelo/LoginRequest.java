package com.chiligram.android.app.modelo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    public LoginRequest(String user, String password){
        this.identifier = new Identifier();
        this.identifier.setType("m.id.user");
        this.type = "m.login.password";
        this.identifier.setUser(user);
        this.password = password;
    }
    @SerializedName("identifier")
    @Expose
    private Identifier identifier;
    @SerializedName("initial_device_display_name")
    @Expose
    private String initialDeviceDisplayName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("type")
    @Expose
    private String type;

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getInitialDeviceDisplayName() {
        return initialDeviceDisplayName;
    }

    public void setInitialDeviceDisplayName(String initialDeviceDisplayName) {
        this.initialDeviceDisplayName = initialDeviceDisplayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}