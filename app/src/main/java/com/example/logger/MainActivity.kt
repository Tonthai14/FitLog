package com.example.logger

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var currentWeek: Array<String?> = arrayOfNulls(7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setting dates for the current week
        val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        for (i in currentWeek.indices) {
            currentWeek[i] = sdf.format(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }

        // Bottom toolbar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val appBarConfig = AppBarConfiguration.Builder(
            R.id.navigation_calendar,
            R.id.navigation_statistics,
            R.id.navigation_home,
            R.id.navigation_body,
            R.id.navigation_presets
        ).build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
        NavigationUI.setupWithNavController(navView, navController)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drop_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val layout = if (item.itemId == R.id.settings_menu) {
            Intent(this, Settings::class.java)
        } else {
            Intent(this, DayLayout::class.java)
        }
        var day: String? = null
        when (item.itemId) {
            R.id.Sunday    -> day = currentWeek[0]
            R.id.Monday    -> day = currentWeek[1]
            R.id.Tuesday   -> day = currentWeek[2]
            R.id.Wednesday -> day = currentWeek[3]
            R.id.Thursday  -> day = currentWeek[4]
            R.id.Friday    -> day = currentWeek[5]
            R.id.Saturday  -> day = currentWeek[6]
        }
        layout.putExtra("date", day)

        startActivity(layout)

        return super.onOptionsItemSelected(item)
    }
}