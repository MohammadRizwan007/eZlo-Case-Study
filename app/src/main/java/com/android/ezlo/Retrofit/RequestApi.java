package com.android.ezlo.Retrofit;

import com.android.ezlo.Model.Devices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {
    @GET("test_android/items.test")
    Call<Devices> eZLO();

    @GET("test_android/items.test/{PK_Device}")
    Call<Void> deletePost(@Path("PK_Device") String PK_Device);
}
