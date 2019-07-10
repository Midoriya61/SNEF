package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Store;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StoreUtilities {
    private static final String storeId = "storeId";
    private static final String accountId = "accountId";
    private static final String storeName = "storeName";
    private static final String locationId = "locationId";
    private static final String ratingPoint = "ratingPoint";
    private static final String avatar = "avatar";
    private static final String openHour = "openHour";
    private static final String closeHour = "closeHour";
    private static final String address = "address";
    private static final String district = "district";
    private static final String ward = "ward";
    private static final String city = "city";
    private static final String country = "country";

    // 6/18/2019 TinLM Create getListStoreArround
    public List<Store> getListStoreArround() {
        List<Store> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreURL;
        String respone = "";
        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONArray arr = new JSONArray(respone);
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Store store = new Store();

                if(jsonObj.has(storeId)){
                    store.setStoreId(jsonObj.getInt(storeId));
                }
                if(jsonObj.has(storeName)){
                    store.setStoreName(jsonObj.getString(storeName));
                }
                if(jsonObj.has(locationId)){
                    store.setLocationId(jsonObj.getInt(locationId));
                }
                if(jsonObj.has(openHour)){
                    store.setOpenHour(jsonObj.getString(openHour));
                }
                if(jsonObj.has(closeHour)){
                    store.setClodeHour(jsonObj.getString(closeHour));
                }
                if(jsonObj.has(avatar)){
                    store.setAvatar(jsonObj.getString(avatar));
                }

                result.add(store);
            }

        }catch (Exception e){
            Log.e("Error Aroud", e.getMessage());
        }
        return result;
    }

    public Store getStoreById(int storeId) {
        Store store = new Store();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreURL + ConstainServer.GetStoreById + storeId;
        String respone = "";
        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONObject jsonObj = new JSONObject(respone);

                if(jsonObj.has(storeName)){
                    store.setStoreName(jsonObj.getString(storeName));
                }
                if(jsonObj.has(locationId)){
                    store.setLocationId(jsonObj.getInt(locationId));
                }
                if(jsonObj.has(openHour)){
                    store.setOpenHour(jsonObj.getString(openHour));
                }
                if(jsonObj.has(closeHour)){
                    store.setClodeHour(jsonObj.getString(closeHour));
                }if(jsonObj.has(ratingPoint)){
                    store.setRatingPoint(jsonObj.getDouble(ratingPoint));
                }

                store.setStoreId(storeId);


        }catch (Exception e){
            Log.e("Error Aroud", e.getMessage());
        }
        return store;
    }

}
