package com.android.ezlo.Adapter;

import static com.android.ezlo.Views.MainActivity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ezlo.Model.Devices;
import com.android.ezlo.R;
import com.android.ezlo.Retrofit.ApiClient;
import com.android.ezlo.Retrofit.RequestApi;
import com.android.ezlo.Views.Fragments.DevicesDetailsFragment;

import java.text.BreakIterator;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesDetailsAdapter extends RecyclerView.Adapter<DevicesDetailsAdapter.ViewHolder> {

    public static ArrayList<Devices.DevicesDetails> dataSet;
    private final Context context;
    private int lastPosition = -1;

    public DevicesDetailsAdapter(ArrayList<Devices.DevicesDetails> dataSet, Context context) {
        DevicesDetailsAdapter.dataSet = dataSet;
        this.context = context;
    }


    @Override
    public DevicesDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.devices_details_adapter, parent, false);
        return new DevicesDetailsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Devices.DevicesDetails dataModel = dataSet.get(position);
        lastPosition = holder.getAdapterPosition();
        if (dataModel.getPlatform().equals("Sercomm G450")) {
            holder.image_View.setBackgroundResource(R.drawable.vera_plus_big);
        } else if (dataModel.getPlatform().equals("Sercomm NA301")) {
            holder.image_View.setBackgroundResource(R.drawable.vera_edge_big);
        } else {
            holder.image_View.setBackgroundResource(R.drawable.vera_edge_big);
        }
        holder.tv_platform.setText(dataModel.getPlatform());
        holder.tv_sNo.setText("SN: " + dataModel.getPK_Device());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.setValues(0); // Editable No
                Fragment devicesDetailsFragment = new DevicesDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Platform", dataModel.getPlatform());
                bundle.putString("PK_Device", dataModel.getPK_Device());
                bundle.putString("MacAddress", dataModel.getMacAddress());
                bundle.putString("Firmware", dataModel.getFirmware());
                bundle.putString("IsEditable", "no");
                bundle.putString("Position", String.valueOf(lastPosition));
                devicesDetailsFragment.setArguments(bundle);
                ((AppCompatActivity) activity).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, devicesDetailsFragment)
                        .addToBackStack(null).commit();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(v.getContext(), "Position is " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                showDialogue(dataModel.getPK_Device());
                return false;
            }
        });
    }


    private void showDialogue(String PK_Device) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to Delete ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        DeleteRequest(PK_Device);
                        RemoveList(lastPosition);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void RemoveList(int lastPosition) {
        dataSet.remove(lastPosition);
        notifyItemRemoved(lastPosition);
        notifyDataSetChanged();
//        notifyItemRangeChanged(lastPosition, dataSet.size());
    }

    private void DeleteRequest(String PK_Device) {
        RequestApi api = ApiClient.getInstance();
        Call<Void> call = api.deletePost(PK_Device);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.e("Response successful", ": " + response.code());

                } else {
                    Toast.makeText(activity, "Something went wrong, Try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("Error In Deleting", ": " + t);
                Toast.makeText(activity, "Unable to connect the server at this time, please try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ln_recyclerView;
        ImageView image_View;
        ImageView IV_edit;
        ImageView IV_details;
        TextView tv_platform;
        TextView tv_sNo;

        LinearLayout ln_details;
        ImageView image_View_details;
        TextView tv_platform_details;
        TextView tv_sNo_details;
        TextView tv_macAddress_details;
        TextView tv_firmWare_details;
        EditText et_platform_details;
        Button btn_save_changes;


        public ViewHolder(View itemView) {
            super(itemView);
            this.ln_recyclerView = itemView.findViewById(R.id.ln_recyclerView);
            this.image_View = itemView.findViewById(R.id.image_View);
            this.IV_edit = itemView.findViewById(R.id.IV_edit);
            this.IV_details = itemView.findViewById(R.id.IV_details);
            this.tv_platform = itemView.findViewById(R.id.tv_platform);
            this.tv_sNo = itemView.findViewById(R.id.tv_sNo);

            this.ln_details = itemView.findViewById(R.id.ln_details);
            this.image_View_details = itemView.findViewById(R.id.image_View_details);
            this.tv_platform_details = itemView.findViewById(R.id.tv_platform_details);
            this.tv_sNo_details = itemView.findViewById(R.id.tv_sNo_details);
            this.tv_macAddress_details = itemView.findViewById(R.id.tv_macAddress_details);
            this.tv_firmWare_details = itemView.findViewById(R.id.tv_firmWare_details);
            this.et_platform_details = itemView.findViewById(R.id.et_platform_details);
            this.btn_save_changes = itemView.findViewById(R.id.btn_save_changes);


            this.IV_edit.setOnClickListener(v -> {

//                setValues(1); // Editable Yes
                Fragment devicesDetailsFragment = new DevicesDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Platform", dataSet.get(getAdapterPosition()).getPlatform());
                bundle.putString("PK_Device", dataSet.get(getAdapterPosition()).getPK_Device());
                bundle.putString("MacAddress", dataSet.get(getAdapterPosition()).getMacAddress());
                bundle.putString("Firmware", dataSet.get(getAdapterPosition()).getFirmware());
                bundle.putString("IsEditable", "yes");
                bundle.putString("Position", String.valueOf(getAdapterPosition()));
                devicesDetailsFragment.setArguments(bundle);
                ((AppCompatActivity) activity).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, devicesDetailsFragment)
                        .addToBackStack(null).commit();
            });

        }

//        private void setValues(int editAble) {
//
//            ln_recyclerView.setVisibility(View.GONE);
//            ln_details.setVisibility(View.VISIBLE);
//
//            if (dataSet.get(getAdapterPosition()).getPlatform().equals("Sercomm G450")) {
//                image_View.setBackgroundResource(R.drawable.vera_plus_big);
//            } else if (dataSet.get(getAdapterPosition()).getPlatform().equals("Sercomm NA301")) {
//                image_View.setBackgroundResource(R.drawable.vera_edge_big);
//            } else {
//                image_View.setBackgroundResource(R.drawable.vera_edge_big);
//            }
//
//            if (editAble == 1) {
//                tv_platform_details.setVisibility(View.GONE);
//                et_platform_details.setVisibility(View.VISIBLE);
//                et_platform_details.setHint(dataSet.get(getAdapterPosition()).getPlatform());
//                btn_save_changes.setVisibility(View.VISIBLE);
//
//                btn_save_changes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        checkFields();
//                    }
//                });
//
//            } else {
//                et_platform_details.setVisibility(View.GONE);
//                tv_platform_details.setVisibility(View.VISIBLE);
//                tv_platform_details.setText(dataSet.get(getAdapterPosition()).getPlatform());
//            }
//
//            tv_sNo_details.setText("SN: " + dataSet.get(getAdapterPosition()).getPK_Device());
//            tv_macAddress_details.setText("MAC Address: " + dataSet.get(getAdapterPosition()).getMacAddress());
//            tv_firmWare_details.setText("Firmware: " + dataSet.get(getAdapterPosition()).getFirmware());
//        }
//
//        private void checkFields() {
//            if (btn_save_changes.getText().toString().equals("")) {
//                Toast.makeText(activity, "Field can't be left empty", Toast.LENGTH_SHORT).show();
//            } else {
//                UpdateField();
//            }
//        }
//
//        private void UpdateField() {
//
//        }
    }
}
