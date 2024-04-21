package com.example.logger.day

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logger.Entry
import com.example.logger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    date: String?,
    entries: MutableList<Entry>?,
    onNavigateToAddEntry: (date: String?) -> Unit,
    onNavigateToEntry: (entryId: Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = date!!, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { onNavigateToAddEntry(date) }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Entry")
                    }
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            for (entry in entries!!) {
                EntryDisplay(entry, onNavigateToEntry)
            }
        }
    }
}

@Composable
fun EntryDisplay(entry: Entry, onNavigateToEntry: (entryId: Long) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                Log.d("Day Screen", "Entry ID ${entry.id}")
                onNavigateToEntry(entry.id)
            }
            .padding(16.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(40))
            .padding(16.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.Right,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = entry.exercise!!,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right
            )
        }
        VerticalDivider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        Row(modifier = Modifier.weight(1f)) {
            Column {
                Text(text = entry.exerciseType!!)
                var duration = "None"
                when (entry.programType) {
                    "Sets x Reps" -> duration = LocalContext.current.getString(R.string.programNumbers, entry.sets, "Sets", "x", entry.reps, "Reps")
                    "Sets x Duration" -> duration = LocalContext.current.getString(R.string.programNumbers, entry.sets, "Sets", "x", entry.elapsedMin, "Mins")
                    "Reps" -> duration = LocalContext.current.getString(R.string.programNumbers, "", entry.reps, "Reps", "", "")
                    "1 Rep Max" -> duration = entry.programType!!
                }
                Text(text = duration)
            }
        }
    }
}

@Preview
@Composable
fun DayScreenPreview() {
    val entry1 = Entry()
    entry1.exercise = "Squat"
    entry1.date = "04-01-2024"
    entry1.exerciseType = "Weights"
    entry1.programType = "Sets x Reps"
    entry1.sets = "3"
    entry1.reps = "6"
    val entry2 = Entry()
    entry2.exercise = "Push Ups"
    entry2.date = "04-01-2024"
    entry2.exerciseType = "Bodyweight"
    entry2.programType = "Reps"
    entry2.reps = "24"
    val entry3 = Entry()
    entry3.exercise = "Deadlift"
    entry3.date = "04-01-2024"
    entry3.exerciseType = "Weights"
    entry3.programType = "Sets x Reps"
    entry3.sets = "3"
    entry3.reps = "8"
    val entries = mutableListOf(entry1, entry2, entry3)

    DayScreen(date = "04-01-2024", entries = entries, {}, {})
}