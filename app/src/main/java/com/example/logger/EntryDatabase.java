package com.example.logger;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EntryDatabase extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 14;
    private static final String DATABASE_NAME = "EntryDB";
    private static final String TABLE_NAME = "EntryTable";

    public EntryDatabase(Context ct) {
        super(ct, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_EXERCISE = "exercise";
    private static final String KEY_INTENSITY = "intensity";
    private static final String KEY_EXERCISE_TYPE = "exerciseType";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_WEIGHT_UNIT = "weightUnit";
    private static final String KEY_WEIGHT_TYPE = "weightType";
    private static final String KEY_WEIGHT_TYPE_OTHER = "weightTypeOther";
    private static final String KEY_PROGRAM_TYPE = "programType";
    private static final String KEY_SETS = "sets";
    private static final String KEY_REPS = "reps";
    private static final String KEY_ELAPSED_HRS = "elapsedHrs";
    private static final String KEY_ELAPSED_MIN = "elapsedMin";
    private static final String KEY_ELAPSED_SEC = "elapsedSec";
    private static final String KEY_REST_MIN = "restMin";
    private static final String KEY_REST_SEC = "restSec";
    private static final String KEY_RPE = "rpe";
    private static final String KEY_DATE_HRS = "dateHrs";
    private static final String KEY_DATE_MIN = "dateMin";
    private static final String KEY_AM_PM = "AM_PM";

    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_DATE + " TEXT," +
                KEY_EXERCISE + " TEXT," +
                KEY_INTENSITY + " TEXT," +
                KEY_EXERCISE_TYPE + " TEXT," +
                KEY_WEIGHT + " TEXT," +
                KEY_WEIGHT_UNIT + " TEXT," +
                KEY_WEIGHT_TYPE + " TEXT," +
                KEY_WEIGHT_TYPE_OTHER + " TEXT," +
                KEY_PROGRAM_TYPE + " TEXT," +
                KEY_SETS + " TEXT," +
                KEY_REPS + " TEXT," +
                KEY_ELAPSED_HRS + " TEXT," +
                KEY_ELAPSED_MIN + " TEXT," +
                KEY_ELAPSED_SEC + " TEXT," +
                KEY_REST_MIN + " TEXT," +
                KEY_REST_SEC + " TEXT," +
                KEY_RPE + " TEXT," +
                KEY_DATE_HRS + " TEXT," +
                KEY_DATE_MIN + " TEXT," +
                KEY_AM_PM + " TEXT"
                + " )";
        db.execSQL(createTable);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion > newVersion) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE, entry.getDate());
        cv.put(KEY_EXERCISE, entry.getExercise());
        cv.put(KEY_INTENSITY, entry.getIntensity());
        cv.put(KEY_EXERCISE_TYPE, entry.getExerciseType());
        cv.put(KEY_WEIGHT, entry.getWeight());
        cv.put(KEY_WEIGHT_UNIT, entry.getWeightUnit());
        cv.put(KEY_WEIGHT_TYPE, entry.getWeightType());
        cv.put(KEY_WEIGHT_TYPE_OTHER, entry.getWeightTypeOther());
        cv.put(KEY_PROGRAM_TYPE, entry.getProgramType());
        cv.put(KEY_SETS, entry.getSets());
        cv.put(KEY_REPS, entry.getReps());
        cv.put(KEY_ELAPSED_HRS, entry.getElapsedHrs());
        cv.put(KEY_ELAPSED_MIN, entry.getElapsedMin());
        cv.put(KEY_ELAPSED_SEC, entry.getElapsedSec());
        cv.put(KEY_REST_MIN, entry.getRestMin());
        cv.put(KEY_REST_SEC, entry.getRestSec());
        cv.put(KEY_RPE, entry.getRpe());
        cv.put(KEY_DATE_HRS, entry.getDateHrs());
        cv.put(KEY_DATE_MIN, entry.getDateMin());
        cv.put(KEY_AM_PM, entry.getAM_PM());
        // Return ID
        return db.insert(TABLE_NAME, null, cv);
    }

    public Entry getEntry(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]
                {KEY_ID, KEY_DATE, KEY_EXERCISE, KEY_INTENSITY, KEY_EXERCISE_TYPE,
                        KEY_WEIGHT, KEY_WEIGHT_UNIT, KEY_WEIGHT_TYPE, KEY_WEIGHT_TYPE_OTHER,
                        KEY_PROGRAM_TYPE, KEY_SETS, KEY_REPS, KEY_ELAPSED_HRS, KEY_ELAPSED_MIN,
                        KEY_ELAPSED_SEC, KEY_REST_MIN, KEY_REST_SEC, KEY_RPE, KEY_DATE_HRS,
                        KEY_DATE_MIN, KEY_AM_PM};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME, query, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        return new Entry(
                Long.parseLong(cursor.getString(0)),
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
                cursor.getString(20));
    }

    public List<Entry> getAllEntries() {
        List<Entry> allEntries = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Entry entry = new Entry();
                entry.setId(Long.parseLong(cursor.getString(0)));
                entry.setDate(cursor.getString(1));
                entry.setExercise(cursor.getString(2));
                entry.setIntensity(cursor.getString(3));
                entry.setExerciseType(cursor.getString(4));
                entry.setWeight(cursor.getString(5));
                entry.setWeightUnit(cursor.getString(6));
                entry.setWeightType(cursor.getString(7));
                entry.setWeightTypeOther(cursor.getString(8));
                entry.setProgramType(cursor.getString(9));
                entry.setSets(cursor.getString(10));
                entry.setReps(cursor.getString(11));
                entry.setElapsedHrs(cursor.getString(12));
                entry.setElapsedMin(cursor.getString(13));
                entry.setElapsedSec(cursor.getString(14));
                entry.setRestMin(cursor.getString(15));
                entry.setRestSec(cursor.getString(16));
                entry.setRpe(cursor.getString(17));
                entry.setDateHrs(cursor.getString(18));
                entry.setDateMin(cursor.getString(19));
                entry.setAM_PM(cursor.getString(20));
                allEntries.add(entry);
            } while(cursor.moveToNext());
        }
        return allEntries;
    }

    public List<Entry> getDayEntries(String date) {
        List<Entry> dayEntries = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("getDayEntries", date);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(date)) {
                    Entry entry = new Entry();
                    entry.setId(Long.parseLong(cursor.getString(0)));
                    entry.setDate(cursor.getString(1));
                    entry.setExercise(cursor.getString(2));
                    entry.setIntensity(cursor.getString(3));
                    entry.setExerciseType(cursor.getString(4));
                    entry.setWeight(cursor.getString(5));
                    entry.setWeightUnit(cursor.getString(6));
                    entry.setWeightType(cursor.getString(7));
                    entry.setWeightTypeOther(cursor.getString(8));
                    entry.setProgramType(cursor.getString(9));
                    entry.setSets(cursor.getString(10));
                    entry.setReps(cursor.getString(11));
                    entry.setElapsedHrs(cursor.getString(12));
                    entry.setElapsedMin(cursor.getString(13));
                    entry.setElapsedSec(cursor.getString(14));
                    entry.setRestMin(cursor.getString(15));
                    entry.setRestSec(cursor.getString(16));
                    entry.setRpe(cursor.getString(17));
                    entry.setDateHrs(cursor.getString(18));
                    entry.setDateMin(cursor.getString(19));
                    entry.setAM_PM(cursor.getString(20));
                    dayEntries.add(entry);
                }
            } while(cursor.moveToNext());
        }
        return dayEntries;
    }

    public int editEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_EXERCISE, entry.getExercise());
        cv.put(KEY_DATE, entry.getDate());
        cv.put(KEY_INTENSITY, entry.getIntensity());
        cv.put(KEY_EXERCISE_TYPE, entry.getExerciseType());
        cv.put(KEY_WEIGHT, entry.getWeight());
        cv.put(KEY_WEIGHT_UNIT, entry.getWeightUnit());
        cv.put(KEY_WEIGHT_TYPE, entry.getWeightType());
        cv.put(KEY_WEIGHT_TYPE_OTHER, entry.getWeightTypeOther());
        cv.put(KEY_PROGRAM_TYPE, entry.getProgramType());
        cv.put(KEY_SETS, entry.getSets());
        cv.put(KEY_REPS, entry.getReps());
        cv.put(KEY_ELAPSED_HRS, entry.getElapsedHrs());
        cv.put(KEY_ELAPSED_MIN, entry.getElapsedMin());
        cv.put(KEY_ELAPSED_SEC, entry.getElapsedSec());
        cv.put(KEY_REST_MIN, entry.getRestMin());
        cv.put(KEY_REST_SEC, entry.getRestSec());
        cv.put(KEY_RPE, entry.getRpe());
        cv.put(KEY_DATE_HRS, entry.getDateHrs());
        cv.put(KEY_DATE_MIN, entry.getDateMin());
        cv.put(KEY_AM_PM, entry.getAM_PM());
        return db.update(TABLE_NAME, cv, KEY_ID + "=?",
                new String[]{String.valueOf(entry.getId())});
    }

    void deleteEntry(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
