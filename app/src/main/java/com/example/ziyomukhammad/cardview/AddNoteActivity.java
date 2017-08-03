package com.example.ziyomukhammad.cardview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.Time;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private TextView mNoteTextTV;
    private TextView mDateTV;
    private Button mSaveButton;
    private  Button mCancelButton;

    private DatePickerDialog myDatePickerDialog;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mNoteTextTV = (TextView)findViewById(R.id.note_text_et);
        mDateTV = (TextView)findViewById(R.id.date_et);

        mSaveButton = (Button)findViewById(R.id.save_note_button);
        mCancelButton = (Button)findViewById(R.id.cancel_note_button);

        final Intent retIntent = new Intent();

        final String noteText = "";
        final String noteDate = "";

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("text", "This is Note text");
                returnIntent.putExtra("date", "12/04/2013");

                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }
}


