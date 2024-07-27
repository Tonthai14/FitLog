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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.data.ExerciseEntry
import com.example.logger.shared.viewmodels.EntriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    date: String?,
    onNavigateToAddEntry: (date: String?) -> Unit,
    onNavigateToEntry: (entryId: Long) -> Unit,
    viewModel: EntriesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.loadEntriesByDate(date!!)
    }
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
            for (entry in viewModel.exerciseEntries) {
                EntryDisplay(entry, onNavigateToEntry)
            }
        }
    }
}

@Composable
fun EntryDisplay(entry: ExerciseEntry, onNavigateToEntry: (entryId: Long) -> Unit) {
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
            Text(text = entry.exerciseName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right
            )
        }
        VerticalDivider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        Row(modifier = Modifier.weight(1f)) {
            Column {
                Text(text = entry.exerciseType.toString())
                Text(text = entry.structure.toString())
            }
        }
    }
}