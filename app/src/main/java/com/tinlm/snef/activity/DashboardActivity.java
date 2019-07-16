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
import com.tinlm.snef.constain.ConstainApp;

// 6/23/2019 TinLM Create class
// 6/23/2019 TinLM Create init
// 6/23/2019 TinLM Create createListCategories
// 6/23/2019 TinLM Create createListHostProduct
// 6/23/2019 TinLM Create createListFSP
public class DashboardActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;
    TextView txtRFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtRFind = findViewById(R.id.txtRFind);
        Intent dbIntent = getIntent();
        String searchString = dbIntent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME);
        if(searchString != null ) {
            if((searchString.length() != 0 && !searchString.equals(getResources().getString(R.string.msg_find)))) {
                txtRFind.setText(searchString);
            }
        }

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


    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        String searchProduct = txtRFind.getText().toString();
        if(searchProduct != null) {
            if(  (searchProduct.length() != 0 && !searchProduct.equals(getResources().getString(R.string.msg_find))) ) {
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, searchProduct);
            }
        }

        startActivity(intent);

    }
}
