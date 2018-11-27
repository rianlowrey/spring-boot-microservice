package com.company.microservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.company.microservice.validation.ValidationConstants.MAX_KEY_SIZE;

@Entity
@Table(name = "session", uniqueConstraints = {
        @UniqueConstraint(name = "idx_session_key", columnNames = "key"),
})
public class SessionEntity extends AbstractEntity {

    @Column(name = "key", unique = true, nullable = false, length = MAX_KEY_SIZE)
    private String key;

    @ManyToOne
    @JoinColumn(name = "mode_id")
    private ModeEntity mode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "player_id")
    private Set<PlayerEntity> players = Collections.synchronizedSet(new HashSet<>());

    private SessionEntity() {}

    public SessionEntity(String key, ModeEntity mode) {
        this.key = key;
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public ModeEntity getMode() {
        return this.mode;
    }

    public void addPlayer(PlayerEntity player) {
        this.players.add(player);
    }

    public void removePlayer(PlayerEntity player) {
        this.players.remove(player);
    }

    public Set<PlayerEntity> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

}
