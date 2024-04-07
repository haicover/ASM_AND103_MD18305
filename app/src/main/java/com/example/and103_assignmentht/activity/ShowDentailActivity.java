package com.example.and103_assignmentht.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.and103_assignmentht.ManagamentCart;
import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.model.Fruit;

import java.util.ArrayList;

public class ShowDentailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt,priceTxt,descriptionTxt;
    private ImageView picFood;
    private Fruit fruit;
    private ManagamentCart managamentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dentail);

        addToCartBtn = findViewById(R.id.addToCartBtn);
        managamentCart = new ManagamentCart(this);
        initView();
        getBundle();
    }
    private void getBundle(){
        fruit = (Fruit) getIntent().getSerializableExtra("fruit");

        // Kiểm tra xem image có giá trị null hay không
        if (fruit != null && fruit.getImage() != null && fruit.getImage().size() > 0) {
            // Lấy đường dẫn ảnh đầu tiên từ mảng image
            String imageUrl = fruit.getImage().get(0);
            // Sử dụng thư viện Glide để tải ảnh từ URL và hiển thị nó trong ImageView
            Glide.with(this)
                    .load(imageUrl)
                    .into(picFood);
        }

        titleTxt.setText(fruit.getName());
        priceTxt.setText("$" + fruit.getPrice());
        descriptionTxt.setText(fruit.getDescription());

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managamentCart.insertFood(fruit);
                startActivity(new Intent(ShowDentailActivity.this, CartActivity.class));
            }
        });

    }


    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picFood = findViewById(R.id.picFruit);
    }
}