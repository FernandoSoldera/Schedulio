package com.studiovibes.schedulio.controllers;

import com.studiovibes.schedulio.services.BasicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BasicController {

    public String validateIfExist(Map<BasicService, String> hashMap) {
        for(Map.Entry<BasicService, String> entry : hashMap.entrySet()) {
            UUID id = UUID.fromString(entry.getValue().split(":")[1]);
            boolean exist = entry.getKey().ifExistById(id);
            if(!exist) {
                return entry.getValue().split(":")[0] + " not found";
            }
        }
        return "";
    }

}
