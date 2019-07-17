package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreProductImageService {
    @GET( ConstainServer.StoreProductImageURL + ConstainServer.GetOneImage + "{storeProductId}")
    Call<String> getOneImageByStoreProductId(@Path("storeProductId") int storeProductId);
}
