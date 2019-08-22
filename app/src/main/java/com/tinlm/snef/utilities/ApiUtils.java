package com.tinlm.snef.utilities;

import android.content.Context;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.service.CategoriesService;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.service.ProductService;
import com.tinlm.snef.service.StoreProductImageService;
import com.tinlm.snef.service.StoreService;

import okhttp3.Cache;
import retrofit2.Retrofit;

public class ApiUtils {
    private static StoreService storeService = null;
    private static FlashSaleProductService flashSaleProductService = null;
    private static StoreProductImageService storeProductImageService = null;
    private static ProductService productService = null;


    // Categories
    public static CategoriesService getCategoriesService(Context context, Cache cache) {
        return RetrofitClient.getClient1(context, cache, ConstainApp.TIMECACHINGCATEGORIES1).create(CategoriesService.class);
    }

    // Flash sale product
    public static FlashSaleProductService getFlashSaleProductService() {
        if ( flashSaleProductService == null ) {
            flashSaleProductService = RetrofitClient.getClient(ConstainServer.BaseURL).create(FlashSaleProductService.class);
        }
        return flashSaleProductService;
    }

    public static FlashSaleProductService getFlashSaleProductService30(Context context, Cache cache, final String urlSharePrefer) {
        return RetrofitClient.getClient30(context, cache, urlSharePrefer).create(FlashSaleProductService.class);
    }

    // Store product image

    public static StoreProductImageService getStoreProductImageService() {
        if ( storeProductImageService == null ) {
            storeProductImageService = RetrofitClient.getClient(ConstainServer.BaseURL).create(StoreProductImageService.class);
        }
        return storeProductImageService;
    }

    // Product
    public static ProductService getProductService() {
        if ( productService == null ) {
            productService = RetrofitClient.getClient(ConstainServer.BaseURL).create(ProductService.class);
        }
        return productService;
    }

    public static ProductService getProductService1(Context context, Cache cache) {
        return  RetrofitClient.getClient1(context, cache, ConstainApp.TIMECACHINGPRODUCT1).create(ProductService.class);
    }


    // Store
    public static StoreService getStoreService() {
        if (storeService == null) {
            storeService = RetrofitClient.getClient(ConstainServer.BaseURL).create(StoreService.class);
        }
        return storeService;
    }

    public static StoreService getStoreService30(Context context, Cache cache, final String urlSharePrefer) {
        return RetrofitClient.getClient30(context, cache, urlSharePrefer).create(StoreService.class);
    }

}
