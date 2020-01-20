
package com.chiligram.android.app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unsigned {

    @SerializedName("age")
    @Expose
    private Long age;
    @SerializedName("replaces_state")
    @Expose
    private String replacesState;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getReplacesState() {
        return replacesState;
    }

    public void setReplacesState(String replacesState) {
        this.replacesState = replacesState;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}