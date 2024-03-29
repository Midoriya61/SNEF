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
import com.tinlm.snef.activity.DashboardActivity;
import com.tinlm.snef.adapter.CategoriesAdapter;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.service.CategoriesService;
import com.tinlm.snef.utilities.ApiUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesHomeFragment extends Fragment {

    private CategoriesService categoriesService;
    private RecyclerView rcListCategories;
    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_home, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        rcListCategories = view.findViewById(R.id.rcListCategories);

        Long cacheSize = Long.valueOf((256 * 1024));
        Cache cache = new Cache(view.getContext().getCacheDir(), cacheSize);
        categoriesService = ApiUtils.getCategoriesService(view.getContext(), cache);

        categoriesService.getAllCategories().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                List<Categories> categoryList = response.body();
//                CategoriesUtilities categoriesUtilities = new CategoriesUtilities();
//                categoryList = categoriesUtilities.getAllCategories();

                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(CategoriesHomeFragment.this.getContext(), categoryList, ConstainApp.CategoriesHomeFragment);

                final RecyclerView.LayoutManager mLayoutManager
                        = new LinearLayoutManager(CategoriesHomeFragment.this.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);

                rcListCategories.setItemAnimator(new DefaultItemAnimator());
                rcListCategories.setLayoutManager(mLayoutManager);
                rcListCategories.setAdapter(categoriesAdapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Log.d("CategoriesHomeFragment", "error loading from API");
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
