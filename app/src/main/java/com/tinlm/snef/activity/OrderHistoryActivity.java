package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    DecimalFormat df = new DecimalFormat("#,###,###,###");


    TextView txtOrderID;
    TextView txtOrderStatus;
    TextView txtOrderDateTime;
    TextView txtConfirmCode;
    RecyclerView rcListOrderItem;
    TextView txtTotalOrderPrice;
    TextView storeName;
    TextView storeAddress;

    RatingBar ratingBar;
    Button btnSubmitRating;
    TextView txtRating;
    EditText editTextComment;
    CardView cvComment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        init();
        navigateDashboard();
    }

    private void init() {

        intent = getIntent();

        txtOrderID = findViewById(R.id.txtOrderID);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderDateTime = findViewById(R.id.txtOrderDateTime);
        txtConfirmCode = findViewById(R.id.txtConfirmCode);
        rcListOrderItem = findViewById(R.id.rcListOrderItem);
        txtTotalOrderPrice = findViewById(R.id.txtTotalOrderPrice);
        storeName = findViewById(R.id.storeName);
        storeAddress = findViewById(R.id.storeAddress);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);
        txtRating = findViewById(R.id.txtRating);
        editTextComment = findViewById(R.id.editTextComment);
        cvComment = findViewById(R.id.cvComment);

        final OrderUtilities orderUtilities = new OrderUtilities();
        final int orderId = intent.getIntExtra(ConstainApp.JS_ORDERID, 0);
        order = orderUtilities.getOrderById(orderId);

        //Set text for top section
        txtOrderID.setText(String.valueOf(orderId));

        if (order.isStatus() != true) {
            txtOrderStatus.setText(R.string.orderstatus_paid);
        } else {
            txtOrderStatus.setText(R.string.orderstatus_pickedup);
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String orderDate = dateformat.format(order.getDateOrder());
        txtOrderDateTime.setText(orderDate);

        txtConfirmCode.setText(order.getConfirmationCode());

        //Set recycler view for order detail
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        List<OrderDetail> orderDetailList = orderDetailUtilities.getAllOrderDetailByOrderId(orderId);

        ListOrderHistoryProductAdapter listOrderHistoryProductAdapter = new ListOrderHistoryProductAdapter(OrderHistoryActivity.this, orderDetailList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcListOrderItem.setItemAnimator(new DefaultItemAnimator());
        rcListOrderItem.setLayoutManager(mLayoutManager);
        rcListOrderItem.setAdapter(listOrderHistoryProductAdapter);
        rcListOrderItem.addItemDecoration(new DividerItemDecoration(this, 0));

        txtTotalOrderPrice.setText(String.valueOf(df.format(order.getTotalPrice())));

        //Set text for store info
        StoreUtilities storeUtilities = new StoreUtilities();
        Store store = storeUtilities.getStoreById(order.getStoreId());

        storeName.setText(store.getStoreName());
        storeAddress.setText(store.getAddress());

        //Order created, then delete shopping cart
        String storeName = intent.getStringExtra(ConstainApp.JS_STORENAME);
        DBManager dbManager = new DBManager(OrderHistoryActivity.this);
        List<Cart> cartList = dbManager.getAllCartByStoreName(storeName);
        for (int a = 0; a < cartList.size(); a++) {
            dbManager.deleteCart(cartList.get(a));
        }

        //Submit Feedback

        if (order.getRatingPoint() == 0) {
            btnSubmitRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ratingBar.getRating() == 0) {
                        txtRating.setText(R.string.msg_askrating);
                    } else {
                        String comment = String.valueOf(editTextComment.getText());
                        if (comment.equals("")) {
                            orderUtilities.submitFeedback(orderId, ratingBar.getRating(), "null");
                        } else {
                            orderUtilities.submitFeedback(orderId, ratingBar.getRating(), editTextComment.getText().toString());
                        }
                        ratingBar.setEnabled(false);
                        btnSubmitRating.setVisibility(View.INVISIBLE);
                        cvComment.setVisibility(View.INVISIBLE);
                        txtRating.setText(R.string.msg_thanksrating);
                    }

                }
            });
        } else {
            ratingBar.setRating(order.getRatingPoint());
            ratingBar.setEnabled(false);
            btnSubmitRating.setVisibility(View.GONE);
            cvComment.setVisibility(View.GONE);
            txtRating.setText(R.string.msg_thanksrating);
        }


//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if(btnSubmitRating.isc)
//
//            }
//        });
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