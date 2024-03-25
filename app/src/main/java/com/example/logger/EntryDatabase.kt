package com.example.logger

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


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
    fun addEntry(entry: Entry): Long {
        val cv = ContentValues()
        cv.put(KEY_DATE, entry.date)
        cv.put(KEY_EXERCISE, entry.exercise)
        cv.put(KEY_INTENSITY, entry.intensity)
        cv.put(KEY_EXERCISE_TYPE, entry.exerciseType)
        cv.put(KEY_WEIGHT, entry.weight)
        cv.put(KEY_WEIGHT_UNIT, entry.weightUnit)
        cv.put(KEY_WEIGHT_TYPE, entry.weightType)
        cv.put(KEY_WEIGHT_TYPE_OTHER, entry.weightTypeOther)
        cv.put(KEY_PROGRAM_TYPE, entry.programType)
        cv.put(KEY_SETS, entry.sets)
        cv.put(KEY_REPS, entry.reps)
        cv.put(KEY_ELAPSED_HRS, entry.elapsedHrs)
        cv.put(KEY_ELAPSED_MIN, entry.elapsedMin)
        cv.put(KEY_ELAPSED_SEC, entry.elapsedSec)
        cv.put(KEY_REST_MIN, entry.restMin)
        cv.put(KEY_REST_SEC, entry.restSec)
        cv.put(KEY_RPE, entry.rpe)
        cv.put(KEY_DATE_HRS, entry.dateHrs)
        cv.put(KEY_DATE_MIN, entry.dateMin)
        cv.put(KEY_AM_PM, entry.aM_PM)
        return this.writableDatabase.insert(TABLE_NAME, null, cv)
    }
    fun getEntry(id: Long): Entry {
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
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return Entry(
            cursor.getString(0).toLong(),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8),
            cursor.getString(9),
            cursor.getString(10),
            cursor.getString(11),
            cursor.getString(12),
            cursor.getString(13),
            cursor.getString(14),
            cursor.getString(15),
            cursor.getString(16),
            cursor.getString(17),
            cursor.getString(18),
            cursor.getString(19),
            cursor.getString(20)
        )
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
                entry.aM_PM = cursor.getString(20)
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
                    entry.aM_PM = cursor.getString(20)
                    dayEntries.add(entry)
                }
            } while (cursor.moveToNext())
        }
        return dayEntries
    }
    fun editEntry(entry: Entry): Int {
        val cv = ContentValues()
        cv.put(KEY_EXERCISE, entry.exercise)
        cv.put(KEY_DATE, entry.date)
        cv.put(KEY_INTENSITY, entry.intensity)
        cv.put(KEY_EXERCISE_TYPE, entry.exerciseType)
        cv.put(KEY_WEIGHT, entry.weight)
        cv.put(KEY_WEIGHT_UNIT, entry.weightUnit)
        cv.put(KEY_WEIGHT_TYPE, entry.weightType)
        cv.put(KEY_WEIGHT_TYPE_OTHER, entry.weightTypeOther)
        cv.put(KEY_PROGRAM_TYPE, entry.programType)
        cv.put(KEY_SETS, entry.sets)
        cv.put(KEY_REPS, entry.reps)
        cv.put(KEY_ELAPSED_HRS, entry.elapsedHrs)
        cv.put(KEY_ELAPSED_MIN, entry.elapsedMin)
        cv.put(KEY_ELAPSED_SEC, entry.elapsedSec)
        cv.put(KEY_REST_MIN, entry.restMin)
        cv.put(KEY_REST_SEC, entry.restSec)
        cv.put(KEY_RPE, entry.rpe)
        cv.put(KEY_DATE_HRS, entry.dateHrs)
        cv.put(KEY_DATE_MIN, entry.dateMin)
        cv.put(KEY_AM_PM, entry.aM_PM)
        return this.writableDatabase.update(TABLE_NAME, cv, "$KEY_ID=?", arrayOf(entry.id.toString()))
    }
    fun deleteEntry(id: Long) {
        this.writableDatabase.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        this.writableDatabase.close()
    }
}