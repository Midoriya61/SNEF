package com.tinlm.snef.algo;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.AroundStoreActivity;
import com.tinlm.snef.constain.ConstainApp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation {

    // CConvert address to location to get longitude and latitude
    public static String getAddressFromLocation( String locationAddress,
                                                 Context context) {
        {

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String result = null;
            try {
                List
                        addressList = geocoder.getFromLocationName(locationAddress, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = (Address) addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    sb.append(address.getLatitude()).append("\n");
                    sb.append(address.getLongitude()).append("\n");
                    result = sb.toString();
                }
            } catch (IOException e) {
                Log.e(ConstainApp.GeocodingLocation, "Unable to connect to Geocoder", e);
            } finally {
                Message message = Message.obtain();

            }
            return result;
        }

    }

    // Alert turn GPS
    public static void buildAlertMessageNoGps(final Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(R.string.OpenGPS)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        buildAlertMessageNoGps(context);
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    // get location of phone by gps
    public static double[] getLocation(LocationManager locationManager, Context context) {
        double[] locationStore = new double[2];
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                locationStore[0] =  location.getLatitude();
                locationStore[1] = location.getLongitude();
            } else  if (location1 != null) {
                locationStore[0] = location1.getLatitude();
                locationStore[1] = location1.getLongitude();
            } else  if (location2 != null) {
                locationStore[0] = location2.getLatitude();
                locationStore[1] = location2.getLongitude();
            }else{
                Toast.makeText(context,"Unble to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
        return locationStore;
    }

}
