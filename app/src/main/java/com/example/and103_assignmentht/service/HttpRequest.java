package com.example.and103_assignmentht.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {

    private  ApiServices apiServices;
    public HttpRequest(){
        apiServices = new Retrofit.Builder()
                .baseUrl("https://65d076a6ab7beba3d5e327fb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);
    }

    public ApiServices callAPI(){
        return apiServices;
    }
}
