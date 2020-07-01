package com.example.firstapp;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class addExercise extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText exercise,
            weight, weightTypeOther,
            sets, reps,
            elapsedHrs, elapsedMin, elapsedSec,
            restMin, restSec,
            dateHrs, dateMin;
    Spinner intensity,
            exerciseType,
            weightUnit,
            weightType,
            programType,
            rpe,
            partOfDay; // AM or PM
    TextView setsDisplay, repsDisplay,
            durationDisplay, elapsedHrsDisplay, elapsedMinDisplay, elapsedSecDisplay,
            restDisplay, restMinDisplay, restSecDisplay,
            rpeDisplay,
            dateDisplay, dateHrsDisplay, dateMinDisplay;
    Switch moreSpecifications;

    List<TextView> durationItems;

    private boolean extraSpecificationsSet = false;
    private void findExtraViews() {
        // EditTexts
        restMin = findViewById(R.id.restMin);
        restSec = findViewById(R.id.restSec);
        dateHrs = findViewById(R.id.dateHrs);
        dateMin = findViewById(R.id.dateMin);

        restMin.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(restMin.getText().length() >= 2) {
                    restSec.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        restSec.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(restSec.getText().length() <= 0) {
                    restMin.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        dateHrs.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(dateHrs.getText().length() >= 2) {
                    dateMin.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        dateMin.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(dateMin.getText().length() <= 0) {
                    dateHrs.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // TextViews
        restDisplay = findViewById(R.id.restDisplay);
        restMinDisplay = findViewById(R.id.restMinDisplay);
        restSecDisplay = findViewById(R.id.restSecDisplay);
        rpeDisplay = findViewById(R.id.rpeDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);
        dateHrsDisplay = findViewById(R.id.dateHrsDisplay);
        dateMinDisplay = findViewById(R.id.dateMinDisplay);

        // Spinners
        rpe = findViewById(R.id.rpe);
        setSpinner(rpe, R.array.rpe);
        partOfDay = findViewById(R.id.partOfDay);
        setSpinner(partOfDay, R.array.timeAM_PM);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Entry");

        // EditTexts
        exercise = findViewById(R.id.exercise);
        weight = findViewById(R.id.weight);
        weightTypeOther = findViewById(R.id.weightTypeOther);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        elapsedHrs = findViewById(R.id.elapsedHrs);
        elapsedMin = findViewById(R.id.elapsedMin);
        elapsedSec = findViewById(R.id.elapsedSec);

        // Set toolbar title to Exercise Name
        exercise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(s);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set focus to the next EditText for an input group
        elapsedHrs.addTextChangedListener(new TextWatcher() { // Duration group
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(elapsedHrs.getText().length() >= 2) {
                    elapsedMin.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        elapsedMin.addTextChangedListener(new TextWatcher() { // Duration group
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int elapsedMinLength = elapsedMin.getText().length();
                if(elapsedMinLength >= 2) {
                    elapsedSec.requestFocus();
                }
                else if(elapsedMinLength <= 0) {
                    elapsedHrs.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        elapsedSec.addTextChangedListener(new TextWatcher() { // Duration group
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(elapsedSec.getText().length() <= 0) {
                    elapsedMin.requestFocus();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });


        // TextViews
        setsDisplay = findViewById(R.id.setsDisplay);
        repsDisplay = findViewById(R.id.repsDisplay);
        durationDisplay = findViewById(R.id.durationDisplay);
        elapsedHrsDisplay = findViewById(R.id.elapsedHrsDisplay);
        elapsedMinDisplay = findViewById(R.id.elapsedMinDisplay);
        elapsedSecDisplay = findViewById(R.id.elapsedSecDisplay);

        // Lists to change visibility of one input group
        durationItems = Arrays.asList(durationDisplay, elapsedHrs, elapsedHrsDisplay, elapsedMin,
                elapsedMinDisplay, elapsedSec, elapsedSecDisplay);

        // Spinners
        intensity = findViewById(R.id.intensity);
        setSpinner(intensity, R.array.intensity);
        exerciseType = findViewById(R.id.exerciseType);
        setSpinner(exerciseType, R.array.exerciseType);
        weightUnit = findViewById(R.id.weightUnit);
        setSpinner(weightUnit, R.array.weightUnit);
        weightType = findViewById(R.id.weightType);
        setSpinner(weightType, R.array.weightType);
        programType = findViewById(R.id.programType);
        setSpinner(programType, R.array.programType);

        // Switch button
        moreSpecifications = findViewById(R.id.moreSpecifications);
        moreSpecifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!extraSpecificationsSet) {
                        findExtraViews();
                        extraSpecificationsSet = true;
                    }
                    setExtraSpecificationsVisibility(View.VISIBLE);
                } else {
                    setExtraSpecificationsVisibility(View.GONE);
                }
            }
        });
    }

    private void setSpinner(Spinner spinner, int entries) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(new DefaultSelectSpinnerAdapter(
                adapter, R.layout.spinner_default_select, this));
        spinner.setOnItemSelectedListener(this);
    }

    private void setExtraSpecificationsVisibility(int visibility) {
        List<TextView> extraSpecifications = Arrays.asList(restDisplay, restMin, restMinDisplay,
                restSec, restSecDisplay, rpeDisplay, dateDisplay, dateHrs, dateHrsDisplay,
                dateMin, dateMinDisplay);
        for(int index = 0; index < extraSpecifications.size(); index++) {
            extraSpecifications.get(index).setVisibility(visibility);
        }
        // Spinners
        rpe.setVisibility(visibility);
        partOfDay.setVisibility(visibility);
    }

    public String checkSpinnerContent(Spinner spinner) {
        if(spinner.getSelectedItem() == null) {
            return "";
        }
        return spinner.getSelectedItem().toString();
    }

    private String exerciseInput;
    private String intensityInput;
    private String exerciseTypeInput;
    private String weightInput;
    private String weightUnitInput;
    private String weightTypeInput;
    private String weightTypeOtherInput;
    private String programTypeInput;
    private String setsInput;
    private String repsInput;
    private String elapsedHrsInput;
    private String elapsedMinInput;
    private String elapsedSecInput;
    private String restMinInput;
    private String restSecInput;
    private String rpeInput;
    private String dateHrsInput;
    private String dateMinInput;
    private String partOfDayInput;
    public void collectItemInputs() {
        exerciseInput = exercise.getText().toString();
        intensityInput = checkSpinnerContent(intensity);
        exerciseTypeInput = checkSpinnerContent(exerciseType);
        weightInput = weight.getText().toString();
        weightUnitInput = checkSpinnerContent(weightUnit);
        weightTypeInput = checkSpinnerContent(weightType);
        weightTypeOtherInput = weightTypeOther.getText().toString();
        programTypeInput = checkSpinnerContent(programType);
        setsInput = sets.getText().toString();
        repsInput = reps.getText().toString();
        elapsedHrsInput = elapsedHrs.getText().toString();
        elapsedMinInput = elapsedMin.getText().toString();
        elapsedSecInput = elapsedSec.getText().toString();
        if(extraSpecificationsSet) {
            restMinInput = restMin.getText().toString();
            restSecInput = restSec.getText().toString();
            rpeInput = checkSpinnerContent(rpe);
            dateHrsInput = dateHrs.getText().toString();
            dateMinInput = dateMin.getText().toString();
            partOfDayInput = checkSpinnerContent(partOfDay);
        } else {
            restMinInput = null;
            restSecInput = null;
            rpeInput = null;
            dateHrsInput = null;
            dateMinInput = null;
            partOfDayInput = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.saveEntry) {
            collectItemInputs();
            Entry entry = new Entry(
                    exerciseInput,
                    intensityInput,
                    exerciseTypeInput,
                    weightInput,
                    weightUnitInput,
                    weightTypeInput,
                    weightTypeOtherInput,
                    programTypeInput,
                    setsInput,
                    repsInput,
                    elapsedHrsInput,
                    elapsedMinInput,
                    elapsedSecInput,
                    restMinInput,
                    restSecInput,
                    rpeInput,
                    dateHrsInput,
                    dateMinInput,
                    partOfDayInput);
            EntryDatabase db = new EntryDatabase(this);
            long id = db.addEntry(entry);
            Entry confirm = db.getEntry(id);
            Log.d("inserted", "Entry:" + id + " -> Name: " + confirm.getExercise());
            onBackPressed();
            Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setProgramTypeVisibility(boolean hasSets, boolean hasReps, boolean hasDuration) {
        int setsVis = View.GONE;
        if(hasSets) { setsVis = View.VISIBLE; }
        sets.setVisibility(setsVis);
        setsDisplay.setVisibility(setsVis);

        int repsVis = View.GONE;
        if(hasReps) { repsVis = View.VISIBLE; }
        reps.setVisibility(repsVis);
        repsDisplay.setVisibility(repsVis);

        int durationVis = View.GONE;
        if(hasDuration) { durationVis = View.VISIBLE; }
        for(int index = 0; index < durationItems.size(); index++) {
            durationItems.get(index).setVisibility(durationVis);
        }
    }

    public void onItemSelected(@NonNull AdapterView<?> parent, View v, int pos, long id) {
        if(parent.getItemAtPosition(pos) == null) {
            return;
        }
        String itemSelected = parent.getItemAtPosition(pos).toString();
        if(parent.getId() == exerciseType.getId()) {
            switch (itemSelected) {
                case "Weights":
                    weightType.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    weightUnit.setVisibility(View.VISIBLE);
                    break;
                case "Bodyweight": case "Cardio":
                    weight.setVisibility(View.GONE);
                    weightUnit.setVisibility(View.GONE);
                    break;
                default:
                    weightType.setVisibility(View.GONE);
                    weight.setVisibility(View.VISIBLE);
                    weightUnit.setVisibility(View.VISIBLE);
            }
        }
        else if(parent.getId() == weightType.getId()) {
            if(itemSelected.equals("Other")) {
                weightTypeOther.setVisibility(View.VISIBLE);
            } else {
                weightTypeOther.setVisibility(View.GONE);
            }
        }
        else if(parent.getId() == programType.getId()) {
            switch (itemSelected) {
                case "Sets x Reps":
                    setProgramTypeVisibility(true, true, false);
                    break;
                case "Sets x Duration":
                    setProgramTypeVisibility(true, false, true);
                    break;
                case "Duration":
                    setProgramTypeVisibility(false, false, true);
                    break;
                case "1 Rep Max":
                    setProgramTypeVisibility(false, false, false);
                    break;
                case "Reps":
                    setProgramTypeVisibility(false, true, false);
                    break;
            }
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }
}
