package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ViewPagerAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.fragment.CustomDialogFragment;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.List;

public class FlashSalesProductDetailActivity extends AppCompatActivity {

    ElegantNumberButton btnChange;
    Button addToCart;
    TextView foodPriceDiscount,totalPrice, foodName, expiredDate,foodPrice;
    ViewPager imgProductFS;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_sales_product_detail);
        init();
        navigateDashboard();
    }

    private void init() {
        btnChange = findViewById(R.id.btnChange);
        addToCart = findViewById(R.id.addToCart);
        foodPriceDiscount = findViewById(R.id.foodPriceDiscount);
        totalPrice = findViewById(R.id.totalPrice);
        foodName = findViewById(R.id.foodName);
        expiredDate = findViewById(R.id.expiredDate);
        foodPrice = findViewById(R.id.foodPrice);
        imgProductFS = findViewById(R.id.imgProductFS);

        final Intent intent = getIntent();

        final float price = intent.getFloatExtra(ConstainApp.PRICE, 0);
        foodPrice.setText(String.format("%,d", (int)price) + " đ");

        foodPriceDiscount.setText((String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)));

        StoreProductImageUtilities storeProductImageUtilities = new StoreProductImageUtilities();
        List<String> listImage = storeProductImageUtilities.getImageByStoreProductId(intent.getIntExtra(ConstainApp.STOREPRODUCTID,0));
        imgProductFS =  findViewById(R.id.imgProductFS);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(FlashSalesProductDetailActivity.this, listImage);
        imgProductFS.setAdapter(viewPagerAdapter);

        foodName.setText(intent.getStringExtra(ConstainApp.PRODUCTNAME));
        String endda = intent.getStringExtra(ConstainApp.ENDDATE);
        expiredDate.setText("Expired on " + intent.getStringExtra(ConstainApp.ENDDATE));

        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText(String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100));

        final int quantity = intent.getIntExtra(ConstainApp.QUANTITY, 0);
        btnChange.setRange(1,quantity);
        btnChange.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if(newValue > quantity ) {
                    view.setNumber(String.valueOf(oldValue));
                    CustomDialogFragment cdf = new CustomDialogFragment();
                    cdf.show(getSupportFragmentManager(), intent.getStringExtra(ConstainApp.USERNAME));
                } else {
                    addToCart.setText("Add " + newValue + " to cart");
                    totalPrice.setText(String.format("%,d",(intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)* newValue) );
                }
            }
        });

    }

    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.action_category);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(FlashSalesProductDetailActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(FlashSalesProductDetailActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        intent = new Intent(FlashSalesProductDetailActivity.this, AroundStoreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_orders:
                        intent = new Intent(FlashSalesProductDetailActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_account:
                        intent = new Intent(FlashSalesProductDetailActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
