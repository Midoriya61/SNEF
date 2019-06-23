package com.tinlm.snef.model;

import java.sql.Date;

public class FlashSaleProduct {
        private int flashSaleProductId;
        private int quantity;
        private int discount;
        private Date startDate;
        private Date endDate;
        private String productName;
        private float price;
        private String description;
        private int storeProductId;
        private int flashSalesId;

        private int storeId;
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

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
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