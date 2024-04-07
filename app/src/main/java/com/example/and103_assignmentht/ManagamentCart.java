package com.example.and103_assignmentht;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.and103_assignmentht.model.Fruit;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ManagamentCart {
    private Context context;
    private TinyDB tinyDB;

    private static final String PREF_NAME = "cart_prefs";
    private SharedPreferences preferences;
    private Gson gson;


    public ManagamentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);

        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void insertFood(Fruit item) {
        ArrayList<Fruit> listFood = getListCart();
        boolean exitsAlready = false;
        for (Fruit fruit : listFood) {
            if (fruit.getName().equals(item.getName())) {
                exitsAlready = true;
                break;
            }
        }

        if (!exitsAlready) {
            listFood.add(item);
            tinyDB.putListObject("CartList", listFood);
            Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "This item is already in your cart", Toast.LENGTH_SHORT).show();
        }
    }
    public void clearCart() {
        // Xóa toàn bộ danh sách sản phẩm khỏi giỏ hàng
        tinyDB.clear();
    }


    public ArrayList<Fruit> getListCart() {
        return tinyDB.getListObject("CartList");
    }
}
