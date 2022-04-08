package com.android.ezlo.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.ezlo.Adapter.DevicesDetailsAdapter;
import com.android.ezlo.Model.Devices;
import com.android.ezlo.R;

public class DevicesDetailsFragment extends Fragment {

    Context context;
    ImageView image_View;
    TextView tv_platform_details;
    TextView tv_sNo_details;
    TextView tv_macAddress_details;
    TextView tv_firmWare_details;
    EditText et_platform_details;
    Button btn_save_changes;

    String Platform = "";
    String PK_Device = "";
    String MacAddress = "";
    String Firmware = "";
    String IsEditable = "";
    String Position = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices_details, container, false);
        context = getContext();
        findViews(view);
        getBundles();
        return view;
    }

    private void findViews(View view) {
        image_View = view.findViewById(R.id.image_View_details);
        tv_platform_details = view.findViewById(R.id.tv_platform_details);
        tv_sNo_details = view.findViewById(R.id.tv_sNo_details);
        tv_macAddress_details = view.findViewById(R.id.tv_macAddress_details);
        tv_firmWare_details = view.findViewById(R.id.tv_firmWare_details);
        et_platform_details = view.findViewById(R.id.et_platform_details);
        btn_save_changes = view.findViewById(R.id.btn_save_changes);
    }

    private void getBundles() {
        Bundle mBundle;
        mBundle = getArguments();
        if (mBundle != null) {
            Platform = mBundle.getString("Platform");
            PK_Device = mBundle.getString("PK_Device");
            MacAddress = mBundle.getString("MacAddress");
            Firmware = mBundle.getString("Firmware");
            IsEditable = mBundle.getString("IsEditable");
            Position = mBundle.getString("Position");

            Log.e("Test ", "Bundle Platform : " + Platform);
            Log.e("Test ", "Bundle PK_Device : " + PK_Device);
            Log.e("Test ", "Bundle MacAddress : " + MacAddress);
            Log.e("Test ", "Bundle Firmware : " + Firmware);
            Log.e("Test ", "Bundle IsEditable : " + IsEditable);

            setValues();
        }
    }

    private void setValues() {

        if (Platform.equals("Sercomm G450")) {
            image_View.setBackgroundResource(R.drawable.vera_plus_big);
        } else if (Platform.equals("Sercomm NA301")) {
            image_View.setBackgroundResource(R.drawable.vera_edge_big);
        } else {
            image_View.setBackgroundResource(R.drawable.vera_edge_big);
        }

        if (IsEditable.equals("yes")) {
            tv_platform_details.setVisibility(View.GONE);
            et_platform_details.setVisibility(View.VISIBLE);
            et_platform_details.setHint(Platform);
            btn_save_changes.setVisibility(View.VISIBLE);

            btn_save_changes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkFields();
                }
            });

        } else {
            et_platform_details.setVisibility(View.GONE);
            tv_platform_details.setVisibility(View.VISIBLE);
            tv_platform_details.setText(Platform);
        }


        tv_sNo_details.setText("SN: " + PK_Device);
        tv_macAddress_details.setText("MAC Address: " + MacAddress);
        tv_firmWare_details.setText("Firmware: " + Firmware);

    }

    private void checkFields() {
        if (btn_save_changes.getText().toString().equals("")){
            Toast.makeText(context, "Field can't be left empty", Toast.LENGTH_SHORT).show();
        }else {
            UpdateField();
        }
    }

    private void UpdateField() {
        Toast.makeText(context, "it can be done because you didn't give an update API", Toast.LENGTH_SHORT).show();
    }
}
