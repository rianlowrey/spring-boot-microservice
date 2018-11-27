package com.company.microservice.domain;

public class ModePopularity {
    private String mode;
    private long popularity;

    public ModePopularity(String mode, long popularity) {
        this.mode = mode;
        this.popularity = popularity;
    }

    public String getMode() {
        return mode;
    }

    public float getPopularity() {
        return popularity;
    }
}
