package com.example.firstapp;

public class Preset {
    private long id;
    private String group;
    private String exercise;
    private boolean hasVariation;

    Preset(String group, String exercise, boolean hasVariation) {
        this.group = group;
        this.exercise = exercise;
        this.hasVariation = hasVariation;
    }

    Preset(long id, String group, String exercise, boolean hasVariation) {
        this.id = id;
        this.group = group;
        this.exercise = exercise;
        this.hasVariation = hasVariation;
    }

    Preset() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public String getExercise() { return exercise; }
    public void setExercise(String exercise) { this.exercise = exercise; }

    public boolean hasVariation() { return hasVariation; }
    public void setHasVariation(boolean hasVariation) { this.hasVariation = hasVariation; }
}
