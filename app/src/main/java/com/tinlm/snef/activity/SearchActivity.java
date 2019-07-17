package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ProductNameAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.fragment.CategoriesHomeFragment;
import com.tinlm.snef.model.Product;
import com.tinlm.snef.service.AllService;
import com.tinlm.snef.service.ProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.ProductUtilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    SearchView searchProductBar;
    RecyclerView listNameProduct;
    ProductService productService;
    List<Product> productList = new ArrayList<>();
    ProductNameAdapter productNameAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
//        ProductUtilities productUtilities = new ProductUtilities();
        searchProductBar = findViewById(R.id.searchProductBar);
        listNameProduct = findViewById(R.id.listNameProduct);
        productService = AllService.getProductService();


        productService.getListNameProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> listProductName = response.body();
                productNameAdapter = new ProductNameAdapter(SearchActivity.this, listProductName);
                RecyclerView.LayoutManager mLayoutManager
                        = new LinearLayoutManager(SearchActivity.this,
                        LinearLayoutManager.VERTICAL, false);
                listNameProduct.setItemAnimator(new DefaultItemAnimator());
                listNameProduct.setLayoutManager(mLayoutManager);
                listNameProduct.setAdapter(productNameAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        productService.getListNameProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                // change text and load suggest product name
                searchProductBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent intent = new Intent(SearchActivity.this, SearchProductByNameActivity.class);
                        intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, query);
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        List<Product> products = new ArrayList<>();

                        for (Product product: productList
                        ) {
                            if (product.getProductName().toLowerCase().contains(newText)) {
                                products.add(product);
                            }
                        }
                        productNameAdapter.updateReceiptsList(products);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

}
