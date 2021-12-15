package com.example.asus.androidnewsapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.androidnewsapp.Interface.IItemClickListener;
import com.example.asus.androidnewsapp.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView article_title;
    public RelativeTimeTextView article_time;
    public CircleImageView article_image;
    private IItemClickListener iItemClickListener;

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public ListNewsViewHolder(View itemView) {
        super(itemView);

        article_image = (CircleImageView)itemView.findViewById(R.id.article_image);
        article_time = (RelativeTimeTextView)itemView.findViewById(R.id.article_time);
        article_title = (TextView)itemView.findViewById(R.id.article_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        iItemClickListener.onClick(v , getAdapterPosition() , false);
    }
}
