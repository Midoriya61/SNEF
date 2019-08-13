package com.tinlm.snef.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.OrderUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreUtilities;


public class PayPalActivity extends AppCompatActivity {
    TextView m_response;
    Intent intent;
    int totalAmount = 0;
    List<Cart> cartList;
    List<Order> orderList;
    Order order;
    Context mContext;

    PayPalConfiguration m_configuration;
    //the id is in the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = "AbwZsHD8qlcsvJ6hxNBecdvJ1pt1bZgz5yUvCVoWo0jmEYkbYQRRJSv2da-g-ze9reoQhmJ8nwlPkdFr";
    Intent m_service;
    int m_paypalRequestCode = 111; //any number

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
//        this.onBackPressed();

        m_response = findViewById(R.id.response);

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test, production for real
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); //configuration above
        startService(m_service); //paypal service listening to calls to paypal app

        intent = getIntent();

        DBManager dbManager = new DBManager(PayPalActivity.this);
        cartList = dbManager.getAllCartByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));

        for (int i = 0; i < cartList.size(); i++) {

            totalAmount += (((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100) * cartList.get(i).getQuantity());
        }

        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalAmount), "USD",
                "Test Pay with Paypal", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, m_paypalRequestCode);

    }

    void pay(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == m_paypalRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                //avoid fraud
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null) {
                    String state = confirmation.getProofOfPayment().getState();

                    if (state.equals("approved")) //if the payment worked, the state equals approved
                    {
//                        super.onBackPressed();

                        DBManager dbManager = new DBManager(PayPalActivity.this);
                        cartList = dbManager.getAllCartByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));

                        //Get current customer ID
                        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
                        int accountId = sharedPreferences.getInt(ConstainApp.ACCOUNTID,0);

                        //Get random confirmation code
                        int min = 100000000;
                        int max = 999999999;
                        Random r = new Random();
                        String confirmationCode = String.valueOf(r.nextInt(max - min + 1) + min);

                        //Get StoreId
                        int storeId = cartList.get(0).getStoreId();

                        //Create a new order
                        OrderUtilities orderUtilities = new OrderUtilities();
                        orderUtilities.insertNewOrder(confirmationCode, accountId, storeId);
                        int lastOrderId = orderUtilities.getLastOrderId();



                        //Insert order detail into the created order
                        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
                        for (int i = 0; i < cartList.size(); i++) {
                            orderDetailUtilities.insertNewOrderDetail(
                                    lastOrderId,
                                    cartList.get(i).getFspId(),
                                    cartList.get(i).getQuantity(),
                                    cartList.get(i).getQuantity() *
                                            ((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100));
                        }

                        //Send user to Order History screen
                        Intent intent = new Intent(this, OrderHistoryActivity.class);
                        intent.putExtra(ConstainApp.JS_ORDERID, lastOrderId);
                        intent.putExtra(ConstainApp.JS_STORENAME, cartList.get(0).getStoreName());
                        this.startActivity(intent);
                        this.finish();

                    } else m_response.setText("Error in the payment.");
                } else
                    m_response.setText("Confirmation is null");
            }
        }
    }
}
