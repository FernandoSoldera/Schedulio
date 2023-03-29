package com.studiovibes.schedulio.controllers;

import com.studiovibes.schedulio.dtos.RoomDto;
import com.studiovibes.schedulio.dtos.ScheduleDto;
import com.studiovibes.schedulio.models.Equipment;
import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.models.Schedule;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public ResponseEntity<Object> saveSchedule(@RequestBody @Valid ScheduleDto scheduleDto) {
        Map<BasicService, String> hashMap = new HashMap<>();
        hashMap.put(studioService, "Studio:" + scheduleDto.getStudio_id());
        hashMap.put(roomService, "Room:" + scheduleDto.getRoom_id());

        if(scheduleDto.getEquipment() != null) {
            scheduleDto.getEquipment().forEach(uuid -> hashMap.put(equipmentService, "Equipment:" + uuid));
        }
        String exception = super.validateIfExist(hashMap);
        if(!exception.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception); }

        Optional<Studio> studioOptional = studioService.findById(scheduleDto.getStudio_id());
        Optional<Room> roomOptional = roomService.findById(scheduleDto.getRoom_id());
        List<Equipment> equipmentList = new ArrayList<>();
        if(scheduleDto.getEquipment() != null) {
            equipmentList = equipmentService.findAllById(scheduleDto.getEquipment());
        }

        var schedule = new Schedule();
        schedule.setScheduleDate(LocalDateTime.parse(scheduleDto.getScheduleDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        schedule.setStudio(studioOptional.get());
        schedule.setRoom(roomOptional.get());
        schedule.setEquipment(equipmentList);
        schedule.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(schedule));
    }

    @GetMapping()
    public ResponseEntity<Page<Schedule>> getAllSchedules(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable(value = "id") UUID id) {
        Optional<Schedule> scheduleOptional = scheduleService.findById(id);
        if (!scheduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoomById(@PathVariable(value = "id") UUID id) {
        Optional<Schedule> scheduleOptional = scheduleService.findById(id);
        if (!scheduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
        }
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body("Schedule deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSchedule(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid ScheduleDto scheduleDto) {
        Map<BasicService, String> hashMap = new HashMap<>();
        hashMap.put(scheduleService, "Schedule:" + id);
        hashMap.put(studioService, "Studio:" + scheduleDto.getStudio_id());
        hashMap.put(roomService, "Room:" + scheduleDto.getRoom_id());

        if(scheduleDto.getEquipment() != null) {
            scheduleDto.getEquipment().forEach(uuid -> hashMap.put(equipmentService, "Equipment:" + uuid));
        }
        String exception = super.validateIfExist(hashMap);
        if(!exception.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception); }

        Optional<Schedule> scheduleOptional = scheduleService.findById(id);
        Optional<Studio> studioOptional = studioService.findById(scheduleDto.getStudio_id());
        Optional<Room> roomOptional = roomService.findById(scheduleDto.getRoom_id());
        List<Equipment> equipmentList = new ArrayList<>();
        if(scheduleDto.getEquipment() != null) {
            equipmentList = equipmentService.findAllById(scheduleDto.getEquipment());
        }

        var schedule = new Schedule();
        schedule.setId(id);
        schedule.setScheduleDate(LocalDateTime.parse(scheduleDto.getScheduleDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        schedule.setStudio(studioOptional.get());
        schedule.setRoom(roomOptional.get());
        schedule.setEquipment(equipmentList);
        schedule.setRegistrationDate(scheduleOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.save(schedule));
    }
}
