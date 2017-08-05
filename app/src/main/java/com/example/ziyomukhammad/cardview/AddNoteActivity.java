package com.example.ziyomukhammad.cardview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    private EditText mNoteTextTV;
    private EditText mDateTV;
    private EditText mNoteTitle;
    private Button mSaveButton;
    private Button mCancelButton;
    DataModel note;

    private DatePickerDialog myDatePickerDialog;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_note );

        mNoteTextTV = (EditText) findViewById( R.id.note_text_et );
        mDateTV = (EditText) findViewById( R.id.date_et );
        mNoteTitle = (EditText) findViewById( R.id.note_title_et );

        mSaveButton = (Button) findViewById( R.id.save_note_button );
        mCancelButton = (Button) findViewById( R.id.cancel_note_button );

        final Intent retIntent = new Intent();

        final String noteText = "";
        final String noteDate = "";

        mSaveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra( "text", "This is Note text" );
                returnIntent.putExtra( "date", "12/04/2013" );
                saveNoteToFirebase();
                setResult( Activity.RESULT_OK, returnIntent );
                finish();

            }
        } );

        mCancelButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult( Activity.RESULT_CANCELED, returnIntent );
                finish();
            }
        });

    }

    public void saveNoteToFirebase(){
        DatabaseReference noteRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_NOTES);
        DataModel new_note = new DataModel(mNoteTitle.getText().toString(), mNoteTextTV.getText().toString(),"12/04/2013");
        noteRef.push().setValue(new_note);
    }

}



