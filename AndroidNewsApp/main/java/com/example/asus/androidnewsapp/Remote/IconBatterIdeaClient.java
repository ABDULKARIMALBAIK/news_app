package com.example.asus.androidnewsapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IconBatterIdeaClient {

    public static Retrofit instance = null;

    public static Retrofit getInstance(){

        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("............................")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return instance;
    }
}
