package com.company.microservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.company.microservice.validation.ValidationConstants.*;

@Entity
@Table(name = "country", uniqueConstraints = {
        @UniqueConstraint(name = "idx_country_name", columnNames = "name"),
        @UniqueConstraint(name = "idx_country_code", columnNames = "code"),
        @UniqueConstraint(name = "idx_country_alpha2", columnNames = "alpha2"),
        @UniqueConstraint(name = "idx_country_alpha3", columnNames = "alpha3"),
})
public class CountryEntity extends AbstractEntity {

    private String name, code, alpha2, alpha3;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    private CountryEntity() {}

    public CountryEntity(String name, String code, String alpha2, String alpha3, RegionEntity region) {
        this.name = name;
        this.code = code;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.region = region;
    }

    public RegionEntity getRegion() {
        return region;
    }

    @Column(name = "name", unique = true, nullable = false, length = MAX_NAME_SIZE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", unique = true, nullable = false, length = MAX_CODE_SIZE)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "alpha2", unique = true, nullable = false, length = MAX_ALPHA2_SIZE)
    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    @Column(name = "alpha3", unique = true, nullable = false, length = MAX_ALPHA3_SIZE)
    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

}