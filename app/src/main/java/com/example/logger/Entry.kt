package com.example.logger

class Entry {
    var id: Long = 0
    var date: String? = null
    var exercise: String? = null
    var intensity: String? = null
    var exerciseType: String? = null
    var weight: String? = null
    var weightUnit: String? = null // Unit of Measurement, e.g. lbs or kgs

    var weightType: String? = null // e.g. Barbell

    var weightTypeOther: String? = null
    var programType: String? = null // e.g. Sets and Reps

    var sets: String? = null
    var reps: String? = null
    var elapsedHrs: String? = null
    var elapsedMin: String? = null
    var elapsedSec: String? = null
    var restMin: String? = null
    var restSec: String? = null
    var rpe: String? = null
    var dateHrs: String? = null
    var dateMin: String? = null
    var AM_PM: String? = null
}