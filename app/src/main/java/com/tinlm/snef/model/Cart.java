package com.tinlm.snef.model;

public class Cart {

    private String storeName;
    public int quantity;
    private float price;
    private String productName;
    private String imageProduct;
    private int fspId;
    private int discount;
    private int pCount;

    public Cart() {
    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getFspId() {
        return fspId;
    }

    public void setFspId(int fspId) {
        this.fspId = fspId;
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
