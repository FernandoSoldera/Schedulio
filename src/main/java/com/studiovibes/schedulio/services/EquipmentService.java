package com.studiovibes.schedulio.services;

import com.studiovibes.schedulio.models.Equipment;
import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.repositories.EquipmentRepository;
import com.studiovibes.schedulio.repositories.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentService {

    final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Page<Equipment> findAll(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    public Optional<Equipment> findById(UUID id) {
        return equipmentRepository.findById(id);
    }

    public void deleteRoom(UUID id) {
        equipmentRepository.deleteById(id);
    }
}
