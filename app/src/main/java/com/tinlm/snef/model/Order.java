package com.tinlm.snef.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Order {

    @SerializedName("orderId")
    @Expose
    private int orderId;
    @SerializedName("dateOrder")
    @Expose
    private Date dateOrder;
    @SerializedName("totalPrice")
    @Expose
    private float totalPrice;
    @SerializedName("confirmationCode")
    @Expose
    private String confirmationCode;
    @SerializedName("orderQuantity")
    @Expose
    private int orderQuantity;
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("ratingPoint")
    @Expose
    private float ratingPoint;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(float ratingPoint) {
        this.ratingPoint = ratingPoint;
    }


}

