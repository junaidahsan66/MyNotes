package com.ywarrior.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNote extends AppCompatActivity {
    EditText text;
    Button submit;
    AppRepository appRepository;
    ImageView imageView,image;
    Uri selectedImage=null;
    int a1;
    Notes_model model;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==2 && grantResults.length>0){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            if (data!=null){
               selectedImage = data.getData();
                try {
                    InputStream inputStream=getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    image.setImageBitmap(bitmap);
                    image.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void selectImage() {

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        appRepository=AppRepository.getInstance(AddNote.this);
        text=findViewById(R.id.textView3);
        submit=findViewById(R.id.button);
        imageView=findViewById(R.id.imageView3);
        image=findViewById(R.id.imageView2);

        Intent intent=getIntent();
        final String a=intent.getStringExtra("val");
        if (a==null){

        }else {
            a1 = Integer.parseInt(a);

            final Observer<List<Notes_model>> observer=new Observer<List<Notes_model>>() {
                @Override
                public void onChanged(List<Notes_model> notes_models2) {
                    model = notes_models2.get(a1);
                    text.setText(model.getText());
                    submit.setText("Update");
                    Log.d("jun", "onChanged: "+a1);
                    appRepository.notes_models.removeObservers(AddNote.this);
                }
            };
            appRepository.notes_models.observe(AddNote.this,observer);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submit.getText().toString().equals("Update")){
                    Notes_model notes_model = new Notes_model(a1,text.getText().toString(),model.getDate(),model.getUrl());
                    appRepository.DeleteNote(model);
                    appRepository.addSingleData(notes_model);
                    startActivity(new Intent(AddNote.this, ScrollingActivity.class));
                    finish();

                }else {
                    Notes_model notes_model = new Notes_model();
                    notes_model.setText(text.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    notes_model.setDate(date);
                    notes_model.setUrl(getUrl(selectedImage));
                    appRepository.addSingleData(notes_model);
                    startActivity(new Intent(AddNote.this, ScrollingActivity.class));
                    finish();
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check_permissions()){
                    ActivityCompat.requestPermissions(AddNote.this,new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},2
                    );
                }
                else {
                    selectImage();

                }


            }
        });
    }

    private String getUrl(Uri selectedImage) {
        if (selectedImage==null){

        }else{
            String[] data = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(getApplicationContext(), selectedImage, data, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }

    public boolean check_permissions() {

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE
        )!= PackageManager.PERMISSION_GRANTED){

        }else {
            return true;
        }
        return false;
    }
}