package com.example.logger.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen(
    onNavigateToDay: (date: String) -> Unit
) {
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    val today = calendar.time
    calendar.roll(Calendar.DATE, 1)
    val yesterday = calendar.time
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        DayButton(text = "Today's Exercises", onNavigateToDay, sdf.format(today))
        DayButton(text = "Yesterday's Exercises", onNavigateToDay = onNavigateToDay, date = sdf.format(yesterday))
    }
}

@Composable
fun DayButton(
    text: String,
    onNavigateToDay: (date: String) -> Unit,
    date: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        Button(
            onClick = { onNavigateToDay(date) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToDay = {})
}