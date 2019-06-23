package com.tinlm.snef.algo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Message;
import android.util.Log;

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
}
