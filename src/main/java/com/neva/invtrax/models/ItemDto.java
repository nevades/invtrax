package com.neva.invtrax.models;

import jakarta.validation.constraints.NotEmpty;

public class ItemDto {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}