package com.example.logger.addentry

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.shared.components.EditableFieldsScreen
import com.example.logger.shared.components.RowDisplay
import com.example.logger.shared.viewmodels.EntryViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEntryScreen(
    date: String?,
    onNavigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    EditableFieldsScreen(
        title = "Add entry for $date",
        extraContent = {
            RowDisplay(content = {
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.saveEntry(date)
                        onNavigateBack()
                    }
                }) {
                    Text(text = "Save")
                }
            })
        },
        viewModel = viewModel
    )
}

@Preview
@Composable
fun AddEntryScreenPreview() {
    AddEntryScreen("2024-04-11", {})
}