package com.tinlm.snef.utilities;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostRequest  extends AsyncTask<URL, Void, HttpURLConnection> {

    @Override
    protected HttpURLConnection doInBackground(URL... urls) {
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) urls[0].openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }
}
