package com.tinlm.snef.service;

import android.app.Activity;
import android.content.Context;

import com.tinlm.snef.activity.FlashSalesProductDetailActivity;
import com.tinlm.snef.utilities.ApiUtils;

public class AllService {
    private static StoreService storeService;;
    private static CategoriesService categoriesService;;
    private static FlashSaleProductService flashSaleProductService;;
    private static ProductService productService;;
    private static StoreProductImageService storeProductImageService;;
    private static StoreProductService storeProductService;;

    public static StoreService getStoreService() {

        if( storeService == null ) {
            storeService = ApiUtils.getStoreService();
        }
        return storeService;

    }

    public static CategoriesService getCategoriesService() {
        if( categoriesService == null ) {
            categoriesService = ApiUtils.getCategoriesService();
        }
        return categoriesService;

    }

    public static FlashSaleProductService getFlashSaleProductService() {
        if( flashSaleProductService == null ) {
            flashSaleProductService = ApiUtils.getFlashSaleProductService();
        }
        return flashSaleProductService;

    }

    public static ProductService getProductService() {
        if( productService == null ) {
            productService = ApiUtils.getProductService();
        }
        return productService;

    }

    public static StoreProductImageService getStoreProductImageService() {
        if( storeProductImageService == null ) {
            storeProductImageService = ApiUtils.getStoreProductImageService();
        }
        return storeProductImageService;

    }

    public static StoreProductService getStoreProductService() {
        if( storeProductService == null ) {
            storeProductService = ApiUtils.getStoreProductService();
        }
        return storeProductService;

    }
}
