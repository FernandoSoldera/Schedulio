package com.studiovibes.schedulio.services;

import java.util.Optional;
import java.util.UUID;

public abstract class BasicService {

    public abstract boolean ifExistById(UUID id);
}
