package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;

import org.json.JSONObject;

import java.net.URL;

public class StoreProductImageUtilities {
    private static final String fspId = "fspId";
    private static final String imageSrc = "imageSrc";
    private static final String storeProductId = "storeProductId";


    // 6/17/2019 TinLM Create getImageByStoreProductId
    public String getImageByStoreProductId(int storeProductId) {
        String result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.StoreProductImageURL + ConstainServer.GetOneImage + storeProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONObject jsonObj = new JSONObject(respone);
            if(jsonObj.has(imageSrc)){
                result = jsonObj.getString(imageSrc);
            }

        }catch (Exception e){
            Log.e("ESPI", e.getMessage());
        }
        return result;
    }

}
