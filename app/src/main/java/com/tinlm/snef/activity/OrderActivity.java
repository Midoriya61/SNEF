package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListStoreOrderItemAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.StoreOrderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    RecyclerView rcOrderStore;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        navigateDashboard();
    }

    private void init() {

        intent = getIntent();

        rcOrderStore = findViewById(R.id.rcOrderStore);

        DBManager dbManager = new DBManager(OrderActivity.this);
        List<StoreOrderItem> storeOrderItems = dbManager.getAllCartGroupStore();


//        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.LIST_ORDER_DETAIL, MODE_PRIVATE);
//        String json_array = sharedPreferences.getString(ConstainApp.JSARROD, null);
//        json_array = json_array.replace("\\","");
//        json_array = json_array.replace("[\"{","[{");
//        json_array = json_array.replace("}\"]","}]");
//        List<OrderDetail> orderDetailList = new ArrayList<>();
//        List<StoreOrderItem> storeOrderItems = new ArrayList<>();
//        try {
//            JSONArray jsonArray = new JSONArray(json_array);
//            for (int i = 0; i < jsonArray.length(); i++)
//            {
//                JSONObject jsonObj = jsonArray.getJSONObject(i);
//                OrderDetail orderDetail = new OrderDetail();
//
//                if(jsonObj.has(ConstainApp.JS_STORENAME)){
//                    orderDetail.setStoreName(jsonObj.getString(ConstainApp.JS_STORENAME));
//                }
//                if(jsonObj.has(ConstainApp.JS_QUANTITY)){
//                    orderDetail.setQuantity(jsonObj.getInt(ConstainApp.JS_QUANTITY));
//                }
//                if(jsonObj.has(ConstainApp.JS_PRODUCTNAME)){
//                    orderDetail.setProductName(jsonObj.getString(ConstainApp.JS_PRODUCTNAME));
//                }
//                if(jsonObj.has(ConstainApp.JS_IMAGEPRODUCT)){
//                    orderDetail.setSetImageProduct(jsonObj.getString(ConstainApp.JS_IMAGEPRODUCT));
//                }
//                if(jsonObj.has(ConstainApp.JS_FSPID)){
//                    orderDetail.setFlashSaleProductId(jsonObj.getInt(ConstainApp.JS_FSPID));
//                }
//                if(jsonObj.has(ConstainApp.JS_PRICE)){
//                    orderDetail.setPrice((float)jsonObj.getDouble(ConstainApp.JS_PRICE));
//                }
//                orderDetailList.add(orderDetail);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        List<OrderDetail> listOrderDetailV2 = orderDetailList;
//
//        for( int i = 0; i < listOrderDetailV2.size() - 1; i++) {
//            if (listOrderDetailV2.get(i).getStoreName() != null) {
//                for (int j = i + 1; j < listOrderDetailV2.size(); j++) {
//                    if (listOrderDetailV2.get(i).getStoreName().equals(listOrderDetailV2.get(j).getStoreName())) {
//                        if (storeOrderItems.size() == 0) {
//                            StoreOrderItem storeOrderItem = new StoreOrderItem();
//                            storeOrderItem.setStoreName(listOrderDetailV2.get(i).getStoreName());
//                            storeOrderItem.setQuantityOrder(1);
//                            storeOrderItems.add(storeOrderItem);
//                        } else {
//                            for (int s = 0; s < storeOrderItems.size(); i++) {
//                                if (storeOrderItems.get(s).getStoreName().equals(listOrderDetailV2.get(i).getStoreName())) {
//                                    storeOrderItems.get(s).setQuantityOrder(storeOrderItems.get(s).getQuantityOrder() + 1);
//                                    listOrderDetailV2.get(j).setProductName(null);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        ListStoreOrderItemAdapter storeOrderItemAdapter = new ListStoreOrderItemAdapter(OrderActivity.this,storeOrderItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcOrderStore.setItemAnimator(new DefaultItemAnimator());
        rcOrderStore.setLayoutManager(mLayoutManager);
        rcOrderStore.setAdapter(storeOrderItemAdapter);
        rcOrderStore.addItemDecoration(new DividerItemDecoration(this, 0));

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
