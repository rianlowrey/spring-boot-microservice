package com.company.microservice.domain;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.company.microservice.validation.ValidationConstants.*;

@ResponseBody
public class Player {

    @NotNull
    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Pattern(regexp = NAME_REGEX)
    private String name;
    private Country country;
    private Session session;

    private Player() {
    }

    public Player(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
