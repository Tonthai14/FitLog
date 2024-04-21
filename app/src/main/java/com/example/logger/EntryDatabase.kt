package com.example.logger

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.logger.shared.viewmodels.EntryViewModel
import kotlin.concurrent.Volatile


class EntryDatabase(ct: Context?) : SQLiteOpenHelper(ct, "EntryDB", null, 14) {
    private val TABLE_NAME = "EntryTable"
    private val KEY_ID = "id"
    private val KEY_DATE = "date"
    private val KEY_EXERCISE = "exercise"
    private val KEY_INTENSITY = "intensity"
    private val KEY_EXERCISE_TYPE = "exerciseType"
    private val KEY_WEIGHT = "weight"
    private val KEY_WEIGHT_UNIT = "weightUnit"
    private val KEY_WEIGHT_TYPE = "weightType"
    private val KEY_WEIGHT_TYPE_OTHER = "weightTypeOther"
    private val KEY_PROGRAM_TYPE = "programType"
    private val KEY_SETS = "sets"
    private val KEY_REPS = "reps"
    private val KEY_ELAPSED_HRS = "elapsedHrs"
    private val KEY_ELAPSED_MIN = "elapsedMin"
    private val KEY_ELAPSED_SEC = "elapsedSec"
    private val KEY_REST_MIN = "restMin"
    private val KEY_REST_SEC = "restSec"
    private val KEY_RPE = "rpe"
    private val KEY_DATE_HRS = "dateHrs"
    private val KEY_DATE_MIN = "dateMin"
    private val KEY_AM_PM = "AM_PM"
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_DATE TEXT," +
                "$KEY_EXERCISE TEXT," +
                "$KEY_EXERCISE_TYPE TEXT," +
                "$KEY_WEIGHT TEXT," +
                "$KEY_WEIGHT_UNIT TEXT," +
                "$KEY_WEIGHT_TYPE TEXT," +
                "$KEY_WEIGHT_TYPE_OTHER TEXT," +
                "$KEY_PROGRAM_TYPE TEXT," +
                "$KEY_SETS TEXT," +
                "$KEY_REPS TEXT," +
                "$KEY_ELAPSED_HRS TEXT," +
                "$KEY_ELAPSED_MIN TEXT," +
                "$KEY_ELAPSED_SEC TEXT," +
                "$KEY_REST_MIN TEXT," +
                "$KEY_REST_SEC TEXT," +
                "$KEY_RPE TEXT," +
                "$KEY_DATE_HRS TEXT," +
                "$KEY_DATE_MIN TEXT," +
                "$KEY_AM_PM)"
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL(createTableSQL)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion > newVersion)
            return
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object {
        @Volatile
        private var instance: EntryDatabase? = null
        fun getInstance(context: Context?) = instance ?: synchronized(this) {
            instance ?: EntryDatabase(context).also { instance = it }
        }
    }
    fun addEntry(entry: Entry): Long {
        val cv = ContentValues().apply {
            put(KEY_DATE, entry.date)
            put(KEY_EXERCISE, entry.exercise)
            put(KEY_INTENSITY, entry.intensity)
            put(KEY_EXERCISE_TYPE, entry.exerciseType)
            put(KEY_WEIGHT, entry.weight)
            put(KEY_WEIGHT_UNIT, entry.weightUnit)
            put(KEY_WEIGHT_TYPE, entry.weightType)
            put(KEY_WEIGHT_TYPE_OTHER, entry.weightTypeOther)
            put(KEY_PROGRAM_TYPE, entry.programType)
            put(KEY_SETS, entry.sets)
            put(KEY_REPS, entry.reps)
            put(KEY_ELAPSED_HRS, entry.elapsedHrs)
            put(KEY_ELAPSED_MIN, entry.elapsedMin)
            put(KEY_ELAPSED_SEC, entry.elapsedSec)
            put(KEY_REST_MIN, entry.restMin)
            put(KEY_REST_SEC, entry.restSec)
            put(KEY_RPE, entry.rpe)
            put(KEY_DATE_HRS, entry.dateHrs)
            put(KEY_DATE_MIN, entry.dateMin)
            put(KEY_AM_PM, entry.AM_PM)
        }
        return this.writableDatabase.insert(TABLE_NAME, null, cv)
    }
    fun addEntry(date: String?, viewModel: EntryViewModel): Long {
        val cv = ContentValues().apply {
            put(KEY_DATE, date)
            put(KEY_EXERCISE, viewModel.exerciseName)
            put(KEY_INTENSITY, "")
            put(KEY_EXERCISE_TYPE, viewModel.exerciseType)
            put(KEY_WEIGHT, viewModel.weightAmount)
            put(KEY_WEIGHT_UNIT, viewModel.weightUnitOfMeasurement.toString())
            put(KEY_WEIGHT_TYPE, "")
            put(KEY_WEIGHT_TYPE_OTHER, "")
            put(KEY_PROGRAM_TYPE, "")
            put(KEY_SETS, viewModel.numberOfSets)
            put(KEY_REPS, viewModel.numberOfReps)
            put(KEY_ELAPSED_HRS, "")
            put(KEY_ELAPSED_MIN, "")
            put(KEY_ELAPSED_SEC, "")
            put(KEY_REST_MIN, "")
            put(KEY_REST_SEC, "")
            put(KEY_RPE, "")
            put(KEY_DATE_HRS, "")
            put(KEY_DATE_MIN, "")
            put(KEY_AM_PM, "")
        }
        return this.writableDatabase.insert(TABLE_NAME, null, cv)
    }
    fun getEntry(idToFetch: Long): Entry {
        val query = arrayOf(
            KEY_ID,
            KEY_DATE,
            KEY_EXERCISE,
            KEY_INTENSITY,
            KEY_EXERCISE_TYPE,
            KEY_WEIGHT,
            KEY_WEIGHT_UNIT,
            KEY_WEIGHT_TYPE,
            KEY_WEIGHT_TYPE_OTHER,
            KEY_PROGRAM_TYPE,
            KEY_SETS,
            KEY_REPS,
            KEY_ELAPSED_HRS,
            KEY_ELAPSED_MIN,
            KEY_ELAPSED_SEC,
            KEY_REST_MIN,
            KEY_REST_SEC,
            KEY_RPE,
            KEY_DATE_HRS,
            KEY_DATE_MIN,
            KEY_AM_PM
        )
        @SuppressLint("Recycle") val cursor: Cursor = this.writableDatabase.query(
            TABLE_NAME,
            query,
            "$KEY_ID=?",
            arrayOf(idToFetch.toString()),
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return Entry().apply {
            id = cursor.getString(0).toLong()
            date = cursor.getString(1)
            exercise = cursor.getString(2)
            intensity = cursor.getString(3)
            exerciseType = cursor.getString(4)
            weight = cursor.getString(5)
            weightUnit = cursor.getString(6)
            weightType = cursor.getString(7)
            weightTypeOther = cursor.getString(8)
            programType = cursor.getString(9)
            sets = cursor.getString(10)
            reps = cursor.getString(11)
            elapsedHrs = cursor.getString(12)
            elapsedMin = cursor.getString(13)
            elapsedSec = cursor.getString(14)
            restMin = cursor.getString(15)
            restSec = cursor.getString(16)
            rpe = cursor.getString(17)
            dateHrs = cursor.getString(18)
            dateMin = cursor.getString(19)
            AM_PM = cursor.getString(20)
        }
    }
    fun getAllEntries(): List<Entry> {
        val allEntries: MutableList<Entry> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $KEY_ID DESC"
        @SuppressLint("Recycle") val cursor = this.readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val entry = Entry()
                entry.id = cursor.getString(0).toLong()
                entry.date = cursor.getString(1)
                entry.exercise = cursor.getString(2)
                entry.intensity = cursor.getString(3)
                entry.exerciseType = cursor.getString(4)
                entry.weight = cursor.getString(5)
                entry.weightUnit = cursor.getString(6)
                entry.weightType = cursor.getString(7)
                entry.weightTypeOther = cursor.getString(8)
                entry.programType = cursor.getString(9)
                entry.sets = cursor.getString(10)
                entry.reps = cursor.getString(11)
                entry.elapsedHrs = cursor.getString(12)
                entry.elapsedMin = cursor.getString(13)
                entry.elapsedSec = cursor.getString(14)
                entry.restMin = cursor.getString(15)
                entry.restSec = cursor.getString(16)
                entry.rpe = cursor.getString(17)
                entry.dateHrs = cursor.getString(18)
                entry.dateMin = cursor.getString(19)
                entry.AM_PM = cursor.getString(20)
                allEntries.add(entry)
            } while (cursor.moveToNext())
        }
        return allEntries
    }
    fun getDayEntries(date: String?): MutableList<Entry> {
        val dayEntries: MutableList<Entry> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $KEY_ID DESC"
        Log.d("getDayEntries", date!!)
        @SuppressLint("Recycle") val cursor = this.readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1) == date) {
                    val entry = Entry()
                    entry.id = cursor.getString(0).toLong()
                    entry.date = cursor.getString(1)
                    entry.exercise = cursor.getString(2)
                    entry.intensity = cursor.getString(3)
                    entry.exerciseType = cursor.getString(4)
                    entry.weight = cursor.getString(5)
                    entry.weightUnit = cursor.getString(6)
                    entry.weightType = cursor.getString(7)
                    entry.weightTypeOther = cursor.getString(8)
                    entry.programType = cursor.getString(9)
                    entry.sets = cursor.getString(10)
                    entry.reps = cursor.getString(11)
                    entry.elapsedHrs = cursor.getString(12)
                    entry.elapsedMin = cursor.getString(13)
                    entry.elapsedSec = cursor.getString(14)
                    entry.restMin = cursor.getString(15)
                    entry.restSec = cursor.getString(16)
                    entry.rpe = cursor.getString(17)
                    entry.dateHrs = cursor.getString(18)
                    entry.dateMin = cursor.getString(19)
                    entry.AM_PM = cursor.getString(20)
                    dayEntries.add(entry)
                }
            } while (cursor.moveToNext())
        }
        return dayEntries
    }
    fun editEntry(entry: Entry): Int {
        val cv = ContentValues().apply {
            put(KEY_EXERCISE, entry.exercise)
            put(KEY_DATE, entry.date)
            put(KEY_INTENSITY, entry.intensity)
            put(KEY_EXERCISE_TYPE, entry.exerciseType)
            put(KEY_WEIGHT, entry.weight)
            put(KEY_WEIGHT_UNIT, entry.weightUnit)
            put(KEY_WEIGHT_TYPE, entry.weightType)
            put(KEY_WEIGHT_TYPE_OTHER, entry.weightTypeOther)
            put(KEY_PROGRAM_TYPE, entry.programType)
            put(KEY_SETS, entry.sets)
            put(KEY_REPS, entry.reps)
            put(KEY_ELAPSED_HRS, entry.elapsedHrs)
            put(KEY_ELAPSED_MIN, entry.elapsedMin)
            put(KEY_ELAPSED_SEC, entry.elapsedSec)
            put(KEY_REST_MIN, entry.restMin)
            put(KEY_REST_SEC, entry.restSec)
            put(KEY_RPE, entry.rpe)
            put(KEY_DATE_HRS, entry.dateHrs)
            put(KEY_DATE_MIN, entry.dateMin)
            put(KEY_AM_PM, entry.AM_PM)
        }
        return this.writableDatabase.update(TABLE_NAME, cv, "$KEY_ID=?", arrayOf(entry.id.toString()))
    }
    fun editEntry(id: Long, date: String?, viewModel: EntryViewModel): Int {
        val cv = ContentValues().apply {
            put(KEY_DATE, date)
            put(KEY_EXERCISE, viewModel.exerciseName)
            put(KEY_INTENSITY, "")
            put(KEY_EXERCISE_TYPE, viewModel.exerciseType)
            put(KEY_WEIGHT, viewModel.weightAmount)
            put(KEY_WEIGHT_UNIT, viewModel.weightUnitOfMeasurement.toString())
            put(KEY_WEIGHT_TYPE, "")
            put(KEY_WEIGHT_TYPE_OTHER, "")
            put(KEY_PROGRAM_TYPE, "")
            put(KEY_SETS, viewModel.numberOfSets)
            put(KEY_REPS, viewModel.numberOfReps)
            put(KEY_ELAPSED_HRS, "")
            put(KEY_ELAPSED_MIN, "")
            put(KEY_ELAPSED_SEC, "")
            put(KEY_REST_MIN, "")
            put(KEY_REST_SEC, "")
            put(KEY_RPE, "")
            put(KEY_DATE_HRS, "")
            put(KEY_DATE_MIN, "")
            put(KEY_AM_PM, "")
        }
        return this.writableDatabase.update(TABLE_NAME, cv, "$KEY_ID=?", arrayOf(id.toString()))
    }
    fun deleteEntry(id: Long) {
        this.writableDatabase.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        this.writableDatabase.close()
    }
}