package com.company.microservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collections;
import java.util.Set;

import static com.company.microservice.validation.ValidationConstants.MAX_CODE_SIZE;
import static com.company.microservice.validation.ValidationConstants.MAX_NAME_SIZE;

@Entity
@Table(name = "region", uniqueConstraints = {
        @UniqueConstraint(name = "idx_region_name", columnNames = "name"),
        @UniqueConstraint(name = "idx_region_code", columnNames = "code"),
})
public class RegionEntity extends AbstractEntity {

    private String name, code;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CountryEntity> countries;

    private RegionEntity() {}

    public RegionEntity(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Set<CountryEntity> getCountries() {
        return Collections.unmodifiableSet(countries);
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

}
