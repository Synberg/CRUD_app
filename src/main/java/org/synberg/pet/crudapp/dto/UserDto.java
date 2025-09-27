package org.synberg.pet.crudapp.dto;

public record UserDto(
        Long id,
        String name,
        String email
) {}
