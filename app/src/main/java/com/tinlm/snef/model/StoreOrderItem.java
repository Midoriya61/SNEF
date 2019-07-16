package com.tinlm.snef.model;

public class StoreOrderItem {

    private String storeName;
    private int quantityOrder;

    public StoreOrderItem(String storeName, int quantityOrder) {
        this.storeName = storeName;
        this.quantityOrder = quantityOrder;
    }

    public StoreOrderItem() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }
}
