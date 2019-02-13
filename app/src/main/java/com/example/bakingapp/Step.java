package com.example.bakingapp;

class Step {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoURL;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoURL = videoUrl;
    }
}
