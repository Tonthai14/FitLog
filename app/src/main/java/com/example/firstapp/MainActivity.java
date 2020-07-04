package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Sunday:
                Intent sunday = new Intent(this, DayLayout.class);
                sunday.putExtra("dayOfTheWeek", "Sunday");
                startActivity(sunday);
                break;
            case R.id.Monday:
                Intent monday = new Intent(this, DayLayout.class);
                monday.putExtra("dayOfTheWeek", "Monday");
                startActivity(monday);
                break;
            case R.id.Tuesday:
                Intent tuesday = new Intent(this, DayLayout.class);
                tuesday.putExtra("dayOfTheWeek", "Tuesday");
                startActivity(tuesday);
                break;
            case R.id.Wednesday:
                Intent wednesday = new Intent(this, DayLayout.class);
                wednesday.putExtra("dayOfTheWeek", "Wednesday");
                startActivity(wednesday);
                break;
            case R.id.Thursday:
                Intent thursday = new Intent(this, DayLayout.class);
                thursday.putExtra("dayOfTheWeek", "Thursday");
                startActivity(thursday);
                break;
            case R.id.Friday:
                Intent friday = new Intent(this, DayLayout.class);
                friday.putExtra("dayOfTheWeek", "Friday");
                startActivity(friday);
                break;
            case R.id.Saturday:
                Intent saturday = new Intent(this, DayLayout.class);
                saturday.putExtra("dayOfTheWeek", "Saturday");
                startActivity(saturday);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
