package com.example.and103_assignmentht.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.and103_assignmentht.ChangeNumberItemList;
import com.example.and103_assignmentht.ManagamentCart;
import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.model.Fruit;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private ArrayList<Fruit> list;
    private ManagamentCart managamentCart;
    private ChangeNumberItemList changeNumberItemsList;
    public CartAdapter(ArrayList<Fruit> list, Context context, ChangeNumberItemList changeNumberItemsList) {
        this.list = list;
        this.managamentCart = new ManagamentCart(context);
        this.changeNumberItemsList = changeNumberItemsList;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.titleTxt.setText(fruit.getName());
        holder.totalEachItem.setText(fruit.getPrice());
        holder.desEachItem.setText(fruit.getDescription());

        String url = fruit.getImage().get(0);
        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt,totalEachItem,desEachItem;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            totalEachItem =itemView.findViewById(R.id.totalEachItem);
            pic = itemView.findViewById(R.id.pic);
            desEachItem = itemView.findViewById(R.id.desEachItem);
        }
    }
}
