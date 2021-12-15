package com.example.asus.androidnewsapp.Remote;

import com.example.asus.androidnewsapp.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface IconBetterIdeaService {

    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);
}
