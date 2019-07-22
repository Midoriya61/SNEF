package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ViewPagerAdapter;
import com.tinlm.snef.algo.GeocodingLocation;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.LocationUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreProductUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;

public class FlashSalesProductDetailActivity extends AppCompatActivity {

    //ElegantNumberButton btnChange;
    //Button addToCart;
    // totalPrice
    TextView foodPriceDiscount, foodName, expiredDate,foodPrice;
    ViewPager imgProductFS;
    CountdownView cv_countdownViewTest1;
    String imgProduct;
    int fspId;
    float price;
    int discount;
//    int discount;

    TextView storeName, description, textReadMore;
            //, storeLocation, workingTime;
//
//    BottomNavigationView bottomNavigation;
//
//    protected LocationManager locationManager;
//    double longPhone;
//    double latPhone;
//
//    double[] locationStore = new double[2];
//    double[] locationStoreCurrent = new double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_sales_product_detail);
        init();
        //navigateDashboard();

    }

    private void init() {
        //btnChange = findViewById(R.id.btnChange);
        //addToCart = findViewById(R.id.addToCart);
        foodPriceDiscount = findViewById(R.id.foodPriceDiscount);
//        totalPrice = findViewById(R.id.totalPrice);
        foodName = findViewById(R.id.foodName);
        expiredDate = findViewById(R.id.expiredDate);
        foodPrice = findViewById(R.id.foodPrice);
        imgProductFS = findViewById(R.id.imgProductFS);
        cv_countdownViewTest1 = findViewById(R.id.cv_countdownViewTest1);
        storeName = findViewById(R.id.storeName);
        description = findViewById(R.id.txtDescriptionBookInCase);
        textReadMore = findViewById(R.id.textReadMore);
//        storeLocation = findViewById(R.id.storeLocation);
//        workingTime = findViewById(R.id.workingTime);

        final Intent intent = getIntent();
        fspId = intent.getIntExtra(ConstainApp.FLASHSALEPRODUCTID, 0);

        int storeId = intent.getIntExtra(ConstainApp.STOREID,0);
        StoreUtilities storeUtilities = new StoreUtilities();
        Store store = storeUtilities.getStoreById(storeId);
        storeName.setText( store.getStoreName());
        storeName.setPaintFlags(storeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //sstoreLocation.setText((Math.floor(store.getDistance() * 100) / 100) + " km");

//        if(store.getOpenHour().equals(store.getClodeHour())) {
//            workingTime.setText(R.string.Open24);
//        } else {
//            workingTime.setText(store.getOpenHour() + "-" + store.getClodeHour());
//        }

        price = intent.getFloatExtra(ConstainApp.PRICE, 0);
        discount = intent.getIntExtra(ConstainApp.DISCOUNT,0);
        foodPrice.setText(String.format("%,d", (int)price) + " đ");
        foodPriceDiscount.setText((String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)));

        StoreProductUtilities storeProductUtilities = new StoreProductUtilities();
        description.setText(storeProductUtilities.getDesById(intent.getIntExtra(ConstainApp.STOREPRODUCTID,0)).replace(" n ","\n"));
//        description.setMaxLines(1);
        StoreProductImageUtilities storeProductImageUtilities = new StoreProductImageUtilities();
        List<String> listImage = storeProductImageUtilities.getImageByStoreProductId(intent.getIntExtra(ConstainApp.STOREPRODUCTID,0));
        imgProduct = listImage.get(0);
        imgProductFS =  findViewById(R.id.imgProductFS);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(FlashSalesProductDetailActivity.this, listImage);
        imgProductFS.setAdapter(viewPagerAdapter);

        foodName.setText(intent.getStringExtra(ConstainApp.PRODUCTNAME));
        String endDate = intent.getStringExtra(ConstainApp.ENDDATE);
        //expiredDate.setText(endDate);
        try {
            Date currentTime = Calendar.getInstance().getTime();
            long currentMillisecond = currentTime.getTime();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            //expiredDate.setText(endDate);
            long milliseconds = date.getTime();
            cv_countdownViewTest1.start(milliseconds - currentMillisecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        totalPrice = findViewById(R.id.totalPrice);
//        totalPrice.setText(String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100));
        final int quantity = intent.getIntExtra(ConstainApp.QUANTITY, 0);
//        btnChange.setRange(1,quantity);
//
//        btnChange.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                if(newValue > quantity ) {
//                    view.setNumber(String.valueOf(oldValue));
//                    CustomDialogFragment cdf = new CustomDialogFragment();
//                    cdf.show(getSupportFragmentManager(), intent.getStringExtra(ConstainApp.USERNAME));
//                } else {
//                    addToCart.setText("Add " + newValue + " to cart");
//                    totalPrice.setText(String.format("%,d",(intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)* newValue) );
//                }
//            }
//        });
    }



    public void clickToReadMore(View view) {
        textReadMore.setVisibility(View.INVISIBLE);
        description.setSingleLine(false);
    }

//    private void navigateDashboard() {
//        bottomNavigation = findViewById(R.id.bottomNavigation);
//
//        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Intent intent;
//                switch (menuItem.getItemId()) {
//                    case R.id.action_home:
//                        intent = new Intent(FlashSalesProductDetailActivity.this, DashboardActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_category:
//                        intent = new Intent(FlashSalesProductDetailActivity.this, CategoryActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_around:
//                        intent = new Intent(FlashSalesProductDetailActivity.this, AroundStoreActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_orders:
//                        intent = new Intent(FlashSalesProductDetailActivity.this, OrderActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.action_account:
//                        intent = new Intent(FlashSalesProductDetailActivity.this, AccountActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                }
//                return false;
//            }
//        });
//    }


    public void clickAddToCard(View view) {
        DBManager dbManager = new DBManager(FlashSalesProductDetailActivity.this);


        Cart cart = dbManager.getProductById(fspId);
        if(cart == null) {
            cart = new Cart();
            cart.setStoreName(storeName.getText().toString());
            cart.setProductName(foodName.getText().toString());
            cart.setImageProduct(imgProduct);
            cart.setFspId(fspId);
            cart.setPrice(price);
            cart.setDiscount(discount);
            dbManager.addCart(cart);
        } else {
            dbManager.updateCart(cart);
        }
        Toast.makeText(this,R.string.msg_add_to_cart_success, Toast.LENGTH_SHORT).show();



//        cart.setQuantity(Integer.parseInt(btnChange.getNumber()));
//        dbManager.addStudent();
//        JsonObject jo = new JsonObject();
        //jo.addProperty(ConstainApp.JS_STORENAME, storeName.getText().toString());
        //jo.addProperty(ConstainApp.JS_QUANTITY, Integer.parseInt(btnChange.getNumber()));
//        jo.addProperty(ConstainApp.JS_PRODUCTNAME, foodName.getText().toString());
//        jo.addProperty(ConstainApp.JS_IMAGEPRODUCT, imgProduct);
//        jo.addProperty(ConstainApp.JS_FSPID, fspId);
//        jo.addProperty(ConstainApp.JS_PRICE, price);

//        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.LIST_ORDER_DETAIL, MODE_PRIVATE);
//        String json_array = sharedPreferences.getString(ConstainApp.JSARROD, null);
//        try {
//            JSONArray jsonArray = new JSONArray();
//            if( json_array != null )
//                jsonArray = new JSONArray(json_array);
//            jsonArray.put(jo);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(ConstainApp.JSARROD, jsonArray.toString());
//            editor.commit();
//            Toast.makeText(this, "Thêm vào vỏ hàng thành công", Toast.LENGTH_SHORT).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


}
