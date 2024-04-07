package com.example.and103_assignmentht.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.model.Distributor;
import com.example.and103_assignmentht.service.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.ViewHolder> {
    private ArrayList<Distributor> list;
    private Context context;
    private DistributorClick distributorClick;

    public DistributorAdapter(ArrayList<Distributor> list, Context context, DistributorClick distributorClick) {
        this.list = list;
        this.context = context;
        this.distributorClick = distributorClick;
    }

    public interface DistributorClick {
        void delete(Distributor distributor);
        void edit(String id,Distributor distributor);
    }

    @NonNull
    @Override
    public DistributorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_distributor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorAdapter.ViewHolder holder, int position) {
        Distributor distributor = list.get(position);
        holder.tv_name.setText(distributor.getName());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributorClick.delete(distributor);
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributorClick.edit(distributor.getId(), distributor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageButton btn_edit,btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
