package com.example.ziyomukhammad.cardview;

import android.service.autofill.Dataset;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ziyo  Mukhammad on 8/3/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mNoteTitleTextView;
        public TextView mNoteSubtitleTextView;
        public ImageView mNoteImageView;
        public TextView mDateTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mNoteTitleTextView = (TextView) itemView.findViewById(R.id.note_title_tv);
            this.mNoteSubtitleTextView = (TextView) itemView.findViewById(R.id.note_subtitle_tv);
            this.mNoteImageView = (ImageView) itemView.findViewById(R.id.note_photo_imageView);
            this.mDateTextView = (TextView) itemView.findViewById(R.id.date_tv);
        }
    }


    public MyAdapter(ArrayList<DataModel> data) {
       this.dataSet = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        TextView title = holder.mNoteTitleTextView;
        TextView subtitle = holder.mNoteSubtitleTextView;
        ImageView image = holder.mNoteImageView;
        TextView date = holder.mDateTextView;

        title.setText(dataSet.get(listPosition).getTitle());
        subtitle.setText(dataSet.get(listPosition).getSubtitle());
        date.setText(dataSet.get(listPosition).getDate());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


