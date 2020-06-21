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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Details extends AppCompatActivity {
    TextView intensity, weight, exerciseType, programTypes, programNumbers, dateTime;
    long id;

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

        intensity = findViewById(R.id.intensityTypes);
        exerciseType = findViewById(R.id.exerciseTypes);
        weight = findViewById(R.id.weight);
        dateTime = findViewById(R.id.dateTime);
        programTypes = findViewById(R.id.programDisplay);
        programNumbers = findViewById(R.id.programNumbers);

        checkHasInput(intensity, entry.getIntensity());
        checkHasInput(exerciseType, entry.getExerciseType());
        checkHasInput(dateTime, entry.getDateTime());
        checkHasInput(programTypes, entry.getProgramType());
        weight.setText(getString(R.string.weightText, entry.getWeight(), entry.getWeightUnit()));


        if(!entry.getSets().isEmpty() && !entry.getReps().isEmpty()) {
            programNumbers.setText(getString(
                    R.string.programNumbers, entry.getSets(), "x", entry.getReps(),"",""));
        } else if(!entry.getSets().isEmpty()) {
            programNumbers.setText(getString(
                    R.string.programNumbers, entry.getSets(),"","","",""));
        } else if(!entry.getReps().isEmpty()) {
            programNumbers.setText(getString(
                    R.string.programNumbers, entry.getReps(),"","","",""));
        }
    }

    public void checkHasInput(TextView item, String text) {
        if(!text.isEmpty()) {
            item.setText(text);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
