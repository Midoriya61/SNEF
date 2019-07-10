package com.tinlm.snef.service;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Categories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesService {
    @GET(ConstainServer.CategoriesURL)
    Call<List<Categories>> getAllCategories();
}
