package com.example.ziyomukhammad.cardview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ziyomukhammad.cardview.Constants.CAMERA_REQUEST;

public class AddNoteActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;

    private  String test = "Push test";

    @BindView(R.id.note_text_et)
    EditText mNoteTextTV;
    @BindView(R.id.date_et)
    EditText mDateTV;
    @BindView(R.id.note_title_et)
    EditText mNoteTitle;
    @BindView(R.id.take_picture_add_activity)
    ImageView mNoteImage;


    @RequiresApi(api = Build.VERSION_CODES.N)

    String dateStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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


    public void saveNoteToFirebase() {
        DatabaseReference noteRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_NOTES);
        DataModel new_note = new DataModel(mNoteTitle.getText().toString(), mNoteTextTV.getText().toString(), "12/04/2013");
        noteRef.push().setValue(new_note);
    }

    @OnClick(R.id.save_note_button)
    public void AddNewMemo(View v) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("text", "This is Note text");
        returnIntent.putExtra("date", "12/04/2013");
        saveNoteToFirebase();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @OnClick(R.id.cancel_note_button)
    public void CancelMemo(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @OnClick(R.id.take_picture_add_activity)
    public void openImageChooser(View v) {
        selectImage();

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNoteActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                } else if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // from camera
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("image", "Image Path : " + path);
                    // Set the image in ImageView
                    mNoteImage.setImageURI(selectedImageUri);

                } else if (requestCode == 2) {


                }
            }
            else if(requestCode == Constants.CAMERA_REQUEST){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mNoteImage.setImageBitmap(photo);
            }
        }
    }
}



