package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderDetailService {

    @GET(ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetAllOrderDetailByOrderId + "/{orderId}")
    Call getAllOrderDetailByOrderId(@Path("orderId") int orderId);

}
