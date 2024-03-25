package com.example.logger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DayLayout : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var db: EntryDatabase? = null
    var noEntries: TextView? = null
    var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_layout)
        date = intent.getStringExtra("date")
        Log.d("DayLayout", date!!)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.title = date
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = EntryDatabase(this)
        val dayEntries: MutableList<Entry>? = db?.getDayEntries(date)
        recyclerView = findViewById(R.id.myRecyclerView)
        noEntries = findViewById(R.id.noEntries)
        listEmpty(dayEntries)
    }
    fun listEmpty(entries: MutableList<Entry>?) {
        if (entries!!.isEmpty()) {
            noEntries!!.visibility = View.VISIBLE
        } else {
            noEntries!!.visibility = View.GONE
            displayList(entries)
        }
    }
    fun displayList(allEntries: List<Entry?>?) {
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(this, allEntries, date)
        recyclerView!!.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new -> {
                Toast.makeText(this, "Add New Entry", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AddExercise::class.java)
                intent.putExtra("date", date)
                Log.d("DayLayout ADD NEW", date!!)
                startActivity(intent)
            }
            R.id.choose_preset -> Toast.makeText(this, "Choose A Preset", Toast.LENGTH_SHORT).show()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        listEmpty(db?.getDayEntries(date))
    }
}