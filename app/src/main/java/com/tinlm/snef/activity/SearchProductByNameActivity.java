package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.fragment.HotProductHomeFragment;
import com.tinlm.snef.fragment.ListFSPFragment;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.AllService;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductByNameActivity extends AppCompatActivity {
    Intent intent;
    TextView txtSearchProduct;
    RecyclerView rcListProductByName;
    FlashSaleProductService flashSaleProductService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_by_name);
        init();
    }

    private void init() {
        intent = getIntent();
        txtSearchProduct = findViewById(R.id.txtSearchProduct);
        rcListProductByName = findViewById(R.id.rcListProductByName);
        txtSearchProduct.setText(intent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME));

        flashSaleProductService = AllService.getFlashSaleProductService();
        flashSaleProductService.getFSPByName(intent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME)).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct> flashSaleProducts = response.body();
                FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(SearchProductByNameActivity.this
                        , flashSaleProducts, ConstainApp.SCSearchProductByNameActivity);

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SearchProductByNameActivity.this,2);
                rcListProductByName.setItemAnimator(new DefaultItemAnimator());
                rcListProductByName.setLayoutManager(mLayoutManager);
                rcListProductByName.setAdapter(flashSaleProductAdapter);

            }

            @Override
            public void onFailure(Call<List<FlashSaleProduct>> call, Throwable t) {

            }
        });

    }

    public void clickToSearchProduct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, intent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME));
        startActivity(intent);
    }
}
