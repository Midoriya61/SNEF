package com.tinlm.snef.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.utilities.CategoriesUtilities;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;

import java.util.ArrayList;
import java.util.List;

// 6/23/2019 TinLM Create class
// 6/23/2019 TinLM Create init
// 6/23/2019 TinLM Create createListCategories
public class DashboardActivity extends AppCompatActivity {

    RecyclerView rcListCategories;
    RecyclerView rcListHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    private void init() {
        createListCategories();
        createListHostProduct();
    }

    // Create list categories
    private void createListCategories() {
        rcListCategories = findViewById(R.id.rcListCategories);

        List<Categories> categoryList = new ArrayList<>();
        CategoriesUtilities categoriesUtilities = new CategoriesUtilities();
        categoryList = categoriesUtilities.getAllCategories();

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categoryList);

        final RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(DashboardActivity.this,
                LinearLayoutManager.HORIZONTAL, false);

        rcListCategories.setItemAnimator(new DefaultItemAnimator());
        rcListCategories.setLayoutManager(mLayoutManager);
        rcListCategories.setAdapter(categoriesAdapter);
    }


    // Load list product with biggest discount
    private void createListHostProduct() {
        rcListHost = findViewById(R.id.rcListHost);
        List<FlashSaleProduct>  flashSaleProducts = new ArrayList<>();
        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        flashSaleProducts = flashSaleProductUtilities.getHotFlashSaleProduct();
        FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(DashboardActivity.this, flashSaleProducts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DashboardActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rcListHost.setItemAnimator(new DefaultItemAnimator());
        rcListHost.setLayoutManager(mLayoutManager);
        rcListHost.setAdapter(flashSaleProductAdapter);
    }

}
