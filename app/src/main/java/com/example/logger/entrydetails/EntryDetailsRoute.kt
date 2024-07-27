package com.example.logger.entrydetails

import androidx.compose.runtime.Composable

@Composable
fun EntryDetailsRoute(id: Long, onNavigateToEdit: (entryId: Long) -> Unit) {
    EntryDetailsScreen(id = id, onNavigateToEdit = onNavigateToEdit)
}