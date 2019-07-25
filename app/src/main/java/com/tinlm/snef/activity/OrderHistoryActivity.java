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
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.OrderUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    TextView storeName;
    TextView storeAddress;
    TextView storeDistance;
    TextView storeWorkTime;
    ImageView storeAvatar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        init();
        navigateDashboard();
    }

    private void init() {

        intent = getIntent();

        OrderUtilities orderUtilities = new OrderUtilities();
        int orderId = intent.getIntExtra(ConstainApp.JS_ORDERID,0);
        order = orderUtilities.getOrderById(orderId);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String orderDate = dateformat.format(order.getDateOrder());


        txtOrderID = findViewById(R.id.txtOrderID);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderDateTime = findViewById(R.id.txtOrderDateTime);
        txtConfirmCode = findViewById(R.id.txtConfirmCode);

        rcListOrderItem = findViewById(R.id.rcListOrderItem);
        txtTotalOrderPrice = findViewById(R.id.txtTotalOrderPrice);

        storeName = findViewById(R.id.storeName);
        storeAddress = findViewById(R.id.storeAddress);
        storeDistance = findViewById(R.id.storeDistance);
        storeWorkTime = findViewById(R.id.storeWorkTime);
        storeAvatar = findViewById(R.id.storeAvatar);


        txtOrderID.setText(String.valueOf(orderId));
        if (order.isStatus() != true) {
            txtOrderStatus.setText(R.string.orderstatus_paid);
        } else {
            txtOrderStatus.setText(R.string.orderstatus_pickedup);
        }
        txtOrderDateTime.setText(orderDate);
        txtConfirmCode.setText(order.getConfirmationCode());

        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        List<OrderDetail> orderDetailList = orderDetailUtilities.getAllOrderDetailByOrderId(orderId);


        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        FlashSaleProduct fsp = flashSaleProductUtilities.getFSPById(orderDetailList.get(0).getFlashSaleProductId());

        StoreUtilities storeUtilities = new StoreUtilities();
        Store store = storeUtilities.getStoreById(fsp.getStoreId());

        storeName.setText(store.getStoreName());
        String openHour = "";
        if(store.getOpenHour().equals(store.getCloseHour())) {
            openHour = this.getResources().getString(R.string.Open24);

        }else
            openHour = store.getOpenHour() + " - " + store.getCloseHour();

        storeAddress.setText(store.getAddress() + ", " + store.getDistrict() + ", " +
                store.getWard()
                + ", " + store.getCity() + ", " + store.getCountry());
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        Picasso.get().load(store.getAvatar()).resize(0,height / 6).into(storeAvatar);
        storeWorkTime.setText(openHour);
        storeDistance.setText((Math.floor(store.getDistance() * 100) / 100) + " km");

        final String finalOpenHour = openHour;

//        Map<Integer, String> listImageProduct = new HashMap<>();
//        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
//
//        for (int i = 0; i < orderDetailList.size(); i++) {
//
//            String productImage = imageUltilities.getOneImageByStoreProductId(orderDetailList.get(i).getFlashSaleProductId());
//            listImageProduct.put(orderDetailList.get(i).getFlashSaleProductId(), productImage);
//
//        }

        ListOrderHistoryProductAdapter listOrderHistoryProductAdapter = new ListOrderHistoryProductAdapter(OrderHistoryActivity.this, orderDetailList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcListOrderItem.setItemAnimator(new DefaultItemAnimator());
        rcListOrderItem.setLayoutManager(mLayoutManager);
        rcListOrderItem.setAdapter(listOrderHistoryProductAdapter);
        rcListOrderItem.addItemDecoration(new DividerItemDecoration(this, 0));

        for (int i = 0; i < orderDetailList.size(); i++) {

            totalAmount += orderDetailList.get(i).getOrderDetailPrice();
        }
        txtTotalOrderPrice.setText(String.valueOf(df.format(totalAmount)));

        String storeName = intent.getStringExtra(ConstainApp.JS_STORENAME);
        DBManager dbManager = new DBManager(OrderHistoryActivity.this);
        List<Cart> cartList = dbManager.getAllCartByStoreName(storeName);
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