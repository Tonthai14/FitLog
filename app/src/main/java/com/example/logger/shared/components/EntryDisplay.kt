package com.example.logger.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.data.fieldoptions.ExerciseStructure
import com.example.logger.data.fieldoptions.ExerciseType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard
import com.example.logger.shared.viewmodels.EntryViewModel

@Composable
fun EditableFieldsScreen(
    title: String,
    extraContent: @Composable () -> Unit,
    viewModel: EntryViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopAppBarDisplay(title = title) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 6.dp)
        ) {
            RowDisplay(content = {
                TextField(
                    label = { Text("Exercise Name") },
                    value = viewModel.exerciseName,
                    onValueChange = { input -> viewModel.onExerciseNameChange(input) },
                    modifier = Modifier.weight(1f)
                )
            })
            RowDisplay(content = {
                val exerciseTypes = ExerciseType.entries.map { value -> value.toString() }
                DropdownOptions(
                    selectableOptions = exerciseTypes,
                    currentValue = viewModel.exerciseType.toString(),
                    onValueChanged = viewModel::onExerciseTypeChange
                )
            })
            RowDisplay(content = {
                TextField(
                    label = { Text("Weight") },
                    value = if (viewModel.weightAmount != null) viewModel.weightAmount.toString() else "",
                    onValueChange = { input ->
                        viewModel.onWeightAmountChange(if (input.isEmpty()) null else input.toFloat())
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                val standards = WeightMeasurementStandard.entries.map { value -> value.toString() }
                DropdownOptions(
                    selectableOptions = standards,
                    currentValue = viewModel.weightUnitOfMeasurement.toString(),
                    onValueChanged = viewModel::onWeightUnitOfMeasurementChange
                )
            })
            RowDisplay(content = {
                val structureTypes = ExerciseStructure.entries.map { value -> value.toString() }
                DropdownOptions(
                    selectableOptions = structureTypes,
                    currentValue = viewModel.exerciseStructure.toString(),
                    onValueChanged = viewModel::onExerciseStructureChange
                )
            })
            RowDisplay(content = {
                TextField(
                    label = { Text("# of Sets") },
                    value = if (viewModel.numberOfSets != null) viewModel.numberOfSets.toString() else "",
                    onValueChange = { input ->
                        viewModel.onNumberOfSetsChange(if (input.isEmpty()) null else input.toInt())
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(
                    label = { Text("# of Reps") },
                    value = if (viewModel.numberOfReps != null) viewModel.numberOfReps.toString() else "",
                    onValueChange = { input ->
                        viewModel.onNumberOfRepsChange(if (input.isEmpty()) null else input.toInt())
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            })
            extraContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDisplay(title: String) {
    TopAppBar(
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun RowDisplay(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        content()
    }
}

@Composable
fun DropdownOptions(
    selectableOptions: List<String>,
    currentValue: String,
    onValueChanged: (newValue: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = !expanded }) {
            Text(currentValue)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            selectableOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option ) },
                    onClick = {
                        expanded = false
                        onValueChanged(option)
                    }
                )
            }
        }
    }
}