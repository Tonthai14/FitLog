package com.example.logger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PresetDatabase extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 0;
    private static final String DATABASE_NAME = "PresetDB";
    private static final String TABLE_NAME = "PresetTable";

    public PresetDatabase(Context ct) {
        super(ct, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String KEY_ID = "id";
    private static final String KEY_GROUP = "exerciseGroup";
    private static final String KEY_EXERCISE = "exercise";
    private static final String KEY_HAS_VARIATION = "noVariation";

    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_GROUP + " TEXT," +
            KEY_EXERCISE + " TEXT," +
            KEY_HAS_VARIATION + " BOOL"
            + " )";
        db.execSQL(createTable);
    }

    void defaultPresets() {
        String[] upper_body_exercises;
        String[] lower_body_exercises;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion > newVersion) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addPreset(Preset preset) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_GROUP, preset.getGroup());
        cv.put(KEY_EXERCISE, preset.getExercise());
        cv.put(KEY_HAS_VARIATION, preset.hasVariation());
        return db.insert(TABLE_NAME, null, cv);
    }

    public Preset getPreset(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] { KEY_ID, KEY_GROUP, KEY_EXERCISE, KEY_HAS_VARIATION };
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME, query, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        return new Preset(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3) == 1);
    }

    public List<Preset> getGroupPresets(String group) {
        List<Preset> groupPresets = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(2).equals(group)) {
                    Preset preset = new Preset();
                    preset.setId(Long.parseLong(cursor.getString(0)));
                    preset.setGroup(cursor.getString(1));
                    preset.setExercise(cursor.getString(2));
                    preset.setHasVariation(cursor.getInt(3) == 1);
                    groupPresets.add(preset);
                }
            } while(cursor.moveToNext());
        }
        return groupPresets;
    }

    void deletePreset(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
