package com.example.asus.androidnewsapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.androidnewsapp.Interface.IItemClickListener;
import com.example.asus.androidnewsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView source_title;
    public CircleImageView source_image;

    private IItemClickListener iItemClickListener;

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public ListSourceViewHolder(View itemView) {
        super(itemView);

        source_title = (TextView)itemView.findViewById(R.id.source_name);
        source_image = (CircleImageView)itemView.findViewById(R.id.source_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        iItemClickListener.onClick(v , getAdapterPosition() , false);
    }
}
