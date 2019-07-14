package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.AllService;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByCategoryActivity extends AppCompatActivity {

    RecyclerView rcListPdCategory;
    FlashSaleProductService flashSaleProductService;
    Intent intent;
    TextView txtCategogy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        init();
    }

    private void init() {
        rcListPdCategory = findViewById(R.id.rcListPdCategory);
        txtCategogy = findViewById(R.id.txtCategogy);
        flashSaleProductService = AllService.getFlashSaleProductService();


        intent = getIntent();

        txtCategogy.setText(intent.getStringExtra(ConstainApp.CATEGORYNAME));

        flashSaleProductService.getFSPByCategoryId(intent.getIntExtra(ConstainApp.CATEGORYID, 0)).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct>  flashSaleProducts = response.body();

                FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(ProductByCategoryActivity.this,
                        flashSaleProducts, ConstainApp.SCProductByCategoryActivity);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ProductByCategoryActivity.this,2);
                rcListPdCategory.setItemAnimator(new DefaultItemAnimator());
                rcListPdCategory.setLayoutManager(mLayoutManager);
                rcListPdCategory.setAdapter(flashSaleProductAdapter);
            }

            @Override
            public void onFailure(Call<List<FlashSaleProduct>> call, Throwable t) {

            }
        });

    }

    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
