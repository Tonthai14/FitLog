package com.example.logger.entrydetails


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logger.Entry
import com.example.logger.EntryDatabase

@Preview
@Composable
fun EntryDetailsScreenPreview() {
    val entry = Entry()
    entry.exercise = "Bench Press"
    entry.intensity = "Moderate"
    entry.exerciseType = "Weights"
    entry.weightType = "Barbell"
    entry.weight = "135"
    entry.programType = "Sets x Reps"
    EntryDetailsScreen(entry = entry, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailsScreen(entry: Entry, onNavigateToEdit: (entryId: Long) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = entry.exercise!!, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Entry")
                    }
                    IconButton(onClick = { onNavigateToEdit(entry.id) }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Entry")
                    }
                }
            )
        })
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DetailRow(label = "Intensity", value = entry.intensity)
            DetailRow(label = "Exercise Type", value = entry.exerciseType)
            DetailRow(label = "Weight Type", value = entry.weightType)
            DetailRow(label = "Weight", value = entry.weight)
            DetailRow(label = "Unit of Measurement", value = entry.weightUnit)
            DetailRow(label = "Program Type", value = entry.programType)
            DetailRow(label = "RPE", value = entry.rpe)
            DetailRow(label = "Time", value = entry.AM_PM)
        }
    }

    if (showDeleteDialog) {
        DeleteEntryDialog(entry.id)
    }
}

@Composable
fun DeleteEntryDialog(id: Long) {
    var dismissAlertDialog by remember { mutableStateOf(false) }
    var confirmDeletion by remember { mutableStateOf(false) }

    if (dismissAlertDialog) return
    AlertDialog(
        title = { Text("Delete Entry") },
        text = { Text("Are you sure you want to delete this entry?") },
        onDismissRequest = { dismissAlertDialog = true },
        confirmButton = {
            TextButton(onClick = { confirmDeletion = true }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissAlertDialog = true }) {
                Text("No")
            }
        }
    )

    if (confirmDeletion) {
        EntryDeletion(id)
    }
}

@Composable
fun EntryDeletion(id: Long) {
    EntryDatabase(LocalContext.current).deleteEntry(id)
    Toast.makeText(LocalContext.current, "Entry Deleted", Toast.LENGTH_SHORT).show()
}

@Composable
fun DetailRow(label: String, value: String?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = value ?: "No Input", fontSize = 18.sp)
    }
}