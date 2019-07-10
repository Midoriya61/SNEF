package com.tinlm.snef.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.service.StoreProductImageService;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class HotProductHomeFragment extends Fragment {

    private RecyclerView rcListHost;
    FlashSaleProductService flashSaleProductService;
    private ShimmerFrameLayout mShimmerViewContainer;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_product_home, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        rcListHost = view.findViewById(R.id.rcListHost);
        flashSaleProductService = ApiUtils.getFlashSaleProductService();


        flashSaleProductService.getHotFlashSaleProduct().enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct> flashSaleProducts = response.body();
                Map<Integer, String> listImageProduct = new HashMap<>();
                Map<Integer, Integer> listTotalPrice = new HashMap<>();
//                FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
                StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
                OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();

                //flashSaleProducts = flashSaleProductUtilities.getAllFSP();
                for (int i = 0; i < flashSaleProducts.size(); i++) {
                    //get image of product
                    String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());

                    listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(), productImage);
                    int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
                    listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);
                }
                FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(HotProductHomeFragment.this.getContext()
                        , flashSaleProducts, listImageProduct, listTotalPrice, ConstainApp.SCHotProductHomeFragment);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HotProductHomeFragment.this.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                rcListHost.setItemAnimator(new DefaultItemAnimator());
                rcListHost.setLayoutManager(mLayoutManager);
                rcListHost.setAdapter(flashSaleProductAdapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<FlashSaleProduct>> call, Throwable t) {
                Log.d("HotProductHomeFragment", "error loading from API");
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

//    @Override
//    public void onPause() {
//        mShimmerViewContainer.stopShimmer();
//        super.onPause();
//    }
}
