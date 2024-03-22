package com.example.and103_assignmentht.service;

import com.example.and103_assignmentht.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiServices {


    @GET("students")
    Call<ArrayList<StudentModel>> getStudents();

    @DELETE("students/{id}")
    Call<List<StudentModel>> deleteStudent(@Path("id") String id);

    @POST("students")
    Call<List<StudentModel>> addStudent(@Body StudentModel model);

    @PUT("students/{id}")
    Call<List<StudentModel>> updateStudent(@Path("id") String id, @Body StudentModel model);
}
