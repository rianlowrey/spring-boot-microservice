package com.company.microservice.domain;

import java.util.Objects;

public class Mode {

    private String name;

    private Mode() {
    }

    public Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mode mode = (Mode) o;
        return Objects.equals(name, mode.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
