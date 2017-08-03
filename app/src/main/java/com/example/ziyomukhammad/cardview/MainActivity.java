package com.example.ziyomukhammad.cardview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mNewNoteButton;



    private static ArrayList<DataModel> myData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mNewNoteButton = (Button) findViewById(R.id.new_memo_button);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myData = new ArrayList<DataModel>();
//        myData.add(new DataModel("Ertengi ishla", "Egzamen", "pryam ertag"));
//
//        mAdapter = new MyAdapter(myData);
//        mRecyclerView.setAdapter(mAdapter);

        mNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(requestCode == 1){
                if(resultCode == Activity.RESULT_OK){
                    String text = data.getStringExtra("text");
                    String date = data.getStringExtra("date");

                    myData.add(new DataModel(text, date, ""));

                    mAdapter = new MyAdapter(myData);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }
        }

}




