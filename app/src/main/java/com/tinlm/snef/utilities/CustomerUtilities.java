package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Customer;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerUtilities {
    private static final String accountId = "accountId";
    private static final String userName = "userName";
    private static final String password = "password";
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String phone = "phone";
    private static final String email = "email";
    private static final String avatar = "avatar";
    private static final String customerId = "customerId";
    private static final String active = "active";


    public Customer login(String username, String password){
        Customer result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CustomerURL + ConstainServer.LoginURL + username + "/" + password;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONObject jsonObj = new JSONObject(respone);
            if (jsonObj != null) {
                result = new Customer();
                if(jsonObj.has(accountId)){
                    result.setUsername(jsonObj.getString(userName));
                }if(jsonObj.has(firstName)){
                    result.setFirstName(jsonObj.getString(firstName));
                }if(jsonObj.has(lastName)){
                    result.setLastName(jsonObj.getString(lastName));
                }if(jsonObj.has(phone)){
                    result.setPhone(jsonObj.getString(phone));
                }if(jsonObj.has(email)){
                    result.setEmail(jsonObj.getString(email));
                }if(jsonObj.has(avatar)){
                    result.setAvatar(jsonObj.getString(avatar));
                }if(jsonObj.has(customerId)){
                    result.setCustomerId(jsonObj.getInt(customerId));
                }if(jsonObj.has(active)){
                    result.setActive(jsonObj.getBoolean(active));
                }
            }
        }catch (Exception e){
            Log.e("ErrorGetUser", e.getMessage());
        }
        return result;
    }

    public boolean register(String username, String password, String firstName, String lastName) {
        boolean result = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CustomerURL + ConstainServer.CreateURL + username + "/" + password
                + "/" + firstName + "/" + lastName;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpURLConnection client = ReadStream.getPostConnection(urll);
            respone = ReadStream.readStream(client.getInputStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            Log.e("ErrorAddUser", e.getMessage());
            return result;
        }
        return result;
    }

}
