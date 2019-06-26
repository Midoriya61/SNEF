package com.tinlm.snef.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.tinlm.snef.R;
import com.tinlm.snef.adapter.ListStoreAdapter;
import com.tinlm.snef.algo.GeocodingLocation;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.LocationUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.util.Collections;
import java.util.List;


public class AroundStoreActivity extends AppCompatActivity {

    RecyclerView rcStoreAround;

    protected LocationManager locationManager;

    double[] locationStore = new double[2];
    double[] locationStoreCurrent = new double[2];


    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_store);
        navigateDashboard();
        checkLocationPermission();
        getCurrentLocation();
        createListStoreAround();
    }


    // get list store around phone with sort nearest
    private void createListStoreAround() {
        rcStoreAround = findViewById(R.id.rcStoreAround);
        StoreUtilities storeUtilities = new StoreUtilities();
        List<Store> storeList = storeUtilities.getListStoreArround();
        LocationUtilities locationUtilities = new LocationUtilities();
        for (int i = 0; i < storeList.size(); i++) {
            locationUtilities.getAddressOfStoreById(storeList.get(i));
            GeocodingLocation locationAddress = new GeocodingLocation();
            String address = storeList.get(i).getAddres() + " " + storeList.get(i).getDistrict()
                    + " " + storeList.get(i).getWard() + " " + storeList.get(i).getCity() + " " + storeList.get(i).getCountry();
            String location = locationAddress.getAddressFromLocation(address,
                    getApplicationContext());
            String[] rear = location.split("\n");
            locationStore[0] = Double.parseDouble(rear[0]);
            locationStore[1] = Double.parseDouble(rear[1]);
            storeList.get(i).distanceBetween2Points(locationStoreCurrent[0], locationStoreCurrent[1], locationStore[0], locationStore[1]);
        }
        // Sort store by distance from phone to store
        Collections.sort(storeList);
        ListStoreAdapter listStoreAdapter = new ListStoreAdapter(storeList,AroundStoreActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AroundStoreActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rcStoreAround.setItemAnimator(new DefaultItemAnimator());
        rcStoreAround.setLayoutManager(mLayoutManager);
        rcStoreAround.setAdapter(listStoreAdapter);
        rcStoreAround.addItemDecoration(new DividerItemDecoration(this, 0));
    }


    private void navigateDashboard() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.action_around);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        intent = new Intent(AroundStoreActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_category:
                        intent = new Intent(AroundStoreActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_around:
                        break;
                    case R.id.action_orders:
                        intent = new Intent(AroundStoreActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_account:
                        intent = new Intent(AroundStoreActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AroundStoreActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    // get current location
    private void getLatLong() {
        if(locationStoreCurrent[1] != 0 || locationStoreCurrent[0] != 0) {
            SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.locationPhone, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(ConstainApp.longPhone, (float)locationStoreCurrent[1]);
            editor.putFloat(ConstainApp.latPhone, (float)locationStoreCurrent[0]);
            editor.apply();
        }
    }

    // get current location of phone by gps
    private void getCurrentLocation() {
//        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.locationPhone, Context.MODE_PRIVATE);
//        longPhone = sharedPreferences.getFloat(ConstainApp.longPhone,0);
//        latPhone = sharedPreferences.getFloat(ConstainApp.latPhone,0);
//        if(longPhone == 0 && latPhone == 0) {
//
//        }
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GeocodingLocation.buildAlertMessageNoGps(AroundStoreActivity.this);
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, AroundStoreActivity.this);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationStoreCurrent = GeocodingLocation.getLocation(locationManager, AroundStoreActivity.this);
        }
    }


}
