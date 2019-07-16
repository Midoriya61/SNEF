package com.tinlm.snef.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tinlm.snef.constain.ConstainServer;

public class StoreProductImage {

    @SerializedName("spiId")
    @Expose
    private int spiId;
    @SerializedName("imageSrc")
    @Expose
    private String imageSrc;
    @SerializedName("storeProductId")
    @Expose
    private int storeProductId;

    public StoreProductImage() {
    }

    public int getSpiId() {
        return spiId;
    }

    public void setSpiId(int spiId) {
        this.spiId = spiId;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(int storeProductId) {
        this.storeProductId = storeProductId;
    }
}
