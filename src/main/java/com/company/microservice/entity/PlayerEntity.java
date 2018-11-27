package com.company.microservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.company.microservice.validation.ValidationConstants.MAX_NAME_SIZE;

@Entity
@Table(name = "player", uniqueConstraints = {
        @UniqueConstraint(name = "idx_player_name", columnNames = "name"),
})
public class PlayerEntity extends AbstractEntity {

    @Column(name = "name", unique = true, nullable = false, length = MAX_NAME_SIZE)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    private PlayerEntity() {}

    public PlayerEntity(String name, CountryEntity country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public SessionEntity getSession() {
        return session;
    }

    public void setSession(SessionEntity session) {
        this.session = session;
    }
}
