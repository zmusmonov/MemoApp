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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mNotesReference;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mNewNoteButton;


    private static ArrayList<DataModel> myData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_NOTES);



        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mNewNoteButton = (Button) findViewById(R.id.new_memo_button);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myData = new ArrayList<DataModel>();


        DatabaseReference noteRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_NOTES);
// Attach a listener to read the data at our posts reference
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myData = new ArrayList<DataModel>();
//                DataModel note = dataSnapshot.getValue(DataModel.class);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myData.add(snapshot.getValue(DataModel.class));
                    System.out.println(snapshot.getValue(DataModel.class).getTitle());
                    Log.d( "Size",String.valueOf( myData.size()) );

                }
                mAdapter = new MyAdapter(myData);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




//        myData.add(new DataModel("Ertengi ishla", "Egzamen", "pryam ertag"));
      Log.d( "Sizes",String.valueOf( myData.size()) );


        mAdapter = new MyAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

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

//                    myData.add(new DataModel(text, date, ""));

                    mAdapter = new MyAdapter(myData);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }
        }

}




