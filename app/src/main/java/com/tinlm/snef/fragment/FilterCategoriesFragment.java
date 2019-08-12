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

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.service.CategoriesService;
import com.tinlm.snef.utilities.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterCategoriesFragment extends Fragment {

    RecyclerView rcFilterCategories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter_categories, container, false);
        rcFilterCategories = v.findViewById(R.id.rcFilterCategories);
        CategoriesService categoriesService = ApiUtils.getCategoriesService();
        categoriesService.getAllCategories().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                List<Categories> categoryList = response.body();
//                CategoriesUtilities categoriesUtilities = new CategoriesUtilities();
//                categoryList = categoriesUtilities.getAllCategories();

                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(FilterCategoriesFragment.this.getContext(), categoryList, ConstainApp.FilterCategoriesFragment);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilterCategoriesFragment.this.getContext(),3);
                rcFilterCategories.setItemAnimator(new DefaultItemAnimator());
                rcFilterCategories.setLayoutManager(mLayoutManager);
                rcFilterCategories.setAdapter(categoriesAdapter);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });

        return v;
    }

}
