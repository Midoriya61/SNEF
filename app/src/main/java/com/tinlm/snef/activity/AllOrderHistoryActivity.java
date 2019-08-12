package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListOrderHistoryAdapter;
import com.tinlm.snef.adapter.ListStoreOrderItemAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.StoreOrderItem;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.OrderUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllOrderHistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    RecyclerView rcOrderHistory;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorderhistory);
        init();
        navigateDashboard();
    }

    public void init() {

        rcOrderHistory = findViewById(R.id.rcOrderHistory);

        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
        int accountId = sharedPreferences.getInt(ConstainApp.ACCOUNTID,0);

        OrderUtilities orderUtilities = new OrderUtilities();
        orderList = orderUtilities.getOrderByAccountId(accountId);

        ListOrderHistoryAdapter orderHistoryAdapter = new ListOrderHistoryAdapter(
                AllOrderHistoryActivity.this, orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AllOrderHistoryActivity.this,
                LinearLayoutManager.VERTICAL,true);
        rcOrderHistory.setItemAnimator(new DefaultItemAnimator());
        rcOrderHistory.setLayoutManager(mLayoutManager);
        rcOrderHistory.setAdapter(orderHistoryAdapter);
        rcOrderHistory.addItemDecoration(new DividerItemDecoration(this, 0));

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
                        intent = new Intent(AllOrderHistoryActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(AllOrderHistoryActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        intent = new Intent(AllOrderHistoryActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:

                        break;
                    case R.id.action_account:
                        intent = new Intent(AllOrderHistoryActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }


}
