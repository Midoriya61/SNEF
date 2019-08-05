package com.tinlm.snef.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.FlashSaleProductAdapter;
import com.tinlm.snef.adapter.ListFoundAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends Fragment {

    RecyclerView rcListFound;
    RelativeLayout notifiYetData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_found, container, false);
        // Inflate the layout for this fragment

        DBManager dbManager = new DBManager(FoundFragment.this.getContext());
        List<String> listFoundName = dbManager.getAllProductNameFound();
        if (listFoundName.size() == 0) {
            notifiYetData = view.findViewById(R.id.notifiYetData);
            notifiYetData.setVisibility(View.VISIBLE);
        } else {

            ListFoundAdapter categoriesAdapter = new ListFoundAdapter(listFoundName, FoundFragment.this.getContext(),notifiYetData);
            rcListFound = view.findViewById(R.id.rcListFound);
            final RecyclerView.LayoutManager mLayoutManager
                    = new LinearLayoutManager(FoundFragment.this.getContext(),
                    LinearLayoutManager.VERTICAL, false);

            rcListFound.setItemAnimator(new DefaultItemAnimator());
            rcListFound.setLayoutManager(mLayoutManager);
            rcListFound.setAdapter(categoriesAdapter);
        }

        return view;
    }

}
