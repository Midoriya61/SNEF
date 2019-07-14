package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreService {

    @GET(ConstainServer.StoreURL)
    Call<List<Store>> getListStoreArround();

    @GET(ConstainServer.StoreURL + ConstainServer.GetStoreById + "{storeId}")
    Call<Store> getStoreById(@Path("storeId") int storeId);
}
