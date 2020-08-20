package com.example.smsschedular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadSchedule extends AppCompatActivity {
    RecyclerView recyclerView;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_schedule);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataBase = DataBase.getInstance(this);
        LiveData<List<LiveTest>> liveTests = dataBase.getDao().loadall();
        liveTests.observe(this, new Observer<List<LiveTest>>() {
            @Override
            public void onChanged(List<LiveTest> liveTests) {
                if (liveTests.size() == 0) {
                    List<LiveTest> liveTests1 = new ArrayList<>();
                    liveTests1.add(new LiveTest("No History of Auto Scheduled messages to show", "", "",0L));
                    recyclerView.setAdapter(new RecycleAdapter(liveTests1));
                } else {
                    Collections.reverse(liveTests);
                    recyclerView.setAdapter(new RecycleAdapter(liveTests));

                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(LoadSchedule.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
