package org.synberg.pet.crudapp.dto.create;

import jakarta.validation.constraints.*;

/**
 * DTO для передачи данных о создании нового пользователя.
 *
 * @param name имя пользователя
 * @param email email пользователя
 */
public record UserCreateDto(
        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email
) {}

