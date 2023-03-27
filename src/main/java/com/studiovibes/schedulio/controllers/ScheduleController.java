package com.studiovibes.schedulio.controllers;

import com.studiovibes.schedulio.dtos.RoomDto;
import com.studiovibes.schedulio.dtos.ScheduleDto;
import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.models.Studio;
import com.studiovibes.schedulio.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/schedule")
public class ScheduleController extends BasicController {

    final StudioService studioService;
    final RoomService roomService;
    final EquipmentService equipmentService;
    final ScheduleService scheduleService;

    public ScheduleController(StudioService studioService, RoomService roomService, EquipmentService equipmentService, ScheduleService scheduleService) {
        this.studioService = studioService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
        this.scheduleService = scheduleService;
    }


    @PostMapping()
    public ResponseEntity<Object> saveRoom(@RequestBody @Valid ScheduleDto scheduleDto) {
        //Need validate the equipments too
        String exception = super.validateIfExist(Map.ofEntries(
                Map.entry(studioService, "Studio:" + scheduleDto.getStudio_id()),
                Map.entry(roomService, "Room:" + scheduleDto.getStudio_id())
        ));
        if(!exception.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception); }
        return ResponseEntity.status(HttpStatus.CREATED).body("haha");
//        Optional<Studio> studioOptional = studioService.findById(roomDto.getStudio_id());
//        if (!studioOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
//        }
//        var room = new Room();
//        BeanUtils.copyProperties(roomDto, room);
//        room.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
//        room.setStudio(studioOptional.get());
//        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(room));
    }

    @GetMapping()
    public ResponseEntity<Page<Room>> getAllRooms(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable(value = "id") UUID id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoomById(@PathVariable(value = "id") UUID id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
        roomService.deleteRoom(id);
        return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully");
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateRoom(@PathVariable(value = "id") UUID id,
//                                               @RequestBody @Valid RoomDto roomDto) {
//        Optional<Room> roomOptional = roomService.findById(id);
//        if (!roomOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
//        }
//        Optional<Studio> studioOptional = studioService.findById(roomDto.getStudio_id());
//        if (!studioOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
//        }
//        var room = new Room();
//        BeanUtils.copyProperties(roomDto, room);
//        room.setId(roomOptional.get().getId());
//        room.setRegistrationDate(roomOptional.get().getRegistrationDate());
//        room.setStudio(studioOptional.get());
//        return ResponseEntity.status(HttpStatus.OK).body(roomService.save(room));
//    }
}
