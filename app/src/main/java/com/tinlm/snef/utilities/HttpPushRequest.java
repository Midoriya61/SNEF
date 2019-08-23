package com.tinlm.snef.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPushRequest extends AsyncTask<URL, Void, HttpURLConnection>  {
    @Override
    protected HttpURLConnection doInBackground(URL... urls) {
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) urls[0].openConnection();
            client.setRequestMethod("PUT");
            client.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }
}
