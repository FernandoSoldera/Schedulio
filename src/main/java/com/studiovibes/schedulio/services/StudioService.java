package com.studiovibes.schedulio.services;

import com.studiovibes.schedulio.models.Studio;
import com.studiovibes.schedulio.repositories.StudioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudioService extends BasicService{

    final StudioRepository studioRepository;


    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio save(Studio studio) {
        return studioRepository.save(studio);
    }

    public Page<Studio> findAll(Pageable pageable) {
        return studioRepository.findAll(pageable);
    }

    public Optional<Studio> findById(UUID id) {
        return studioRepository.findById(id);
    }

    public boolean ifExistById(UUID id) {
        return studioRepository.existsById(id);
    }

    public void deleteStudio(UUID id) {
        studioRepository.deleteById(id);
    }
}
