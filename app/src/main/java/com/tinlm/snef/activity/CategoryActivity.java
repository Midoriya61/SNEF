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
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.utilities.CategoriesUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rcListCategories;
    private TextView txtRFind, txtCartNumber;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        txtRFind = findViewById(R.id.txtRFind);
        txtCartNumber = findViewById(R.id.txtCartNumber);
        init();
        navigateDashboard();
    }

    @Override
    public void onResume(){
        super.onResume();
        txtCartNumber.setText(String.valueOf(new DBManager(this).getCartNumber()));
    }

    private void navigateDashboard() {
//        bottomNavigation = findViewById(R.id.bottomNavigation);
//        bottomNavigation.setSelectedItemId(R.id.action_category);
//        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Intent intent;
//                switch (menuItem.getItemId()) {
//                    case R.id.action_home:
//                        intent = new Intent(CategoryActivity.this, DashboardActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_category:
//                        break;
//                    case R.id.action_around:
//                        intent = new Intent(CategoryActivity.this, AroundStoreActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_orders:
//                        intent = new Intent(CategoryActivity.this, OrderActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_account:
//                        intent = new Intent(CategoryActivity.this, AccountActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void init() {
        rcListCategories = findViewById(R.id.rcListCategories);
        List<Categories> categoryList = new ArrayList<>();
        CategoriesUtilities categoriesUtilities = new CategoriesUtilities();
        categoryList = categoriesUtilities.getAllCategories();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categoryList, ConstainApp.CategoryActivity);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        rcListCategories.setItemAnimator(new DefaultItemAnimator());
        rcListCategories.setLayoutManager(mLayoutManager);
        rcListCategories.setAdapter(categoriesAdapter);
    }

    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        String searchProduct = txtRFind.getText().toString();
        if (searchProduct != null) {
            if ((searchProduct.length() != 0 && !searchProduct.equals(getResources().getString(R.string.msg_find)))) {
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, searchProduct);
            }
        }

        startActivity(intent);

    }

    public void clickToShoppingCart(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}
