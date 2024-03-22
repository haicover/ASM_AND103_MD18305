package com.example.and103_assignmentht.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.adapter.StudentAdapter;
import com.example.and103_assignmentht.model.StudentModel;
import com.example.and103_assignmentht.service.ApiServices;
import com.example.and103_assignmentht.service.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    ListView lvStudent;
    List<StudentModel> models = new ArrayList<>();
    StudentAdapter adapter;
    FloatingActionButton cartBtn;
    HttpRequest httpRequest = new HttpRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation();

        lvStudent = findViewById(R.id.lvStudent);
        cartBtn = findViewById(R.id.cartBtn);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://65d076a6ab7beba3d5e327fb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<ArrayList<StudentModel>> call = apiServices.getStudents();
        call.enqueue(new Callback<ArrayList<StudentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentModel>> call, Response<ArrayList<StudentModel>> response) {
                if (response.isSuccessful()) {
                    models = response.body();
                    adapter = new StudentAdapter(getApplicationContext(), models);
                    lvStudent.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StudentModel>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
        //thémv
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });
        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.item_update_student);
                // Ánh xạ các view trong dialog
                EditText upName = dialog.findViewById(R.id.upName);
                EditText upMssv = dialog.findViewById(R.id.upMssv);
                TextView btnUpdate = dialog.findViewById(R.id.btnUpdate);
                // Lấy thông tin của sinh viên tại vị trí position
                StudentModel student = models.get(i);
                // Điền dữ liệu hiện tại của sinh viên vào các trường EditText
                upName.setText(student.getName());
                upMssv.setText(student.getMssv());
                // Xử lý sự kiện khi nhấn nút "Sửa"
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy dữ liệu đã sửa từ các trường EditText
                        String updatedName = upName.getText().toString().trim();
                        String updatedMssv = upMssv.getText().toString().trim();
                        // Kiểm tra nếu dữ liệu không trống
                        if (!updatedName.isEmpty() && !updatedMssv.isEmpty()) {
                            // Cập nhật thông tin của sinh viên
                            student.setName(updatedName);
                            student.setMssv(updatedMssv);
                            // Gọi API để cập nhật sinh viên
                            httpRequest.callAPI()
                                    .updateStudent(student.getid(), student)
                                    .enqueue(new Callback<List<StudentModel>>() {
                                        @Override
                                        public void onResponse(Call<List<StudentModel>> call, Response<List<StudentModel>> response) {
                                            if (response.isSuccessful()) {
                                                // Xử lý thành công khi cập nhật sinh viên
                                                List<StudentModel> updatedStudent = response.body();
                                                models.set(i, (StudentModel) updatedStudent);
                                                adapter.notifyDataSetChanged();
                                                Log.d("UpdateStudent", "Student has been updated successfully: ");
                                            } else {
                                                // Xử lý khi gặp lỗi từ máy chủ
                                                Log.e("UpdateStudent", "Failed to update student. Error code: " + response.code());
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<List<StudentModel>> call, Throwable t) {
                                            Log.e("UpdateStudent", "Failed to update student: " + t.getMessage());
                                        }
                                    });
                            // Đóng dialog sau khi hoàn tất
                            dialog.dismiss();
                        } else {
                            // Hiển thị thông báo nếu các trường thông tin không được nhập đầy đủ
                            Toast.makeText(HomeActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // Hiển thị dialog sửa item
                dialog.show();
                return true; // Set to true to indicate that the event is consumed
            }
        });
    }
    private void bottomNavigation() {
        ImageView personBtn = findViewById(R.id.btnPerson);

        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PersonActivity.class));
            }
        });
        ImageView btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
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
    private void showAddItemDialog() {
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.item_add_student);
        // Ánh xạ các view trong layout item_add
        EditText addName = dialog.findViewById(R.id.addName);
        EditText addMssv = dialog.findViewById(R.id.addMssv);
        TextView btnAdd = dialog.findViewById(R.id.btnAdd);
        // Xử lý sự kiện click cho button Thêm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường nhập
                String name = addName.getText().toString();
                String mssv = addMssv.getText().toString();
                if (name.isEmpty() || mssv.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Thực hiện thêm sinh viên vào danh sách hoặc gọi API để thêm vào cơ sở dữ liệu
                    StudentModel newStudent = new StudentModel();
                    newStudent.setName(name);
                    newStudent.setMssv(mssv);
                    httpRequest.callAPI().addStudent(newStudent).enqueue(new Callback<List<StudentModel>>() {
                        @Override
                        public void onResponse(Call<List<StudentModel>> call, Response<List<StudentModel>> response) {
                            List<StudentModel> newStudents = response.body();
                            models.addAll(newStudents); // Add all students from the response
                            adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                            Toast.makeText(HomeActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<List<StudentModel>> call, Throwable t) {
                            Log.e("HomeActivity", "Failed to add student: " + t.getMessage());
                            Toast.makeText(HomeActivity.this, "Thêm sinh viên thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Dismiss dialog sau khi thêm thành công
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}