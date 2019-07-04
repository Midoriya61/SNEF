package com.tinlm.snef.model;

import com.google.gson.annotations.SerializedName;

public class StoreProductImage {


    private int fspId;

    private String imageSrc;

    private int storeProductId;

    public StoreProductImage() {
    }

    public StoreProductImage(int fspId, String imageSrc, int storeProductId) {
        this.fspId = fspId;
        this.imageSrc = imageSrc;
        this.storeProductId = storeProductId;
    }

    public int getFspId() {
        return fspId;
    }

    public void setFspId(int fspId) {
        this.fspId = fspId;
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
