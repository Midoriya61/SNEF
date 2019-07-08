package com.tinlm.snef.model;

//import android.content.SharedPreferences;
//
//import com.google.gson.Gson;

import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;

public class OrderDetail {

    private int orderDetailId;
    private int orderId;
    private int flashSaleProductId;
    private int quantity;
    private float orderDetailPrice;

    // setting for preference

    private int fspId;
    private int quantityFsp;
    private int discount;
    private Date endDate;
    private String productName;
    private int fsId;
    private String storeName;
    private float price;
    private String setImageProduct;


    public OrderDetail() {
    }

    public String getSetImageProduct() {
        return setImageProduct;
    }

    public void setSetImageProduct(String setImageProduct) {
        this.setImageProduct = setImageProduct;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getFspId() {
        return fspId;
    }

    public void setFspId(int fspId) {
        this.fspId = fspId;
    }

    public int getQuantityFsp() {
        return quantityFsp;
    }

    public void setQuantityFsp(int quantityFsp) {
        this.quantityFsp = quantityFsp;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getFsId() {
        return fsId;
    }

    public void setFsId(int fsId) {
        this.fsId = fsId;
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
