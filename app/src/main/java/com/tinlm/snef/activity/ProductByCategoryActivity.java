package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.FlashSaleProduct;
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

    private RecyclerView rcListPdCategory;
    private FlashSaleProductService flashSaleProductService;
    private Intent intent;
    private TextView txtCategogy;
    private TextView txtRFind, txtCartNumber;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        txtRFind = findViewById(R.id.txtRFind);
        txtCartNumber = findViewById(R.id.txtCartNumber);
        init();
        navigateDashboard();
    }

    @Override
    public void onResume(){
        super.onResume();
        txtCartNumber.setText(String.valueOf(new DBManager(this).getCartNumber()));
    }

    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(ProductByCategoryActivity.this, DashboardActivity.class);
                        finish();
                        startActivity(intent);
                        break;
//                    case R.id.action_category:
//                        intent = new Intent(DashboardActivity.this, CategoryActivity.class);
//                        finish();
//                        startActivity(intent);
//
//                        break;
                    case R.id.action_around:
                        intent = new Intent(ProductByCategoryActivity.this, AroundStoreActivity.class);
                        finish();
                        startActivity(intent);

                        break;
//                    case R.id.action_orders:
//                        intent = new Intent(DashboardActivity.this, OrderActivity.class);
//                        finish();
//                        startActivity(intent);
//
//                        break;
                    case R.id.action_account:
                        intent = new Intent(ProductByCategoryActivity.this, AccountActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });
    }

    private void init() {
        rcListPdCategory = findViewById(R.id.rcListPdCategory);
        txtCategogy = findViewById(R.id.txtCategogy);
        flashSaleProductService = ApiUtils.getFlashSaleProductService();

        intent = getIntent();
//        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();

        txtCategogy.setText(intent.getStringExtra(ConstainApp.CATEGORYNAME));

//        flashSaleProducts = flashSaleProductUtilities.getFSPByCategoryId(intent.getIntExtra(ConstainApp.CATEGORYID, 0));
        flashSaleProductService.getFSPByCategoryId(intent.getIntExtra(ConstainApp.CATEGORYID, 0)).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct>  flashSaleProducts = response.body();
                StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
                OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
//                Map<Integer, String> listImageProduct = new HashMap<>();
//                Map<Integer, Integer> listTotalPrice = new HashMap<>();
//                for (int i = 0; i < flashSaleProducts.size(); i++) {
//
//                    String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
//                    listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );
//
//                    int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
//                    listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);
//
//                }
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
        String searchProduct = txtRFind.getText().toString();
        if (searchProduct != null) {
            if ((searchProduct.length() != 0 && !searchProduct.equals(getResources().getString(R.string.msg_find)))) {
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, searchProduct);
            }
        }

        startActivity(intent);

    }

    public void clickToShoppingCart(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}
