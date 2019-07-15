package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Like;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LikeService {
    @GET(ConstainServer.LikeURL + ConstainServer.GetLikeById + "{customerId}/" + "{storeProductId}")
    Call<Like> getLIkeById(@Path("customerId") int customerId, @Path("storeProductId") int storeProductId);
}
