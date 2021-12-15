package com.example.asus.androidnewsapp;

import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.asus.androidnewsapp.Adapter.ListSourceAdapter;
import com.example.asus.androidnewsapp.Common.Common;
import com.example.asus.androidnewsapp.Model.WebSite;
import com.example.asus.androidnewsapp.Remote.NewsService;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    //URL: https://newsapi.org/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init Cache
        Paper.init(this);

        //Init Service
        mService = Common.getNewService();

        //Init Views
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Common.isConnectionToInternet(MainActivity.this))
                    loadWebsiteSource(true);
                else
                    Toast.makeText(MainActivity.this, "Please check your connection by internet !", Toast.LENGTH_SHORT).show();
            }
        });

        listWebsite = (RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        listWebsite.setLayoutManager(new LinearLayoutManager(this));

        dialog = new SpotsDialog(this);

        if (Common.isConnectionToInternet(this))
            loadWebsiteSource(false);
        else
            Toast.makeText(this, "Please check your connection by internet !", Toast.LENGTH_SHORT).show();
    }

    private void loadWebsiteSource(boolean isRefreshing) {

        if (!isRefreshing){

            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty() && !cache.equals("null")){  //I have cache

                WebSite webSite = new Gson().fromJson(cache , WebSite.class); //Convert cache from json to Object
                adapter = new ListSourceAdapter(this , webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else {  //If not have cache

                dialog.show();

                //fetch new data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                        adapter = new ListSourceAdapter(getApplicationContext() , response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to Cache
                        Paper.book().write("cache" , new Gson().toJson(response.body()));

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        else {

            swipeRefreshLayout.setRefreshing(true);

            //fetch new data
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                    adapter = new ListSourceAdapter(getApplicationContext() , response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to Cache
                    Paper.book().write("cache" , new Gson().toJson(response.body()));

                    //Dismiss refresh progressing
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
