package br.nom.carneiro.carlos.backend_challenge.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Entity {
    protected String _id;
    protected LocalDateTime _createdAt;
    protected LocalDateTime _modifiedAt;

    public Entity() {
        this._createdAt = LocalDateTime.now(ZoneId.of("UTC-03:00"));
    }

    public String id() {
        return this._id;
    }

    public void id(String id) {
        this._id = id;
    }

    public LocalDateTime createdAt() {
        return this._createdAt;
    }

    public void createdAt(LocalDateTime createdAt) {
        this._createdAt = createdAt;
    }

    public LocalDateTime modifiedAt() {
        return this._modifiedAt;
    }

    public void modifiedAt(LocalDateTime modifiedAt) {
        this._modifiedAt = modifiedAt;
    }

    public void modifiedNow() {
        this.modifiedAt(LocalDateTime.now(ZoneId.of("UTC-03:00")));
    }
}