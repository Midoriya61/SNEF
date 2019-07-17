package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreProductService {

    @GET(ConstainServer.StoreProductURL + ConstainServer.GetDesById + "{storeProductId}")
    Call<String> getDesById(@Path("storeProductId") int storeProductId);
}
