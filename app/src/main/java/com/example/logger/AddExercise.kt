package com.example.logger

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Arrays

class AddExercise : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var exercise: EditText? = null
    var weight: EditText? = null; var weightTypeOther: EditText? = null
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
    var partOfDay: Spinner? = null // AM or PM

    var setsDisplay: TextView? = null; var repsDisplay:TextView? = null
    var durationDisplay: TextView? = null
    var elapsedHrsDisplay:TextView? = null
    var elapsedMinDisplay:TextView? = null
    var elapsedSecDisplay:TextView? = null
    var restDisplay: TextView? = null
    var restMinDisplay:TextView? = null
    var restSecDisplay:TextView? = null
    var rpeDisplay: TextView? = null
    var dateDisplay: TextView? = null
    var dateHrsDisplay:TextView? = null
    var dateMinDisplay:TextView? = null

    var moreSpecifications: Switch? = null
    var durationItems: MutableList<TextView>? = null
    private var date: String? = null
    private var extraSpecificationsSet: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_entry)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.title = "New Entry"

        date = intent.getStringExtra("date")

        // EditTexts
        exercise = findViewById(R.id.exercise)
        exercise?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                supportActionBar?.title = s
            }
        })
        weight = findViewById(R.id.weight)
        weightTypeOther = findViewById(R.id.weightTypeOther)
        sets = findViewById(R.id.sets)
        reps = findViewById(R.id.reps)
        elapsedHrs = findViewById(R.id.elapsedHrs)
        elapsedHrs?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (elapsedHrs?.text?.length!! >= 2) {
                    elapsedMin?.requestFocus()
                }
            }
        })
        elapsedMin = findViewById(R.id.elapsedMin)
        elapsedMin?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val elapsedMinLength = elapsedMin?.text?.length!!
                if (elapsedMinLength >= 2 || elapsedMinLength <= 0) {
                    elapsedMin?.requestFocus()
                }
            }
        })
        elapsedSec = findViewById(R.id.elapsedSec)
        elapsedSec?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (elapsedSec?.text?.length!! <= 0) {
                    elapsedMin?.requestFocus()
                }
            }
        })

        // TextViews
        setsDisplay = findViewById(R.id.setsDisplay)
        repsDisplay = findViewById(R.id.repsDisplay)
        durationDisplay = findViewById(R.id.durationDisplay)
        elapsedHrsDisplay = findViewById(R.id.elapsedHrsDisplay)
        elapsedMinDisplay = findViewById(R.id.elapsedMinDisplay)
        elapsedSecDisplay = findViewById(R.id.elapsedSecDisplay)

        // Lists to change visibility of one input group
        durationItems = Arrays.asList(
            durationDisplay, elapsedHrs, elapsedHrsDisplay, elapsedMin,
            elapsedMinDisplay, elapsedSec, elapsedSecDisplay
        )

        // Spinners
        intensity = findViewById(R.id.intensity)
        setSpinner(intensity, R.array.intensity)
        exerciseType = findViewById(R.id.exerciseType)
        setSpinner(exerciseType, R.array.exerciseType)
        weightUnit = findViewById(R.id.weightUnit)
        setSpinner(weightUnit, R.array.weightUnit)
        weightType = findViewById(R.id.weightType)
        setSpinner(weightType, R.array.weightType)
        programType = findViewById(R.id.programType)
        setSpinner(programType, R.array.programType)

        // Switch button
        moreSpecifications = findViewById(R.id.moreSpecifications)
        moreSpecifications?.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                if (!extraSpecificationsSet) {
                    findExtraViews()
                    extraSpecificationsSet = true
                }
                setExtraSpecificationsVisibility(View.VISIBLE)
            } else {
                setExtraSpecificationsVisibility(View.GONE)
            }
        }
    }
    override fun onItemSelected(parent: AdapterView<*>, v: View, pos: Int, id: Long) {
        if (parent.getItemAtPosition(pos) == null) {
            return
        }
        val itemSelected = parent.getItemAtPosition(pos).toString()
        if (parent.id == exerciseType!!.id) {
            when (itemSelected) {
                "Weights" -> {
                    weightType!!.visibility = View.VISIBLE
                    weight!!.visibility = View.VISIBLE
                    weightUnit!!.visibility = View.VISIBLE
                }

                "Bodyweight", "Cardio" -> {
                    weightType!!.visibility = View.GONE
                    weight!!.visibility = View.GONE
                    weightUnit!!.visibility = View.GONE
                }

                "Weighted" -> {
                    weightType!!.visibility = View.GONE
                    weight!!.visibility = View.VISIBLE
                    weightUnit!!.visibility = View.VISIBLE
                }
            }
        } else if (parent.id == weightType!!.id) {
            if (itemSelected == "Other") {
                weightTypeOther!!.visibility = View.VISIBLE
            } else {
                weightTypeOther!!.visibility = View.GONE
            }
        } else if (parent.id == programType!!.id) {
            when (itemSelected) {
                "Sets x Reps" -> setProgramTypeVisibility(true, true, false)
                "Sets x Duration" -> setProgramTypeVisibility(true, false, true)
                "Duration" -> setProgramTypeVisibility(false, false, true)
                "1 Rep Max" -> setProgramTypeVisibility(false, false, false)
                "Reps" -> setProgramTypeVisibility(false, true, false)
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {}
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }
    private fun setSpinner(spinner: Spinner?, entries: Int) {
        val adapter = ArrayAdapter.createFromResource(this, entries, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = DefaultSelectSpinnerAdapter(adapter, R.layout.spinner_default_select, this)
        spinner?.onItemSelectedListener = this
    }

    private fun setExtraSpecificationsVisibility(visiblity: Int) {
        val extraSpecifications = listOf(restDisplay, restMin, restMinDisplay, restSec, restSecDisplay,
            rpeDisplay, dateDisplay, dateHrs, dateHrsDisplay, dateMin, dateMinDisplay)
        for (specification in extraSpecifications) {
            specification?.visibility = visiblity
        }
        rpe?.visibility = visiblity
        partOfDay?.visibility = visiblity
    }

    private fun findExtraViews() {
        restMin = findViewById(R.id.restMin)
        restMin?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (restMin!!.text.length >= 2)
                    restSec?.requestFocus()
            }
        })
        restSec = findViewById(R.id.restSec)
        restSec?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (restSec!!.text.isEmpty())
                    restMin?.requestFocus()
            }
        })
        dateHrs = findViewById(R.id.dateHrs)
        dateHrs?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (dateHrs!!.text.length >= 2)
                    dateMin?.requestFocus()
            }
        })
        dateMin = findViewById(R.id.dateMin)
        dateMin?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (dateMin!!.text.isEmpty())
                    dateHrs?.requestFocus()
            }
        })

        // TextViews
        restDisplay = findViewById(R.id.restDisplay)
        restMinDisplay = findViewById(R.id.restMinDisplay)
        restSecDisplay = findViewById(R.id.restSecDisplay)
        rpeDisplay = findViewById(R.id.rpeDisplay)
        dateDisplay = findViewById(R.id.dateDisplay)
        dateHrsDisplay = findViewById(R.id.dateHrsDisplay)
        dateMinDisplay = findViewById(R.id.dateMinDisplay)

        // Spinners
        rpe = findViewById(R.id.rpe)
        setSpinner(rpe, R.array.rpe)
        partOfDay = findViewById(R.id.partOfDay)
        setSpinner(partOfDay, R.array.timeAM_PM)
    }

    private var exerciseInput: String? = null
    private var intensityInput: String? = null
    private var exerciseTypeInput: String? = null
    private var weightInput: String? = null
    private var weightUnitInput: String? = null
    private var weightTypeInput: String? = null
    private var weightTypeOtherInput: String? = null
    private var programTypeInput: String? = null
    private var setsInput: String? = null
    private var repsInput: String? = null
    private var elapsedHrsInput: String? = null
    private var elapsedMinInput: String? = null
    private var elapsedSecInput: String? = null
    private var restMinInput: String? = null
    private var restSecInput: String? = null
    private var rpeInput: String? = null
    private var dateHrsInput: String? = null
    private var dateMinInput: String? = null
    private var partOfDayInput: String? = null
    fun collectItemInputs() {
        exerciseInput = exercise!!.text.toString()
        intensityInput = checkSpinnerContent(intensity)
        exerciseTypeInput = checkSpinnerContent(exerciseType)
        weightInput = weight!!.text.toString()
        weightUnitInput = checkSpinnerContent(weightUnit)
        weightTypeInput = checkSpinnerContent(weightType)
        weightTypeOtherInput = weightTypeOther!!.text.toString()
        programTypeInput = checkSpinnerContent(programType)
        setsInput = sets!!.text.toString()
        repsInput = reps!!.text.toString()
        elapsedHrsInput = elapsedHrs!!.text.toString()
        elapsedMinInput = elapsedMin!!.text.toString()
        elapsedSecInput = elapsedSec!!.text.toString()
        if (extraSpecificationsSet) {
            restMinInput = restMin!!.text.toString()
            restSecInput = restSec!!.text.toString()
            rpeInput = checkSpinnerContent(rpe)
            dateHrsInput = dateHrs!!.text.toString()
            dateMinInput = dateMin!!.text.toString()
            partOfDayInput = checkSpinnerContent(partOfDay)
        } else {
            restMinInput = null
            restSecInput = null
            rpeInput = null
            dateHrsInput = null
            dateMinInput = null
            partOfDayInput = null
        }
    }
    fun checkSpinnerContent(spinner: Spinner?): String {
        return if (spinner?.selectedItem == null) {
            ""
        } else spinner.selectedItem.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveEntry) {
            collectItemInputs()
            val entry = Entry()
            entry.date = date
            entry.exercise = exerciseInput
            entry.intensity = intensityInput
            entry.exerciseType = exerciseTypeInput
            entry.weight = weightInput
            entry.weightUnit = weightUnitInput
            entry.weightType = weightTypeInput
            entry.weightTypeOther = weightTypeOtherInput
            entry.programType = programTypeInput
            entry.sets = setsInput
            entry.reps = repsInput
            entry.elapsedHrs = elapsedHrsInput
            entry.elapsedMin = elapsedMinInput
            entry.elapsedSec = elapsedSecInput
            entry.restMin = restMinInput
            entry.restSec = restSecInput
            entry.rpe = rpeInput
            entry.dateHrs = dateHrsInput
            entry.dateMin = dateMinInput
            entry.AM_PM = partOfDayInput

            val db = EntryDatabase(this)
            val id = db.addEntry(entry)
            val confirm = db.getEntry(id)
            Log.d("inserted", "Entry:" + id + " -> Name: " + confirm.exercise)
            onBackPressed()
            Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setProgramTypeVisibility(hasSets: Boolean, hasReps: Boolean, hasDuration: Boolean) {
        var setsVis = View.GONE
        if (hasSets) {
            setsVis = View.VISIBLE
        }
        sets!!.visibility = setsVis
        setsDisplay!!.visibility = setsVis

        var repsVis = View.GONE
        if (hasReps) {
            repsVis = View.VISIBLE
        }
        reps!!.visibility = repsVis
        repsDisplay!!.visibility = repsVis

        var durationVis = View.GONE
        if (hasDuration) {
            durationVis = View.VISIBLE
        }
        for (index in durationItems!!.indices) {
            durationItems!![index].visibility = durationVis
        }
    }
}