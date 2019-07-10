package com.tinlm.snef.utilities;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.CategoriesService;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.service.ProductService;
import com.tinlm.snef.service.StoreProductImageService;

public class ApiUtils {

    // Categories
    public static CategoriesService getCategoriesService() {
        return RetrofitClient.getClient(ConstainServer.BaseURL).create(CategoriesService.class);
    }

    // Flash sale product

    public static FlashSaleProductService getFlashSaleProductService() {
        return RetrofitClient.getClient(ConstainServer.BaseURL).create(FlashSaleProductService.class);
    }

    // Store product image

    public static StoreProductImageService getStoreProductImageService() {
        return RetrofitClient.getClient(ConstainServer.BaseURL).create(StoreProductImageService.class);
    }

    // Product
    public static ProductService getProductService() {
        return RetrofitClient.getClient(ConstainServer.BaseURL).create(ProductService.class);
    }

}
