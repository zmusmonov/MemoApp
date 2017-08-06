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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mNotesReference;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.new_memo_button)
    Button mNewNoteButton;

    private static ArrayList<DataModel> myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        FirebaseDatabase.getInstance().setPersistenceEnabled( true );

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText( this, "Signed in", Toast.LENGTH_SHORT );
        } else {
            Toast.makeText( this, "Not Signed in", Toast.LENGTH_SHORT );
        }

        mNotesReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child( Constants.FIREBASE_CHILD_NOTES );

        mRecyclerView.setHasFixedSize( true );

        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );

        myData = new ArrayList<DataModel>();


        DatabaseReference noteRef = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FIREBASE_CHILD_NOTES );
        noteRef.keepSynced( true );


        // Attach a listener to read the data at ourr posts reference
        noteRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myData = new ArrayList<DataModel>();
//                DataModel note = dataSnapshot.getValue(DataModel.class);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataModel note = snapshot.getValue( DataModel.class );
                    note.setPushID( snapshot.getKey() );
                    myData.add( note );
                    System.out.println( snapshot.getValue( DataModel.class ).getTitle() );
                    System.out.println( snapshot.getValue( DataModel.class ).getImageUrl() );
                    Log.d( "Size", String.valueOf( myData.size() ) );
                }
                mAdapter = new MyAdapter( myData );
                mRecyclerView.setAdapter( mAdapter );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println( "The read failed: " + databaseError.getCode() );
            }
        } );


        Log.d( "Sizes", String.valueOf( myData.size() ) );
        mAdapter = new MyAdapter( myData );
        mRecyclerView.setAdapter( mAdapter );

    }



    @OnClick(R.id.new_memo_button)
    public void NewMemo(View v) {
        Intent intent = new Intent( MainActivity.this, AddNoteActivity.class );
        startActivity(intent);
    }
}




