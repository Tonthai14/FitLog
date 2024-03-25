package com.example.logger

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.indices

class Edit : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var exercise: EditText? = null
    var weight: EditText? = null
    var weightTypeOther: EditText? = null
    var sets: EditText? = null; var reps: EditText? = null
    var elapsedHrs: EditText? = null; var elapsedMin: EditText? = null; var elapsedSec: EditText? = null
    var restMin: EditText? = null; var restSec: EditText? = null
    var dateHrs: EditText? = null; var dateMin: EditText? = null

    var intensity: Spinner? = null
    var exerciseType: Spinner? = null
    var weightUnit: Spinner? = null
    var weightType: Spinner? = null
    var programType: Spinner? = null
    var rpe: Spinner? = null
    var timeAM_PM: Spinner? = null

    var id: Long? = null
    var date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_entry)
        setSupportActionBar(findViewById(R.id.toolbar))
        // EditTexts
        exercise = findViewById(R.id.exercise)
        weight = findViewById(R.id.weight)
        weightTypeOther = findViewById(R.id.weightTypeOther)
        sets = findViewById(R.id.sets)
        reps = findViewById(R.id.reps)
        elapsedHrs = findViewById(R.id.elapsedHrs)
        elapsedMin = findViewById(R.id.elapsedMin)
        elapsedSec = findViewById(R.id.elapsedSec)
        restMin = findViewById(R.id.restMin)
        restSec = findViewById(R.id.restSec)
        dateHrs = findViewById(R.id.dateHrs)
        dateMin = findViewById(R.id.dateMin)
        // Spinners
        intensity = findViewById(R.id.intensity)
        setSpinner(intensity!!, R.array.intensity)
        exerciseType = findViewById(R.id.exerciseType)
        setSpinner(exerciseType!!, R.array.exerciseType)
        weightUnit = findViewById(R.id.weightUnit)
        setSpinner(weightUnit!!, R.array.weightUnit)
        weightType = findViewById(R.id.weightType)
        setSpinner(weightType!!, R.array.weightType)
        programType = findViewById(R.id.programType)
        setSpinner(programType!!, R.array.programType)
        rpe = findViewById(R.id.rpe)
        setSpinner(rpe!!, R.array.rpe)
        timeAM_PM = findViewById(R.id.partOfDay)
        setSpinner(timeAM_PM!!, R.array.timeAM_PM)

        id = intent.getLongExtra("ID", 0)
        val db = EntryDatabase(this)
        val entry = db.getEntry(id!!)
        date = entry.date

        exercise!!.setText(entry.exercise)
        weight!!.setText(entry.weight)
        weightTypeOther!!.setText(entry.weightTypeOther)
        sets!!.setText(entry.sets)
        reps!!.setText(entry.reps)
        elapsedHrs!!.setText(entry.elapsedHrs)
        elapsedMin!!.setText(entry.elapsedMin)
        elapsedSec!!.setText(entry.elapsedSec)
        restMin!!.setText(entry.restMin)
        restSec!!.setText(entry.restSec)
        dateHrs!!.setText(entry.dateHrs)
        dateMin!!.setText(entry.dateMin)

        setSpinnerItem(intensity!!, entry.intensity)
        setSpinnerItem(exerciseType!!, entry.exerciseType)
        setSpinnerItem(weightUnit!!, entry.weightType)
        setSpinnerItem(programType!!, entry.programType)
        setSpinnerItem(rpe!!, if (entry.rpe == null) "" else entry.rpe)
        setSpinnerItem(timeAM_PM!!, if (entry.AM_PM == null) "" else entry.AM_PM)

        exercise!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                supportActionBar!!.title = entry.exercise
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    supportActionBar!!.title = s
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setSpinner(spinner: Spinner, entries: Int) {
        val adapter = ArrayAdapter.createFromResource(this, entries, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    private fun setSpinnerItem(spinner: Spinner, item: String?) {
        for (index in spinner.indices) {
            if (spinner.getItemAtPosition(index).equals(item)) {
                spinner.setSelection(index)
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveEntry) {
            val entry = Entry()
            entry.id = id!!
            entry.date = date
            entry.exercise = exercise!!.text.toString()
            entry.intensity = intensity!!.selectedItem.toString()
            entry.exerciseType = exerciseType!!.selectedItem.toString()
            entry.weight = weight!!.text.toString()
            entry.weightUnit = weightUnit!!.selectedItem.toString()
            entry.weightType = weightType!!.selectedItem.toString()
            entry.weightTypeOther = weightTypeOther!!.text.toString()
            entry.programType = programType!!.selectedItem.toString()
            entry.sets = sets!!.text.toString()
            entry.reps = reps!!.text.toString()
            entry.elapsedHrs = elapsedHrs!!.text.toString()
            entry.elapsedMin = elapsedMin!!.text.toString()
            entry.elapsedSec = elapsedSec!!.text.toString()
            entry.restMin= restMin!!.text.toString()
            entry.restSec = restSec!!.text.toString()
            entry.rpe = rpe!!.selectedItem.toString()
            entry.dateHrs = dateHrs!!.text.toString()
            entry.dateMin = dateMin!!.text.toString()
            entry.AM_PM = timeAM_PM!!.selectedItem.toString()
            Log.d("EDITED", "ID before saving edit: ${entry.id}")

            val db = EntryDatabase(applicationContext)
            val updatedId = db.editEntry(entry)
            Log.d("EDITED", "ID after saving edit: $updatedId")

            // Go back to main screen
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Successfully edited entry", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
    override fun onNothingSelected(parent: AdapterView<*>?) {}
}