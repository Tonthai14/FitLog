package com.example.firstapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Objects;

public class Details extends AppCompatActivity {
    TextView intensity,
            weight,
            weightTypeDisplay, weightType,
            exerciseType,
            programTypes, programNumbers, // Includes Sets, Reps, and Duration
            rpeDisplay, rpe,
            dateTimeDisplay, dateTime;
    long id;

    enum extraSpecs {
        WEIGHTS,
        RPE,
        DATE
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        Intent intent = getIntent();
        id = intent.getLongExtra("ID", 0);
        EntryDatabase db = new EntryDatabase(this);
        Entry entry = db.getEntry(id);
        Objects.requireNonNull(getSupportActionBar()).setTitle(entry.getExercise());

        // TextViews
        intensity = findViewById(R.id.intensity);
        exerciseType = findViewById(R.id.exerciseType);
        weight = findViewById(R.id.weight);
        weightTypeDisplay = findViewById(R.id.weightTypeDisplay);
        weightType = findViewById(R.id.weightType);
        programTypes = findViewById(R.id.programDisplay);
        programNumbers = findViewById(R.id.programNumbers);
        rpeDisplay = findViewById(R.id.rpeDisplay);
        rpe = findViewById(R.id.rpe);
        dateTimeDisplay = findViewById(R.id.dateTimeDisplay);
        dateTime = findViewById(R.id.dateTime);

        checkHasInput(intensity, entry.getIntensity());
        checkHasInput(exerciseType, entry.getExerciseType());

        boolean hasWeights = checkHasInput(weightType, entry.getWeightType());
        boolean hasRPE = checkHasInput(rpe, entry.getRpe());
        boolean hasDate =
                checkHasInput(dateTime, entry.getDateHrs()) ||
                checkHasInput(dateTime, entry.getDateMin());

        boolean[] specsVisibility = new boolean[]{ hasWeights, hasRPE, hasDate };
        int index = 0;
        for(extraSpecs spec : extraSpecs.values()) {
            int visible = View.GONE;
            if(specsVisibility[index]) { visible = View.VISIBLE; }
            switch (spec) {
                case WEIGHTS:
                    weightTypeDisplay.setVisibility(visible);
                    weightType.setVisibility(visible);
                    break;
                case RPE:
                    rpeDisplay.setVisibility(visible);
                    rpe.setVisibility(visible);
                    break;
                case DATE:
                    dateTimeDisplay.setVisibility(visible);
                    dateTime.setVisibility(visible);
                    break;
            }
            index++;
        }

        checkHasInput(programTypes, entry.getProgramType());
        weight.setText(getString(R.string.weightText, entry.getWeight(), entry.getWeightUnit()));
        displayProgramType(entry);
    }

    private boolean checkHasInput(TextView item, String text) {
        if(text != null) { // Not null or not empty
            if(!item.getText().equals("")) {
                item.setText(getString(R.string.addSpace, item.getText(), " "));
            }
            item.setText(text);
            return true;
        }
        return false;
    }

    private void displayProgramType(Entry entry) {
        switch(entry.getProgramType()) {
            case "Sets x Reps":
                programNumbers.setText(getString(R.string.programNumbers,
                        "", entry.getSets(), "x", entry.getReps(), ""));
                break;
            case "Sets x Duration":
                programNumbers.setText(getString(R.string.programNumbers,
                        entry.getSets(), "x", entry.getElapsedHrs(),
                        entry.getElapsedMin(), entry.getElapsedSec()));
                break;
            case "Duration":
                checkHasInput(programNumbers, entry.getElapsedHrs());
                checkHasInput(programNumbers, entry.getElapsedMin());
                checkHasInput(programNumbers, entry.getElapsedSec());
                break;
            case "Reps":
                programNumbers.setText(getString(R.string.programNumbers,
                        "", entry.getReps(), "reps", "", ""));
                break;
            case "1 Rep Max":
                programNumbers.setText(getString(R.string.oneRepMax));
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editEntry) {
            Intent intent = new Intent(this, Edit.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        } else if(item.getItemId() == R.id.deleteEntry) {
            EntryDatabase db = new EntryDatabase(getApplicationContext());
            db.deleteEntry(id);
            Toast.makeText(getApplicationContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
            // Go back to main
            Intent intent = new Intent(this, DayLayout.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
