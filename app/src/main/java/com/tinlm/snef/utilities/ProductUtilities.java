package com.tinlm.snef.utilities;

import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// TinLM Create getListNameProduct
public class ProductUtilities {

    private static final String productId = "productId";
    private static final String productName = "productName";
    private static final String categoriesId = "categoriesId";
    private static final String imageSrc = "imageSrc";


    public List<Product> getListNameProduct(){
        List<Product> result = new ArrayList<>();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.ProductURL + ConstainServer.GetListNameProduct;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Product product = new Product();


                if(jsonObj.has(productName)){
                    product.setProductName(jsonObj.getString(productName));
                }

                result.add(product);
            }

        }catch (Exception e){
            Log.e("Error Product", e.getMessage());
        }
        return result;
    }
}
