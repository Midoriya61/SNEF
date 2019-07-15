package com.tinlm.snef.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.fragment.ListFSPFragment;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {

    TextView storeName,txtAddress, txtPRatingPoint, txtOpenHour;
    ImageView storeAvatar;
    RecyclerView rcListFlashSaleProduct;
    Intent intent;
    FlashSaleProductService flashSaleProductService;

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

        flashSaleProductService.getFSPByCategoryId(intent.getIntExtra(ConstainApp.STOREID, 0)).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct> flashSaleProducts = response.body();
                Map<Integer, String> listImageProduct = new HashMap<>();
                Map<Integer, Integer> listTotalPrice = new HashMap<>();
//                FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
//                StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
//                OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
//
//                for (int i = 0; i < flashSaleProducts.size(); i++) {
//
//                    String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
//                    listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );
//                    // get total quantity of order detail
//                    int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
//                    listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);
//                }
                FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(getBaseContext(), flashSaleProducts,
                         ConstainApp.SCStoreActivity);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(),2);
                rcListFlashSaleProduct.setItemAnimator(new DefaultItemAnimator());
                rcListFlashSaleProduct.setLayoutManager(mLayoutManager);
                rcListFlashSaleProduct.setAdapter(flashSaleProductAdapter);


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
        txtOpenHour.setText(intent.getStringExtra(ConstainApp.OPENHOUR));
        float ratingPoint = intent.getFloatExtra(ConstainApp.RATINGPOINT, 0);
        if (ratingPoint == 0) {
            txtPRatingPoint.setText("Chưa có đánh giá");
        } else {
            txtPRatingPoint.setText(intent.getStringExtra(ConstainApp.RATINGPOINT));
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
    }
}