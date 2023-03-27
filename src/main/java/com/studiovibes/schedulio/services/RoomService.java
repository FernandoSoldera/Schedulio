package com.studiovibes.schedulio.services;

import com.studiovibes.schedulio.controllers.BasicController;
import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.models.Studio;
import com.studiovibes.schedulio.repositories.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService extends BasicService {

    final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public Optional<Room> findById(UUID id) {
        return roomRepository.findById(id);
    }

    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    @Override
    public boolean ifExistById(UUID id) {
        return roomRepository.existsById(id);
    }
}
