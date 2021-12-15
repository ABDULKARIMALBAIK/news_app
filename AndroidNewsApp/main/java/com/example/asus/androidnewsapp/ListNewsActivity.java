package com.example.asus.androidnewsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.androidnewsapp.Adapter.ListNewsAdapter;
import com.example.asus.androidnewsapp.Common.Common;
import com.example.asus.androidnewsapp.Model.Article;
import com.example.asus.androidnewsapp.Model.News;
import com.example.asus.androidnewsapp.Remote.NewsService;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsActivity extends AppCompatActivity {

    KenBurnsView kenBurnsView;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView top_author , top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source ="" , webHotURL ="";

    ListNewsAdapter adapter;
    RecyclerView lstNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //Service
        mService = Common.getNewService();

        dialog = new SpotsDialog(this);

        //Init Views
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (getIntent().getExtras() != null){

                    source = getIntent().getStringExtra("source");

                    if (!source.isEmpty()){

                        if (Common.isConnectionToInternet(ListNewsActivity.this))
                            loadNews(source , true);
                        else
                            Toast.makeText(ListNewsActivity.this, "Please check your connection by internet !", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        diagonalLayout = (DiagonalLayout)findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Click to hot => latest news read
                Intent detailIntent = new Intent(getApplicationContext() , DetailArticleActivity.class);
                detailIntent.putExtra("webURL" , webHotURL);
                startActivity(detailIntent);
            }
        });

        kenBurnsView = (KenBurnsView)findViewById(R.id.top_image);
        top_author = (TextView)findViewById(R.id.top_author);
        top_title = (TextView)findViewById(R.id.top_title);

        lstNews = (RecyclerView)findViewById(R.id.lstNews);
        lstNews.setHasFixedSize(true);
        lstNews.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getExtras() != null){

            source = getIntent().getStringExtra("source");

            if (!source.isEmpty()){

                if (Common.isConnectionToInternet(this))
                    loadNews(source , false);
                else
                    Toast.makeText(this, "Please check your connection by internet !", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void loadNews(String source, boolean isRefresed) {

        if (!isRefresed){

            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                            dialog.dismiss();

                            //Get first article
                            Picasso.get()
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);
                            top_author.setText(response.body().getArticles().get(0).getAuthor());
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            webHotURL = response.body().getArticles().get(0).getUrl();


                            //load remain articles
                            List<Article> removeFirstitem =  response.body().getArticles();
                            //Because we already load first item to show on Diagonal Layout
                            //So we need remove first item
                            removeFirstitem.remove(0);

                            adapter = new ListNewsAdapter(getApplicationContext() , removeFirstitem);
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            Toast.makeText(ListNewsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{

            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                            dialog.dismiss();

                            //Get first article
                            Picasso.get()
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);
                            top_author.setText(response.body().getArticles().get(0).getAuthor());
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            webHotURL = response.body().getArticles().get(0).getUrl();


                            //load remain articles
                            List<Article> removeFirstitem =  response.body().getArticles();
                            //Because we already load first item to show on Diagonal Layout
                            //So we need remove first item
                            removeFirstitem.remove(0);

                            adapter = new ListNewsAdapter(getApplicationContext() , removeFirstitem);
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            Toast.makeText(ListNewsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
