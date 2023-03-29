package com.studiovibes.schedulio.controllers;

import com.studiovibes.schedulio.dtos.StudioDto;
import com.studiovibes.schedulio.models.Studio;
import com.studiovibes.schedulio.models.User;
import com.studiovibes.schedulio.services.StudioService;
import com.studiovibes.schedulio.services.UserService;
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
@RequestMapping("/studio")
public class StudioController {
    final StudioService studioService;
    final UserService userService;

    public StudioController(StudioService studioService, UserService userService) {
        this.studioService = studioService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveStudio(@RequestBody @Valid StudioDto studioDto) {
        Optional<User> userOptional = userService.findById(studioDto.getUser_id());
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var studio = new Studio();
        BeanUtils.copyProperties(studioDto, studio);
        studio.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        studio.setUser(userOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(studioService.save(studio));
    }

    @GetMapping()
    public ResponseEntity<Page<Studio>> getAllStudios(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(studioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudioById(@PathVariable(value = "id") UUID id) {
        Optional<Studio> studioOptional = studioService.findById(id);
        if (!studioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(studioOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudioById(@PathVariable(value = "id") UUID id) {
        Optional<Studio> studioOptional = studioService.findById(id);
        if (!studioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
        }
        studioService.deleteStudio(id);
        return ResponseEntity.status(HttpStatus.OK).body("Studio deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudio(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid StudioDto studioDto) {
        Optional<Studio> studioOptional = studioService.findById(id);
        if (!studioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio not found");
        }
        Optional<User> userOptional = userService.findById(studioDto.getUser_id());
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var studio = new Studio();
        BeanUtils.copyProperties(studioDto, studio);
        studio.setId(studioOptional.get().getId());
        studio.setRegistrationDate(studioOptional.get().getRegistrationDate());
        studio.setUser(userOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(studioService.save(studio));
    }
}
