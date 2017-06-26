package com.tairui.smartcommunity.tenant.net;

import com.tairui.smartcommunity.tenant.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jeffery on 2017/5/4.
 */

public class ApiService {

    private static ApiInterface mApiInterface = null;


    public static ApiInterface getService() {
        if (mApiInterface == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            mApiInterface = retrofit.create(ApiInterface.class);
        }
        return mApiInterface;
    }

}
