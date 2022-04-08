package com.android.ezlo.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static RequestApi getInstance() {

        String BASEURL="https://veramobile.mios.com/";

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()

//comment this Interceptor because it show the api url
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(client).
                addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit.create(RequestApi.class);
    }
}
