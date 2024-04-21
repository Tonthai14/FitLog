package com.example.logger.day

import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import com.example.logger.Entry
import com.example.logger.EntryDatabase

@Composable
fun DayRoute(
    date: String?,
    onNavigateToAddEntry: (date: String?) -> Unit,
    onNavigateToEntry: (entryId: Long) -> Unit
) {
    val db = EntryDatabase(LocalContext.current)
    val dayEntries: MutableList<Entry> = db.getDayEntries(date)
    DayScreen(
        date = date,
        entries = dayEntries,
        onNavigateToAddEntry = onNavigateToAddEntry,
        onNavigateToEntry = onNavigateToEntry
    )
}