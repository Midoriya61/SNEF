package com.tinlm.snef.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.service.FlashSaleProductService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SawFragment extends Fragment {

    RecyclerView rcListSaw;
    RelativeLayout notifiYetData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saw, container, false);
        DBManager dbManager = new DBManager(SawFragment.this.getContext());
        List<Integer> listFspId = dbManager.getAllProductSaw();
        if (listFspId.size() == 0) {
            notifiYetData = view.findViewById(R.id.notifiYetData);
            notifiYetData.setVisibility(View.VISIBLE);
        } else {
            final List<FlashSaleProduct> flashSaleProducts = new ArrayList<>();
            FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
            for (int i = 0; i < listFspId.size(); i++) {
                FlashSaleProduct flashSaleProduct  = flashSaleProductUtilities.getFSPById(listFspId.get(i));
                flashSaleProducts.add(flashSaleProduct);
            }

            FlashSaleProductAdapter flashSaleProductAdapter = new FlashSaleProductAdapter(SawFragment.this.getContext(), flashSaleProducts, ConstainApp.SawFragment);
            rcListSaw = view.findViewById(R.id.rcListSaw);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SawFragment.this.getContext(),2);

            rcListSaw.setItemAnimator(new DefaultItemAnimator());
            rcListSaw.setLayoutManager(mLayoutManager);
            rcListSaw.setAdapter(flashSaleProductAdapter);
        }

        return view;
    }

}
