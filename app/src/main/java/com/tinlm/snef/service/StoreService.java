package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreService {

    @GET(ConstainServer.StoreURL + "{latitude}/" + "{longitude}")
    Call<List<Store>> getListStoreArround(@Path("latitude") double latitude, @Path("longitude") double longitude );

    @GET(ConstainServer.StoreURL + ConstainServer.GetStoreById + "{storeId}")
    Call<List<Store>> getStoreById(@Path("storeId") int storeId);
}
