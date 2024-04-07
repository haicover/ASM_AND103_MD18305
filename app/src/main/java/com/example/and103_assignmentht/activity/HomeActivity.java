package com.example.and103_assignmentht.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.adapter.DistributorAdapter;
import com.example.and103_assignmentht.model.Distributor;
import com.example.and103_assignmentht.service.HttpRequest;
import com.example.and103_assignmentht.model.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



import retrofit2.Call;
import retrofit2.Callback;


public class HomeActivity extends AppCompatActivity implements DistributorAdapter.DistributorClick {
    private HttpRequest httpRequest;
    private ArrayList<Distributor> list = new ArrayList<>();
    private DistributorAdapter adapter;
    private static final String TAG = "HomeActivity";
    EditText ed_search;
    RecyclerView rcv_distributor;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation();

        httpRequest = new HttpRequest();
        httpRequest.callAPI()
                .getListDistributor()
                .enqueue(getDistributorAPI);
        ed_search = findViewById(R.id.ed_search);
        rcv_distributor = findViewById(R.id.rcv_distributor);
        btnAdd = findViewById(R.id.btnAdd);

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String key = ed_search.getText().toString().trim();
                    httpRequest.callAPI()
                            .searchDistributor(key)
                            .enqueue(getDistributorAPI);
                    Log.d(TAG, "onEditorAction: " + key);
                    return true;
                }
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

    }
    private void bottomNavigation() {
        ImageView personBtn = findViewById(R.id.btnPerson);
        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });
        ImageView btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FruitActivity.class));
            }
        });
        ImageView btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            }
        });
    }

    private void showDialogAdd() {
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_add);

        EditText add_name = dialog.findViewById(R.id.add_name);
        Button btnThem  = dialog.findViewById(R.id.btnThem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "you must enter name", Toast.LENGTH_SHORT).show();
                }   else {
                    Distributor distributor = new Distributor();
                    distributor.setName(name);
                    httpRequest.callAPI()
                            .addDistributor(distributor)
                            .enqueue(responseDistributorAPI);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }




    private void getData() {
        adapter = new DistributorAdapter(list, this,this );
        rcv_distributor.setAdapter(adapter);
    }


    Callback<Response<ArrayList<Distributor>>> getDistributorAPI = new Callback<Response<ArrayList<Distributor>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Distributor>>> call, retrofit2.Response<Response<ArrayList<Distributor>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    list = response.body().getData();
                    getData();
                    Log.d(TAG, "onResponse: "+ list.size());
                }
            }
        }
        @Override
        public void onFailure(Call<Response<ArrayList<Distributor>>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t.getMessage() );
        }


    };


    Callback<Response<Distributor>> responseDistributorAPI  = new Callback<Response<Distributor>>() {
        @Override
        public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callAPI()
                            .getListDistributor()
                            .enqueue(getDistributorAPI);
                    Toast.makeText(HomeActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Distributor>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+t.getMessage() );
        }
    };

    private void showDialogEdit(String id,Distributor distributor) {
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_update);

        EditText up_name = dialog.findViewById(R.id.up_name);
        Button btnUpdate  = dialog.findViewById(R.id.btnUpdate);

        String distributorName = distributor.getName();
        up_name.setText(distributorName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = distributor.getName();

                if (name.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "you must enter name", Toast.LENGTH_SHORT).show();
                }   else {

                    Distributor distributor1 = new Distributor();
                    distributor1.setName(up_name.getText().toString().trim());
                    httpRequest.callAPI()
                            .updateDistributor(id,distributor1)
                            .enqueue(responseDistributorAPI);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    @Override
    public void delete(Distributor distributor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("yes", (dialog, which) -> {
            httpRequest.callAPI()
                    .deleteDistributor(distributor.getId())
                    .enqueue(responseDistributorAPI);
        });
        builder.setNegativeButton("no", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
    @Override
    public void edit(String id,Distributor distributor) {
        showDialogEdit(id,distributor);
    }
}