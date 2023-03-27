package com.studiovibes.schedulio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EquipmentDto {

    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotBlank
    private String description;
    @NotNull
    private UUID studio_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(UUID studio_id) {
        this.studio_id = studio_id;
    }
}
