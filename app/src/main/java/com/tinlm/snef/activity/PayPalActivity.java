package com.tinlm.snef.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import okhttp3.OkHttpClient;

import com.paypal.android.sdk.payments.*;

import java.math.BigDecimal;
import java.util.List;

import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;


public class PayPalActivity extends AppCompatActivity {
    TextView m_response;
    Intent intent;
    int totalAmount = 0;
    List<Cart> cartList;

    PayPalConfiguration m_configuration;
    //the id is in the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = "AbwZsHD8qlcsvJ6hxNBecdvJ1pt1bZgz5yUvCVoWo0jmEYkbYQRRJSv2da-g-ze9reoQhmJ8nwlPkdFr";
    Intent m_service;
    int m_paypalRequestCode = 111; //any number

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);


        m_response = findViewById(R.id.response);

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test, production for real
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); //configuration above
        startService(m_service); //paypal service listening to calls to paypal app

        intent = getIntent();

        DBManager dbManager = new DBManager(PayPalActivity.this);
        cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));
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
                        Intent intent = new Intent(this, OrderHistoryActivity.class);
                        intent.putExtra(ConstainApp.JS_STORENAME, cartList.get(0).getStoreName());
                        this.startActivity(intent);

                    } else m_response.setText("Error in the payment.");
                } else
                    m_response.setText("Confirmation is null");
            }
        }
    }
}
