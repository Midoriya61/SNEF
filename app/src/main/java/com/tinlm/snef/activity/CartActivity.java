package com.tinlm.snef.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListCartAdapter;
import com.tinlm.snef.adapter.ListStoreAdapter;
import com.tinlm.snef.adapter.ListStoreOrderItemAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.fragment.CustomDialogFragment;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.model.StoreOrderItem;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.math.BigDecimal;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    DecimalFormat df = new DecimalFormat("#,###,###,###");
    RecyclerView rcListCartItem;
    TextView txtTotalCartPrice;
    ElegantNumberButton btnCartQuantity;
    Button btnCheckout;
    int totalAmount = 0;
    int fspId;
    Intent intent;
    Context mContext;


    //PayPal
    PayPalConfiguration m_configuration;
    //the id is in the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = "AbwZsHD8qlcsvJ6hxNBecdvJ1pt1bZgz5yUvCVoWo0jmEYkbYQRRJSv2da-g-ze9reoQhmJ8nwlPkdFr";
    Intent m_service;
    int m_paypalRequestCode = 111; //any number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartdetail);
        init();
    }

    private void init() {

        intent = getIntent();

        DBManager dbManager = new DBManager(CartActivity.this);
        List<Cart> cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));

        if (cartList.size() == 0) {
            this.finish();
        } else createListCart();

        //PayPal
        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test, production for real
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); //configuration above
        startService(m_service); //paypal service listening to calls to paypal app

    }

    // Create list flash sale product
    public void createListCart() {
        rcListCartItem = findViewById(R.id.rcListCartItem);
        txtTotalCartPrice = findViewById(R.id.txtTotalCartPrice);
        btnCartQuantity = findViewById(R.id.btnCartQuantity);

        Map<Integer, String> listImageProduct = new HashMap<>();
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();


        DBManager dbManager = new DBManager(CartActivity.this);
        List<Cart> cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));
        for (int i = 0; i < cartList.size(); i++) {

            String productImage = imageUltilities.getOneImageByStoreProductId(cartList.get(i).getFspId());
            listImageProduct.put(cartList.get(i).getFspId(), productImage);

        }

        ListCartAdapter listCartAdapter = new ListCartAdapter(this, cartList, listImageProduct);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rcListCartItem.setItemAnimator(new DefaultItemAnimator());
        rcListCartItem.setLayoutManager(mLayoutManager);
        rcListCartItem.setAdapter(listCartAdapter);

        for (int i = 0; i < cartList.size(); i++) {

            totalAmount += (((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100) * cartList.get(i).getQuantity());
        }
        txtTotalCartPrice.setText(String.valueOf(df.format(totalAmount)));
    }


    public void clickToPay(View view) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalAmount), "USD",
                "Test Pay with Paypal", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, m_paypalRequestCode);
    }

    public void AddToOrder() {

    }

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
                        txtTotalCartPrice.setText("Success");
                        DBManager dbManager = new DBManager(CartActivity.this);
                        List<Cart> cartList = dbManager.getProductByStoreName(intent.getStringExtra(ConstainApp.JS_STORENAME));
                        for (int i = 0; i < cartList.size(); i++) {

                            Cart cart = dbManager.getProductById(cartList.get(i).getFspId());
                            OrderDetail orderDetail = new OrderDetail();
                            List<OrderDetail> ORList = dbManager.getAllOrderDetail();
                            ContentValues values = new ContentValues();

                            orderDetail.setOrderDetailId((ORList.get(ORList.size()-1).getOrderDetailId()) + 1);
                            orderDetail.setOrderId((ORList.get(ORList.size()-1).getOrderId())+1);
                            orderDetail.setFspId(cart.getFspId());
                            orderDetail.setQuantity(cart.getQuantity());
                            orderDetail.setOrderDetailPrice(totalAmount);
                            dbManager.addOrderDetail(orderDetail);

                        }

                        dbManager.getAllOrderDetail();

                    } else txtTotalCartPrice.setText("Error in the payment.");
                } else
                    txtTotalCartPrice.setText("Confirmation is null");
            }
        }
    }
}