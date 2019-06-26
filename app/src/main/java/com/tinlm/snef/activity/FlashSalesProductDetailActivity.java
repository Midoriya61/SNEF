package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.tinlm.snef.algo.GeocodingLocation;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.fragment.CustomDialogFragment;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.LocationUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class FlashSalesProductDetailActivity extends AppCompatActivity {

    ElegantNumberButton btnChange;
    Button addToCart;
    TextView foodPriceDiscount,totalPrice, foodName, expiredDate,foodPrice;
    ViewPager imgProductFS;
    CountdownView cv_countdownViewTest1;

    TextView storeName, storeLocation, workingTime;

    BottomNavigationView bottomNavigation;

    protected LocationManager locationManager;
    double longPhone;
    double latPhone;

    double[] locationStore = new double[2];
    double[] locationStoreCurrent = new double[2];

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
        cv_countdownViewTest1 = findViewById(R.id.cv_countdownViewTest1);
        storeName = findViewById(R.id.storeName);
        storeLocation = findViewById(R.id.storeLocation);
        workingTime = findViewById(R.id.workingTime);

        final Intent intent = getIntent();

        int storeId = intent.getIntExtra(ConstainApp.STOREID,0);
        StoreUtilities storeUtilities = new StoreUtilities();
        Store store = storeUtilities.getStoreById(storeId);
        storeName.setText(store.getStoreName());

        LocationUtilities locationUtilities = new LocationUtilities();
        locationUtilities.getAddressOfStoreById(store);

        String address = (store.getAddres() + " " + store.getDistrict() + " " + store.getWard() + " " + store.getDistrict() + " " + store.getCity() + " " + store.getCountry());

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GeocodingLocation.buildAlertMessageNoGps(FlashSalesProductDetailActivity.this);
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, FlashSalesProductDetailActivity.this);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, FlashSalesProductDetailActivity.this);
        }
        String location = GeocodingLocation.getAddressFromLocation(address,
                getApplicationContext());
        String[] rear = location.split("\n");
        locationStore[0] = Double.parseDouble(rear[0]);
        locationStore[1] = Double.parseDouble(rear[1]);
        store.distanceBetween2Points(locationStoreCurrent[0], locationStoreCurrent[1], locationStore[0], locationStore[1]);

        storeLocation.setText((Math.floor(store.getDistance() * 100) / 100) + " km");

        if(store.getOpenHour().equals(store.getClodeHour())) {
            workingTime.setText(R.string.Open24);
        } else {
            workingTime.setText(store.getOpenHour() + "-" + store.getClodeHour());
        }

        final float price = intent.getFloatExtra(ConstainApp.PRICE, 0);
        foodPrice.setText(String.format("%,d", (int)price) + " đ");

        foodPriceDiscount.setText((String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)));

        StoreProductImageUtilities storeProductImageUtilities = new StoreProductImageUtilities();
        List<String> listImage = storeProductImageUtilities.getImageByStoreProductId(intent.getIntExtra(ConstainApp.STOREPRODUCTID,0));
        imgProductFS =  findViewById(R.id.imgProductFS);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(FlashSalesProductDetailActivity.this, listImage);
        imgProductFS.setAdapter(viewPagerAdapter);

        foodName.setText(intent.getStringExtra(ConstainApp.PRODUCTNAME));
        String endDate = intent.getStringExtra(ConstainApp.ENDDATE);
        //expiredDate.setText(endDate);
        try {
            Date currentTime = Calendar.getInstance().getTime();
            long currentMillisecond = currentTime.getTime();
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
            expiredDate.setText(endDate);
            long milliseconds = date.getTime();
            cv_countdownViewTest1.start(milliseconds - currentMillisecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
