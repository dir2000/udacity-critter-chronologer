package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
