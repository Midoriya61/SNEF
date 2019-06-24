package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tinlm.snef.R;

public class OrderActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        navigateDashboard();
    }

    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.action_orders);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(OrderActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(OrderActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        intent = new Intent(OrderActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:

                        break;
                    case R.id.action_account:
                        intent = new Intent(OrderActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }


}
