package com.tinlm.snef.model;

public class StoreProductImage {

    private int fspId;
    private String imageSrc;
    private int flashSaleProductId;

    public StoreProductImage() {
    }

    public StoreProductImage(int fspId, String imageSrc, int flashSaleProductId) {
        this.fspId = fspId;
        this.imageSrc = imageSrc;
        this.flashSaleProductId = flashSaleProductId;
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

    public int getFlashSaleProductId() {
        return flashSaleProductId;
    }

    public void setFlashSaleProductId(int flashSaleProductId) {
        this.flashSaleProductId = flashSaleProductId;
    }
}
