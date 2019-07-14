package com.tinlm.snef.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tinlm.snef.R;
import com.tinlm.snef.activity.DashboardActivity;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.AllService;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFSPFragment extends Fragment {
    RecyclerView rcListFlashSaleProduct;
    FlashSaleProductService flashSaleProductService;
    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if(getActivity().equals(DashboardActivity.class))
       View view = inflater.inflate(R.layout.fragment_list_fs, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        rcListFlashSaleProduct = view.findViewById(R.id.rcListFlashSaleProduct);
        flashSaleProductService = AllService.getFlashSaleProductService();

        flashSaleProductService.getAllFSP().enqueue(new Callback<List<FlashSaleProduct>>() {
            @Override
            public void onResponse(Call<List<FlashSaleProduct>> call, Response<List<FlashSaleProduct>> response) {
                List<FlashSaleProduct> flashSaleProducts = response.body();
                Collections.sort(flashSaleProducts);
                Map<Integer, String> listImageProduct = new HashMap<>();
                Map<Integer, Integer> listTotalPrice = new HashMap<>();
//                FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
//                StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
//                OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();

//                for (int i = 0; i < flashSaleProducts.size(); i++) {
//
//                    String productImage = imageUltilities.getOneImageByStoreProductId(flashSaleProducts.get(i).getStoreProductId());
//                    listImageProduct.put(flashSaleProducts.get(i).getStoreProductId(),productImage );
//
//                    // get total quantity of order detail
//                    int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProducts.get(i).getFlashSaleProductId());
//                    listTotalPrice.put(flashSaleProducts.get(i).getFlashSaleProductId(), totalQuantity);
//                }

                FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(ListFSPFragment.this.getContext(),
                        flashSaleProducts, ConstainApp.SCListFSPFragment);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ListFSPFragment.this.getContext(),2);
                rcListFlashSaleProduct.setItemAnimator(new DefaultItemAnimator());
                rcListFlashSaleProduct.setLayoutManager(mLayoutManager);
                rcListFlashSaleProduct.setAdapter(flashSaleProductAdapter);

                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<FlashSaleProduct>> call, Throwable t) {
                Log.d("ListFSPFragment", "error loading from API");
            }
        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }


}
