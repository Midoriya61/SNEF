package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Store;

import org.json.JSONObject;

import java.net.URL;

public class LocationUtilities {

    private static final String locationId = "locationId";
    private static final String address = "address";
    private static final String district = "district";
    private static final String ward = "ward";
    private static final String city = "city";
    private static final String country = "country";


    // 6/17/2019 TinLM Create
    // Get address of Store by id of location
    public void getAddressOfStoreById(Store store) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.LocationURL + store.getLocationId();
        String respone = "";

        try {
            URL url1 = new URL(url);
            respone = ReadStream.readStream(url1.openStream());
            JSONObject jsonObject = new JSONObject(respone);

            if (jsonObject.has(country)) {
                store.setCountry(jsonObject.getString(country));
            }
            if (jsonObject.has(city)) {
                store.setCity(jsonObject.getString(city));
            }
            if (jsonObject.has(ward)) {
                store.setWard(jsonObject.getString(ward));
            }
            if (jsonObject.has(district)) {
                store.setDistrict(jsonObject.getString(district));
            }
            if (jsonObject.has(address)) {
                store.setAddres(jsonObject.getString(address));
            }

        } catch (Exception e) {
            Log.e("ELocation", e.getMessage());
        }
    }
}
