package com.studiovibes.schedulio.controllers;

import com.studiovibes.schedulio.dtos.EquipmentDto;
import com.studiovibes.schedulio.dtos.RoomDto;
import com.studiovibes.schedulio.models.Equipment;
import com.studiovibes.schedulio.models.Room;
import com.studiovibes.schedulio.models.Studio;
import com.studiovibes.schedulio.services.EquipmentService;
import com.studiovibes.schedulio.services.RoomService;
import com.studiovibes.schedulio.services.StudioService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/equipment")
public class EquipmentController {

    final EquipmentService equipmentService;
    final StudioService studioService;

    public EquipmentController(EquipmentService equipmentService, StudioService studioService) {
        this.equipmentService = equipmentService;
        this.studioService = studioService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveEquipment(@RequestBody @Valid EquipmentDto equipmentDto) {
        Optional<Studio> studioOptional = studioService.findById(equipmentDto.getStudio_id());
        if (!studioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
        }
        var equipment = new Equipment();
        BeanUtils.copyProperties(equipmentDto, equipment);
        equipment.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        equipment.setStudio(studioOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.save(equipment));
    }

    @GetMapping()
    public ResponseEntity<Page<Equipment>> getAllEquipments(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEquipmentById(@PathVariable(value = "id") UUID id) {
        Optional<Equipment> equipmentOptional = equipmentService.findById(id);
        if (!equipmentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquipmentById(@PathVariable(value = "id") UUID id) {
        Optional<Equipment> equipmentOptional = equipmentService.findById(id);
        if (!equipmentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found");
        }
        equipmentService.deleteRoom(id);
        return ResponseEntity.status(HttpStatus.OK).body("Equipment deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEquipment(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid EquipmentDto equipmentDto) {
        Optional<Equipment> equipmentOptional = equipmentService.findById(id);
        if (!equipmentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found");
        }
        Optional<Studio> studioOptional = studioService.findById(equipmentDto.getStudio_id());
        if (!studioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
        }
        var equipment = new Equipment();
        BeanUtils.copyProperties(equipmentDto, equipment);
        equipment.setId(equipmentOptional.get().getId());
        equipment.setRegistrationDate(equipmentOptional.get().getRegistrationDate());
        equipment.setStudio(studioOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.save(equipment));
    }
}
