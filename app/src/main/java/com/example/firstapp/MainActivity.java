package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Calendar calendar;
    String[] currentWeek = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Setting dates for the current week
        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for(int i = 0; i < currentWeek.length; i++) {
            currentWeek[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        // Bottom toolbar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_home, R.id.navigation_presets).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Sunday:
                Intent sunday = new Intent(this, DayLayout.class);
                sunday.putExtra("date", currentWeek[0]);
                startActivity(sunday);
                break;
            case R.id.Monday:
                Intent monday = new Intent(this, DayLayout.class);
                monday.putExtra("date", currentWeek[1]);
                startActivity(monday);
                break;
            case R.id.Tuesday:
                Intent tuesday = new Intent(this, DayLayout.class);
                tuesday.putExtra("date", currentWeek[2]);
                startActivity(tuesday);
                break;
            case R.id.Wednesday:
                Intent wednesday = new Intent(this, DayLayout.class);
                wednesday.putExtra("date", currentWeek[3]);
                startActivity(wednesday);
                break;
            case R.id.Thursday:
                Intent thursday = new Intent(this, DayLayout.class);
                thursday.putExtra("date", currentWeek[4]);
                startActivity(thursday);
                break;
            case R.id.Friday:
                Intent friday = new Intent(this, DayLayout.class);
                friday.putExtra("date", currentWeek[5]);
                startActivity(friday);
                break;
            case R.id.Saturday:
                Intent saturday = new Intent(this, DayLayout.class);
                saturday.putExtra("date", currentWeek[6]);
                startActivity(saturday);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
