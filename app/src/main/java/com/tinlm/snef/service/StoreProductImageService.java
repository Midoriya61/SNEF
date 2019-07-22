package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.StoreProductImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreProductImageService {
    @GET( ConstainServer.StoreProductImageURL + ConstainServer.GetOneImage + "{storeProductId}")
    Call<String> getOneImageByStoreProductId(@Path("storeProductId") int storeProductId);

    @GET( ConstainServer.StoreProductImageURL + ConstainServer.GetImage + "{storeProductId}")
    Call<List<String>> getImageByStoreProductId(@Path("storeProductId") int storeProductId);
}
