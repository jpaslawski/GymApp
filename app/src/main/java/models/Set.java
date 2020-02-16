package models;

public class Set {
    private int id;
    private int reps;
    private Float weight;
    private int lifter;
    private int exercise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getLifter() {
        return lifter;
    }

    public void setLifter(int lifter) {
        this.lifter = lifter;
    }

    public int getExercise() {
        return exercise;
    }

    public void setExercise(int exercise) {
        this.exercise = exercise;
    }
}
