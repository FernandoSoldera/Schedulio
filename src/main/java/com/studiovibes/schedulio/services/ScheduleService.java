package com.studiovibes.schedulio.services;

import com.studiovibes.schedulio.models.Schedule;
import com.studiovibes.schedulio.repositories.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService extends BasicService {

    final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Page<Schedule> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

    public Optional<Object> findById(UUID id) {
        return Optional.of(scheduleRepository.findById(id));
    }

    public boolean ifExistById(UUID id) {
        return scheduleRepository.existsById(id);
    }

    public void deleteRoom(UUID id) {
        scheduleRepository.deleteById(id);
    }
}
