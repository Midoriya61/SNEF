package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderService {
    @GET(ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.InsertNewOrder + "{confirmationCode}/" + "{accountId}")
    Call insertNewOrder(@Path("confirmationCode") String confirmationCode, @Path("accountId") int accountId);


}
