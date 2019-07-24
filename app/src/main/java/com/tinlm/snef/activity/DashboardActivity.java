package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;

import java.util.List;

// 6/23/2019 TinLM Create class
// 6/23/2019 TinLM Create init
// 6/23/2019 TinLM Create createListCategories
// 6/23/2019 TinLM Create createListHostProduct
// 6/23/2019 TinLM Create createListFSP
public class DashboardActivity extends AppCompatActivity {

    //    RecyclerView rcListCategories;
//    RecyclerView rcListHost;
    RecyclerView rcListFlashSaleProduct;

    BottomNavigationView bottomNavigation;
    public TextView txtCartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigateDashboard();
        txtCartNumber = findViewById(R.id.txtCartNumber);
        txtCartNumber.setText(String.valueOf(getCartNumber()));
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        txtCartNumber = findViewById(R.id.txtCartNumber);
        txtCartNumber.setText(String.valueOf(getCartNumber()));
    }

    public int getCartNumber() {
        DBManager dbManager = new DBManager(DashboardActivity.this);
        List<Cart> cartList = dbManager.getAllCart();
        int cartNumber = 0;
        for (int i = 0; i < cartList.size(); i++) {
            cartNumber += cartList.get(i).getQuantity();
        }
        return cartNumber;
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


    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    public void clickToShoppingCart(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}
