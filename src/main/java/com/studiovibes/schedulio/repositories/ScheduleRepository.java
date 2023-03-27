package com.studiovibes.schedulio.repositories;

import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
}
