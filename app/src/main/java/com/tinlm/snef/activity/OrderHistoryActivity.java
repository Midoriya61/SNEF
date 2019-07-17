package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListCartAdapter;
import com.tinlm.snef.adapter.ListOrderHistoryProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    Intent intent;
    Context mContext;
    Order order;
    int totalAmount = 0;
    DecimalFormat df = new DecimalFormat("#,###,###,###");


    TextView txtOrderID;
    TextView txtOrderStatus;
    TextView txtOrderDateTime;
    TextView txtConfirmCode;
    RecyclerView rcListOrderItem;
    TextView txtTotalOrderPrice;
//    TextView storeName;
//    TextView storeAddress;
//    TextView storeDistance;
//    TextView storeWorkTime;
//    ImageView storeAvatar;
//    Store store;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        init();
        navigateDashboard();
    }

    private void init() {

        intent = getIntent();
        DBManager dbManager = new DBManager(OrderHistoryActivity.this);
        order = dbManager.getOrderbyId(intent.getIntExtra(ConstainApp.JS_ORDERID, 0));

        intent = getIntent();
        DBManager dbManager2 = new DBManager(OrderHistoryActivity.this);
        List<Cart> cartList = dbManager2.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));

        txtOrderID = findViewById(R.id.txtOrderID);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderDateTime = findViewById(R.id.txtOrderDateTime);
        txtConfirmCode = findViewById(R.id.txtConfirmCode);

        rcListOrderItem = findViewById(R.id.rcListOrderItem);
        txtTotalOrderPrice = findViewById(R.id.txtTotalOrderPrice);
//        storeName.findViewById(R.id.storeName);
//        storeAddress.findViewById(R.id.storeAddress);
//        storeDistance.findViewById(R.id.storeDistance);
//        storeWorkTime.findViewById(R.id.storeWorkTime);
//        storeAvatar.findViewById(R.id.storeAvatar);


        txtOrderID.setText(String.valueOf(order.getOrderId()));
        if (order.isStatus() != true) {
            String orderStatus = "Đã Thanh Toán";
            txtOrderStatus.setText(orderStatus.toString());
        } else {
            String orderStatus = "Đã Nhận Hàng";
            txtOrderStatus.setText(orderStatus.toString());
        }
        txtOrderDateTime.setText(order.getDateOrder().toString());
        txtConfirmCode.setText(order.getConfirmationCode());

        Map<Integer, String> listImageProduct = new HashMap<>();
//        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();

        for (int i = 0; i < cartList.size(); i++) {

            String productImage = imageUltilities.getOneImageByStoreProductId(cartList.get(i).getFspId());
            listImageProduct.put(cartList.get(i).getFspId(), productImage);

        }
        ListOrderHistoryProductAdapter listOrderHistoryProductAdapter = new ListOrderHistoryProductAdapter(OrderHistoryActivity.this, cartList, listImageProduct);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcListOrderItem.setItemAnimator(new DefaultItemAnimator());
        rcListOrderItem.setLayoutManager(mLayoutManager);
        rcListOrderItem.setAdapter(listOrderHistoryProductAdapter);
        rcListOrderItem.addItemDecoration(new DividerItemDecoration(this, 0));

        for (int i = 0; i < cartList.size(); i++) {

            totalAmount += (((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100) * cartList.get(i).getQuantity());
        }
        txtTotalOrderPrice.setText(String.valueOf(df.format(totalAmount)));

        for (int a = 0; a < cartList.size(); a++) {
            dbManager.deleteCart(cartList.get(a));
        }


//        store = dbManager.getStoreByName(intent.getStringExtra(ConstainApp.JS_STORENAME));

//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        txtOrderDateTime.setText(currentDateTimeString);

//        storeName.setText(store.getStoreName());
//        storeAddress.setText(store.getAddress());

//        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int height = size.y;
//        Picasso.get().load(store.getAvatar()).resize(0, height / 6).into(storeAvatar);

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
                        intent = new Intent(OrderHistoryActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(OrderHistoryActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        intent = new Intent(OrderHistoryActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:

                        break;
                    case R.id.action_account:
                        intent = new Intent(OrderHistoryActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}