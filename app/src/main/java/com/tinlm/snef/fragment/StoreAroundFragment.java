package com.tinlm.snef.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.AroundStoreActivity;
import com.tinlm.snef.adapter.ListStoreAdapter;
import com.tinlm.snef.algo.GeocodingLocation;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.service.StoreService;
import com.tinlm.snef.utilities.ApiUtils;
import com.tinlm.snef.utilities.StoreUtilities;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreAroundFragment extends Fragment {


    RecyclerView rcStoreAround;
    StoreService storeService;
    double[] locationStoreCurrent = new double[2];

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    protected LocationManager locationManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_around, container, false);
        checkLocationPermission();
        getCurrentLocation();
        storeService = ApiUtils.getStoreService();
        rcStoreAround = view.findViewById(R.id.rcStoreAround);
        StoreUtilities storeUtilities = new StoreUtilities();
        storeService.getListStoreArround(locationStoreCurrent[0], locationStoreCurrent[1]).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {

                List<Store> storeList = response.body();

                for (int i = 0; i < storeList.size(); i++) {
//
//                    storeList.get(i).distanceBetween2Points(, locationStoreCurrent[1]);
//            storeList.get(i).setDistance(i);
                }
                // Sort store by distance from phone to store
                Collections.sort(storeList);
                ListStoreAdapter listStoreAdapter = new ListStoreAdapter(storeList, StoreAroundFragment.this.getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StoreAroundFragment.this.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                rcStoreAround.setItemAnimator(new DefaultItemAnimator());
                rcStoreAround.setLayoutManager(mLayoutManager);
                rcStoreAround.setAdapter(listStoreAdapter);
                rcStoreAround.addItemDecoration(new DividerItemDecoration(StoreAroundFragment.this.getContext(), 0));
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(rcStoreAround);

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("Error ASA", t.getMessage());
            }
        });
        return view;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(StoreAroundFragment.this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(StoreAroundFragment.this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(StoreAroundFragment.this.getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(StoreAroundFragment.this.getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(StoreAroundFragment.this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    // get current location
//    private void getLatLong() {
//        if(locationStoreCurrent[1] != 0 || locationStoreCurrent[0] != 0) {
//            SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.locationPhone, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putFloat(ConstainApp.longPhone, (float)locationStoreCurrent[1]);
//            editor.putFloat(ConstainApp.latPhone, (float)locationStoreCurrent[0]);
//            editor.apply();
//        }
//    }

    // get current location of phone by gps
    private void getCurrentLocation() {
//        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.locationPhone, Context.MODE_PRIVATE);
//        longPhone = sharedPreferences.getFloat(ConstainApp.longPhone,0);
//        latPhone = sharedPreferences.getFloat(ConstainApp.latPhone,0);
//        if(longPhone == 0 && latPhone == 0) {
//
//        }
        ActivityCompat.requestPermissions(StoreAroundFragment.this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager) StoreAroundFragment.this.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GeocodingLocation.buildAlertMessageNoGps(StoreAroundFragment.this.getContext());
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, StoreAroundFragment.this.getContext());
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, StoreAroundFragment.this.getContext());
        }
    }
}
