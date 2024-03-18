package com.example.logger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class DayLayout extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntryDatabase db;
    TextView noEntries;
    String date;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_layout);
        
        Intent prevIntent = getIntent();
        date = prevIntent.getStringExtra("date");
        Log.d("DayLayout", date);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setTitle(date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new EntryDatabase(this);
        List<Entry> dayEntries = db.getDayEntries(date);
        recyclerView = findViewById(R.id.myRecyclerView);
        noEntries = findViewById(R.id.noEntries);

        listEmpty(dayEntries);
    }

    public void listEmpty(List<Entry> entries) {
        if(entries.isEmpty()) {
            noEntries.setVisibility(View.VISIBLE);
        } else {
            noEntries.setVisibility(View.GONE);
            displayList(entries);
        }
    }

    public void displayList(List<Entry> allEntries) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, allEntries, date);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new:
                Toast.makeText(this, "Add new entry", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddExercise.class);
                intent.putExtra("date", date);
                Log.d("DayLayout ADD NEW", date);
                startActivity(intent);
                break;
            case R.id.choose_preset:
                Toast.makeText(this, "Choose a preset", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Entry> getDayEntries = db.getDayEntries(date);
        listEmpty(getDayEntries);
    }
}