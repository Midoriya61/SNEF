package com.tinlm.snef.model;

import java.sql.Date;

public class FlashSaleProduct {

//        @SerializedName("flashSaleProductId")
        private int flashSaleProductId;
//    @SerializedName("quantity")
        private int quantity;
//    @SerializedName("discount")
        private int discount;
//    @SerializedName("endDate")
        private Date endDate;
//    @SerializedName("productName")
        private String productName;
//    @SerializedName("price")
        private float price;

        private String description;
//    @SerializedName("storeProductId")
        private int storeProductId;
//    @SerializedName("flashSalesId")
        private int flashSalesId;
//    @SerializedName("storeId")
        private int storeId;
//    @SerializedName("spQuantity")
        private int spQuantity;

        public FlashSaleProduct() {
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
    }