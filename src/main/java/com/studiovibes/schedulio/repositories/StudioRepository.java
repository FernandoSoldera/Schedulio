package com.studiovibes.schedulio.repositories;

import com.studiovibes.schedulio.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, UUID> {
}
