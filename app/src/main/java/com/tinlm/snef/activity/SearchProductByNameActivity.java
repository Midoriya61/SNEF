package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductByNameActivity extends AppCompatActivity {
    private Intent intent;
    private TextView txtSearchProduct, txtSortPrice;
    private RecyclerView rcListProductByName;
    private ImageView imgIncre, imgDecen;
    private FlashSaleProductService flashSaleProductService;
    private LinearLayout buttonSortPrice;
    private TextView txtCartNumber;


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
        txtSortPrice = findViewById(R.id.txtSortPrice);
        imgIncre = findViewById(R.id.imgIncre);
        imgDecen = findViewById(R.id.imgDecen);
        buttonSortPrice = findViewById(R.id.buttonSortPrice);

        txtCartNumber = findViewById(R.id.txtCartNumber);
        txtCartNumber.setText(String.valueOf(new DBManager(this).getCartNumber()));


        // reset button sort of search price
        SharedPreferences sharedPreferencesSort = getSharedPreferences(ConstainApp.CATEGORIESPREFER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSort.edit();
        editor.putInt(ConstainApp.IMGPRICE, 0);


        txtSearchProduct.setText(intent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME));


        flashSaleProductService = ApiUtils.getFlashSaleProductService();

        // set search when don;t choose categories
        final SharedPreferences sharedPreferencesSearch = getSharedPreferences(ConstainApp.CATEGORIESPREFER, MODE_PRIVATE);
        String categoriesList = sharedPreferencesSearch.getString(ConstainApp.CATEGORIESID, null);
        if( categoriesList == null ) {
            categoriesList = "all";
        }

        flashSaleProductService.getFSPByName(intent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME),categoriesList).enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                final List<FlashSaleProduct> flashSaleProducts = response.body();
                final FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(SearchProductByNameActivity.this
                        , flashSaleProducts, ConstainApp.SCSearchProductByNameActivity);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SearchProductByNameActivity.this,2);
                rcListProductByName.setItemAnimator(new DefaultItemAnimator());
                rcListProductByName.setLayoutManager(mLayoutManager);
                rcListProductByName.setAdapter(flashSaleProductAdapter);
                buttonSortPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences1 = getSharedPreferences(ConstainApp.CATEGORIESPREFER, MODE_PRIVATE);
                        int bitPrice = sharedPreferences1.getInt(ConstainApp.IMGPRICE, 0);
                        if( bitPrice == 0 ) {
                            txtSortPrice.setTextColor(Color.RED);
                            imgDecen.setImageResource(R.drawable.decent_red);
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putInt(ConstainApp.IMGPRICE, 1);
                            editor.apply();
                        } else {
                            if( bitPrice == 1 ) {
                                Collections.sort(flashSaleProducts, new Comparator<FlashSaleProduct>() {
                                    @Override
                                    public int compare(FlashSaleProduct o1, FlashSaleProduct o2) {
                                        return  o1.compareTo(o2);
                                    }
                                });
                                flashSaleProductAdapter.updateSortList();
                                imgIncre.setImageResource(R.drawable.inscrease_red);
                                imgDecen.setImageResource(R.drawable.decent_black);
                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                editor.putInt(ConstainApp.IMGPRICE, 2);
                                editor.apply();
                            } else if ( bitPrice == 2) {
                                Collections.sort(flashSaleProducts, new Comparator<FlashSaleProduct>() {
                                    @Override
                                    public int compare(FlashSaleProduct o1, FlashSaleProduct o2) {
                                        return  o2.compareTo(o1);
                                    }
                                });

                                flashSaleProductAdapter.updateSortList();
                                imgIncre.setImageResource(R.drawable.inscrease_black);
                                imgDecen.setImageResource(R.drawable.decent_red);
                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                editor.putInt(ConstainApp.IMGPRICE, 1);
                                editor.apply();
                            }
                        }
                    }
                });
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

    public void clickToShoppingCart(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}
