package com.example.asus.androidnewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.androidnewsapp.Common.Common;
import com.example.asus.androidnewsapp.Interface.IItemClickListener;
import com.example.asus.androidnewsapp.ListNewsActivity;
import com.example.asus.androidnewsapp.Model.IconBetterIdea;
import com.example.asus.androidnewsapp.Model.WebSite;
import com.example.asus.androidnewsapp.R;
import com.example.asus.androidnewsapp.Remote.IconBetterIdeaService;
import com.example.asus.androidnewsapp.ViewHolder.ListSourceViewHolder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;
    private WebSite webSite;

    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.source_layout , parent , false);
        return new ListSourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {

        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(context , ListNewsActivity.class);
                intent.putExtra("source" , webSite.getSources().get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
