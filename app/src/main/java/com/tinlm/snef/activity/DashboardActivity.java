package com.tinlm.snef.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;

// 6/23/2019 TinLM Create class
// 6/23/2019 TinLM Create init
// 6/23/2019 TinLM Create createListCategories
// 6/23/2019 TinLM Create createListHostProduct
// 6/23/2019 TinLM Create createListFSP
public class DashboardActivity extends AppCompatActivity {

    //    RecyclerView rcListCategories;
//    RecyclerView rcListHost;
    private BottomNavigationView bottomNavigation;
    private TextView txtRFind;
    private TextView txtCartNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        txtRFind = findViewById(R.id.txtRFind);
        Intent dbIntent = getIntent();
        String searchString = dbIntent.getStringExtra(ConstainApp.SEARCHPRODUCTNAME);
        if (searchString != null) {
            if ((searchString.length() != 0 && !searchString.equals(getResources().getString(R.string.msg_find)))) {
                txtRFind.setText(searchString);
            }
        }

        txtCartNumber = findViewById(R.id.txtCartNumber);
        txtCartNumber.setText(String.valueOf(new DBManager(this).getCartNumber()));

//        txtRFind.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        Drawable img = getResources().getDrawable(
//                                R.drawable.find);
//                        img.setBounds(-10, 0,
//                                (img.getIntrinsicWidth() * txtRFind.getMeasuredHeight() / img.getIntrinsicHeight()),
//                                txtRFind.getMeasuredHeight());
//                        txtRFind.setCompoundDrawables(img, null, null, null);
//                        txtRFind.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                });


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

                        break;
//                    case R.id.action_category:
//                        intent = new Intent(DashboardActivity.this, CategoryActivity.class);
//                        finish();
//                        startActivity(intent);
//
//                        break;
                    case R.id.action_around:
                        intent = new Intent(DashboardActivity.this, AroundStoreActivity.class);
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
                        intent = new Intent(DashboardActivity.this, AccountActivity.class);
                        finish();
                        startActivity(intent);

                        break;
                }
                return false;
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
                                ActivityCompat.requestPermissions(DashboardActivity.this,
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
}
