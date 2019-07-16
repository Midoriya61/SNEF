package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StoreProductUtilities {

    // Get quantity of store product by store product id
    public int getQuantityById( int storeProductId ) {
       int result = 0;


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CategoriesURL;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = Integer.parseInt(respone);

        }catch (Exception e){
            Log.e("ESPQ", e.getMessage());
        }
        return result;
    }

    public String getDesById( int storeProductId ) {
       String result = "";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreProductURL + ConstainServer.GetDesById + storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = respone;

        }catch (Exception e){
            Log.e("ESPQ", e.getMessage());
        }
        return result;
    }
}
