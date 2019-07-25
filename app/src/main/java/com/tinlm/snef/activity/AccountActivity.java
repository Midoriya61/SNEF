package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;

public class AccountActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private TextView txtAccountName,txtEditProfile,tvOrderHistory;
    private SharedPreferences sharedPreferences;



    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        intit();
        navigateDashboard();
    }
    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.action_account);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(AccountActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(AccountActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        intent = new Intent(AccountActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:
                        intent = new Intent(AccountActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_account:
                        break;
                }
                return false;
            }
        });
    }

    private void intit() {
        sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
        imgAvatar = findViewById(R.id.imgAvatar);
        Picasso.get().load(sharedPreferences.getString(ConstainApp.AVATAR, null)).resize(300,350).into(imgAvatar);

        txtAccountName = findViewById(R.id.txtAccountName);
        txtAccountName.setText(sharedPreferences.getString(ConstainApp.LASTNAME, null) + " " + sharedPreferences.getString(ConstainApp.FIRSTNAME, null));
    }

    public void clickToShowAllOrderHistory(View view) {
        Intent intent = new Intent(this, AllOrderHistoryActivity.class);
        startActivity(intent);
    }
}
