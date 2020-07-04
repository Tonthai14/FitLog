package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DayLayout extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntryDatabase db;
    TextView noEntries;
    String dayOfTheWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        Intent prevIntent = getIntent();
        dayOfTheWeek = prevIntent.getStringExtra("dayOfTheWeek");
        db = new EntryDatabase(this);
        List<Entry> dayEntries = db.getDayEntries(dayOfTheWeek);
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
        MyAdapter adapter = new MyAdapter(this, allEntries);
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
        if(item.getItemId() == R.id.addEntry) {
            Toast.makeText(this, "Add new entry", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, addExercise.class);
            intent.putExtra("dayOfTheWeek", dayOfTheWeek);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Entry> getDayEntries = db.getDayEntries(dayOfTheWeek);
        listEmpty(getDayEntries);
    }
}