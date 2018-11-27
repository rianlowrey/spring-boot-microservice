package com.company.microservice.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mode", uniqueConstraints = {
        @UniqueConstraint(name = "idx_mode_name", columnNames = "name"),
})
public class ModeEntity extends AbstractEntity {

    private String name;

    private ModeEntity() {}

    public ModeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
