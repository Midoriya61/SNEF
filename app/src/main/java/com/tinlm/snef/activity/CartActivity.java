package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListCartAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.fragment.CustomDialogFragment;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartdetail);
        init();
    }

    private void init() {

        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PayPalActivity.class);
                startActivity(intent);
                finish();


            }
        });

        createListCart();
    }

    // Create list flash sale product
    private void createListCart() {
        rcListCartItem = findViewById(R.id.rcListCartItem);
        txtTotalCartPrice = findViewById(R.id.txtTotalCartPrice);
        btnCartQuantity = findViewById(R.id.btnCartQuantity);

        Map<Integer, String> listImageProduct = new HashMap<>();
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();


        DBManager dbManager = new DBManager(CartActivity.this);
        List<Cart> cartList = dbManager.getAllCart();

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


        int total = 0;
        for (int i = 0; i < cartList.size(); i++) {

            total += (((cartList.get(i).getPrice() * cartList.get(i).getDiscount()) / 100) * cartList.get(i).getQuantity());
        }

        txtTotalCartPrice.setText(String.valueOf(df.format(total)));




    }

//    public void C

}