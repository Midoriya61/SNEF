package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ProductNameAdapter;
import com.tinlm.snef.adapter.TabViewPagerAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Product;
import com.tinlm.snef.service.ProductService;
import com.tinlm.snef.utilities.ApiUtils;
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
    TabLayout tabLayout;

    private ViewPager viewPager;



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
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        TabViewPagerAdapter pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // get name of product
        productService = ApiUtils.getProductService();
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

        // search product name
        productService.getListNameProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                // change text and load suggest product name
                searchProductBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        DBManager dbManager = new DBManager(SearchActivity.this);
                        dbManager.addTableFound(query);
                        Intent intent = new Intent(SearchActivity.this, SearchProductByNameActivity.class);
                        intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, query);
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        List<Product> products = new ArrayList<>();
                        if( newText.length() > 0 ) {
                            listNameProduct.setVisibility(View.VISIBLE);
                            tabLayout.setVisibility(View.INVISIBLE);
                            viewPager.setVisibility(View.INVISIBLE);
                        } else {
                            listNameProduct.setVisibility(View.INVISIBLE);
                            tabLayout.setVisibility(View.VISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                        }
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
