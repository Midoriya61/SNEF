package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StoreProductImageUtilities {
    private static final String fspId = "fspId";
    private static final String imageSrc = "imageSrc";
    private static final String storeProductId = "storeProductId";


    // 6/17/2019 TinLM Create getImageByStoreProductId
    public String getOneImageByStoreProductId(int storeProductId) {
        String result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreProductImageURL + ConstainServer.GetOneImage + storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONObject jsonObj = new JSONObject(respone);
            if(jsonObj.has(imageSrc)){
                result = jsonObj.getString(imageSrc);
            }

        }catch (Exception e){
            Log.e("ESPI", e.getMessage());
        }
        return result;
    }

    public List<String> getImageByStoreProductId(int storeProductId) {
        List<String> result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreProductImageURL + ConstainServer.GetImage + storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);
            result = new ArrayList<>();
            for (int i  = 0; i < arr.length(); i++) {
                result.add((String) arr.get(i));
            }

        }catch (Exception e){
            Log.e("ESPI", e.getMessage());
        }
        return result;
    }

}
