package com.teammusa.mematerial.api_network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HashCode on 6:35 AM 20/03/2018.
 */

public class RetrofitBuilder {
    public static final String BASE_URL = "https://8ff4e50a.ngrok.io/";
    //http://127.0.0.1:3000/

    public static Retrofit getRetrofit() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(170, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }
}
