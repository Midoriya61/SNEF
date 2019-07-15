package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.utilities.CategoriesUtilities;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rcListCategories;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();
        navigateDashboard();
    }

    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.action_category);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(CategoryActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        break;
                    case R.id.action_around:
                        intent = new Intent(CategoryActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:
                        intent = new Intent(CategoryActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_account:
                        intent = new Intent(CategoryActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    private void init() {
        rcListCategories = findViewById(R.id.rcListCategories);
        List<Categories> categoryList = new ArrayList<>();
        CategoriesUtilities categoriesUtilities = new CategoriesUtilities();
        categoryList = categoriesUtilities.getAllCategories();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categoryList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        rcListCategories.setItemAnimator(new DefaultItemAnimator());
        rcListCategories.setLayoutManager(mLayoutManager);
        rcListCategories.setAdapter(categoriesAdapter);
    }
}
