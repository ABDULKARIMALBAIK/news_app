package com.example.asus.androidnewsapp.Remote;

import com.example.asus.androidnewsapp.Common.Common;
import com.example.asus.androidnewsapp.Model.News;
import com.example.asus.androidnewsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {

    @GET("......................." + Common.API_KEY)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
