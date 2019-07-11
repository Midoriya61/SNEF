package com.tinlm.snef.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Categories {

    @SerializedName("categoriesId")
    @Expose
    private int categoriesId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("imageSrc")
    @Expose
    private String ImageSrc;

    public Categories() {
    }

    public Categories(int categoriesId, String categoryName, String imageSrc) {
        this.categoriesId = categoriesId;
        this.categoryName = categoryName;
        ImageSrc = imageSrc;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        ImageSrc = imageSrc;
    }
}
