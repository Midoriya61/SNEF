package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;

import java.net.URL;

public class OrderDetailUtilities {

    // Get quantity of store product by store product id
    public int getQuantityByFSPId( int flashsaleProductId ) {
        int result = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderDetail + ConstainServer.GetQuantityByFSPId + flashsaleProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = Integer.parseInt(respone);

        }catch (Exception e){
            Log.e("EODQ", e.getMessage());
        }
        return result;
    }
}
