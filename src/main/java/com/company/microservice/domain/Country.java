package com.company.microservice.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.company.microservice.validation.ValidationConstants.*;

public class Country {

    private String name;
    private String code;
    @NotNull
    @Size(min = MIN_ALPHA2_SIZE, max = MAX_ALPHA2_SIZE)
    @Pattern(regexp = ALPHA2_REGEX)
    private String alpha2;
    private String alpha3;
    private Region region;

    private Country() {
    }

    public Country(String name, String code, String alpha2, String alpha3, Region region) {
        this.name = name;
        this.code = code;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getAlpha2() {
        return alpha2;
    }


    public String getAlpha3() {
        return alpha3;
    }


    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
