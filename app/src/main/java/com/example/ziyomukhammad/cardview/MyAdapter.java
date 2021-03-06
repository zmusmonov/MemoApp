package com.example.ziyomukhammad.cardview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.service.autofill.Dataset;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Ziyo  Mukhammad on 8/3/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<DataModel> dataSet;
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
        Log.d("Image",dataSet.get(listPosition).getImageUrl());
        try {

            Bitmap imageBitmap = decodeFromFirebaseBase64(dataSet.get(listPosition).getImageUrl());
            image.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }



        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(holder.itemView.getContext(), AddNoteActivity.class);
                intent.putExtra("postID",dataSet.get( listPosition).getPushID());
                intent.putExtra( "isNew",false );
                holder.itemView.getContext().startActivity(intent);
                Log.d("Index",dataSet.get( listPosition).getPushID());
            }
        } );




    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


