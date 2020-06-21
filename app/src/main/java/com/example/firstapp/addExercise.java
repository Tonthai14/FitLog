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
    EditText exercise, weight, wTypeOther, sets, reps, elapsedHrs, elapsedMin, elapsedSec, dateTime,
            restTime;
    Spinner intensity, exerciseTypes, weightUnits, weightTypes, programTypes, rpe, timeRange;
    TextView setsDisplay, repsDisplay, durationDisplay, hourDisplay, minuteDisplay, secondDisplay,
            setRest, rpeDisplay, dateDisplay;
    Switch moreSpecifications;

    List<TextView> durationItems;
    // Test comment
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
        wTypeOther = findViewById(R.id.wTypeOther);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        elapsedHrs = findViewById(R.id.elapsedHrs);
        elapsedMin = findViewById(R.id.elapsedMin);
        elapsedSec = findViewById(R.id.elapsedSec);
        restTime = findViewById(R.id.restTime);
        dateTime = findViewById(R.id.dateTime);

        // TextViews
        setsDisplay = findViewById(R.id.setsDisplay);
        repsDisplay = findViewById(R.id.repsDisplay);
        durationDisplay = findViewById(R.id.durationDisplay);
        hourDisplay = findViewById(R.id.hourDisplay);
        minuteDisplay = findViewById(R.id.minuteDisplay);
        secondDisplay = findViewById(R.id.secondDisplay);
        setRest = findViewById(R.id.setRest);
        rpeDisplay = findViewById(R.id.rpeDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);

        // Lists to change visibility of one input group
        durationItems = Arrays.asList(durationDisplay, elapsedHrs, hourDisplay, elapsedMin,
                minuteDisplay, elapsedSec, secondDisplay);

        // Spinners
        intensity = findViewById(R.id.intensityTypes);
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

        timeRange = findViewById(R.id.timeRange);
        setSpinner(timeRange, R.array.timeRange);

        // Switch buttons
        moreSpecifications = findViewById(R.id.moreSpecifications);
        moreSpecifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    toggleExtraSpecifications(View.VISIBLE);
                } else {
                    toggleExtraSpecifications(View.GONE);
                }
            }
        });
        hideViewsOnStart();
    }

    public void setSpinner(Spinner spinner, int entries) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(new DefaultSelectSpinnerAdapter(
                adapter, R.layout.spinner_default_select, this));
        spinner.setOnItemSelectedListener(this);
    }

    public void setDurationVisibility(int visibility) {
        for(int index = 0; index < durationItems.size(); index++) {
            durationItems.get(index).setVisibility(visibility);
        }
    }

    public void toggleExtraSpecifications(int visibility) {
        setRest.setVisibility(visibility);
        restTime.setVisibility(visibility);
        rpeDisplay.setVisibility(visibility);
        rpe.setVisibility(visibility);
        dateDisplay.setVisibility(visibility);
        dateTime.setVisibility(visibility);
        timeRange.setVisibility(visibility);
    }

    public void hideViewsOnStart() {
        weightTypes.setVisibility(View.GONE);
        wTypeOther.setVisibility(View.GONE);
        setsDisplay.setVisibility(View.GONE);
        sets.setVisibility(View.GONE);
        repsDisplay.setVisibility(View.GONE);
        reps.setVisibility(View.GONE);
        durationDisplay.setVisibility(View.GONE);
        elapsedHrs.setVisibility(View.GONE);
        hourDisplay.setVisibility(View.GONE);
        elapsedMin.setVisibility(View.GONE);
        minuteDisplay.setVisibility(View.GONE);
        elapsedSec.setVisibility(View.GONE);
        secondDisplay.setVisibility(View.GONE);
        toggleExtraSpecifications(View.GONE);
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
                    dateTime.getText().toString());
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


    private void setProgramVisibility(boolean hasSets, boolean hasReps, boolean hasDuration) {

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
                wTypeOther.setVisibility(View.VISIBLE);
            } else {
                wTypeOther.setVisibility(View.GONE);
            }
        }
        else if(parent.getId() == programTypes.getId()) {
            switch (itemSelected) {
                case "Sets x Duration":
                    setsDisplay.setVisibility(View.VISIBLE);
                    sets.setVisibility(View.VISIBLE);
                    repsDisplay.setVisibility(View.GONE);
                    reps.setVisibility(View.GONE);
                    break;
                case "Duration":
                    setDurationVisibility(View.VISIBLE);
                case "1 Rep Max":
                    setsDisplay.setVisibility(View.GONE);
                    sets.setVisibility(View.GONE);
                    repsDisplay.setVisibility(View.GONE);
                    reps.setVisibility(View.GONE);
                    break;
                case "Reps":
                    setsDisplay.setVisibility(View.GONE);
                    sets.setVisibility(View.GONE);
                    repsDisplay.setVisibility(View.VISIBLE);
                    reps.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {}
}
