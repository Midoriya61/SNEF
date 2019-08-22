package com.tinlm.snef.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static String CACHE_CONTROL = "Cache-Control";
    private static final String TIME_CACHE_ONLINE_30 = "public, max-age = 10";
    private static final String TIME_CACHE_ONLINE_1 = "public, max-age = 10";
    private static final String TIME_CACHE_OFFLINE_30 = "public, only-if-cached, max-stale = 60 * 30";
    private static final String TIME_CACHE_OFFLINE_1 = "public, only-if-cached, max-stale = 60 * 60 * 24";
    private static final Long  Half_Hour =  1000*60*30L;
    private static final Long  One_Day =  1000*60 * 60 * 24L;



//    public static Retrofit getClient(String baseUrl) {
//        if (retrofit==null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        return retrofit;
            //    }
            public static Retrofit getClient(String baseUrl) {
                if (retrofit==null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
                return retrofit;
            }

            public static Retrofit getClient30(final Context context, Cache cache, final String urlSharePrefer) {
//                ConnectionPool pool = new ConnectionPool(5, 1000*60*60, TimeUnit.MILLISECONDS);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .cache(cache)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain)
                                    throws IOException {
                                Request request = chain.request();
                                if (!isNetworkAvailable(context)) {
                                    request = request.newBuilder()
                                            .header(CACHE_CONTROL, TIME_CACHE_OFFLINE_30)
                                            .build();
                                } else {
                                    SharedPreferences sharedPreferences = context.getSharedPreferences(ConstainApp.APICACHING, MODE_PRIVATE);
                                    long datesdaf = sharedPreferences.getLong(urlSharePrefer, 0);
                                    Date date = Calendar.getInstance().getTime();
                                    long millisecond = date.getTime();
                                    if (datesdaf == 0) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putLong(urlSharePrefer,millisecond);
                                        editor.apply();
                                        request = request.newBuilder().header(CACHE_CONTROL, TIME_CACHE_ONLINE_30).build();
                                    } else {
                                        if ( millisecond - datesdaf <= Half_Hour && millisecond - datesdaf >= 3000) {
                                            request = request.newBuilder()
                                                    .header(CACHE_CONTROL, TIME_CACHE_OFFLINE_30)
                                                    .build();
                                        } else {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putLong(urlSharePrefer,millisecond);
                                            editor.apply();
                                            request = request.newBuilder().header(CACHE_CONTROL, TIME_CACHE_ONLINE_30).build();

                                        }
                                    }
                                }
                                return chain.proceed(request);
                            }
                        })
                        .build();
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(ConstainServer.BaseURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit30 = builder.build();
                return retrofit30;
            }

    public static Retrofit getClient1(final Context context, Cache cache, final String urlSharePrefer) {
//        ConnectionPool pool = new ConnectionPool(5, 1000*60*60, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain)
                            throws IOException {
                        Request request = chain.request();

                        if (!isNetworkAvailable(context)) {
                            request = request.newBuilder()
                                    .header(CACHE_CONTROL, TIME_CACHE_OFFLINE_1)
                                    .build();
                        } else {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(ConstainApp.APICACHING, MODE_PRIVATE);
                            long datesdaf = sharedPreferences.getLong(urlSharePrefer, 0);
                            Date date = Calendar.getInstance().getTime();
                            long millisecond = date.getTime();
                            if (datesdaf == 0) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putLong(urlSharePrefer,millisecond);
                                editor.apply();
                                request = request.newBuilder().header(CACHE_CONTROL, TIME_CACHE_ONLINE_1).build();
                            } else {
                                if ( millisecond - datesdaf <= One_Day && millisecond - datesdaf >= 3000) {
                                    request = request.newBuilder()
                                            .header(CACHE_CONTROL, TIME_CACHE_OFFLINE_1)
                                            .build();
                                } else {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putLong(urlSharePrefer,millisecond);
                                    editor.apply();
                                    request = request.newBuilder().header(CACHE_CONTROL, TIME_CACHE_ONLINE_1).build();
                                }
                            }
                        }
                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ConstainServer.BaseURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = builder.build();
        return retrofit1;
    }


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
