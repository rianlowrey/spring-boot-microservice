package com.company.microservice.domain;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Region {

    private String name;
    private String code;
    private Set<Country> countries;

    private Region() {
    }

    public Region(String name, String code) {
        this.name = name;
        this.code = code;
        this.countries = Collections.unmodifiableSet(Collections.emptySet());
    }

    public Region(String name, String code, Set<Country> countries) {
        this.name = name;
        this.code = code;
        this.countries = countries;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(code, region.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
