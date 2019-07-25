package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderService {
    @GET(ConstainServer.OrderURL + ConstainServer.InsertNewOrder + "{confirmationCode}/" + "{accountId}")
    Call insertNewOrder(@Path("confirmationCode") String confirmationCode, @Path("accountId") int accountId);

    @GET(ConstainServer.OrderURL + ConstainServer.GetLastOrder)
    Call<Order> getLastOrder();

    @GET(ConstainServer.OrderURL + ConstainServer.GetAllOrder)
    Call getAllOrder();

    @GET(ConstainServer.OrderURL + ConstainServer.GetOrderById + "{orderId}")
    Call getOrderById(@Path("orderId") int orderId);

    @GET(ConstainServer.OrderURL + ConstainServer.UpdateRatingBar + "{orderId}" + "{ratingPoint}")
    Call updateRatingBar(@Path("orderId") int orderId, @Path("ratingPoint") int ratingPoint);
}
