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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNoteActivity extends AppCompatActivity {

    @BindView(R.id.note_text_et)EditText mNoteTextTV;
    @BindView(R.id.date_et) EditText mDateTV;
    @BindView(R.id.note_title_et) EditText mNoteTitle;

    @RequiresApi(api = Build.VERSION_CODES.N)

    String dateStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_note );
        ButterKnife.bind(this);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener  date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }
        };

        mDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNoteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel(Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDateTV.setText(sdf.format(calendar.getTime()));
        dateStr = calendar.getTime().toString();
    }


    public void saveNoteToFirebase(){
        DatabaseReference noteRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_NOTES);
        DataModel new_note = new DataModel(mNoteTitle.getText().toString(), mNoteTextTV.getText().toString(),"12/04/2013");
        noteRef.push().setValue(new_note);
    }

    @OnClick(R.id.save_note_button)
    public void AddNewMemo(View v){

        Intent returnIntent = new Intent();
        returnIntent.putExtra( "text", "This is Note text" );
        returnIntent.putExtra( "date", "12/04/2013" );
        saveNoteToFirebase();
        setResult( Activity.RESULT_OK, returnIntent );
        finish();
    }

    @OnClick(R.id.cancel_note_button)
    public void CancelMemo(View v){
        Intent returnIntent = new Intent();
        setResult( Activity.RESULT_CANCELED, returnIntent );
        finish();
    }


}



