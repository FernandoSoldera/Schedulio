package com.studiovibes.schedulio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ScheduleDto {

    @NotNull
    private UUID studio_id;
    @NotNull
    private UUID room_id;
    private List<UUID> equipment;
    @NotBlank
    private String scheduleDate;

    public UUID getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(UUID studio_id) {
        this.studio_id = studio_id;
    }

    public UUID getRoom_id() {
        return room_id;
    }

    public void setRoom_id(UUID room_id) {
        this.room_id = room_id;
    }

    public List<UUID> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<UUID> equipment) {
        this.equipment = equipment;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}
