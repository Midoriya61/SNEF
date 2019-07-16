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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.service.AllService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }



}
