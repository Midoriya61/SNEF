package com.tinlm.snef.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Like {

    @SerializedName("likeId")
    @Expose
    private int likeId;

    @SerializedName("customerId")
    @Expose
    private int customerId;
    @SerializedName("storeProductId")
    @Expose
    private int storeProductId;

    public Like() {
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(int storeProductId) {
        this.storeProductId = storeProductId;
    }
}
