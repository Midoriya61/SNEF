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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FlashSaleProductUtilities {

    private static final String flashSaleProductId = "flashSaleProductId";
    private static final String quantity = "quantity";
    private static final String storeProductId = "storeProductId";
    private static final String flashSalesId = "flashSalesId";
    private static final String productName = "productName";
    private static final String spQuantity = "spQuantity";
    private static final String price = "price";
    private static final String storeId = "storeId";
    private static final String discount = "discount";
    private static final String endDate = "endDate";

    // 6/17/2019 TinLM Create getHotFlashSaleProduct
    public List<FlashSaleProduct> getHotFlashSaleProduct() {
        List<FlashSaleProduct> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.FlashSaleProductURL + ConstainServer.GetHotFlashSaleProductURL;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                FlashSaleProduct flashSaleProduct = new FlashSaleProduct();

                if (jsonObj.has(flashSaleProductId)) {
                    flashSaleProduct.setFlashSaleProductId(jsonObj.getInt(flashSaleProductId));
                }
                if (jsonObj.has(quantity)) {
                    flashSaleProduct.setQuantity(jsonObj.getInt(quantity));
                }
                if (jsonObj.has(storeProductId)) {
                    flashSaleProduct.setStoreProductId(jsonObj.getInt(storeProductId));
                }
                if (jsonObj.has(flashSalesId)) {
                    flashSaleProduct.setFlashSalesId(jsonObj.getInt(flashSalesId));
                }
                if (jsonObj.has(productName)) {
                    flashSaleProduct.setProductName(jsonObj.getString(productName));
                }
                if (jsonObj.has(spQuantity)) {
                    flashSaleProduct.setSpQuantity(jsonObj.getInt(spQuantity));
                }
                if (jsonObj.has(price)) {
                    flashSaleProduct.setPrice(jsonObj.getInt(price));
                }
                if (jsonObj.has(storeId)) {
                    flashSaleProduct.setStoreId(jsonObj.getInt(storeId));
                }

                if (jsonObj.has(endDate)) {
//                    flashSaleProduct.setEndDate(Date.valueOf(jsonObj.getString(endDate)));
                    flashSaleProduct.setEndDate(jsonObj.getString(endDate));
                }
                if (jsonObj.has(discount)) {
                    flashSaleProduct.setDiscount(jsonObj.getInt(discount));
                }
                result.add(flashSaleProduct);
            }

        } catch (Exception e) {
            Log.e("Error HotFS", e.getMessage());
        }
        return result;
    }

    public List<FlashSaleProduct> getAllFSP() {
        List<FlashSaleProduct> result = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.FlashSaleProductURL + ConstainServer.GetAllFSP;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                FlashSaleProduct flashSaleProduct = new FlashSaleProduct();

                if (jsonObj.has(flashSaleProductId)) {
                    flashSaleProduct.setFlashSaleProductId(jsonObj.getInt(flashSaleProductId));
                }
                if (jsonObj.has(quantity)) {
                    flashSaleProduct.setQuantity(jsonObj.getInt(quantity));
                }
                if (jsonObj.has(storeProductId)) {
                    flashSaleProduct.setStoreProductId(jsonObj.getInt(storeProductId));
                }
                if (jsonObj.has(flashSalesId)) {
                    flashSaleProduct.setFlashSalesId(jsonObj.getInt(flashSalesId));
                }
                if (jsonObj.has(productName)) {
                    flashSaleProduct.setProductName(jsonObj.getString(productName));
                }
                if (jsonObj.has(spQuantity)) {
                    flashSaleProduct.setSpQuantity(jsonObj.getInt(spQuantity));
                }
                if (jsonObj.has(price)) {
                    flashSaleProduct.setPrice(jsonObj.getInt(price));
                }
                if (jsonObj.has(storeId)) {
                    flashSaleProduct.setStoreId(jsonObj.getInt(storeId));
                }

                if (jsonObj.has(endDate)) {
//                    flashSaleProduct.setEndDate(Date.valueOf(jsonObj.getString(endDate)));
                    flashSaleProduct.setEndDate(jsonObj.getString(endDate));
                }
                if (jsonObj.has(discount)) {
                    flashSaleProduct.setDiscount(jsonObj.getInt(discount));
                }
                result.add(flashSaleProduct);
            }

        } catch (Exception e) {
            Log.e("Error AllFS", e.getMessage());
        }
        return result;
    }

    public List<FlashSaleProduct> getFSPByStoreId(int sStoreId) {
        List<FlashSaleProduct> result = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPByStoreId + sStoreId;
        String respone = "";
        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                FlashSaleProduct flashSaleProduct = new FlashSaleProduct();

                if (jsonObj.has(flashSaleProductId)) {
                    flashSaleProduct.setFlashSaleProductId(jsonObj.getInt(flashSaleProductId));
                }
                if (jsonObj.has(quantity)) {
                    flashSaleProduct.setQuantity(jsonObj.getInt(quantity));
                }
                if (jsonObj.has(storeProductId)) {
                    flashSaleProduct.setStoreProductId(jsonObj.getInt(storeProductId));
                }
                if (jsonObj.has(flashSalesId)) {
                    flashSaleProduct.setFlashSalesId(jsonObj.getInt(flashSalesId));
                }
                if (!jsonObj.getString(productName).contains("null")) {
                    flashSaleProduct.setProductName(jsonObj.getString(productName));
                }
                if (jsonObj.has(spQuantity)) {
                    flashSaleProduct.setSpQuantity(jsonObj.getInt(spQuantity));
                }
                if (jsonObj.has(price)) {
                    flashSaleProduct.setPrice(jsonObj.getInt(price));
                }
                if (jsonObj.has(storeId)) {
                    flashSaleProduct.setStoreId(jsonObj.getInt(storeId));
                }

                if (!jsonObj.getString(endDate).contains("null")) {
//                    flashSaleProduct.setEndDate(Date.valueOf(jsonObj.getString(endDate)));
                    flashSaleProduct.setEndDate(jsonObj.getString(endDate));
                }
                if (jsonObj.has(discount)) {
                    flashSaleProduct.setDiscount(jsonObj.getInt(discount));
                }
                result.add(flashSaleProduct);
            }

        } catch (Exception e) {
            Log.e("Error AllFS", e.getMessage());
        }
        return result;
    }

    public List<FlashSaleProduct> getFSPByCategoryId(int categoryId) {
        List<FlashSaleProduct> result = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.FlashSaleProductURL + ConstainServer.GetFSPByCategoryId + categoryId;
        String respone = "";
        try {
            URL url1 = new URL(url);
            respone = ReadStream.readStream(url1.openStream());
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                FlashSaleProduct flashSaleProduct = new FlashSaleProduct();

                if (jsonObj.has(flashSaleProductId)) {
                    flashSaleProduct.setFlashSaleProductId(jsonObj.getInt(flashSaleProductId));
                }
                if (jsonObj.has(quantity)) {
                    flashSaleProduct.setQuantity(jsonObj.getInt(quantity));
                }
                if (jsonObj.has(storeProductId)) {
                    flashSaleProduct.setStoreProductId(jsonObj.getInt(storeProductId));
                }
                if (jsonObj.has(flashSalesId)) {
                    flashSaleProduct.setFlashSalesId(jsonObj.getInt(flashSalesId));
                }
                if (jsonObj.has(productName)) {
                    flashSaleProduct.setProductName(jsonObj.getString(productName));
                }
                if (jsonObj.has(spQuantity)) {
                    flashSaleProduct.setSpQuantity(jsonObj.getInt(spQuantity));
                }
                if (jsonObj.has(price)) {
                    flashSaleProduct.setPrice(jsonObj.getInt(price));
                }
                if (jsonObj.has(storeId)) {
                    flashSaleProduct.setStoreId(jsonObj.getInt(storeId));
                }

                if (!jsonObj.getString(endDate).contains("null")) {
//                    flashSaleProduct.setEndDate(Date.valueOf(jsonObj.getString(endDate)));
                    flashSaleProduct.setEndDate(jsonObj.getString(endDate));
                }
                if (jsonObj.has(discount)) {
                    flashSaleProduct.setDiscount(jsonObj.getInt(discount));
                }
                result.add(flashSaleProduct);
            }
        } catch (Exception e) {
            Log.e("EFSPSID", e.getMessage());
        }
        return result;
    }

    public FlashSaleProduct getFSPById(int flashSaleProductId) {
        FlashSaleProduct fsp = new FlashSaleProduct();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.FlashSaleProductURL +
                ConstainServer.GetFSPById + flashSaleProductId;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONObject jsonObj = new JSONObject(respone);

            fsp.setFlashSaleProductId(flashSaleProductId);

            if (jsonObj.has(quantity)) {
                fsp.setQuantity(jsonObj.getInt(quantity));
            }
            if (jsonObj.has(storeProductId)) {
                fsp.setStoreProductId(jsonObj.getInt(storeProductId));
            }
            if (jsonObj.has(flashSalesId)) {
                fsp.setFlashSalesId(jsonObj.getInt(flashSalesId));
            }
            if (!jsonObj.getString(productName).contains("null")) {
                fsp.setProductName(jsonObj.getString(productName));
            }
            if (jsonObj.has(spQuantity)) {
                fsp.setSpQuantity(jsonObj.getInt(spQuantity));
            }
            if (jsonObj.has(price)) {
                fsp.setPrice(jsonObj.getInt(price));
            }
            if (jsonObj.has(storeId)) {
                fsp.setStoreId(jsonObj.getInt(storeId));
            }

            if (!jsonObj.getString(endDate).contains("null")) {
                fsp.setEndDate(jsonObj.getString(endDate));
            }
            if (jsonObj.has(discount)) {
                fsp.setDiscount(jsonObj.getInt(discount));
            }

        } catch (Exception e) {
            Log.e("Error FSPById", e.getMessage());
        }
        return fsp;
    }


}
