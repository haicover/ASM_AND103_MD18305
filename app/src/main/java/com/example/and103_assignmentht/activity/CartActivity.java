package com.example.and103_assignmentht.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.and103_assignmentht.ChangeNumberItemList;
import com.example.and103_assignmentht.ManagamentCart;
import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.adapter.CartAdapter;
import com.example.and103_assignmentht.model.Fruit;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagamentCart managamentCart;
    TextView emptyTxt,btnCheckOut;
    private ScrollView scrollView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managamentCart = new ManagamentCart(this);
        btnCheckOut = findViewById(R.id.btnCheckOut);


        bottomNavigation();
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalAmount = calculateTotalAmount(managamentCart.getListCart());

                // Hiển thị thông báo "Thanh toán thành công" và tổng số tiền đã thanh toán
                showSuccessMessage(totalAmount);
                // Xóa danh sách sản phẩm khỏi giỏ hàng
                managamentCart.clearCart();
                adapter.notifyDataSetChanged();

            }
        });
        initView();
        initList();
    }
    private double calculateTotalAmount(List<Fruit> cartItems) {
        double totalAmount = 0;
        for (Fruit fruit : cartItems) {
            totalAmount += Double.parseDouble(fruit.getPrice());
        }
        return totalAmount;
    }

    private void showSuccessMessage(double totalAmount) {
        // Hiển thị thông báo "Thanh toán thành công" và tổng số tiền đã thanh toán
        String message = "Bạn đã thanh toán thành công. " +
                "Tổng số tiền đã thanh toán là: " + totalAmount + " đồng.";
        Toast.makeText(CartActivity.this, message, Toast.LENGTH_LONG).show();
    }
    private void bottomNavigation() {
        ImageView settingBtn = findViewById(R.id.btnSetting);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, SettingActivity.class));
            }
        });
        ImageView btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, FruitActivity.class));
            }
        });
        ImageView btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
            }
        });
    }
    private void initView() {
        recyclerView = findViewById(R.id.recyclerView4);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView3 = findViewById(R.id.scrollView3);
        recyclerView = findViewById(R.id.recyclerView4);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managamentCart.getListCart(), this, new ChangeNumberItemList() {
            @Override
            public void changed() {
            }
        });
        recyclerView.setAdapter(adapter);
        if (managamentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView3.setVisibility(View.GONE);
        }else {
            emptyTxt.setVisibility(View.GONE);
            scrollView3.setVisibility(View.VISIBLE);
        }
    }

}