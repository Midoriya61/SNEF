package com.tinlm.snef.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tinlm.snef.constain.ConstainApp;

public class Store implements Comparable<Store>{
    @SerializedName("storeId")
    private int storeId;
    @SerializedName("accountId")
    private int accountId;
    @SerializedName("storeName")
    private String storeName;
    @SerializedName("locationId")
    private int locationId;
    @SerializedName("ratingPoint")
    private double ratingPoint;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("openHour")
    private String openHour;
    @SerializedName("closeHour")
    private String closeHour;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("phone")
    private String phone;

    private String accountName;
    // variable for layout

    @SerializedName("distance")
    private double distance;


    public Store() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(double ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Calculate distance between 2 store
    public void distanceBetween2Points(double la, double lo
                                       ) {
//        double dLat = (this.latitude - la) * (Math.PI / 180);
//        double dLon = (this.longitude - lo) * (Math.PI / 180);
//        double la1ToRad = la * (Math.PI / 180);
//        double la2ToRad = this.latitude * (Math.PI / 180);
        double a = Math.sin((this.latitude - la) * (Math.PI / 180) / 2) * Math.sin((this.latitude - la) * (Math.PI / 180) / 2)
                + Math.cos(la * (Math.PI / 180))
                * Math.cos(this.latitude * (Math.PI / 180)) * Math.sin((this.longitude - lo) * (Math.PI / 180) / 2)
                * Math.sin((this.longitude - lo) * (Math.PI / 180) / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        this.distance = ConstainApp.rEarth * c; }

    @Override
    public int compareTo(Store o) {
        return Double.compare(this.distance, o.getDistance());
    }
}
