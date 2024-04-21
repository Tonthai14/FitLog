package com.example.logger.addentry

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.EntryDatabase
import com.example.logger.shared.components.EditableFieldsScreen
import com.example.logger.shared.components.RowDisplay
import com.example.logger.shared.viewmodels.EntryViewModel

@Composable
fun AddEntryScreen(
    date: String?,
    onNavigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel()
) {
    EditableFieldsScreen(
        title = "Add entry for $date",
        extraContent = {
            RowDisplay(content = {
                Button(onClick = {
                    onSaveEntry(date, viewModel)
                    onNavigateBack()
                }) {
                    Text(text = "Save")
                }
            })
        },
        viewModel = viewModel
    )
}

fun onSaveEntry(date: String?, viewModel: EntryViewModel) {
    val db = EntryDatabase.getInstance(null)
    val createdId = db.addEntry(date, viewModel)
}

@Preview
@Composable
fun AddEntryScreenPreview() {
    AddEntryScreen("2024-04-11", {})
}