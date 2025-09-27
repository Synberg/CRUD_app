package org.synberg.pet.crudapp.dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для передачи данных об обновлении существующего пользователя.
 *
 * @param name имя пользователя
 * @param email email пользователя
 */
public record UserUpdateDto (
        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email
){}
