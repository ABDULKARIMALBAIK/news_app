package com.example.asus.androidnewsapp.Remote;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit instance = null;

    public static Retrofit getInstance(String baseUrl){

        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return instance;
    }
}
