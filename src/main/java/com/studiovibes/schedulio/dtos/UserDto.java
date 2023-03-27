package com.studiovibes.schedulio.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
