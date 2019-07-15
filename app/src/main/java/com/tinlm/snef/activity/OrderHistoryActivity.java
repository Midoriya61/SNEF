package com.tinlm.snef.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tinlm.snef.R;

public class OrderHistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
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
                        intent = new Intent(OrderHistoryActivity.this, CategoryActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_around:
                        intent = new Intent(OrderHistoryActivity.this, AroundStoreActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_orders:
                        intent = new Intent(OrderHistoryActivity.this, OrderActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                    case R.id.action_account:
                        intent = new Intent(OrderHistoryActivity.this, AccountActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });
    }

}
