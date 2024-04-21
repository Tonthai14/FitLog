package com.example.logger.editentry

import androidx.compose.runtime.Composable
import com.example.logger.EditEntryScreen
import com.example.logger.Entry

@Composable
fun EditEntryRoute(entry: Entry, onNavigateBack: () -> Unit) {
    EditEntryScreen(entry = entry, onNavigateBack = onNavigateBack)
}