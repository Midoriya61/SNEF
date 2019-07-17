package com.tinlm.snef.utilities;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Categories;
import com.tinlm.snef.utilities.ReadStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoriesUtilities  {

    private static final String categoriesId = "categoriesId";
    private static final String imageSrc = "imageSrc";
    private static final String categoryName = "categoryName";

    // 6/17/2019 TinLM Create getAllCategories
    public List<Categories> getAllCategories(){
        List<Categories> result = new ArrayList<>();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CategoriesURL;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
//            HttpGetRequest httpGetRequest = new HttpGetRequest();
            respone = httpGetRequest.execute(urll.openStream()).get();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Categories category = new Categories();

                if(jsonObj.has(categoriesId)){
                    category.setCategoriesId(jsonObj.getInt(categoriesId));
                }
                if(jsonObj.has(imageSrc)){
                    category.setImageSrc(jsonObj.getString(imageSrc));
                }
                if(jsonObj.has(categoryName)){
                    category.setCategoryName(jsonObj.getString(categoryName));
                }

                result.add(category);
            }

        }catch (Exception e){
            Log.e("Error categories", e.getMessage());
        }
        return result;
    }
}
