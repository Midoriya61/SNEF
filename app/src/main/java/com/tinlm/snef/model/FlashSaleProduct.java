package com.tinlm.snef.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.sql.Date;

public class FlashSaleProduct implements Comparable<FlashSaleProduct> {


    @SerializedName("flashSaleProductId")
    @Expose
    private int flashSaleProductId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("discount")
    @Expose
    private int discount;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private float price;

    @SerializedName("storeProductId")
    @Expose
    private int storeProductId;
    @SerializedName("flashSalesId")
    @Expose
    private int flashSalesId;
    @SerializedName("storeId")
    @Expose
    private int storeId;
    @SerializedName("spQuantity")
    @Expose

    private int spQuantity;

    @SerializedName("totalQuantity")
    @Expose
    private int totalQuantity;

    @SerializedName("imageSrc")
    @Expose
    private String imageSrc;

    @SerializedName("description")
    @Expose
    private String description;

    public FlashSaleProduct() {
    }


    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getSpQuantity() {
        return spQuantity;
    }

    public void setSpQuantity(int spQuantity) {
        this.spQuantity = spQuantity;
    }

    public int getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(int storeProductId) {
        this.storeProductId = storeProductId;
    }

    public int getFlashSalesId() {
        return flashSalesId;
    }

    public void setFlashSalesId(int flashSalesId) {
        this.flashSalesId = flashSalesId;
    }

    public int getFlashSaleProductId() {
        return flashSaleProductId;
    }

    public void setFlashSaleProductId(int flashSaleProductId) {
        this.flashSaleProductId = flashSaleProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int compareTo(FlashSaleProduct o) {
        return (int) (this.price - o.getPrice());
    }

}