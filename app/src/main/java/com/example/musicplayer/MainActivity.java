package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.musicplayer.EXTRA_TEXT";
    public static final String EXTRA_Number = "com.example.musicplayer.EXTRA_Number";
    public static final String EXTRA_ARRAY_LIST = "com.example.musicplayer.EXTRA_ARRAY_LIST";
    public static final String EXTRA_RANDOM_NUMBER =  "com.example.musicplayer.EXTRA_RANDOM_NUMBER";

    private RecyclerView recyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private  ArrayList<Example> exampleArrayList;
    private ArrayList<String> example_Song_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createExampleList();
        buildRecyclerView();
    }
    public void createExampleList(){
        exampleArrayList = new ArrayList<>();
        example_Song_url = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            Log.i("MainActivity", fields[count].getName());
            example_Song_url.add(fields[count].getName());
            exampleArrayList.add(new Example(R.drawable.ic_music_note_black_24dp,fields[count].getName()," "));
        }
    }
    public  void changeItem(int pos){
        Intent intent = new Intent(this,SongPlay.class);
        intent.putExtra(EXTRA_TEXT,exampleArrayList.get(pos).getMtext1());
        intent.putExtra(EXTRA_Number,pos);
        intent.putExtra(EXTRA_RANDOM_NUMBER,exampleArrayList.size());
        intent.putStringArrayListExtra(EXTRA_ARRAY_LIST,example_Song_url);
        startActivity(intent);
    }
    public void buildRecyclerView(){
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                changeItem(pos);
            }
        });
    }

}
