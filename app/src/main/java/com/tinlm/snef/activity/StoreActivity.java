package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {

    TextView storeName,txtAddress, txtPRatingPoint, txtOpenHour, txtPhone;
    ImageView storeAvatar;
    RecyclerView rcListFlashSaleProduct;
    Intent intent;
    FlashSaleProductService flashSaleProductService;
    RelativeLayout notYetProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        init();
        intAction();
        createProduct();
    }

    private void createProduct() {
        flashSaleProductService = ApiUtils.getFlashSaleProductService();

        flashSaleProductService.getFSPByStoreId(intent.getIntExtra(ConstainApp.STOREID, 0)).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct> flashSaleProducts = response.body();
                if( flashSaleProducts.size() == 0 ) {
                    notYetProduct = findViewById(R.id.notYetProduct);
                    notYetProduct.setVisibility(View.VISIBLE);
                } else {
                    FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(getBaseContext(), flashSaleProducts,
                            ConstainApp.SCStoreActivity);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(),2);
                    rcListFlashSaleProduct.setItemAnimator(new DefaultItemAnimator());
                    rcListFlashSaleProduct.setLayoutManager(mLayoutManager);
                    rcListFlashSaleProduct.setAdapter(flashSaleProductAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<FlashSaleProduct>> call, Throwable t) {
                Log.d("ListFSPFragment", "error loading from API");
            }
        });
    }

    private void intAction() {
        intent = getIntent();
        storeName.setText(intent.getStringExtra(ConstainApp.JS_STORENAME));
        txtAddress.setText(intent.getStringExtra(ConstainApp.ADDRESS));
        txtAddress.setSelected(true);
        txtAddress.setPaintFlags(txtAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtOpenHour.setText(intent.getStringExtra(ConstainApp.OPENHOUR));
        txtPhone.setText(intent.getStringExtra(ConstainApp.STOREPHONE));
//        Bundle bundle = intent.getBundleExtra(ConstainApp.BUNDLERATING);
        float ratingPoint = intent.getFloatExtra(ConstainApp.RATINGPOINT,0);
        if (ratingPoint == 0) {
            txtPRatingPoint.setText(getResources().getString(R.string.msg_still_not_rating));
        } else {
            txtPRatingPoint.setText(ratingPoint + "/5");
        }
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Picasso.get().load(intent.getStringExtra(ConstainApp.STOREAVATAR)).resize(width, height/3).into(storeAvatar);
    }

    private void init() {
        storeName = findViewById(R.id.storeName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPRatingPoint = findViewById(R.id.txtPRatingPoint);
        storeAvatar = findViewById(R.id.storeAvatar);
        rcListFlashSaleProduct = findViewById(R.id.rcListFlashSaleProduct);
        txtOpenHour = findViewById(R.id.txtOpenHour);
        txtPhone = findViewById(R.id.txtPhone);
    }

    public void clickToDirectStore(View view) {
//        Uri gmmIntentUri = Uri.parse("google.navigation:q=latitude,longitude");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
        String packageName = "com.google.android.apps.maps";
        String query = "google.navigation:q="+ intent.getStringExtra(ConstainApp.ADDRESS);
        Intent intentMap = this.getPackageManager().getLaunchIntentForPackage(packageName);
        intentMap.setAction(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse(query));
        startActivity(intentMap);
    }
}
