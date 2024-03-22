package com.example.and103_assignmentht.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.and103_assignmentht.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bottomNavigation();

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) { // Kiểm tra xem có người dùng nào đang đăng nhập không
                    mAuth.signOut();
                    Toast.makeText(SettingActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(SettingActivity.this, "Đăng xuất thất bại. Không có người dùng đang đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void bottomNavigation() {
        ImageView personBtn = findViewById(R.id.btnPerson);

        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, PersonActivity.class));
            }
        });
        ImageView btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ChatActivity.class));
            }
        });
        ImageView btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
            }
        });
    }
}