package com.example.firstapp;

public class Entry {
    private long id;
    private String exercise;
    private String intensity;
    private String exerciseType;
    private String weight;
    private String weightUnit; // Unit of Measurement, e.g. lbs or kgs
    private String weightType; // e.g. Barbell
    private String programType; // e.g. Sets and Reps
    private String sets;
    private String reps;
    private String elapsedHrs;
    private String elapsedMin;
    private String elapsedSec;
    private String dateTime;

    Entry(String exercise, String intensity, String exerciseType, String weight, String weightUnit,
          String weightType, String programType, String sets, String reps,
          String elapsedHrs, String elapsedMin, String elapsedSec, String dateTime) {
        this.exercise = exercise;
        this.intensity = intensity;
        this.exerciseType = exerciseType;
        this.weight = weight;
        this.weightUnit  = weightUnit;
        this.weightType = weightType;
        this.programType = programType;
        this.sets = sets;
        this.reps = reps;
        this.elapsedHrs = elapsedHrs;
        this.elapsedMin = elapsedMin;
        this.elapsedSec = elapsedSec;
        this.dateTime = dateTime;
    }

    Entry(long id, String exercise, String intensity, String exerciseType, String weight,
          String weightUnit, String weightType, String programType, String sets,
          String reps, String elapsedHrs, String elapsedMin, String elapsedSec, String dateTime) {
        this.id = id;
        this.exercise = exercise;
        this.intensity = intensity;
        this.exerciseType = exerciseType;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.weightType = weightType;
        this.programType = programType;
        this.sets = sets;
        this.reps = reps;
        this.elapsedHrs = elapsedHrs;
        this.elapsedMin = elapsedMin;
        this.elapsedSec = elapsedSec;
        this.dateTime = dateTime;
    }

    Entry() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightUnit() { return weightUnit; }

    public void setWeightUnit(String weightUnit) { this.weightUnit = weightUnit; }

    public String getWeightType() { return weightType; }

    public void setWeightType(String weightType) { this.weightType = weightType; }

    public String getProgramType() { return programType; }

    public void setProgramType(String programType) { this.programType = programType; }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() { return reps; }

    public void setReps(String reps) { this.reps = reps; }

    public String getExerciseType() { return exerciseType;}

    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType;}

    public String getElapsedHrs() { return elapsedHrs; }

    public void setElapsedHrs(String elapsedHrs) { this.elapsedHrs = elapsedHrs; }

    public String getElapsedMin() { return elapsedMin; }

    public void setElapsedMin(String elapsedMin) { this.elapsedMin = elapsedMin; }

    public String getElapsedSec() { return elapsedSec; }

    public void setElapsedSec(String elapsedSec) { this.elapsedSec = elapsedSec; }

    public String getDateTime() { return dateTime; }

    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
}
