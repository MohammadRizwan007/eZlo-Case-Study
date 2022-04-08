package com.android.ezlo.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.ezlo.Adapter.DevicesDetailsAdapter;
import com.android.ezlo.R;
import com.android.ezlo.Retrofit.ApiClient;
import com.android.ezlo.Retrofit.RequestApi;
import com.android.ezlo.Model.Devices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Context activity;
    ProgressBar progress_bar;

    ArrayList<Devices.DevicesDetails> dataModels;
    DevicesDetailsAdapter adapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        findViews();
        getList();
    }

    private void findViews() {
        recycler_view = findViewById(R.id.recycler_view);
        progress_bar = findViewById(R.id.progress_bar);
    }

    private void getList() {
        progress_bar.setVisibility(View.VISIBLE);
        dataModels = new ArrayList<>();
        RequestApi api = ApiClient.getInstance();
        Call<Devices> call = api.eZLO();
        call.enqueue(new Callback<Devices>() {
            @Override
            public void onResponse(@NonNull Call<Devices> call, @NonNull Response<Devices> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(View.GONE);
                    assert response.body() != null;
                    ArrayList<Devices.DevicesDetails> arrayList = response.body().getDevices();
                    Log.e("PK_Device",": "+arrayList);
                    if (arrayList.size() > 0){
                        for (Devices.DevicesDetails data : arrayList) {
                            Log.e("PK_Device",": "+data.getPK_Device());
                            dataModels.add(new Devices.DevicesDetails(data.getPlatform(),data.getPK_Device(),data.getMacAddress(),data.getFirmware()));
                            adapter = new DevicesDetailsAdapter(dataModels, getApplicationContext());
                            recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recycler_view.setHasFixedSize(false);
                            recycler_view.setAdapter(adapter);
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong, Try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Devices> call, @NonNull Throwable t) {
                Log.e("Error",": "+t);
                Toast.makeText(getApplicationContext(), "Unable to connect the server at this time, please try again later.", Toast.LENGTH_LONG).show();
            }
        });

    }
}