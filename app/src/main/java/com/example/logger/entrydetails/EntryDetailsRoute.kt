package com.example.logger.entrydetails

import androidx.compose.runtime.Composable
import com.example.logger.Entry

@Composable
fun EntryDetailsRoute(entry: Entry, onNavigateToEdit: (entryId: Long) -> Unit) {
    EntryDetailsScreen(entry = entry, onNavigateToEdit = onNavigateToEdit)
}