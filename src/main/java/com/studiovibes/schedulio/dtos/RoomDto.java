package com.studiovibes.schedulio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RoomDto {

    @NotBlank
    private String name;
    @NotBlank
    private String size;
    @NotBlank
    private String category;
    @NotNull
    private UUID studio_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UUID getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(UUID studio_id) {
        this.studio_id = studio_id;
    }
}
