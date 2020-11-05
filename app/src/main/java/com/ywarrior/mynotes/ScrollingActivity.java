package com.ywarrior.mynotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG ="jun" ;
    ImageView imageView;
    List<Notes_model>notes_models;
    private AppRepository repository;
    NotesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        recyclerView=findViewById(R.id.rview);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        notes_models=new ArrayList<>();
        repository=AppRepository.getInstance(ScrollingActivity.this);


        Observer<List<Notes_model>>observer=new Observer<List<Notes_model>>() {
            @Override
            public void onChanged(List<Notes_model> notes_models2) {
                notes_models.clear();
                notes_models.addAll(notes_models2);
                if (adapter==null){
                    setAdapter();
                }else{
//                    if (notes_models.size()!=0)
                    adapter.notifyDataSetChanged();
                }
            }
        };
        repository.notes_models.observe(ScrollingActivity.this,observer);
//        Notes_model notes_model=new Notes_model("hello", Calendar.getInstance().getTime());
//        notes_models.add(notes_model);
//        repository.addData(notes_models);

        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Notes_model>ab=new ArrayList<>();
                startActivity(new Intent(ScrollingActivity.this,AddNote.class));
//                Notes_model notes_model=new Notes_model("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ", Calendar.getInstance().getTime());
//                ab.add(notes_model);
//                repository.addData(ab);
            }
        });

    }

    private void setAdapter() {

        adapter=new NotesAdapter(ScrollingActivity.this,notes_models, new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int postion, final Notes_model item, View view) {
                final Dialog dialog = new Dialog(ScrollingActivity.this);
                dialog.setCancelable(true);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title

                Button del = (Button) dialog.findViewById(R.id.Delete);
                Button edit = (Button) dialog.findViewById(R.id.Edit);


                dialog.show();
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repository.DeleteNote(item);
                        dialog.dismiss();
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: "+postion);
                        Intent intent=new Intent(ScrollingActivity.this,AddNote.class);
                        intent.putExtra("val",String.valueOf(postion));
                        startActivity(intent);
                    }
                });

            }
        });
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }
}