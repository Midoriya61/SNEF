package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Order;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

public class OrderUtilities {

    private static final String orderId = "orderId";
    private static final String dateOrder = "dateOrder";
    private static final String confirmationCode = "confirmationCode";
    private static final String status = "status";
    private static final String ratingPoint = "ratingPoint";
    private static final String accountId = "accountId";
    public SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

    public void insertNewOrder(String confirmationCode, int accountId){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.InsertNewOrder + confirmationCode + "/" + accountId;
//        String respone = "";

        try {
            URL urll = new URL(url);
            ReadStream.readStream(urll.openStream());

        }catch (Exception e){
            Log.e("EINO", e.getMessage());
        }
    }

    public Order getLastOrder() {
        Order store = new Order();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetLastOrder;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONObject jsonObj = new JSONObject(respone);

            if(jsonObj.has(orderId)){
                store.setOrderId(jsonObj.getInt(orderId));
            }
            if(jsonObj.has(dateOrder)){
                java.util.Date date = sdf1.parse(jsonObj.getString(dateOrder));
                java.sql.Date dateOrderSql = new java.sql.Date(date.getTime());
                store.setDateOrder(dateOrderSql);
            }
            if(jsonObj.has(confirmationCode)){
                store.setConfirmationCode(jsonObj.getString(confirmationCode));
            }
            if(jsonObj.has(status)){
                store.setStatus(jsonObj.getBoolean(status));
            }if(jsonObj.has(ratingPoint)){
                store.setRatingPoint(BigDecimal.valueOf(jsonObj.getDouble(ratingPoint)).floatValue());
            }if(jsonObj.has(accountId)){
                store.setAccountId(jsonObj.getInt(accountId));
            }

        }catch (Exception e){
            Log.e("Error Aroud", e.getMessage());
        }
        return store;
    }

}
