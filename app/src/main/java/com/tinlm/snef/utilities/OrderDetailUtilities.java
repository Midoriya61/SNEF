package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.OrderDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailUtilities {

    private static final String orderDetailId = "orderDetailId";
    private static final String orderId = "orderId";
    private static final String flashSaleProductId = "flashSaleProductId";
    private static final String quantity = "quantity";
    private static final String orderDetailPrice = "orderDetailPrice";

    // Get quantity of store product by store product id
    public int getQuantityByFSPId(int flashsaleProductId) {
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

        } catch (Exception e) {
            Log.e("EODQ", e.getMessage());
        }
        return result;
    }

    //Insert new order detail into the created order
    public void insertNewOrderDetail(int orderId, int fspId, int quantity, float orderDetailPrice) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderDetailURL + ConstainServer.InsertNewOrderDetail + "/" + orderId + "/" + fspId + "/" + quantity + "/" + orderDetailPrice;
//        String respone = "";

        try {
            URL urll = new URL(url);
            ReadStream.readStream(urll.openStream());

        } catch (Exception e) {
            Log.e("EINOD", e.getMessage());
        }
    }

    //Get all order detail by order ID
    public List<OrderDetail> getAllOrderDetailByOrderId(int orderId) {
        List<OrderDetail> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.OrderDetailURL + ConstainServer.GetAllOrderDetailByOrderId + orderId;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                OrderDetail orderDetail = new OrderDetail();

                if (jsonObj.has(orderDetailId)) {
                    orderDetail.setOrderDetailId(jsonObj.getInt(orderDetailId));
                }

                if (jsonObj.has(flashSaleProductId)) {
                    orderDetail.setFlashSaleProductId(jsonObj.getInt(flashSaleProductId));
                }

                if (jsonObj.has(quantity)) {
                    orderDetail.setQuantity(jsonObj.getInt(quantity));
                }

                if (jsonObj.has(orderDetailPrice)) {
                    orderDetail.setOrderDetailPrice(BigDecimal.valueOf(jsonObj.getDouble(orderDetailPrice)).floatValue());
                }

                orderDetail.setOrderId(orderId);


                result.add(orderDetail);
            }

        } catch (Exception e) {
            Log.e("Error order detail", e.getMessage());
        }
        return result;
    }
}
