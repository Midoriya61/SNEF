package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductByCategoryActivity extends AppCompatActivity {

    RecyclerView rcListPdCategory;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        init();
    }

    private void init() {
        rcListPdCategory = findViewById(R.id.rcListPdCategory);
        List<FlashSaleProduct>  flashSaleProducts = new ArrayList<>();
        Map<Integer, String> listImageProduct = new HashMap<>();
        Map<Integer, Integer> listTotalPrice = new HashMap<>();
        intent = getIntent();
        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        flashSaleProducts = flashSaleProductUtilities.getFSPByCategoryId(intent.getIntExtra(ConstainApp.CATEGORYID, 0));

        for (int i = 0; i < flashSaleProducts.size(); i++) {

            String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
            listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );

            int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
            listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);


        }
        FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(this, flashSaleProducts, listImageProduct, listTotalPrice);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        rcListPdCategory.setItemAnimator(new DefaultItemAnimator());
        rcListPdCategory.setLayoutManager(mLayoutManager);
        rcListPdCategory.setAdapter(flashSaleProductAdapter);
    }
}
