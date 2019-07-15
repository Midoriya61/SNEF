package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.model.Like;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class LikeUtilities {

    private static final String likeIdURL = "likeId";
    private static final String  customerIdURL= "customerId";
    private static final String storeProductIdURL = "storeProductId";

    public Like getLikeById(int customerId, int storeProductId) {
        Like result = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.LikeURL + ConstainServer.GetLikeById + customerId + "/" +
                storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
//            HttpGetRequest httpGetRequest = new HttpGetRequest();

            JSONObject jsonObj = new JSONObject(respone);

            if( jsonObj != null) {
                result = new Like();
                result.setLikeId(jsonObj.getInt(likeIdURL));
                result.setCustomerId(jsonObj.getInt(customerIdURL));
                result.setStoreProductId(jsonObj.getInt(storeProductIdURL));
            }

        }catch (Exception e){
            Log.e("Error categories", e.getMessage());
        }
        return  result;
    }


    public String insertNewLikes(int customerId, int storeProductId) {
        String result = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.LikeURL + ConstainServer.InsertNewLikes + customerId + "/" +
                storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
//            respone = httpGetRequest.execute(urll.openStream()).get();
//            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = respone;

        }catch (Exception e){
            Log.e("Error categories", e.getMessage());
        }
        return  result;
    }

    public String deleteLikeById(int likeId) {
        String result = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.LikeURL + ConstainServer.DeleteLike + likeId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();

//            HttpGetRequest httpGetRequest = new HttpGetRequest();
//            respone = httpGetRequest.execute(urll.openStream()).get();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = respone;

        }catch (Exception e){
            Log.e("Error categories", e.getMessage());
        }
        return  result;
    }

}
