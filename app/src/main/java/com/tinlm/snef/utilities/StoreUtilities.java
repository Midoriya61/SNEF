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
    private static final String ratingPoint = "ratingPoint";
    private static final String avatar = "avatar";
    private static final String openHour = "openHour";
    private static final String closeHour = "closeHour";
    private static final String address = "address";
    private static final String latitude = "latitude";
    private static final String longitude = "longitude";
    private static final String phone = "phone";

    // 6/18/2019 TinLM Create getListStoreArround
    public List<Store> getListStoreArround() {
        List<Store> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreURL;
        String respone = "";
        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
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
                if(jsonObj.has(openHour)){
                    store.setOpenHour(jsonObj.getString(openHour));
                }
                if(jsonObj.has(closeHour)){
                    store.setCloseHour(jsonObj.getString(closeHour));
                }
                if(jsonObj.has(avatar)){
                    store.setAvatar(jsonObj.getString(avatar));
                }
                if(jsonObj.has(ratingPoint)){
                    store.setRatingPoint(jsonObj.getDouble(ratingPoint));
                }
                if(jsonObj.has(address)){
                    store.setAddress(jsonObj.getString(address));
                }
                if(jsonObj.has(latitude)){
                    store.setLatitude(jsonObj.getDouble(latitude));
                }
                if(jsonObj.has(longitude)){
                    store.setLongitude(jsonObj.getDouble(longitude));
                }
                if(jsonObj.getString(phone) != null){
                    store.setPhone(jsonObj.getString(phone));
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
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONObject jsonObj = new JSONObject(respone);

            if(jsonObj.has(storeName)){
                store.setStoreName(jsonObj.getString(storeName));
            }
            if(jsonObj.has(openHour)){
                store.setOpenHour(jsonObj.getString(openHour));
            }
            if(jsonObj.has(closeHour)){
                store.setCloseHour(jsonObj.getString(closeHour));
            }
            if(jsonObj.has(avatar)){
                store.setAvatar(jsonObj.getString(avatar));
            }
            if(jsonObj.has(ratingPoint)){
                store.setRatingPoint(jsonObj.getDouble(ratingPoint));
            }
            if(jsonObj.has(address)){
                store.setAddress(jsonObj.getString(address));
            }
            if(jsonObj.has(latitude)){
                store.setLatitude(jsonObj.getDouble(latitude));
            }
            if(jsonObj.has(longitude)){
                store.setLongitude(jsonObj.getDouble(longitude));
            }
            if(jsonObj.has(phone)){
                store.setPhone(jsonObj.getString(phone));
            }
            store.setStoreId(storeId);

        }catch (Exception e){
            Log.e("Error Aroud", e.getMessage());
        }
        return store;
    }

}
