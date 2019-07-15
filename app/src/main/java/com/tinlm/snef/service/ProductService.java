package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET(ConstainServer.ProductURL + ConstainServer.GetListNameProduct)
    Call<List<Product>> getListNameProduct();
}
