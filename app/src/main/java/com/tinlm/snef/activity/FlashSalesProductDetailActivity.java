package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ViewPagerAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.service.StoreProductImageService;
import com.tinlm.snef.service.StoreService;
import com.tinlm.snef.utilities.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;

public class FlashSalesProductDetailActivity extends AppCompatActivity {

    //ElegantNumberButton btnChange;
    //Button addToCart;
    // totalPrice
    private TextView foodPriceDiscount, foodName, expiredDate, foodPrice;
    private ViewPager imgProductFS;
    private CountdownView cv_countdownViewTest1;
    private String imgProduct;
    private int fspId;
    private float price;
    private int discount;
    private TextView btnAddToCart;
    private RelativeLayout llAddToCard;

//    int discount;

    private TextView storeName;
    private ReadMoreTextView description;
    private Store store;
    private TextView txtCartNumber;
    TextView txtCartNumberDB;
    Context mContext;
    private TextView txtRFind;

    private String endDate;
    private int storeId;
    private int totalQuanitity;
    private int quantity;
//    , textReadMore;
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

        txtRFind = findViewById(R.id.txtRFind);
        Intent dbIntent = getIntent();
        String searchString = dbIntent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME);
        if (searchString != null) {
            if ((searchString.length() != 0 && !searchString.equals(getResources().getString(R.string.msg_find)))) {
                txtRFind.setText(searchString);
            }
        }

        init();
        //navigateDashboard();

    }

    @Override
    public void onResume(){
        super.onResume();
        txtCartNumber.setText(String.valueOf(getCartNumber()));
    }


    private void init() {
        //btnChange = findViewById(R.id.btnChange);
        //addToCart = findViewById(R.id.addToCart);

        txtCartNumber = findViewById(R.id.txtCartNumber);
        txtCartNumber.setText(String.valueOf(getCartNumber()));

        foodPriceDiscount = findViewById(R.id.foodPriceDiscount);
//        totalPrice = findViewById(R.id.totalPrice);
        foodName = findViewById(R.id.foodName);
        expiredDate = findViewById(R.id.expiredDate);
        foodPrice = findViewById(R.id.foodPrice);
        imgProductFS = findViewById(R.id.imgProductFS);
        cv_countdownViewTest1 = findViewById(R.id.cv_countdownViewTest1);
        storeName = findViewById(R.id.storeName);
        llAddToCard = findViewById(R.id.llAddToCard);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        description = findViewById(R.id.txtDescriptionBookInCase);
//        textReadMore = findViewById(R.id.textReadMore);
//        storeLocation = findViewById(R.id.storeLocation);
//        workingTime = findViewById(R.id.workingTime);

        final Intent intent = getIntent();
        fspId = intent.getIntExtra(ConstainApp.FLASHSALEPRODUCTID, 0);

        storeId = intent.getIntExtra(ConstainApp.STOREID, 0);
        StoreService storeService = ApiUtils.getStoreService();
        storeService.getStoreById(storeId).enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                store = response.body();
                storeName.setText(store.getStoreName());
                storeName.setPaintFlags(storeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                storeName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentStore = new Intent(FlashSalesProductDetailActivity.this, StoreActivity.class);
                        intentStore.putExtra(ConstainApp.JS_STORENAME, store.getStoreName());
                        intentStore.putExtra(ConstainApp.STOREAVATAR, store.getAvatar());
                        String address = store.getAddress();
                        intentStore.putExtra(ConstainApp.ADDRESS, address);
                        float ratingPoint = (float)(Math.floor(store.getRatingPoint() * 100) / 100);
                        intentStore.putExtra(ConstainApp.RATINGPOINT, ratingPoint);
                        intentStore.putExtra(ConstainApp.STOREID, store.getStoreId());
                        intentStore.putExtra(ConstainApp.LATITUDE, store.getLatitude());
                        intentStore.putExtra(ConstainApp.LONGITUDE, store.getLocationId());
                        intentStore.putExtra(ConstainApp.STOREPHONE, store.getPhone());

                        String finalOpenHour = "";
                        if (store.getOpenHour().equals(store.getCloseHour())) {
                            finalOpenHour = getResources().getString(R.string.Open24);

                        } else
                            finalOpenHour = store.getOpenHour() + " - " + store.getCloseHour();

                        intentStore.putExtra(ConstainApp.OPENHOUR, finalOpenHour);
                        startActivity(intentStore);
                    }
                });
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {

            }
        });
//        StoreUtilities storeUtilities = new StoreUtilities();
//        store = storeUtilities.getStoreById(storeId);


        //sstoreLocation.setText((Math.floor(store.getDistance() * 100) / 100) + " km");

//        if(store.getOpenHour().equals(store.getClodeHour())) {
//            workingTime.setText(R.string.Open24);
//        } else {
//            workingTime.setText(store.getOpenHour() + "-" + store.getClodeHour());
//        }

        price = intent.getFloatExtra(ConstainApp.PRICE, 0);
        discount = intent.getIntExtra(ConstainApp.DISCOUNT, 0);
        foodPrice.setText(String.format("%,d", (int) price) + " đ");
        foodPriceDiscount.setText((String.format("%,d", (int) price - intent.getIntExtra(ConstainApp.DISCOUNT, 0) * (int) price / 100)));

        description.setText(intent.getStringExtra(ConstainApp.DESCRIPTION));
//        description.setMaxLines(1);
        StoreProductImageService storeProductImageService = ApiUtils.getStoreProductImageService();
        storeProductImageService.getImageByStoreProductId(intent.getIntExtra(ConstainApp.STOREPRODUCTID, 0)).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> listImage = response.body();
                if (listImage.size() == 0) {
                    listImage.add("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1559727025/noimage.jpg");
                }
                imgProduct = listImage.get(0);

                imgProductFS = findViewById(R.id.imgProductFS);
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(FlashSalesProductDetailActivity.this, listImage);
                imgProductFS.setAdapter(viewPagerAdapter);
                imgProductFS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(FlashSalesProductDetailActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
//        StoreProductImageUtilities storeProductImageUtilities = new StoreProductImageUtilities();
//        List<String> listImage = storeProductImageUtilities.getImageByStoreProductId(intent.getIntExtra(ConstainApp.STOREPRODUCTID, 0));
//        imgProduct = listImage.get(0);

        foodName.setText(intent.getStringExtra(ConstainApp.PRODUCTNAME));
        endDate = intent.getStringExtra(ConstainApp.ENDDATE);
        //expiredDate.setText(endDate);
        // check remaining quantity of fsp
        totalQuanitity = intent.getIntExtra(ConstainApp.TOTALQUANTITY, 0);
        quantity = intent.getIntExtra(ConstainApp.QUANTITY, 0);
        if( totalQuanitity >= quantity ) {
            btnAddToCart.setText(getResources().getString(R.string.msg_product_sould_out) + "");
            llAddToCard.setClickable(false);
            llAddToCard.setBackgroundColor(Color.GRAY);
            btnAddToCart.setTextColor(Color.WHITE);
        }
        //check date flash sales if customer review product saw
        try {
            Date currentTime = Calendar.getInstance().getTime();
            long currentMillisecond = currentTime.getTime();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            //expiredDate.setText(endDate);
            long milliseconds = date.getTime();
            cv_countdownViewTest1.start(milliseconds - currentMillisecond);
            if( milliseconds <= currentMillisecond ) {
                btnAddToCart.setText(getResources().getString(R.string.msg_end_sell));
                btnAddToCart.setClickable(false);
                llAddToCard.setBackgroundColor(Color.GRAY);
                btnAddToCart.setTextColor(Color.WHITE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        totalPrice = findViewById(R.id.totalPrice);
//        totalPrice.setText(String.format("%,d",intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100));

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
        saveSawTable();


    }


//    public void clickToReadMore(View view) {
//        textReadMore.setVisibility(View.INVISIBLE);
//        description.setSingleLine(false);
//    }

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

    public int getCartNumber() {
        DBManager dbManager = new DBManager(FlashSalesProductDetailActivity.this);
        List<Cart> cartList = dbManager.getAllCart();
        int cartNumber = 0;
        for (int i = 0; i < cartList.size(); i++) {
            cartNumber += cartList.get(i).getQuantity();
        }
        return cartNumber;
    }

    public void clickAddToCard(View view) {
        FlashSaleProductService flashSaleProductService = ApiUtils.getFlashSaleProductService();
        flashSaleProductService.getRemaingQuantity(fspId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int remaningQuantity = response.body();
                if( remaningQuantity == 0 ) {
                    Toast.makeText(FlashSalesProductDetailActivity.this,
                            getResources().getString(R.string.msg_product_max) + " " + (quantity - totalQuanitity) + " sản phẩm", Toast.LENGTH_SHORT).show();
                    llAddToCard.setBackgroundColor(Color.GRAY);
                    btnAddToCart.setTextColor(Color.WHITE);
                    btnAddToCart.setText(getResources().getString(R.string.msg_product_max) + " " + (quantity - totalQuanitity) + " sản phẩm");
                    llAddToCard.setClickable(false);
                } else {
                    try {
                        Date currentTime = Calendar.getInstance().getTime();
                        long currentMillisecond = currentTime.getTime();
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                        //expiredDate.setText(endDate);
                        long milliseconds = date.getTime();
                        cv_countdownViewTest1.start(milliseconds - currentMillisecond);
                        if( milliseconds <= currentMillisecond ) {
                            llAddToCard.setClickable(false);
                            btnAddToCart.setText(getResources().getString(R.string.msg_end_sell));
                            llAddToCard.setBackgroundColor(Color.GRAY);
                            btnAddToCart.setTextColor(Color.WHITE);
                            Toast.makeText(FlashSalesProductDetailActivity.this,getResources().getString(R.string.msg_fsp_end_sell), Toast.LENGTH_SHORT).show();
                        } else {

                            DBManager dbManager = new DBManager(FlashSalesProductDetailActivity.this);
                            int cartQuantity = dbManager.getCateQuantity(fspId);
                            if( (totalQuanitity + cartQuantity ) >= quantity ) {
                                llAddToCard.setClickable(false);
                                btnAddToCart.setText(getResources().getString(R.string.msg_product_max)  + " " + (quantity - totalQuanitity) + " sản phẩm");
                                llAddToCard.setBackgroundColor(Color.GRAY);
                                btnAddToCart.setTextColor(Color.WHITE);
                                Toast.makeText(FlashSalesProductDetailActivity.this,
                                        getResources().getString(R.string.msg_product_max)  + " " + (quantity - totalQuanitity) + " sản phẩm",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Cart cart = dbManager.getProductById(fspId);
                                if (cart == null) {
                                    cart = new Cart();
                                    cart.setStoreName(storeName.getText().toString());
                                    cart.setProductName(foodName.getText().toString());
                                    cart.setImageProduct(imgProduct);
                                    cart.setFspId(fspId);
                                    cart.setPrice(price);
                                    cart.setDiscount(discount);
                                    cart.setStoreId(storeId);
                                    dbManager.addCart(cart);
                                    txtCartNumber.setText(String.valueOf(Integer.parseInt(String.valueOf(txtCartNumber.getText())) + 1));
//            txtCartNumberDB = ((DashboardActivity) mContext).findViewById(R.id.txtCartNumber);
//            txtCartNumberDB.setText(String.valueOf(Integer.parseInt(String.valueOf(txtCartNumber.getText())) + 1));
                                } else {
                                    dbManager.updateCart(cart);
                                    txtCartNumber.setText(String.valueOf(Integer.parseInt(String.valueOf(txtCartNumber.getText())) + 1));
//            txtCartNumberDB = ((DashboardActivity) mContext).findViewById(R.id.txtCartNumber);
//            txtCartNumberDB.setText(String.valueOf(Integer.parseInt(String.valueOf(txtCartNumber.getText())) + 1));
                                }
                                Toast.makeText(FlashSalesProductDetailActivity.this, R.string.msg_add_to_cart_success, Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

//        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
//        int remaningQuantity = flashSaleProductUtilities.getRemaingQuantity(fspId);




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
//    public void clickToStore(View view) {
//        Intent intent = new Intent(this, StoreActivity.class);
//        intent.putExtra(ConstainApp.JS_STORENAME, store.getStoreName());
//        intent.putExtra(ConstainApp.STOREAVATAR, store.getAvatar());
//        String address = store.getAddress();
//        intent.putExtra(ConstainApp.ADDRESS, address);
//        intent.putExtra(ConstainApp.RATINGPOINT, store.getRatingPoint());
//        intent.putExtra(ConstainApp.STOREID, store.getStoreId());
//        String openHour = "";
//        if(store.getOpenHour().equals(store.getCloseHour())) {
//            openHour = getResources().getString(R.string.Open24);
//        }else
//            openHour = store.getOpenHour() + " - " + store.getCloseHour();
//
//        intent.putExtra(ConstainApp.OPENHOUR, openHour);
//        startActivity(intent);
//    }

    public void clickToShoppingCart(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void saveSawTable() {
        DBManager dbManager = new DBManager(FlashSalesProductDetailActivity.this);
        dbManager.addTableSaw(fspId);
    }

    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        String searchProduct = txtRFind.getText().toString();
        if (searchProduct != null) {
            if ((searchProduct.length() != 0 && !searchProduct.equals(getResources().getString(R.string.msg_find)))) {
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, searchProduct);
            }
        }

        startActivity(intent);
    }
}
