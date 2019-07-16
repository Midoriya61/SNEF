package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListCartAdapter;
import com.tinlm.snef.adapter.ListCartPaymentConfirmAdapter;
import com.tinlm.snef.adapter.ListStoreOrderItemAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OrderHistoryActivity extends AppCompatActivity {

    Intent intent;
    Context mContext;

    DecimalFormat df = new DecimalFormat("#,###,###,###");
    RecyclerView rcListCartPaymentConfirm;
    TextView txtTotalPaymentConfirm;
    TextView tvStoreName;
    TextView txtOrderStatus;
    TextView txtOrderID;
    TextView txtConfirmCode;
    int confirmCode;
    int orderID = 53;
    int totalAmount;
    TextView txtOrderTime;
    TextView txtOrderDate;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        navigateDashboard();
        init();
    }

    private void init() {
        intent = getIntent();

        DBManager dbManager = new DBManager(OrderHistoryActivity.this);
        List<Cart> cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));

        if (cartList.size() == 0) {
            this.finish();
        } else createListCart();
    }

    private void createListCart() {
        rcListCartPaymentConfirm = findViewById(R.id.rcListCartPaymentConfirm);
        txtTotalPaymentConfirm = findViewById(R.id.txtTotalPaymentConfirm);
        tvStoreName = findViewById(R.id.tvStoreName);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderID = findViewById(R.id.txtOrderStatus);
        txtConfirmCode = findViewById(R.id.txtConfirmCode);
        txtOrderDate = findViewById(R.id.txtOrderDate);

        Map<Integer, String> listImageProduct = new HashMap<>();
//        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();


        DBManager dbManager = new DBManager(OrderHistoryActivity.this);
        List<Cart> cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));
        for (int i = 0; i < cartList.size(); i++) {

            String productImage = imageUltilities.getOneImageByStoreProductId(cartList.get(i).getFspId());
            listImageProduct.put(cartList.get(i).getFspId(), productImage);

        }

        ListCartAdapter listCartAdapter = new ListCartAdapter(OrderHistoryActivity.this, cartList, listImageProduct);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcListCartPaymentConfirm.setItemAnimator(new DefaultItemAnimator());
        rcListCartPaymentConfirm.setLayoutManager(mLayoutManager);
        rcListCartPaymentConfirm.setAdapter(listCartAdapter);
        rcListCartPaymentConfirm.addItemDecoration(new DividerItemDecoration(this, 0));

        for (int i = 0; i < cartList.size(); i++) {

            totalAmount += (((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100) * cartList.get(i).getQuantity());
        }
        txtTotalPaymentConfirm.setText(String.valueOf(df.format(totalAmount)));
        tvStoreName.setText(String.valueOf(intent.getStringExtra(ConstainApp.JS_STORENAME)));
        txtOrderStatus.setText(String.valueOf("Chờ Lấy Hàng"));
        orderID = orderID + 1;
        txtOrderID.setText(String.valueOf(orderID));
        final int min = 100000000;
        final int max = 999999999;
        confirmCode = new Random().nextInt((max - min) + 1) + min;
        txtConfirmCode.setText(String.valueOf(confirmCode));

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        txtOrderDate.setText(currentDateTimeString);

        for (int i = 0; i < cartList.size(); i++) {
            dbManager.deleteCart(cartList.get(i));
        }

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
