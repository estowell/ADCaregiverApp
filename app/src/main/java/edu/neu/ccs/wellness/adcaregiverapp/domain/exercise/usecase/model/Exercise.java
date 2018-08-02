package edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.model;

import java.util.List;

public class Exercise {

    private String videoUrl;

    private String exerciseName;
    private List<String> steps;

    private String headText;

    public Exercise(String videoUrl, List<String> steps, String headText, String exerciseName) {
        this.videoUrl = videoUrl;
        this.steps = steps;
        this.headText = headText;
        this.exerciseName = exerciseName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getHeadText() {
        return headText;
    }

    public void setHeadText(String headText) {
        this.headText = headText;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
