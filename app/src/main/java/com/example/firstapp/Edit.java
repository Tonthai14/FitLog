package com.example.firstapp;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText exercise,
            weight,
            weightTypeOther,
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
    long id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        Intent intent = getIntent();
        id = intent.getLongExtra("ID", 0);
        EntryDatabase db = new EntryDatabase(this);
        Entry entry = db.getEntry(id);

        // EditTexts
        exercise = findViewById(R.id.exercise);
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

        final String name = entry.getExercise();

        exercise.setText(name);
        weight.setText(entry.getWeight());
        // TODO: Include weightTypeOther
        sets.setText(entry.getSets());
        reps.setText(entry.getReps());
        elapsedHrs.setText(entry.getElapsedHrs());
        elapsedMin.setText(entry.getElapsedMin());
        elapsedSec.setText(entry.getElapsedSec());
        restMin.setText(entry.getRestMin());
        restSec.setText(entry.getRestSec());
        dateHrs.setText(entry.getDateHrs());
        dateMin.setText(entry.getDateMin());

        setSpinnerItem(intensity, entry.getIntensity());
        setSpinnerItem(exerciseTypes, entry.getExerciseType());
        setSpinnerItem(weightUnits, entry.getWeightType());
        setSpinnerItem(programTypes, entry.getProgramType());
        setSpinnerItem(rpe, entry.getRpe());
        setSpinnerItem(timeAM_PM, entry.getAM_PM());

        exercise.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(name);
            }
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void setSpinner(Spinner spinner, int entries) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void setSpinnerItem(Spinner spinner, String item) {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.saveEntry) {
            Entry entry = new Entry(id,
                    exercise.getText().toString(),
                    intensity.getSelectedItem().toString(),
                    exerciseTypes.getSelectedItem().toString(),
                    weight.getText().toString(),
                    weightUnits.getSelectedItem().toString(),
                    weightTypes.getSelectedItem().toString(),
                    programTypes.getSelectedItem().toString(),
                    sets.getText().toString(),
                    reps.getText().toString(),
                    elapsedHrs.getText().toString(),
                    elapsedMin.getText().toString(),
                    elapsedSec.getText().toString(),
                    restMin.getText().toString(),
                    restSec.getText().toString(),
                    rpe.getSelectedItem().toString(),
                    dateHrs.getText().toString(),
                    dateMin.getText().toString(),
                    timeAM_PM.getSelectedItem().toString());
            Log.d("EDITED", "Edit before saving id: ->" + entry.getId());
            EntryDatabase db = new EntryDatabase(getApplicationContext());
            long id = db.editEntry(entry);
            Log.d("EDITED", "Edit made id: ->" + id);
            // Go back to main
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Edited Entry", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {}
    public void onNothingSelected(AdapterView<?> parent) {}
}
