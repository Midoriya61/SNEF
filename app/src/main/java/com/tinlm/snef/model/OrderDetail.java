package com.tinlm.snef.model;

public class OrderDetail {

    private int orderDetailId;
    private int orderId;
    private int flashSaleProductId;
    private int quantity;
    private float orderDetailPrice;

    public OrderDetail() {
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public float getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(float orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

}
