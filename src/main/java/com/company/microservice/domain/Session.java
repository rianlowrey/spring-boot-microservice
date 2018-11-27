package com.company.microservice.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

import static com.company.microservice.validation.ValidationConstants.*;

public class Session {

    @NotNull
    @Size(min = MIN_KEY_SIZE, max = MAX_KEY_SIZE)
    @Pattern(regexp = KEY_REGEX)
    private String key;
    private Mode mode;
    private Set<Player> players;

    private Session() {
    }

    public Session(String key, Mode mode, Set<Player> players) {
        this.key = key;
        this.mode = mode;
        this.players = players;
    }

    public String getKey() {
        return key;
    }

    public Mode getMode() {
        return mode;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(key, session.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
