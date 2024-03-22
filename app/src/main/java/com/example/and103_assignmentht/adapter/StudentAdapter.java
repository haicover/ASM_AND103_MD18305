package com.example.and103_assignmentht.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.and103_assignmentht.R;
import com.example.and103_assignmentht.model.StudentModel;
import com.example.and103_assignmentht.service.ApiServices;
import com.example.and103_assignmentht.service.HttpRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends BaseAdapter {
    Context context;
    List<StudentModel> mList ;
    HttpRequest httpRequest;
    public StudentAdapter(Context context, List<StudentModel> mList) {
        this.context = context;
        this.mList = mList;
        this.httpRequest = new HttpRequest();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.item_student, viewGroup, false);

        TextView tvName = view1.findViewById(R.id.tvName);
        TextView tvMssv = view1.findViewById(R.id.tvMssv);
        ImageView imgAvatatr = view1.findViewById(R.id.imgAvatatr);
        ImageView btnDelete = view1.findViewById(R.id.btnDelete);

        StudentModel student = mList.get(i);
        if (student != null) {
            tvName.setText(student.getName());
            tvMssv.setText(student.getMssv());
            imgAvatatr.setImageResource(R.drawable.img2);

            btnDelete.setOnClickListener(v -> {
                String studentId = student.getid();
                if (studentId != null) {
                    httpRequest.callAPI()
                            .deleteStudent(studentId)
                            .enqueue(new Callback<List<StudentModel>>() {
                                @Override
                                public void onResponse(Call<List<StudentModel>> call, Response<List<StudentModel>> response) {
                                    if (response.isSuccessful()) {
                                        mList.remove(student);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Delete failed. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<StudentModel>> call, Throwable t) {
                                    Log.e("DeleteStudent", "Failed to delete student: " + t.getMessage());
                                    Toast.makeText(context, "Delete failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }

        return view1;
    }

}
