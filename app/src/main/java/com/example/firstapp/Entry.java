package com.example.firstapp;

public class Entry {
    private long id;
    private String date;
    private String exercise;
    private String intensity;
    private String exerciseType;
    private String weight;
    private String weightUnit; // Unit of Measurement, e.g. lbs or kgs
    private String weightType; // e.g. Barbell
    private String weightTypeOther;
    private String programType; // e.g. Sets and Reps
    private String sets;
    private String reps;
    private String elapsedHrs;
    private String elapsedMin;
    private String elapsedSec;
    private String restMin;
    private String restSec;
    private String rpe;
    private String dateHrs;
    private String dateMin;
    private String AM_PM;

    Entry(String date,
          String exercise,
          String intensity,
          String exerciseType,
          String weight, String weightUnit,
          String weightType, String weightTypeOther,
          String programType,
          String sets, String reps,
          String elapsedHrs, String elapsedMin, String elapsedSec,
          String restMin, String restSec,
          String rpe,
          String dateHrs, String dateMin, String AM_PM) {
        this.date = date;
        this.exercise = exercise;
        this.intensity = intensity;
        this.exerciseType = exerciseType;
        this.weight = weight;
        this.weightUnit  = weightUnit;
        this.weightType = weightType;
        this.weightTypeOther = weightTypeOther;
        this.programType = programType;
        this.sets = sets;
        this.reps = reps;
        this.elapsedHrs = elapsedHrs;
        this.elapsedMin = elapsedMin;
        this.elapsedSec = elapsedSec;
        this.restMin = restMin;
        this.restSec = restSec;
        this.rpe = rpe;
        this.dateHrs = dateHrs;
        this.dateMin = dateMin;
        this.AM_PM = AM_PM;
    }

    Entry(long id,
          String date,
          String exercise,
          String intensity,
          String exerciseType,
          String weight, String weightUnit,
          String weightType, String weightTypeOther,
          String programType,
          String sets, String reps,
          String elapsedHrs, String elapsedMin, String elapsedSec,
          String restMin, String restSec,
          String rpe,
          String dateHrs, String dateMin, String AM_PM) {
        this.id = id;
        this.date = date;
        this.exercise = exercise;
        this.intensity = intensity;
        this.exerciseType = exerciseType;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.weightType = weightType;
        this.weightTypeOther = weightTypeOther;
        this.programType = programType;
        this.sets = sets;
        this.reps = reps;
        this.elapsedHrs = elapsedHrs;
        this.elapsedMin = elapsedMin;
        this.elapsedSec = elapsedSec;
        this.restMin = restMin;
        this.restSec = restSec;
        this.rpe = rpe;
        this.dateHrs = dateHrs;
        this.dateMin = dateMin;
        this.AM_PM = AM_PM;
    }

    Entry() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

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

    public String getWeightTypeOther() { return weightTypeOther; }
    public void setWeightTypeOther(String weightTypeOther) { this.weightTypeOther = weightTypeOther; }

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

    public String getRestMin() { return restMin; }
    public void setRestMin(String restMin) { this.restMin = restMin; }

    public String getRestSec() { return restSec; }
    public void setRestSec(String restSec) { this.restSec = restSec; }

    public String getRpe() { return rpe; }
    public void setRpe(String rpe) { this.rpe = rpe;}

    public String getDateHrs() { return dateHrs; }
    public void setDateHrs(String dateHrs) { this.dateHrs = dateHrs; }

    public String getDateMin() { return dateMin; }
    public void setDateMin(String dateMin) { this.dateMin = dateMin; }

    public String getAM_PM() { return AM_PM; }
    public void setAM_PM(String AM_PM) { this.AM_PM = AM_PM; }
}
