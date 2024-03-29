package com.example.logger

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Details : AppCompatActivity() {
    var intensity: TextView? = null
    var weight: TextView? = null
    var weightTypeDisplay: TextView? = null
    var weightType:TextView? = null
    var exerciseType: TextView? = null
    var programTypes: TextView? = null
    var programNumbers:TextView? = null // Includes Sets, Reps, and Duration

    // Includes Sets, Reps, and Duration
    var rpeDisplay: TextView? = null
    var rpe:TextView? = null
    var dateTimeDisplay: TextView? = null
    var dateTime:TextView? = null
    var id: Long = 0
    var date: String? = null

    enum class ExtraSpecs {
        WEIGHTS, RPE, DATE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(toolbar)

        id = intent.getLongExtra("ID", 0)
        date = intent.getStringExtra("date")

        val db = EntryDatabase(this)
        val entry = db.getEntry(id)
        supportActionBar?.title = entry.exercise

        // TextViews
        intensity = findViewById(R.id.intensity)
        exerciseType = findViewById(R.id.exerciseType)
        weight = findViewById(R.id.weight)
        weightTypeDisplay = findViewById(R.id.weightTypeDisplay)
        weightType = findViewById(R.id.weightType)
        programTypes = findViewById(R.id.programDisplay)
        programNumbers = findViewById(R.id.programNumbers)
        rpeDisplay = findViewById(R.id.rpeDisplay)
        rpe = findViewById(R.id.rpe)
        dateTimeDisplay = findViewById(R.id.dateTimeDisplay)
        dateTime = findViewById(R.id.dateTime)

        checkHasInput(intensity!!, entry.intensity)
        checkHasInput(exerciseType!!, entry.exerciseType)

        val hasWeights = checkHasInput(weightType!!, entry.weightType)
        val hasRPE = checkHasInput(rpe!!, entry.rpe)
        val hasDate = checkHasInput(dateTime!!, entry.dateHrs) || checkHasInput(dateTime!!, entry.dateMin)

        val specsVisibility = booleanArrayOf(hasWeights, hasRPE, hasDate)
        for ((index, spec) in ExtraSpecs.entries.withIndex()) {
            var visible = View.GONE
            if (specsVisibility[index]) {
                visible = View.VISIBLE
            }
            when (spec) {
                ExtraSpecs.WEIGHTS -> {
                    weightTypeDisplay?.setVisibility(visible)
                    weightType!!.visibility = visible
                }
                ExtraSpecs.RPE -> {
                    rpeDisplay?.setVisibility(visible)
                    rpe!!.visibility = visible
                }
                ExtraSpecs.DATE -> {
                    dateTimeDisplay?.setVisibility(visible)
                    dateTime!!.visibility = visible
                }
            }
        }

        checkHasInput(programTypes!!, entry.programType!!)
        weight?.setText(getString(R.string.weightText, entry.weight, entry.weightUnit))
        displayProgramType(entry)
    }
    private fun checkHasInput(item: TextView, text: String?): Boolean {
        if (text == null)
            return false
        if (!item.text.equals("")) {
            item.text = getString(R.string.addSpace, item.text, " ")
        } else {
            item.text = text
        }
        return true
    }
    private fun displayProgramType(entry: Entry) {
        when (entry.programType) {
            "Sets x Reps" -> programNumbers!!.text = getString(
                R.string.programNumbers,
                "", entry.sets, "x", entry.reps, ""
            )
            "Sets x Duration" -> programNumbers!!.text = getString(
                R.string.programNumbers,
                entry.sets, "x", entry.elapsedHrs,
                entry.elapsedMin, entry.elapsedSec
            )
            "Duration" -> {
                checkHasInput(programNumbers!!, entry.elapsedHrs!!)
                checkHasInput(programNumbers!!, entry.elapsedMin!!)
                checkHasInput(programNumbers!!, entry.elapsedSec!!)
            }
            "Reps" -> programNumbers!!.text = getString(
                R.string.programNumbers,
                "", entry.reps, "reps", "", ""
            )
            "1 Rep Max" -> programNumbers!!.text = getString(R.string.oneRepMax)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.editEntry) {
            val intent = Intent(this, Edit::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
        } else if (item.itemId == R.id.deleteEntry) {
            AlertDialog.Builder(this@Details)
                .setTitle("Delete Entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    val db = EntryDatabase(applicationContext)
                    db.deleteEntry(id)
                    Toast.makeText(applicationContext, "Entry Deleted", Toast.LENGTH_SHORT).show()
                    finish() // Reset detail activity
                    finishActivity(1) // for deleted entry
                    // Go back to day
                    val intent = Intent(this@Details, DayLayout::class.java)
                    intent.putExtra("date", date)
                    startActivity(intent)
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }
}