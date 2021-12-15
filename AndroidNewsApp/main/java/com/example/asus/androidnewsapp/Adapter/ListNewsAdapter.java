package com.example.asus.androidnewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.androidnewsapp.Common.ISO8601Parse;
import com.example.asus.androidnewsapp.DetailArticleActivity;
import com.example.asus.androidnewsapp.Interface.IItemClickListener;
import com.example.asus.androidnewsapp.Model.Article;
import com.example.asus.androidnewsapp.R;
import com.example.asus.androidnewsapp.ViewHolder.ListNewsViewHolder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    Context context;
    List<Article> articleList;

    public ListNewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_layout , parent , false);
        return new ListNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {

        Picasso.get()
                .load(articleList.get(position).getUrlToImage())
                .into(holder.article_image);

        if (articleList.get(position).getTitle().length() > 65)
            holder.article_title.setText(articleList.get(position).getTitle().substring(0,65) + "...");
        else
            holder.article_title.setText(articleList.get(position).getTitle());


        //Because NewsAPI use ISC8601 format dateTime => SO we use ISC8901Parse class to convert it to Date
        java.util.Date date = null;
        try {

            date = ISO8601Parse.parse(articleList.get(position).getPublishedAt());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null)
            holder.article_time.setReferenceTime(date.getTime());
        else
            holder.article_time.setReferenceTime(0);

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent detailIntent = new Intent(context , DetailArticleActivity.class);
                detailIntent.putExtra("webURL" , articleList.get(position).getUrl());
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
