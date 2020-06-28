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
            exerciseTypes,
            weightUnits,
            weightTypes,
            programTypes,
            rpe,
            timeAM_PM;
    TextView setsDisplay, repsDisplay,
            durationDisplay, elapsedHrsDisplay, elapsedMinDisplay, elapsedSecDisplay,
            restDisplay, restMinDisplay, restSecDisplay,
            rpeDisplay,
            dateDisplay, dateHrsDisplay, dateMinDisplay;
    Switch moreSpecifications;

    List<TextView> durationItems, extraSpecifications;

    private void findExtraViews() {
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
        weight = findViewById(R.id.weight);
        weightTypeOther = findViewById(R.id.weightTypeOther);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        elapsedHrs = findViewById(R.id.elapsedHrs);
        elapsedMin = findViewById(R.id.elapsedMin);
        elapsedSec = findViewById(R.id.elapsedSec);
        restMin = findViewById(R.id.restMin);
        restSec = findViewById(R.id.restSec);
        dateHrs = findViewById(R.id.dateHrs);
        dateMin = findViewById(R.id.dateMin);

        // TextViews
        setsDisplay = findViewById(R.id.setsDisplay);
        repsDisplay = findViewById(R.id.repsDisplay);
        durationDisplay = findViewById(R.id.durationDisplay);
        elapsedHrsDisplay = findViewById(R.id.elapsedHrsDisplay);
        elapsedMinDisplay = findViewById(R.id.elapsedMinDisplay);
        elapsedSecDisplay = findViewById(R.id.elapsedSecDisplay);
        restDisplay = findViewById(R.id.restDisplay);
        restMinDisplay = findViewById(R.id.restMinDisplay);
        restSecDisplay = findViewById(R.id.restSecDisplay);
        rpeDisplay = findViewById(R.id.rpeDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);
        dateHrsDisplay = findViewById(R.id.dateHrsDisplay);
        dateMinDisplay = findViewById(R.id.dateMinDisplay);

        // Lists to change visibility of one input group
        durationItems = Arrays.asList(durationDisplay, elapsedHrs, elapsedHrsDisplay, elapsedMin,
                elapsedMinDisplay, elapsedSec, elapsedSecDisplay);
        extraSpecifications = Arrays.asList(restDisplay, restMin, restMinDisplay, restSec,
                restSecDisplay, rpeDisplay, dateDisplay, dateHrs, dateHrsDisplay, dateMin,
                dateMinDisplay);

        // Spinners
        intensity = findViewById(R.id.intensity);
        setSpinner(intensity, R.array.intensity);

        exerciseTypes = findViewById(R.id.exerciseTypes);
        setSpinner(exerciseTypes, R.array.exerciseTypes);

        weightUnits = findViewById(R.id.weightUnits);
        setSpinner(weightUnits, R.array.weightUnits);

        weightTypes = findViewById(R.id.weightTypes);
        setSpinner(weightTypes, R.array.weightTypes);

        programTypes = findViewById(R.id.programTypes);
        setSpinner(programTypes, R.array.programTypes);

        rpe = findViewById(R.id.rpe);
        setSpinner(rpe, R.array.numTen);

        timeAM_PM = findViewById(R.id.timeAM_PM);
        setSpinner(timeAM_PM, R.array.timeAM_PM);

        // Switch button
        moreSpecifications = findViewById(R.id.moreSpecifications);
        moreSpecifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
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
        for(int index = 0; index < extraSpecifications.size(); index++) {
            extraSpecifications.get(index).setVisibility(visibility);
        }
        // Spinners
        rpe.setVisibility(visibility);
        timeAM_PM.setVisibility(visibility);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    public String checkSpinnerContent(Spinner spinner) {
        if(spinner.getSelectedItem() == null) {
            return "";
        }
        return spinner.getSelectedItem().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.saveEntry) {
            Entry entry = new Entry(
                    exercise.getText().toString(),
                    checkSpinnerContent(intensity),
                    checkSpinnerContent(exerciseTypes),
                    weight.getText().toString(),
                    checkSpinnerContent(weightUnits),
                    checkSpinnerContent(weightTypes),
                    checkSpinnerContent(programTypes),
                    sets.getText().toString(),
                    reps.getText().toString(),
                    elapsedHrs.getText().toString(),
                    elapsedMin.getText().toString(),
                    elapsedSec.getText().toString(),
                    restMin.getText().toString(),
                    restSec.getText().toString(),
                    checkSpinnerContent(rpe),
                    dateHrs.getText().toString(),
                    dateMin.getText().toString(),
                    checkSpinnerContent(timeAM_PM));
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
        if(parent.getId() == exerciseTypes.getId()) {
            switch (itemSelected) {
                case "Weights":
                    weightTypes.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    weightUnits.setVisibility(View.VISIBLE);
                    break;
                case "Bodyweight": case "Cardio":
                    weight.setVisibility(View.GONE);
                    weightUnits.setVisibility(View.GONE);
                    break;
                default:
                    weightTypes.setVisibility(View.GONE);
                    weight.setVisibility(View.VISIBLE);
                    weightUnits.setVisibility(View.VISIBLE);
            }
        }
        else if(parent.getId() == weightTypes.getId()) {
            if(itemSelected.equals("Other")) {
                weightTypeOther.setVisibility(View.VISIBLE);
            } else {
                weightTypeOther.setVisibility(View.GONE);
            }
        }
        else if(parent.getId() == programTypes.getId()) {
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
}
