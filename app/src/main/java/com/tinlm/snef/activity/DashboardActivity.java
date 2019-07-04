package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.utilities.CategoriesUtilities;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 6/23/2019 TinLM Create class
// 6/23/2019 TinLM Create init
// 6/23/2019 TinLM Create createListCategories
// 6/23/2019 TinLM Create createListHostProduct
// 6/23/2019 TinLM Create createListFSP
public class DashboardActivity extends AppCompatActivity {

    RecyclerView rcListCategories;
    RecyclerView rcListHost;
    RecyclerView rcListFlashSaleProduct;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        navigateDashboard();
    }

    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_category:
                        intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_around:
                        intent = new Intent(DashboardActivity.this, AroundStoreActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_orders:
                        intent = new Intent(DashboardActivity.this, OrderActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_account:
                        intent = new Intent(DashboardActivity.this, AccountActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });
    }

    private void init() {
        createListCategories();
        createListHostProduct();
        createListFSP();
    }

    // Create list flash sale product
    private void createListFSP() {
        rcListFlashSaleProduct = findViewById(R.id.rcListFlashSaleProduct);
        List<FlashSaleProduct>  flashSaleProducts = new ArrayList<>();
        Map<Integer, String> listImageProduct = new HashMap<>();
        Map<Integer, Integer> listTotalPrice = new HashMap<>();
        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();

        flashSaleProducts = flashSaleProductUtilities.getAllFSP();
        for (int i = 0; i < flashSaleProducts.size(); i++) {

            String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
            listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );

            int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
            listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);


        }


        FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(this, flashSaleProducts, listImageProduct, listTotalPrice);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        rcListFlashSaleProduct.setItemAnimator(new DefaultItemAnimator());
        rcListFlashSaleProduct.setLayoutManager(mLayoutManager);
        rcListFlashSaleProduct.setAdapter(flashSaleProductAdapter);
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
        Map<Integer, String> listImageProduct = new HashMap<>();
        Map<Integer, Integer> listTotalPrice = new HashMap<>();
        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();

        flashSaleProducts = flashSaleProductUtilities.getAllFSP();
        for (int i = 0; i < flashSaleProducts.size(); i++) {
            String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
            listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );
            int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
            listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);
        }
        FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(this, flashSaleProducts, listImageProduct, listTotalPrice);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rcListHost.setItemAnimator(new DefaultItemAnimator());
        rcListHost.setLayoutManager(mLayoutManager);
        rcListHost.setAdapter(flashSaleProductAdapter);
    }

}
