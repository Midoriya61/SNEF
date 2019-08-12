package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.FlashSaleProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlashSaleProductService {
    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetAllFSP)
    Call<List<FlashSaleProduct>> getAllFSP();

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetHotFlashSaleProductURL)
    Call<List<FlashSaleProduct>> getHotFlashSaleProduct();

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPByCategoryId + "{categoryId}")
    Call<List<FlashSaleProduct>> getFSPByCategoryId(@Path("categoryId") int categoryId);

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPByStoreId + "{storeId}")
    Call<List<FlashSaleProduct>> getFSPByStoreId(@Path("storeId") int storeId);

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPByName + "{searchName}" + "/{searchCategories}")
    Call<List<FlashSaleProduct>> getFSPByName(@Path("searchName") String searchName, @Path("searchCategories") String searchCategories);

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPById + "{flashSaleProductId}")
    Call<FlashSaleProduct> getFSPById(@Path("flashSaleProductId") int flashSaleProductId);

    @GET(ConstainServer.FlashSaleProductURL + ConstainServer.GetRemaingQuantity + "{flashSaleProductId}")
    Call<Integer> getRemaingQuantity(@Path("flashSaleProductId") int flashSaleProductId);
}
