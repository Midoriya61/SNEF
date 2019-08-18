package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderUtilities {

    private static final String orderId = "orderId";
    private static final String dateOrder = "dateOrder";
    private static final String confirmationCode = "confirmationCode";
    private static final String status = "status";
    private static final String ratingPoint = "ratingPoint";
    private static final String accountId = "accountId";
    private static final String totalPrice = "totalPrice";
    private static final String orderQuantity = "orderQuantity";
    private static final String storeId = "storeId";
    private static final String storeName = "storeName";
    private static final String comment = "comment";

    public SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

    public void insertNewOrder(String confirmationCode, int accountId, int storeId) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.InsertNewOrder + confirmationCode + "/" + accountId + "/" + storeId;
//        String respone = "";

        try {
            URL urll = new URL(url);
            ReadStream.readStream(urll.openStream());

        } catch (Exception e) {
            Log.e("EINO", e.getMessage());
        }
    }

    public List<Order> getAllOrder() {
        List<Order> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetAllOrder;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Order order = new Order();

                if (jsonObj.has(orderId)) {
                    order.setOrderId(jsonObj.getInt(orderId));
                }
                if (jsonObj.has(dateOrder)) {
                    java.util.Date date = sdf1.parse(jsonObj.getString(dateOrder));
                    java.sql.Date dateOrderSql = new java.sql.Date(date.getTime());
                    order.setDateOrder(dateOrderSql);
                }
                if (jsonObj.has(confirmationCode)) {
                    order.setConfirmationCode(jsonObj.getString(confirmationCode));
                }
                if (jsonObj.has(status)) {
                    order.setStatus(jsonObj.getBoolean(status));
                }
                if (jsonObj.has(ratingPoint)) {
                    order.setRatingPoint(BigDecimal.valueOf(jsonObj.getDouble(ratingPoint)).floatValue());
                }
                if (jsonObj.has(accountId)) {
                    order.setAccountId(jsonObj.getInt(accountId));
                }
                if (jsonObj.has(totalPrice)) {
                    order.setTotalPrice(BigDecimal.valueOf(jsonObj.getDouble(totalPrice)).floatValue());
                }
                if (jsonObj.has(orderQuantity)) {
                    order.setOrderQuantity(jsonObj.getInt(orderQuantity));
                }
                if (jsonObj.has(storeId)) {
                    order.setStoreId(jsonObj.getInt(storeId));
                }
                if (jsonObj.has(storeName)) {
                    order.setStoreName(jsonObj.getString(storeName));
                }
                if (jsonObj.has(comment)) {
                    order.setComment(jsonObj.getString(comment));
                }

                result.add(order);
            }

        } catch (Exception e) {
            Log.e("Error AllOrder", e.getMessage());
        }
        return result;
    }

    public List<Order> getOrderByAccountId(int accountId) {
        List<Order> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetOrderByAccountId + accountId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Order order = new Order();

                if (jsonObj.has(orderId)) {
                    order.setOrderId(jsonObj.getInt(orderId));
                }
                if (jsonObj.has(dateOrder)) {
                    java.util.Date date = sdf1.parse(jsonObj.getString(dateOrder));
                    java.sql.Date dateOrderSql = new java.sql.Date(date.getTime());
                    order.setDateOrder(dateOrderSql);
                }
                if (jsonObj.has(confirmationCode)) {
                    order.setConfirmationCode(jsonObj.getString(confirmationCode));
                }
                if (jsonObj.has(status)) {
                    order.setStatus(jsonObj.getBoolean(status));
                }
                if (jsonObj.has(ratingPoint)) {
                    order.setRatingPoint(BigDecimal.valueOf(jsonObj.getDouble(ratingPoint)).floatValue());
                }

                order.setAccountId(accountId);

                if (jsonObj.has(totalPrice)) {
                    order.setTotalPrice(BigDecimal.valueOf(jsonObj.getDouble(totalPrice)).floatValue());
                }
                if (jsonObj.has(orderQuantity)) {
                    order.setOrderQuantity(jsonObj.getInt(orderQuantity));
                }
                if (jsonObj.has(storeId)) {
                    order.setStoreId(jsonObj.getInt(storeId));
                }
                if (jsonObj.has(storeName)) {
                    order.setStoreName(jsonObj.getString(storeName));
                }
                if (jsonObj.has(comment)) {
                    order.setComment(jsonObj.getString(comment));
                }

                result.add(order);
            }

        } catch (Exception e) {
            Log.e("Error AllOrder", e.getMessage());
        }
        return result;
    }

    public int getLastOrderId(int accountId) {
        int result = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetLastOrderId + accountId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            result = Integer.parseInt(respone);

        } catch (Exception e) {
            Log.e("EGLOI", e.getMessage());
        }
        return result;
    }

    public Order getOrderById(int orderId) {
        Order order = new Order();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.GetOrderById + orderId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONObject jsonObj = new JSONObject(respone);

            order.setOrderId(orderId);

            if (jsonObj.has(dateOrder)) {
                java.util.Date date = sdf1.parse(jsonObj.getString(dateOrder));
                java.sql.Date dateOrderSql = new java.sql.Date(date.getTime());
                order.setDateOrder(dateOrderSql);
            }
            if (jsonObj.has(confirmationCode)) {
                order.setConfirmationCode(jsonObj.getString(confirmationCode));
            }
            if (jsonObj.has(status)) {
                order.setStatus(jsonObj.getBoolean(status));
            }
            if (jsonObj.has(ratingPoint)) {
                order.setRatingPoint(BigDecimal.valueOf(jsonObj.getDouble(ratingPoint)).floatValue());
            }
            if (jsonObj.has(accountId)) {
                order.setAccountId(jsonObj.getInt(accountId));
            }
            if (jsonObj.has(totalPrice)) {
                order.setTotalPrice(BigDecimal.valueOf(jsonObj.getDouble(totalPrice)).floatValue());
            }
            if (jsonObj.has(orderQuantity)) {
                order.setOrderQuantity(jsonObj.getInt(orderQuantity));
            }
            if (jsonObj.has(storeId)) {
                order.setStoreId(jsonObj.getInt(storeId));
            }
            if (jsonObj.has(storeName)) {
                order.setStoreName(jsonObj.getString(storeName));
            }
            if (jsonObj.has(comment)) {
                order.setComment(jsonObj.getString(comment));
            }

        } catch (Exception e) {
            Log.e("Error GOBI", e.getMessage());
        }
        return order;
    }

    public void submitFeedback(int orderId, float ratingPoint, String comment) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderURL + ConstainServer.SubmitFeedback + orderId + "/" + ratingPoint + "/" + comment;
//        String respone = "";

        try {
            URL urll = new URL(url);
            ReadStream.readStream(urll.openStream());

        } catch (Exception e) {
            Log.e("ESFB", e.getMessage());
        }
    }

}
