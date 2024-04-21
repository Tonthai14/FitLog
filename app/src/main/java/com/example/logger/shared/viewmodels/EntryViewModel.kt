package com.example.logger.shared.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logger.EntryDatabase
import kotlinx.coroutines.launch
import java.lang.Exception

class EntryViewModel: ViewModel() {
    var exerciseName by mutableStateOf("")
        private set
    fun onExerciseNameChange(input: String) {
        exerciseName = input
    }

    var exerciseType by mutableStateOf("")
        private set
    fun onExerciseTypeChange(input: String) {
        exerciseType = input
    }

    private var _weightAmount = mutableStateOf<Float?>(null)
    val weightAmount: Float?
        get() = _weightAmount.value
    fun onWeightAmountChange(input: Float?) {
        _weightAmount.value = input
    }

    enum class MeasurementStandard(val unitOfMeasurement: String) {
        POUNDS("lb"),
        KILOGRAMS("kg")
    }
    var weightUnitOfMeasurement by mutableStateOf(MeasurementStandard.POUNDS)
        private set
    fun onWeightUnitOfMeasurementChange(input: String) {
        weightUnitOfMeasurement = MeasurementStandard.valueOf(input)
    }

    private var _numberOfSets = mutableStateOf<Int?>(null)
    val numberOfSets: Int?
        get() = _numberOfSets.value
    fun onNumberOfSetsChange(input: Int?) {
        _numberOfSets.value = input
    }

    private var _numberOfReps = mutableStateOf<Int?>(null)
    val numberOfReps: Int?
        get() = _numberOfReps.value

    fun onNumberOfRepsChange(input: Int?) {
        _numberOfReps.value = input
    }

    fun fetchEntryData(entryId: Long) {
        viewModelScope.launch {
            try {
                val entry = EntryDatabase.getInstance(null).getEntry(entryId)
                exerciseName = entry.exercise!!
                exerciseType = entry.exerciseType!!
                _weightAmount.value = entry.weight!!.toFloat()
                weightUnitOfMeasurement = MeasurementStandard.valueOf(entry.weightUnit!!)
                _numberOfSets.value = entry.sets!!.toInt()
                _numberOfReps.value = entry.reps!!.toInt()
            } catch (exception: Exception) {

            }
        }
    }
}